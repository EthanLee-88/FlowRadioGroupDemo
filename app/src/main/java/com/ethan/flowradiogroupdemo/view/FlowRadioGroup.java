package com.ethan.flowradiogroupdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

/*******
 * 自定义RadioGroup，实现自定义样式和流式布局
 * 也可以继承自ViewGroup，不过需重写generateLayoutParams()方法，以支持子View的margin属性
 * 继承自 ViewGroup 时不具备 RadioGroup 的特性，其他UI效果一样
 *
 * created by Ethan Lee
 * on 2021/2/15
 *******/

public class FlowRadioGroup extends RadioGroup {
    private static final String TAG = "FlowRadioGroup";
    //装所有的行
    private List<RowViews> mChildView = new ArrayList<>();
    //Adapter用于传递数
    private FlowAdapter mFlowAdapter;

    public FlowRadioGroup(Context context) {
        super(context);
    }

    public FlowRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setFlowAdapter(FlowAdapter flowAdapter) {
        if (flowAdapter == null) {
            throw new NullPointerException("FlowAdapter is null");
        }
        removeAllViews(); //清空所有子View
        this.mFlowAdapter = null;
        this.mFlowAdapter = flowAdapter;
        for (int i = 0; i < this.mFlowAdapter.getCount(); i ++){
            View childView = mFlowAdapter.getView(i, this);
            addView(childView);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //清空，onMeasure可能会被调用两次
        mChildView.clear();
        //装第一行的控件
        RowViews rawViews = new RowViews();
        mChildView.add(rawViews);

        // 计算ViewGroup的有效使用宽度。
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int noPaddingWidth = width - getPaddingLeft() - getPaddingRight();

        int childCount = getChildCount();
        // for 循环，通过测量子View计算出父布局尺寸
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            // 测量子View
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 获取子View的Margin参数
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();

            if (rawViews.getRawWidth() + child.getMeasuredWidth() + params.leftMargin + params.rightMargin > noPaddingWidth) {
                // 此行的空间已经无法摆放，要换行
                rawViews = new RowViews();
                mChildView.add(rawViews);
                rawViews.addView(child);
            } else {
                //不用换行，直接添加
                rawViews.addView(child);
            }
        }
        int height = getPaddingLeft() + getPaddingRight();
        for (int i = 0; i < mChildView.size(); i++) {  //循环累加高度
            height += mChildView.get(i).rawHeight;
        }

        if (getChildCount() == 0) {
            setMeasuredDimension(0, 0);
            return;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

//        super.onLayout(changed, l, t, r, b);
        int xBase = getPaddingLeft();  //每一个View开始的X位置
        int yBase = getPaddingTop();   //每一行开始的Y位置

        for (int i = 0; i < mChildView.size(); i++) {
            for (int j = 0; j < mChildView.get(i).getSize(); j++) {
                View child = mChildView.get(i).getViewList().get(j);
                MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
                // 循环计算每个子View上下左右位置，并摆放好
                int left = xBase + params.leftMargin;
                int top = yBase + params.topMargin;
                int right = left + child.getMeasuredWidth();
                int bottom = top + child.getMeasuredHeight();
                child.layout(left, top, right, bottom); // 还是摆放
                xBase += child.getMeasuredWidth() + params.leftMargin + params.rightMargin;//摆下一个view，X累加
            }
            xBase = getPaddingLeft(); //换行X清零
            yBase += mChildView.get(i).rawHeight;  //换行Y累加
        }
    }

    /**
     * 用于包装一行所有的子View及行宽高等参数
     */
    class RowViews {
        private int rawWidth = 0;
        private int rawHeight = 0;
        private List<View> viewList;

        public RowViews() {
            viewList = new ArrayList<>();
        }

        public int getRawHeight() {
            return rawHeight;
        }

        public int getRawWidth() {
            return rawWidth;
        }

        public int getSize() {
            return viewList.size();
        }

        public List<View> getViewList() {
            return viewList;
        }

        public void addView(View view) {
            if (!viewList.contains(view)) {
                MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
                // 宽度累加，还要加上左右margin值
                rawWidth += view.getMeasuredWidth() + params.leftMargin + params.rightMargin;
                // 行高取最高的子View高度
                rawHeight = Math.max(view.getMeasuredHeight() + params.topMargin + params.bottomMargin, rawHeight);
                // 添加view
                viewList.add(view);
                Log.d(TAG, "topMargin=" + params.topMargin + "-bottomMargin=" + params.bottomMargin);
                Log.d(TAG, "leftMargin=" + params.leftMargin + "-rightMargin=" + params.rightMargin);
            }
        }
    }

    /**
     * 使用Adapter设计模式添加数据
     */
    public static abstract class FlowAdapter {
       //确定数据长度
       public abstract int getCount();
       //获取View,通过position
        public abstract View getView(int position, ViewGroup parent);
    }

    //ViewGroup给子View指定MarginLayoutParams
    //使子View的xml支持margin属性
    //如果是继承自ViewGroup需要重写此方法
//    @Override
//    public LayoutParams generateLayoutParams(AttributeSet attrs) {
//        return new MarginLayoutParams(getContext(), attrs);
//    }

}
