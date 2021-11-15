package kr.carz.savecar.domain;

import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
public class DateTime {
    private Calendar cal;
    private String expected_day;
    private DateFormat df_date;
    private DateFormat df_time;
    private String today_date;
    private String today_time;
    private String after_expected_date;

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

}
