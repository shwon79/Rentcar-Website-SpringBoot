package kr.carz.savecar.dto;

import kr.carz.savecar.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReservationSaveDTO {

    private String name;
    private String phoneNo;
    private String detail;
    private String product;
    private String title;
    private String category1;
    private String category2;
    private String car_name;
    private String mileage;
    private String deposit;
    private String option;
    private String price;
    private String age_limit;
    private String car_num;
    private String region;
    private String resDate;
    private String carAge;

    public Reservation toEntity() {
        return Reservation.builder()
                .name(name)
                .phoneNo(phoneNo)
                .detail(detail)
                .title(title)
                .product(product)
                .category1(category1)
                .category2(category2)
                .car_name(car_name)
                .mileage(mileage)
                .deposit(deposit)
                .option(option)
                .price(price)
                .age_limit(age_limit)
                .car_num(car_num)
                .region(region)
                .resDate(resDate)
                .carAge(carAge)
                .build();

    }
}