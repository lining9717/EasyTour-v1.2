package com.example.lining.easytour;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class TimePicker extends LinearLayout {
    private WheelView mWheelMinute;
    private WheelView mWheelDay;
    private WheelView mWheelHour;
    private int mDay;
    private int mHour;
    private int mMinute;
    private int layout_id;
    private WheelView.OnSelectListener mMinuteListener = new WheelView.OnSelectListener() {
        @Override
        public void endSelect(int minute, String text) {
           mMinute = minute;
        }

        @Override
        public void selecting(int id, String text) {
        }
    };
    private WheelView.OnSelectListener mDayListener = new WheelView.OnSelectListener() {
        @Override
        public void endSelect(int day, String text) {
            mDay = day;
        }

        @Override
        public void selecting(int day, String text) {
        }
    };
    private WheelView.OnSelectListener mHourListener = new WheelView.OnSelectListener() {
        @Override
        public void endSelect(int hour, String text) {
            mHour = hour;
        }

        @Override
        public void selecting(int day, String text) {
        }
    };
    private Context mContext;

    public TimePicker(Context context) {
        this(context, null);
    }

    public TimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimePicker);
        int lenght = array.getIndexCount();
        for (int i = 0; i < lenght; i++) {
            int attr = array.getIndex(i);
            if (attr == R.styleable.TimePicker_layout_id) {
                layout_id = array.getResourceId(attr,0 );
            }
        }
        array.recycle();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContext = getContext();
        LayoutInflater.from(mContext).inflate(layout_id, this);
        mWheelMinute = (WheelView) findViewById(R.id.minute);
        mWheelDay = (WheelView) findViewById(R.id.day);
        mWheelHour = (WheelView) findViewById(R.id.hour);
        mWheelMinute.setOnSelectListener(mMinuteListener);
        mWheelDay.setOnSelectListener(mDayListener);
        mWheelHour.setOnSelectListener(mHourListener);
        setDate(System.currentTimeMillis());
        mWheelDay.setDefault(0);
        mWheelHour.setDefault(3);
        mWheelMinute.setDefault(7);
    }



    /**
     * set WLQQTimePicker date
     *
     * @param date
     */
    public void setDate(long date) {
        mWheelMinute.setData(getDayData());
        mWheelDay.setData(getYearData());
        mWheelHour.setData(getMonthData());
    }

    private ArrayList<String> getYearData() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("2018");
        list.add("2019");
        list.add("2020");
        list.add("2021");
        return list;
    }


    private ArrayList<String> getMonthData() {
        ArrayList<String> list = new ArrayList<String>();
        for(int i =1 ; i < 13;i++) {
            list.add("" + i);
        }
        return list;
    }

    private ArrayList<String> getDayData() {
        ArrayList<String> list = new ArrayList<String>();
        for(int i =1 ; i < 31;i++) {
            list.add("" + i);
        }
        return list;
    }

    public String toString() {
        Log.i("mDay","-------->"+mDay);
        Log.i("mDay","-------->"+mHour);
        Log.i("mDay","-------->"+mMinute);
        return getYearData().get(mDay) + "-" + getMonthData().get(mHour) + "-" + getDayData().get(mMinute);
    }
}