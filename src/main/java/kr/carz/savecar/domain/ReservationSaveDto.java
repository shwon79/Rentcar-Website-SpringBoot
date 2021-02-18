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
    private String category1;
    private String category2;
    private String car_name;
    private String mileage;

    public ReservationSaveDto(String name, String phoneNo, String detail, String product,
                              String category1, String category2, String car_name, String mileage) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.detail = detail;
        this.product = product;
        this.category1 = category1;
        this.category2 = category2;
        this.car_name = car_name;
        this.mileage = mileage;
    }

    public Reservation toEntity() {
        return Reservation.builder()
                .name(name)
                .phoneNo(phoneNo)
                .detail(detail)
                .product(product)
                .category1(category1)
                .category2(category2)
                .car_name(car_name)
                .mileage(mileage)
                .build();

    }
}
