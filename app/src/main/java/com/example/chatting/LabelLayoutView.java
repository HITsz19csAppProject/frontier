package com.example.chatting;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LabelLayoutView extends ViewGroup
{
    private String TAG = LabelLayoutView.class.getSimpleName();
    private List<LabelModel> modelArrayList = new ArrayList<>();
    private List<TextView> textViewList = new ArrayList<>();
    private int tVHorizontalSpace = 10;//TextView之间的水平间距
    private int tVVerticalSpace = 20;//竖直方向的间距
    private int tViewPaddingLeft = 0;
    private int tVViewPaddingRight = 0;
    private int tVViewTextSize = 0;//文字大小
    private int tVViewTextHeight = 0;//TextView的高度

    private int tVViewClickTextColor;//文字点中的颜色
    private int tVViewDefuTextColor;//文字默认的颜色

    private int tVViewDefuBackColor;//默认的颜色
    private int tVViewClickBackColor;//点中，背景色的修改

    private int edTextMaxInputLength = 6;//最大输入个数
    private int edTextWidth = 0;//输入框的宽度
    private boolean isNewAdd = false;//是否要含有+号
    private int tvAddTextWidth = 0;//定义加号的长度

    private Context mContext;

    private OnInputValueListener onInputValueListener;

    public void setOnInputValueListener(OnInputValueListener onInputValueListener) {
        this.onInputValueListener = onInputValueListener;
    }

    public void setOnInputValueListener(OnClickListener onClickListener) {
    }

    public interface OnInputValueListener {
        void onInoutItem(String value);
    }


    //获取点击中的
    public List<LabelModel> getChoiceModelList() {
        List<LabelModel> list = new ArrayList<>();
        for (LabelModel model : modelArrayList) {
            if (model.isClick()) {
                list.add(model);
            }
        }
        return list;
    }


    public List<TextView> getTextViewList() {
        return textViewList;
    }

    public LabelLayoutView(Context context) {
        this(context, null);
    }

    public LabelLayoutView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.LabelLayoutView);
        //定义默认的颜色
        tVViewDefuBackColor = mTypedArray.getResourceId(R.styleable.LabelLayoutView_tVViewDefuBackColor, R.drawable.rectanger_co50_solid_grayf7f7f7);
        tVViewClickBackColor = mTypedArray.getResourceId(R.styleable.LabelLayoutView_tVViewClickBackColor, R.drawable.rectanger_co50_solid_zie9e1f6);

        tVHorizontalSpace = mTypedArray.getDimensionPixelOffset(R.styleable.LabelLayoutView_tVHorizontalSpace, dip2px(14));
        tVVerticalSpace = mTypedArray.getDimensionPixelOffset(R.styleable.LabelLayoutView_tVVerticalSpace, dip2px(12));

        //定义默认的文字颜色
        tVViewDefuTextColor = mTypedArray.getColor(R.styleable.LabelLayoutView_tVViewDefuTextColor, getResources().getColor(R.color.colorAccent));
        //定义选中后的文字颜色
        tVViewClickTextColor = mTypedArray.getColor(R.styleable.LabelLayoutView_tVViewDefuTextColor, getResources().getColor(R.color.colorPrimaryDark));

        tViewPaddingLeft = mTypedArray.getDimensionPixelOffset(R.styleable.LabelLayoutView_tViewPaddingLeft, dip2px(12));
        tVViewPaddingRight = mTypedArray.getDimensionPixelOffset(R.styleable.LabelLayoutView_tVViewPaddingRight, dip2px(12));

        tVViewTextSize = mTypedArray.getDimensionPixelOffset(R.styleable.LabelLayoutView_tVViewTextSize, 20);
        tVViewTextHeight = mTypedArray.getDimensionPixelOffset(R.styleable.LabelLayoutView_tVViewTextHeight, dip2px(40));


        edTextWidth =mTypedArray.getDimensionPixelOffset(R.styleable.LabelLayoutView_edTextWidth,dip2px(87)) ;
        edTextMaxInputLength =mTypedArray.getInt(R.styleable.LabelLayoutView_edTextMaxInputLength,6);
        isNewAdd = mTypedArray.getBoolean(R.styleable.LabelLayoutView_isNewAdd,false);
        tvAddTextWidth =mTypedArray.getDimensionPixelOffset(R.styleable.LabelLayoutView_edTextWidth,dip2px(87)) ;
        mTypedArray.recycle();
    }

    public void setStringList(List<LabelModel> list) {
        if (list.size() > 0) {
            removeAllViews();
            this.textViewList.clear();
            this.modelArrayList.clear();
            this.modelArrayList.addAll(list);
            for (int i = 0; i < list.size(); i++) {
                LabelModel model = list.get(i);
                TextView textView = createTextView(i, model.getTextValue());
                addView(textView);
            }
        }
    }

    //创建TextView
    private TextView createTextView(int position, String string) {
        TextView textView = new TextView(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, tVViewTextHeight);
        textView.setLayoutParams(lp);
        textView.setPadding(tViewPaddingLeft, 0, tVViewPaddingRight, 0);
        textView.setText(string);
        textView.setTextColor(tVViewDefuTextColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, tVViewTextSize);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.rectanger_co50_solid_grayf7f7f7);//设置的是最底层的颜色
        textView.setTag(position);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = (int) v.getTag();
                LabelModel model = modelArrayList.get(i);
                if (model.isClick()) {
                    model.setClick(false);
                    textViewList.get(i).setBackgroundResource(tVViewDefuBackColor);
                    textViewList.get(i).setTextColor(tVViewDefuTextColor);
                } else {
                    model.setClick(true);
                    textViewList.get(i).setBackgroundResource(tVViewClickBackColor);
                    textViewList.get(i).setTextColor(tVViewClickTextColor);
                }
            }
        });
        textViewList.add(textView);
        return textView;
    }


    public int dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
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

