package kr.carz.savecar;

import kr.carz.savecar.repository.MonthlyRentRepository;
import kr.carz.savecar.repository.ReservationRepository;
import kr.carz.savecar.repository.YearlyRentRepository;
import kr.carz.savecar.service.MonthlyRentService;
import kr.carz.savecar.service.ReservationService;
import kr.carz.savecar.service.YearlyRentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final MonthlyRentRepository monthlyRentRepository;
    private final YearlyRentRepository yearlyRentRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public SpringConfig(MonthlyRentRepository monthlyRentRepository, YearlyRentRepository yearlyRentRepository, ReservationRepository reservationRepository) {
        this.monthlyRentRepository = monthlyRentRepository;
        this.yearlyRentRepository = yearlyRentRepository;
        this.reservationRepository = reservationRepository;
    }

    @Bean
    public MonthlyRentService monthlyRentService() {
        return new MonthlyRentService(monthlyRentRepository);
    }

    @Bean
    public YearlyRentService yearlyRentService() {
        return new YearlyRentService(yearlyRentRepository);
    }

    @Bean
    public ReservationService reservationService() { return new ReservationService(reservationRepository); }
}
