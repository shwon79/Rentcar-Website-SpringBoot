package kr.carz.savecar.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationSaveDto {

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

    public ReservationSaveDto(String name, String phoneNo, String detail, String product, String title,
                              String category1, String category2, String car_name, String mileage,
                              String deposit, String option) {
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
                .build();

    }
}
