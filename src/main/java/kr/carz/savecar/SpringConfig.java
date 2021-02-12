package kr.carz.savecar;

import kr.carz.savecar.repository.MonthlyRentRepository;
import kr.carz.savecar.service.MonthlyRentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final MonthlyRentRepository monthlyRentRepository;

    @Autowired
    public SpringConfig(MonthlyRentRepository monthlyRentRepository) {
        this.monthlyRentRepository = monthlyRentRepository;
    }

    @Bean
    public MonthlyRentService monthlyRentService() {
        return new MonthlyRentService(monthlyRentRepository);
    }
}
