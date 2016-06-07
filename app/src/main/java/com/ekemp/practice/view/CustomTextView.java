package com.ekemp.practice.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.ekemp.practice.R;

/**
 * Created by Administrator on 16-2-23.
 */
public class CustomTextView extends View {

    private String mCustomTextViewText;
    private int mCustomTextViewColor;
    private float mCustomTextViewSize;
    private Paint mPaint;
    private Rect mRect;

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 直接在XML中定义>style定义>由defStyleAttr和defStyleRes指定的默认值>直接在Theme中指定的值
     */
    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * 自定义CustomTextView的第二部获取属性值
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        /*
            4个参数的意思分别是：
　　　　     set：属性值的集合
　　　　     attrs：我们要获取的属性的资源ID的一个数组，如同ContextProvider中请求数据库时的Projection数组，就是从一堆属性中我们希望查询什么属性的值
　　　　     defStyleAttr：这个是当前Theme中的一个attribute，是指向style的一个引用，当在layout xml中和style中都没有为View指定属性时，会从Theme中这个attribute指向的Style中查找相应的属性值，这就是defStyle的意思，如果没有指定属性值，就用这个值，所以是默认值，但这个attribute要在Theme中指定，且是指向一个Style的引用，如果这个参数传入0表示不向Theme中搜索默认值
　　　　     defStyleRes：这个也是指向一个Style的资源ID，但是仅在defStyleAttr为0或defStyleAttr不为0但Theme中没有为defStyleAttr属性赋值时起作用
　　         链接中对这个函数说明勉强过得去，这里简要概括一下。对于一个属性可以在多个地方指定它的值，如XML直接定义，style，Theme，而这些位置定义的值有一个优先级，按优先级从高到低依次是：
             直接在XML中定义>style定义>由defStyleAttr和defStyleRes指定的默认值>直接在Theme中指定的值
         */
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTextView, defStyleAttr, 0);
        mCustomTextViewText = ta.getString(R.styleable.CustomTextView_CustomTextViewText);
        mCustomTextViewColor = ta.getColor(R.styleable.CustomTextView_CustomTextViewColor, Color.BLACK);
        mCustomTextViewSize = ta.getDimensionPixelSize(R.styleable.CustomTextView_CustomTextViewSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        ta.recycle();

        mPaint = new Paint();
        mPaint.setTextSize(mCustomTextViewSize);
        mRect = new Rect();
        mPaint.getTextBounds(mCustomTextViewText, 0, mCustomTextViewText.length(), mRect);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height ;
        if (widthMode == MeasureSpec.EXACTLY)
        {
            width = widthSize;
        } else
        {
            mPaint.setTextSize(mCustomTextViewSize);
            mPaint.getTextBounds(mCustomTextViewText, 0, mCustomTextViewText.length(), mRect);
            float textWidth = mRect.width();
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        } else
        {
            mPaint.setTextSize(mCustomTextViewSize);
            mPaint.getTextBounds(mCustomTextViewText, 0, mCustomTextViewText.length(), mRect);
            float textHeight = mRect.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.BLUE);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        mPaint.setColor(mCustomTextViewColor);
        canvas.drawText(mCustomTextViewText, getWidth() / 2 - mRect.width() / 2, getHeight() / 2 + mRect.height() / 2, mPaint);
    }
}
