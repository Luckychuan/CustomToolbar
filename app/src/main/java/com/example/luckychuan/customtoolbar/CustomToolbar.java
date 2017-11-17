package com.example.luckychuan.customtoolbar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by Luckychuan on 2017/11/17.
 */

public class CustomToolbar extends RelativeLayout {

    private static final String TAG = "CustomToolbar";

    public CustomToolbar(Context context) {
        super(context);
    }

    public CustomToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    /**
     * 当宽度和高是wrap_content时，自适应大小
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getAdaptiveWidth(widthMeasureSpec, getMeasuredWidth());
        int height = getAdaptiveHeight(MeasureSpec.getMode(heightMeasureSpec), getMeasuredHeight());
        setMeasuredDimension(width,height);
        Log.d(TAG, "onMeasure: width:" + width + " height:" + height);
        //Log.d(TAG, "onMeasure: size:"+MeasureSpec.getSize(widthMeasureSpec)+" width:"+width);
    }

    private int getAdaptiveWidth(int widthMeasureSpec, int width) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        //当属性是wrap_content时,让宽度自动充满父空间
        if (mode == MeasureSpec.AT_MOST) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }
        return width;
    }

    private int getAdaptiveHeight(int heightMeasureSpec, int height) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        //当属性是wrap_content时,让高度为56dp
        if (mode == MeasureSpec.AT_MOST) {
            height = dpToPx(56);
        }

        return height;
    }

    private int dpToPx(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}
