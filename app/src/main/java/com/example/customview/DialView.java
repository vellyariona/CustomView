package com.example.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.text.ListFormatter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

//menggambar circle diatas sebuah view canvas kotak
public class DialView extends View {
//    ada 4 angka, harus create label 4x dan butuh iterasi
    private static int SELECTION_COUNT = 4;
//    menghitung radius, menyimpan value otomatis
    private float mWidth;
    private  float mHeight;
//    menyimpan gambar dan komponen
    private Paint mTextPaint;
    private Paint mDialPaint;
    private float mRadius;
    private int mActivateSelection;
    private final StringBuffer mTempLabel = new StringBuffer(8);
//    untuk menyimpan koordinat x y atau titik2 label
    private final float[] mTempResult = new float[2];

    //inisialisasi paint
    private void init(){
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(40f);

        mDialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDialPaint.setColor(Color.GRAY);

        mActivateSelection = 0;

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivateSelection = (mActivateSelection + 1) % SELECTION_COUNT;
                if (mActivateSelection >= 1){
                    mDialPaint.setColor(Color.GREEN);
                }else{
                    mDialPaint.setColor(Color.GRAY);
                }
                invalidate();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mRadius = (float) (Math.min(mWidth, mHeight/2*0.8));
    }

//    setiap label dan lingkaran punya radius,
//    radius lingkaran jadi patokan
    private float [] computeXYForPosition(final int pos, final float radius){
        float[] result = mTempResult;
        Double startAngle = Math.PI * (9/8d);
        Double angle = startAngle + (pos * (Math.PI/4));
        result[0] = (float) (radius * Math.cos(angle)) + (mWidth/2);
        result[1] = (float) (radius * Math.sin(angle)) + (mHeight/2);
        return result;
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawCircle(mWidth/2, mHeight/2, mRadius, mDialPaint);

//        draw text
//        mencari radiusnya dulu, default di titik tengah, kalo geser harus ditambahkan
        final float labelRadius = mRadius + 20;
//        Log.d("RADIUS", String.valueOf(mRadius));
        StringBuffer label = mTempLabel;

        for (int i = 0; i< SELECTION_COUNT; i++){
            float [] xyData = computeXYForPosition(i, labelRadius);
            float x = xyData[0];
            float y = xyData[1];
            label.setLength(0);
            label.append(i);
            canvas.drawText(label, 0, label.length(), x, y , mTextPaint);
        }

//        draw mark circle
        final float markerRadius = mRadius - 35;
        float[] xyData = computeXYForPosition(mActivateSelection, markerRadius);
        float x = xyData[0];
        float y = xyData[1];

        canvas.drawCircle(x, y, 20, mTextPaint);

    }

    public DialView(Context context) {
        super(context);
        init();
    }

    public DialView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DialView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}
