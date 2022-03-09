package kr.carz.savecar.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

class ReservationControllerTest {

    @Value("${spring.datasource.username}")
    public String mysql_name;

    @Test
    public void moren_reserve() {
        String test = "2000km";
        System.out.println(test.split("km")[0]);
    }

    @Test
    public void get_application_properties_value() {
        System.out.println("test get_application_properties_value");
        System.out.println(mysql_name);
    }
}