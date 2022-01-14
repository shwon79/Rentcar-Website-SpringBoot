package kr.carz.savecar;

import kr.carz.savecar.repository.*;
import kr.carz.savecar.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final MonthlyRentRepository monthlyRentRepository;
    private final YearlyRentRepository yearlyRentRepository;
    private final ShortRentRepository shortRentRepository;
    private final CampingCarRepository campingCarRepository;
    private final ReservationRepository reservationRepository;
    private final TwoYearlyRentRepository twoYearlyRentRepository;
    private final DiscountRepository discountRepository;
    private final MorenReservationRepository morenReservationRepository;
    private final CampingCarPriceRepository campingCarPriceRepository;
    private final CalendarDateRepository calendarDateRepository;
    private final CalendarTimeRepository calendarTimeRepository;
    private final DateCampingRepository dateCampingRepository;
    private final CampingcarReservationRepository campingcarReservationRepository;
    private final ExplanationRepository explanationRepository;
    private final AdminRepository adminRepository;


    @Autowired
    public SpringConfig(MonthlyRentRepository monthlyRentRepository, YearlyRentRepository yearlyRentRepository,
                        ShortRentRepository shortRentRepository, CampingCarRepository campingCarRepository,
                        ReservationRepository reservationRepository, TwoYearlyRentRepository twoYearlyRentRepository,
                        DiscountRepository discountRepository,
                        MorenReservationRepository morenReservationRepository, CampingCarPriceRepository campingCarPriceRepository,
                        CalendarDateRepository calendarDateRepository, CalendarTimeRepository calendarTimeRepository,
                        DateCampingRepository dateCampingRepository, CampingcarReservationRepository campingcarReservationRepository,
                        ExplanationRepository explanationRepository, AdminRepository adminRepository) {
        this.monthlyRentRepository = monthlyRentRepository;
        this.yearlyRentRepository = yearlyRentRepository;
        this.shortRentRepository = shortRentRepository;
        this.campingCarRepository = campingCarRepository;
        this.reservationRepository = reservationRepository;
        this.twoYearlyRentRepository = twoYearlyRentRepository;
        this.discountRepository = discountRepository;
        this.morenReservationRepository = morenReservationRepository;
        this.campingCarPriceRepository = campingCarPriceRepository;
        this.calendarDateRepository = calendarDateRepository;
        this.calendarTimeRepository = calendarTimeRepository;
        this.dateCampingRepository = dateCampingRepository;
        this.campingcarReservationRepository = campingcarReservationRepository;
        this.explanationRepository = explanationRepository;
        this.adminRepository = adminRepository;
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

    @Bean
    public ShortRentService shortRentService() { return new ShortRentService(shortRentRepository); }

    @Bean
    public CampingCarService campingCarService() { return new CampingCarService(campingCarRepository); }

    @Bean
    public TwoYearlyRentService twoYearlyRentService() { return new TwoYearlyRentService(twoYearlyRentRepository); }

    @Bean
    public DiscountService discountService() { return new DiscountService(discountRepository); }

    @Bean
    public MorenReservationService morenReservationService() { return new MorenReservationService(morenReservationRepository); }

    @Bean
    public CampingCarPriceService campingCarVariableService() { return new CampingCarPriceService(campingCarPriceRepository); }

    @Bean
    public CalendarDateService calendarDateService() { return new CalendarDateService(calendarDateRepository); }

    @Bean
    public CalendarTimeService calendarTimeService() { return new CalendarTimeService(calendarTimeRepository); }

    @Bean
    public DateCampingService dateCampingService() { return new DateCampingService(dateCampingRepository); }

    @Bean
    public CampingcarReservationService campingcarReservationService() { return new CampingcarReservationService(campingcarReservationRepository); }

    @Bean
    public ExplanationService ExplanationService() { return new ExplanationService(explanationRepository); }

    @Bean
    public AdminService adminService() { return new AdminService(adminRepository); }
}
