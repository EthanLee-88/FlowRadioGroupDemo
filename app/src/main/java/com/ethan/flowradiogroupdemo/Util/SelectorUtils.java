package com.ethan.flowradiogroupdemo.Util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;

import static com.ethan.flowradiogroupdemo.ui.FlowApplication.mFlowApplication;

/**
 * Created by bruce on 18/10/09.
 */

public class SelectorUtils {

    private Context mContext;

    private SelectorUtils() {
        mContext = mFlowApplication;
    }

    public static SelectorUtils getInstance() {
        return GetInstance.mSelectorUtils;
    }

    private static class GetInstance {
        private final static SelectorUtils mSelectorUtils = new SelectorUtils();
    }

    /**
     * 获取Selector
     *
     * @param normalDraw
     * @param pressedDraw
     * @return
     */

    public StateListDrawable getSelector(Drawable normalDraw, Drawable pressedDraw) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, pressedDraw);
        stateListDrawable.addState(new int[]{}, normalDraw);
        return stateListDrawable;
    }

    public GradientDrawable getRectangleWithAroundCorner(int bgColor) {
        return getDrawable(15, 15, 15,
                15, bgColor, 1, Color.GRAY,
                GradientDrawable.RECTANGLE);
    }

    /**
     * 设置shape(设置单独圆角)
     *
     * @param topLeftCA
     * @param topRightCA
     * @param topRightCA
     * @param bottomRightCA
     * @param bgColor
     * @param strokeWidth
     * @param strokeColor
     * @return
     */
    public GradientDrawable getDrawable(float topLeftCA,
                                        float topRightCA,
                                        float bottomLeftCA,
                                        float bottomRightCA,
                                        int bgColor, int strokeWidth, int strokeColor,
                                        int shape) {
        //把边框值设置成dp对应的px
        strokeWidth = dp2px(this.mContext, strokeWidth);
        float[] circleAngleArr = {topLeftCA, topLeftCA, topRightCA, topRightCA,
                bottomLeftCA, bottomLeftCA, bottomRightCA, bottomRightCA};
        //把圆角设置成dp对应的px
        for (int i = 0; i < circleAngleArr.length; i++) {
            circleAngleArr[i] = dp2px(this.mContext, circleAngleArr[i]);
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(shape);//形状，如GradientDrawable.RECTANGLE
        gradientDrawable.setCornerRadii(circleAngleArr);//圆角
        gradientDrawable.setColor(bgColor); //背景色
        gradientDrawable.setStroke(strokeWidth, strokeColor); //边框宽度，边框颜色
        return gradientDrawable;
    }

    /**
     * 设置shape(设置无圆角的矩形)
     *
     * @param bgColor
     * @param storkeWidth
     * @param strokeColor
     * @return
     */
    public GradientDrawable getDrawableForNoneConor(int bgColor, int storkeWidth, int strokeColor, int bnWidth, int bnHeight) {
        //把边框值设置成dp对应的px
        storkeWidth = dp2px(this.mContext, storkeWidth);
        bnWidth = dp2px(this.mContext, bnWidth);
        bnHeight = dp2px(this.mContext, bnHeight);
        float[] circleAngleArr = {1, 1, 1, 1,
                1, 1, 1, 1};
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadii(circleAngleArr);
        gradientDrawable.setColor(bgColor); //背景色
        gradientDrawable.setSize(bnWidth, bnHeight);
        gradientDrawable.setStroke(storkeWidth, strokeColor); //边框宽度，边框颜色
        return gradientDrawable;
    }

    /**
     * 设置shape(圆形)
     *
     * @param radiu
     * @param bgColor
     * @param strokeWidth
     * @param strokeColor
     * @return
     */
    public GradientDrawable getDrawableForOval(int radiu, int bgColor, int strokeWidth, int strokeColor) {
        strokeWidth = dp2px(mContext, strokeWidth);
        radiu = dp2px(mContext, radiu);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setSize(2 * radiu, 2 * radiu);
        gradientDrawable.setShape(GradientDrawable.OVAL);
        gradientDrawable.setColor(bgColor);
        gradientDrawable.setStroke(strokeWidth, strokeColor);
        return gradientDrawable;
    }

    public static int dp2px(Context context, float dp) {
        return (int) (0.5F + dp * context.getResources().getDisplayMetrics().density);
    }

    public static float dpToPx(Context context, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
