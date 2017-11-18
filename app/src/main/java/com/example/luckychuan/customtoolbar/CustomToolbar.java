package com.example.luckychuan.customtoolbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Luckychuan on 2017/11/17.
 */

public class CustomToolbar extends RelativeLayout {

    private static final String TAG = "CustomToolbar";

    //onMeasure后高度的偏移量
    private int mHeightOffset;

    private String mTitle;
    private int mMenuId;
    private ImageButton mBackButton;
    private ImageButton mMenuButton;
    private TextView mTextView;

    public CustomToolbar(Context context) {
        super(context);
    }

    public CustomToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);

        //从xml获得属性大小
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar);
        mTitle = ta.getString(R.styleable.CustomToolbar_title);
        if (mTitle == null) {
            mTitle = getResources().getString(R.string.app_name);
        }
        mMenuId = ta.getResourceId(R.styleable.CustomToolbar_menu, 0);
        ta.recycle();

        //设置左右两个按钮
        mBackButton = new ImageButton(context);
        mBackButton.setId(R.id.backButton);
        LayoutParams backLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        backLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        backLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        backLayoutParams.setMargins(dpToPx(16), 0, 0, 0);
        mBackButton.setBackgroundResource(R.drawable.ripple_arrow_back);
        addView(mBackButton, backLayoutParams);

        mMenuButton = new ImageButton(context);
        LayoutParams menuLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        menuLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        menuLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        menuLayoutParams.setMargins(0, 0, dpToPx(16), 0);
        mMenuButton.setBackgroundResource(R.drawable.ripple_more);
        addView(mMenuButton, menuLayoutParams);

        //设置标题
        mTextView = new TextView(context);
        mTextView.setText(mTitle);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        mTextView.setTextColor(Color.WHITE);
        TextPaint paint = mTextView.getPaint();
        paint.setFakeBoldText(true);
        LayoutParams textViewLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textViewLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        textViewLayoutParams.addRule(RelativeLayout.RIGHT_OF, mBackButton.getId());
        textViewLayoutParams.setMargins(dpToPx(16), 0, 0, 0);
        addView(mTextView, textViewLayoutParams);


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
        int height = getAdaptiveHeight(heightMeasureSpec, getMeasuredHeight());
        setMeasuredDimension(width, height);
//        Log.d(TAG, "onMeasure: width:" + width + " height:" + height);
//        Log.d(TAG, "onMeasure: size:"+MeasureSpec.getSize(widthMeasureSpec)+" width:"+width);
    }

    private int getAdaptiveWidth(int widthMeasureSpec, int width) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        //当属性是wrap_content时,让宽度自动充满父空间
        if (mode == MeasureSpec.AT_MOST) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }
        return width;
    }

    /**
     * 重新给子控件布局，让子控件center_vertical
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (mHeightOffset > 0) {
            // 动态获取子View实例
            for (int i = 0, size = getChildCount(); i < size; i++) {
                View view = getChildAt(i);
                Log.d(TAG, "onLayout: " + mHeightOffset);
                //给子控件设置margin的值，让其居中
                view.layout(view.getLeft(), view.getTop() + mHeightOffset / 2, view.getRight(), view.getBottom() + mHeightOffset / 2);
            }
        }
    }

    private int getAdaptiveHeight(int heightMeasureSpec, int height) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        //当属性是wrap_content时,让高度为56dp
        if (mode == MeasureSpec.AT_MOST) {
            Log.d(TAG, "getAdaptiveHeight: ");
            mHeightOffset = dpToPx(56) - height;
            height = dpToPx(56);
        }

        return height;
    }

    private int dpToPx(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}
