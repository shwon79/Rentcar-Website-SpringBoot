package kr.carz.savecar.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTime {
    private static Calendar cal;
    private static String expected_day;
    private static DateFormat df_date;
    private static DateFormat df_time;
    private static String today_date;
    private static String today_time;
    private static String after_expected_date;

    public DateTime(String expected_day, DateFormat df_date, DateFormat df_time){
        this.expected_day = expected_day;
        this.df_date = df_date;
        this.df_time = df_time;
    }

    public void today(){
        this.cal = Calendar.getInstance();
        cal.setTime(new Date());
        this.today_date = this.df_date.format(cal.getTime());
        this.today_time = this.df_time.format(cal.getTime());
    }

    public void expected(){
        this.cal.add(Calendar.DATE, Integer.parseInt(this.expected_day));
        this.after_expected_date = this.df_time.format(cal.getTime());
    }

    public static Calendar getCal() {
        return cal;
    }

    public static void setCal(Calendar cal) {
        DateTime.cal = cal;
    }

    public static String getExpected_day() {
        return expected_day;
    }

    public static void setExpected_day(String expected_day) {
        DateTime.expected_day = expected_day;
    }

    public static DateFormat getDf_date() {
        return df_date;
    }

    public static void setDf_date(DateFormat df_date) {
        DateTime.df_date = df_date;
    }

    public static DateFormat getDf_time() {
        return df_time;
    }

    public static void setDf_time(DateFormat df_time) {
        DateTime.df_time = df_time;
    }

    public static String getToday_date() {
        return today_date;
    }

    public static void setToday_date(String today_date) {
        DateTime.today_date = today_date;
    }

    public static String getToday_time() {
        return today_time;
    }

    public static void setToday_time(String today_time) {
        DateTime.today_time = today_time;
    }

    public static String getAfter_expected_date() {
        return after_expected_date;
    }

    public static void setAfter_expected_date(String after_expected_date) {
        DateTime.after_expected_date = after_expected_date;
    }
}
