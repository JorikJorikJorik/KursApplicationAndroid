package com.example.jorik.kursapplicationandroid.ViewModel;

import android.app.TimePickerDialog;
import android.content.Context;
import android.databinding.Bindable;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.jorik.kursapplicationandroid.DataBase.ApplicationDataBase;
import com.example.jorik.kursapplicationandroid.Utils.CustomTimePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static android.widget.Toast.*;
import com.example.jorik.kursapplicationandroid.BR;

/**
 * Created by jorik on 06.06.16.
 */

public class SettingViewModel extends BaseViewModel {

    private static final int CONST_HOUR = 3600000;
    private static final int CONST_MINUTE = 60000;

    Context mContext;
    String timeStart;
    String timeRepeat;

    public SettingViewModel(Context context){
        mContext = context;
        getDataFromDataBase();
    }

    @Bindable
    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
        notifyPropertyChanged(BR.timeStart);
    }

    @Bindable
    public String getTimeRepeat() {
        return timeRepeat;
    }

    public void setTimeRepeat(String timeRepeat) {
        this.timeRepeat = timeRepeat;
        notifyPropertyChanged(BR.timeRepeat);
    }

    private void getDataFromDataBase() {
        ApplicationDataBase base = ApplicationDataBase.getInstance().getSelectDataBase();

        setTimeStart(timeInMillisecondToDate(base.getStartTime()));
        setTimeRepeat(timeInMillisecondToDate(base.getRepeatTime()));

    }

    public void writeTimeStartToBase() {
        ApplicationDataBase base = ApplicationDataBase.getInstance().getSelectDataBase();

        int [] timeStart = parseTime(base.getStartTime());

        CustomTimePickerDialog mTimePicker = new CustomTimePickerDialog(mContext, (TimePickerDialog.OnTimeSetListener) (timePicker, selectedHour, selectedMinute) -> {
            long time = selectedHour * CONST_HOUR + selectedMinute * CONST_MINUTE;
            base.setStartTime(time);
            base.save();
            setTimeStart(timeInMillisecondToDate(time));
        }, timeStart[0] , timeStart[1], android.text.format.DateFormat.is24HourFormat(mContext));

        mTimePicker.show();
    }

    public void writeTimeRepeatToBase(){
        ApplicationDataBase base = ApplicationDataBase.getInstance().getSelectDataBase();

        int [] timeRepeat = parseTime(base.getRepeatTime());

        CustomTimePickerDialog mTimePicker = new CustomTimePickerDialog(mContext, (TimePickerDialog.OnTimeSetListener) (timePicker, selectedHour, selectedMinute) -> {
            long time = selectedHour * CONST_HOUR + selectedMinute * CONST_MINUTE;
            base.setRepeatTime(time);
            base.save();
            setTimeRepeat(timeInMillisecondToDate(time));
        }, timeRepeat[0], timeRepeat[1], true);

        mTimePicker.show();
    }

    private String timeInMillisecondToDate(long time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return simpleDateFormat.format(calendar.getTime());
    }

    private int[] parseTime(long time){
        int timeMas [] = new int[2];
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.setTimeInMillis(time);
        timeMas[0] = calendar.get(Calendar.HOUR);
        timeMas[1] = calendar.get(Calendar.MINUTE);
        return timeMas;
    }
}
