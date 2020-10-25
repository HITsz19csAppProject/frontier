package com.example.chatting;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CustomView extends ViewGroup {
    private int mleftMargin=20;
    private int mtopMargin=20;
    private int tVVerticalSpace = 20;//竖直方向的间距
    private int tVHorizontalSpace = 10;//TextView之间的水平间距

    public CustomView(Context context) {
        this(context,null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.LabelLayoutView);
        tVHorizontalSpace = mTypedArray.getDimensionPixelOffset(R.styleable.LabelLayoutView_tVHorizontalSpace, dip2px(14));
        tVVerticalSpace = mTypedArray.getDimensionPixelOffset(R.styleable.LabelLayoutView_tVVerticalSpace, dip2px(12));
    }

    public int dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() <= 0) {
            setMeasuredDimension(0, 0);
            return;
        }
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int line = 1;
        int currentWidth = 0;
        int currentHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View textView = getChildAt(i);
            measureChild(textView, widthMeasureSpec, heightMeasureSpec);
            //这里要算出，高度的最大的那个，后面多加一个tVVerticalSpace，是希望底部与TextView有一定的距离
            currentHeight = Math.max(currentHeight, textView.getMeasuredHeight() + tVVerticalSpace);
            int textViewWidth = textView.getMeasuredWidth();

            //一行的最后一个 & 无法放下一个textView和一个间隔
            if (parentWidth - currentWidth < textViewWidth + tVHorizontalSpace) {
                if (parentWidth - currentWidth >= textViewWidth) {//去掉间隔，判断是否可以放的下
                    currentWidth = currentWidth + textViewWidth;
                } else {//放不下，就要去下一行进行存放
                    currentWidth = 0;
                    currentWidth = currentWidth + textViewWidth + tVHorizontalSpace;
                    line = line + 1;
                }
            } else {
                if (parentWidth - currentWidth >= textViewWidth + tVHorizontalSpace) {
                    currentWidth = currentWidth + textViewWidth + tVHorizontalSpace;
                }
            }
        }
        setMeasuredDimension(parentWidth, line * currentHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = 0;
        int top = 0;
        int parentWidth = this.getMeasuredWidth();
        for (int i = 0; i < getChildCount(); i++) {
            View textView = getChildAt(i);
            int childWidth = textView.getMeasuredWidth();
            int childHeight = textView.getMeasuredHeight();
            //一行的最后一个 & 无法放下一个textView和一个间隔
            if (parentWidth - left < childWidth + tVHorizontalSpace) {
                if (parentWidth - left >= childWidth) {//去掉间隔，判断是否可以放的下
                    textView.layout(left, top, left + childWidth, childHeight + top);
                    left = left + childWidth + tVHorizontalSpace;
                } else {
                    left = 0;
                    top = top + childHeight + tVVerticalSpace;
                    textView.layout(left, top, left + childWidth, childHeight + top);//从0位置开始绘制，绘制完后就会占位置，所以需要再加上绘制过的
                    left = left + childWidth + tVHorizontalSpace;
                }
            } else {
                textView.layout(left, top, left + childWidth, childHeight + top);
                left = left + childWidth + tVHorizontalSpace;
            }
        }
    }

}
