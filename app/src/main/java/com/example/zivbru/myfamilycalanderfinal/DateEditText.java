package com.example.zivbru.myfamilycalanderfinal;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by zivbru on 4/14/2016.
 */
public class DateEditText extends EditText {
    int year;
    int month;
    int day;
    OnDateSetListener listener;

    private void init(){
        Calendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public interface OnDateSetListener{
        void  dateSet(int year, int month, int day);
    }

    public void setOnDateSetListener(OnDateSetListener listener) {
        this.listener = listener;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public DateEditText(Context context) {
        super(context);
        init();
    }

    public DateEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DateEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            InnerDatePicker datePicker = new InnerDatePicker();
            datePicker.init(year, month, day);
            datePicker.setListener(new InnerDatePicker.Listener() {
                @Override
                public void done(int year, int month, int day) {
                    DateEditText.this.year = year;
                    DateEditText.this.month = month;
                    DateEditText.this.day = day;
                    setText("" + day + "/" + (++month) + "/" + year);
                    if (listener != null) {
                        listener.dateSet(year, month, day);
                    }
                }
            });
            datePicker.show(((Activity) getContext()).getFragmentManager(), "ziv");
        }
        return super.onTouchEvent(event);
    }

    public static class InnerDatePicker extends DialogFragment {
        int year;
        int month;
        int day;
        public void init(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        interface Listener{
            void done(int year, int month, int day);
        }
        Listener listener;
        public void setListener(Listener listener){
            this.listener = listener;
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    listener.done(year,monthOfYear,dayOfMonth);
                }
            },year,month,day);
            return dialog;

        }
    }

}

