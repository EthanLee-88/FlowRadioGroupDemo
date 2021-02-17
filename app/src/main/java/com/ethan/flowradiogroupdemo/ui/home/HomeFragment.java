package com.ethan.flowradiogroupdemo.ui.home;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ethan.flowradiogroupdemo.R;
import com.ethan.flowradiogroupdemo.Util.SelectorUtils;
import com.ethan.flowradiogroupdemo.view.FlowRadioGroup;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
    private FlowRadioGroup mFlowRadioGroup;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        mFlowRadioGroup = root.findViewById(R.id.my_flow_radio_group);
        homeViewModel.getText().observe(getViewLifecycleOwner(), (String s) -> {
            textView.setText(s);
        });
        initRes();
        return root;
    }

    private void initRes() {
        List<String> mItem = new ArrayList<>();
        mItem.add("钢铁侠");
        mItem.add("外太空的小浣熊");
        mItem.add("美国队长");
        mItem.add("古一法师");
        mItem.add("雷神");
        mItem.add("奇异博士");
        mItem.add("洛杉矶的黑寡妇");
        mItem.add("幻视");
        mItem.add("皇后区的小蜘蛛");
        mItem.add("绿巨人浩克");
        mItem.add("红绯女巫");
        mItem.add("鹰眼");
        mItem.add("布鲁克林的美队");
        mItem.add("蚁人");
        mFlowRadioGroup.setFlowAdapter(new FlowRadioGroup.FlowAdapter(){
            @Override
            public int getCount() {
                return mItem.size();
            }

            @Override
            public View getView(int position, ViewGroup parent) {
                View view = getRadioButton(mItem.get(position), parent);
                view.setOnClickListener((View v) -> {
                    Log.d(TAG, "RadioGetText=" + ((RadioButton)v).getText().toString() +
                            "-position=" + position);
                    Toast.makeText(getContext(), ((RadioButton)v).getText().toString(), Toast.LENGTH_SHORT).show();
                });
                return view;
            }
        });
    }

    private RadioButton getRadioButton(String text, ViewGroup parent){
        RadioButton radioButton = (RadioButton) LayoutInflater.from(getContext()).inflate(
                R.layout.radio_button_simple, parent, false);
        GradientDrawable drawableOne = SelectorUtils.getInstance().getRectangleWithAroundCorner(Color.GRAY);
        GradientDrawable drawableTwo = SelectorUtils.getInstance().getRectangleWithAroundCorner(Color.BLUE);
        StateListDrawable stateListDrawable = SelectorUtils.getInstance().getSelector(drawableOne, drawableTwo);
        radioButton.setText(text);
        radioButton.setBackground(stateListDrawable);
        return radioButton;
    }
}