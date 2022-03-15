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
    private final ReservationRepository reservationRepository;
    private final TwoYearlyRentRepository twoYearlyRentRepository;
    private final DiscountRepository discountRepository;
    private final MorenReservationRepository morenReservationRepository;
    private final CampingCarPriceRepository campingCarPriceRepository;
    private final CalendarDateRepository calendarDateRepository;
    private final CalendarTimeRepository calendarTimeRepository;
    private final DateCampingRepository dateCampingRepository;
    private final CampingcarReservationRepository campingcarReservationRepository;
    private final AdminRepository adminRepository;
    private final ImagesRepository imagesRepository;
    private final ValuesForWebRepository valuesForWebRepository;
    private final CampingCarPriceRateRepository campingCarPriceRateRepository;
    private final CampingCarMainTextRepository campingCarMainTextRepository;
    private final ReviewRepository reviewRepository;
    private final LongTermRentRepository longTermRentRepository;
    private final LongTermRentImageRepository longTermRentImageRepository;
    private final RealTimeRentCarRepository realTimeRentRepository;
    private final RealTimeRentCarImageRepository realTimeRentImageRepository;

    @Autowired
    public SpringConfig(MonthlyRentRepository monthlyRentRepository, YearlyRentRepository yearlyRentRepository,
                        ShortRentRepository shortRentRepository,
                        ReservationRepository reservationRepository, TwoYearlyRentRepository twoYearlyRentRepository,
                        DiscountRepository discountRepository,
                        MorenReservationRepository morenReservationRepository, CampingCarPriceRepository campingCarPriceRepository,
                        CalendarDateRepository calendarDateRepository, CalendarTimeRepository calendarTimeRepository,
                        DateCampingRepository dateCampingRepository, CampingcarReservationRepository campingcarReservationRepository,
                        AdminRepository adminRepository, ImagesRepository imagesRepository,
                        ValuesForWebRepository valuesForWebRepository, CampingCarPriceRateRepository campingCarPriceRateRepository,
                        CampingCarMainTextRepository campingCarMainTextRepository, ReviewRepository reviewRepository,
                        LongTermRentRepository longTermRentRepository, LongTermRentImageRepository longTermRentImageRepository,
                        RealTimeRentCarRepository realTimeRentRepository, RealTimeRentCarImageRepository realTimeRentImageRepository) {
        this.monthlyRentRepository = monthlyRentRepository;
        this.yearlyRentRepository = yearlyRentRepository;
        this.shortRentRepository = shortRentRepository;
        this.reservationRepository = reservationRepository;
        this.twoYearlyRentRepository = twoYearlyRentRepository;
        this.discountRepository = discountRepository;
        this.morenReservationRepository = morenReservationRepository;
        this.campingCarPriceRepository = campingCarPriceRepository;
        this.calendarDateRepository = calendarDateRepository;
        this.calendarTimeRepository = calendarTimeRepository;
        this.dateCampingRepository = dateCampingRepository;
        this.campingcarReservationRepository = campingcarReservationRepository;
        this.adminRepository = adminRepository;
        this.imagesRepository = imagesRepository;
        this.valuesForWebRepository = valuesForWebRepository;
        this.campingCarPriceRateRepository = campingCarPriceRateRepository;
        this.campingCarMainTextRepository = campingCarMainTextRepository;
        this.reviewRepository = reviewRepository;
        this.longTermRentRepository = longTermRentRepository;
        this.longTermRentImageRepository = longTermRentImageRepository;
        this.realTimeRentRepository = realTimeRentRepository;
        this.realTimeRentImageRepository = realTimeRentImageRepository;
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
    public AdminService adminService() { return new AdminService(adminRepository); }

    @Bean
    public ImagesService imagesService() { return new ImagesService(imagesRepository); }

    @Bean
    public ValuesForWebService valuesForWebService() { return new ValuesForWebService(valuesForWebRepository); }

    @Bean
    public CampingCarPriceRateService campingCarPriceRateService() { return new CampingCarPriceRateService(campingCarPriceRateRepository); }

    @Bean
    public CampingCarMainTextService campingCarMainTextService() { return new CampingCarMainTextService(campingCarMainTextRepository); }

    @Bean
    public ReviewService reviewService() { return new ReviewService(reviewRepository); }

    @Bean
    public LongTermRentService longTermRentService() { return new LongTermRentService(longTermRentRepository); }

    @Bean
    public LongTermRentImageService longTermRentImageService() { return new LongTermRentImageService(longTermRentImageRepository); }

    @Bean
    public RealTimeRentCarService realTimeRentService() { return new RealTimeRentCarService(realTimeRentRepository); }

    @Bean
    public RealTimeRentCarImageService realTimeRentImageService() { return new RealTimeRentCarImageService(realTimeRentImageRepository); }
}
