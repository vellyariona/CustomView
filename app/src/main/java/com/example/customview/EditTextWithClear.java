package com.example.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;

public class EditTextWithClear extends AppCompatEditText {

    Drawable mClearButtonImage;

    private void init() {
//        menggambil drawable transparan untuk diinit disemua constructor
        mClearButtonImage = ResourcesCompat.getDrawable(
                getResources(), R.drawable.ic_clear_opaque_24dp, null);

    addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//        untuk ketika teks sudah terisi maka button baru muncul
            showClearButton();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    });

    setOnTouchListener(new OnTouchListener() {
//        mendeteksi koordinat xnya dimana, jadi ketika ditouch akan mendeteksi koordinatnya
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if(getCompoundDrawables()[2] != null){
//                jika clear buttonnya ada:
//                hitungan untuk mennghitung lebar total-padding-lebar dari button
//                untuk mendapat koordinat panjang
                float clearButtonStart = (getWidth()-getPaddingEnd()-mClearButtonImage.getIntrinsicWidth());
                boolean isClearButtonClicked = false;
//                untuk mendeteksi touch
                if (motionEvent.getX() > clearButtonStart){
                    isClearButtonClicked = true;
                }

                if (isClearButtonClicked){

                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                        down ketika menekan
                        mClearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_black_24dp, null);
                        showClearButton();
                    }
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP){
//                        up itu ketika melepas
                        mClearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_opaque_24dp, null);
                        showClearButton();
                        getText().clear();
                        hideClearButton();
                        return true;
                    }
                }
            }
            return false;
        }
    });
    }

    public EditTextWithClear(@NonNull Context context) {
        super(context);
        init();
    }

    public EditTextWithClear(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextWithClear(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void showClearButton(){
//        menempelkan komponen dengan posisi sebelah kanan sesuai parameternya
        setCompoundDrawablesWithIntrinsicBounds(null, null, mClearButtonImage, null);
    }

    private void hideClearButton(){
//        karena mau hide, maka dibuat null semua
        setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }

}
