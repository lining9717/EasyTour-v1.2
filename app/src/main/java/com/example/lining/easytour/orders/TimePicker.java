package com.example.lining.easytour.orders;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.lining.easytour.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TimePicker extends LinearLayout {
    private WheelView mWheelDay;
    private WheelView mWheelYear;
    private WheelView mWheelMonth;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int layout_id;
    private Calendar calendar;
    private WheelView.OnSelectListener mDayListener = new WheelView.OnSelectListener() {
        @Override
        public void endSelect(int day, String text) {
           mDay = day;
        }

        @Override
        public void selecting(int day, String text) {
        }
    };
    private WheelView.OnSelectListener mYearListener = new WheelView.OnSelectListener() {
        @Override
        public void endSelect(int year, String text) {
            mYear = year;
        }

        @Override
        public void selecting(int year, String text) {
        }
    };
    private WheelView.OnSelectListener mMonthListener = new WheelView.OnSelectListener() {
        @Override
        public void endSelect(int month, String text) {
            mMonth = month;
            String select = getMonthData().get(mMonth);
            int selectmonth = Integer.parseInt((select).substring(select.length()-1));
            int currentmonth = calendar.get(Calendar.MONTH)+1;
            if(selectmonth == currentmonth){
                mWheelDay.setData(getDayData());
                mWheelDay.setDefault(0);
                return;
            }
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH, selectmonth-1);
            int MaxDay=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            ArrayList<String> list = new ArrayList<String>();
            for(int i=1;i<=MaxDay;i++) {
                if (i < 10)
                    list.add("0" + i);
                else
                    list.add(i + "");
            }
            mWheelDay.setData(list);
            mWheelDay.setDefault(0);
        }

        @Override
        public void selecting(int month, String text) {
        }
    };
    private Context mContext;

    public TimePicker(Context context) {
        this(context, null);
        calendar = Calendar.getInstance();

    }

    public TimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        calendar = Calendar.getInstance();
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
        mWheelDay = (WheelView) findViewById(R.id.day);
        mWheelYear = (WheelView) findViewById(R.id.year);
        mWheelMonth = (WheelView) findViewById(R.id.month);
        mWheelDay.setOnSelectListener(mDayListener);
        mWheelYear.setOnSelectListener(mYearListener);
        mWheelMonth.setOnSelectListener(mMonthListener);
        setDate(System.currentTimeMillis());
        mWheelYear.setDefault(0);
        mWheelMonth.setDefault(0);
        mWheelDay.setDefault(0);
    }



    /**
     * set WLQQTimePicker date
     *
     * @param date
     */
    public void setDate(long date) {
        mWheelDay.setData(getDayData());
        mWheelYear.setData(getYearData());
        mWheelMonth.setData(getMonthData());
    }

    private ArrayList<String> getYearData() {
        ArrayList<String> list = new ArrayList<String>();
        for(int i=0;i<4;i++){
            list.add((calendar.get(Calendar.YEAR)+i)+"");
        }
        return list;
    }


    private ArrayList<String> getMonthData() {
        ArrayList<String> list = new ArrayList<String>();
        int month = calendar.get(Calendar.MONTH)+1;
        for(int i=month;i<=12;i++){
            if(i<10)
                list.add("0"+i);
            else
                list.add(i+"");
        }
        return list;
    }

    private ArrayList<String> getDayData() {
        ArrayList<String> list = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int daysOfMonth = 31;
        try {
            daysOfMonth = getDaysOfMonth(sdf.parse(sdf.format(calendar.getTime())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for(int i=day;i<=daysOfMonth;i++) {
            if (i < 10)
                list.add("0" + i);
            else
                list.add(i + "");
        }
        return list;
    }

    public String toString() {
        //如果是当前月份
        if(getMonthData().get(mMonth).equals("0"+(calendar.get(Calendar.MONTH)+1)))
            return getYearData().get(mYear) + "-" + getMonthData().get(mMonth) + "-" + getDayData().get(mDay);
        //不是当前月份
        String select = getMonthData().get(mMonth);
        int selectmonth = Integer.parseInt((select).substring(select.length()-1));
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, selectmonth-1);
        int MaxDay=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        ArrayList<String> list = new ArrayList<String>();
        for(int i=1;i<=MaxDay;i++) {
            if (i < 10)
                list.add("0" + i);
            else
                list.add(i + "");
        }
        return getYearData().get(mYear) + "-" + getMonthData().get(mMonth) + "-" + list.get(mDay);
    }

    public int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}