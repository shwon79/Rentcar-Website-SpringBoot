package kr.carz.savecar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "CampingcarDateTime")
public class CampingcarDateTime extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false)
    private String rentDate;

    @Column(nullable = false)
    private String rentTime;

    @Column(nullable = true)
    private String returnDate;

    @Column(nullable = true)
    private String returnTime;


//    @Column(nullable = false)
//    private String name;
//
//    @Column(nullable = false)
//    private String phone;
//
//    @Column(columnDefinition = "LONGTEXT", nullable = true)
//    private String detail;
//
//    @Column(nullable = true)
//    private String desitor;
//
//
//    @Column(nullable = false)
//    private Integer total;
//
//    @Column(nullable = false)
//    private Integer deposit;
//
//    @Column(nullable = true)
//    private String agree;



    @Builder
    public CampingcarDateTime(String rentDate, String rentTime, String returnDate, String returnTime) {
        this.rentDate = rentDate;
        this.rentTime = rentTime;
        this.returnDate = returnDate;
        this.returnTime = returnTime;
    }
}
