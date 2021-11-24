package kr.carz.savecar.domain;

import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
public class DateTime {
    private static Calendar cal;
    private static String expected_day;
    private static DateFormat df_date;
    private static DateFormat df_time;
    private static String today_date;
    private static String today_time;
    private static String after_expected_date;

    public DateTime(String expected_day){
        this.expected_day = expected_day;
        this.df_date = new SimpleDateFormat("yyyy-MM-dd");;
        this.df_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;
    }

    public static String today_date_only(){
        cal = Calendar.getInstance();
        cal.setTime(new Date());
        today_date = df_date.format(cal.getTime());

        return today_date;
    }

    public static String today_date_and_time(){
        cal = Calendar.getInstance();
        cal.setTime(new Date());
        today_time = df_time.format(cal.getTime());

        return today_time;
    }

    public static String expected(){
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, Integer.parseInt(expected_day));
        after_expected_date = df_date.format(cal.getTime());

        return after_expected_date;
    }

}
