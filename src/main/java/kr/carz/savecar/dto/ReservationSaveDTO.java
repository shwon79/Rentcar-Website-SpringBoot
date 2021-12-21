package kr.carz.savecar.dto;

import kr.carz.savecar.domain.Reservation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    public ReservationSaveDTO(String name, String phoneNo, String detail, String product, String title,
                              String category1, String category2, String car_name, String mileage,
                              String deposit, String option, String price, String age_limit,String car_num,
                              String region, String resDate, String carAge) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.detail = detail;
        this.title = title;
        this.product = product;
        this.category1 = category1;
        this.category2 = category2;
        this.car_name = car_name;
        this.mileage = mileage;
        this.deposit = deposit;
        this.option = option;
        this.price = price;
        this.age_limit = age_limit;
        this.car_num = car_num;
        this.region = region;
        this.resDate = resDate;
        this.carAge = carAge;
    }

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