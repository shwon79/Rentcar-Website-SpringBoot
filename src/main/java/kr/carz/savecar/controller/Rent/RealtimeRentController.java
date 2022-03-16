package kr.carz.savecar.controller.Rent;

import kr.carz.savecar.controller.ReservationController;
import kr.carz.savecar.controller.Utils.HttpConnection;
import kr.carz.savecar.dto.*;
import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ArrayUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
public class RealtimeRentController {

    private final MonthlyRentService monthlyRentService;
    private final YearlyRentService yearlyRentService;
    private final TwoYearlyRentService twoYearlyRentService;
    private final DiscountService discountService;
    private final MorenReservationService morenReservationService;
    private final ReservationController reservationController;
    private final RealTimeRentCarService realTimeRentService;
    private final RealTimeRentCarImageService realTimeRentImageService;

    @Autowired
    public RealtimeRentController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService, TwoYearlyRentService twoYearlyRentService,
                                  DiscountService discountService, MorenReservationService morenReservationService, ReservationController reservationController,
                                  RealTimeRentCarService realTimeRentService, RealTimeRentCarImageService realTimeRentImageService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.twoYearlyRentService = twoYearlyRentService;
        this.discountService = discountService;
        this.morenReservationService = morenReservationService;
        this.reservationController = reservationController;
        this.realTimeRentService = realTimeRentService;
        this.realTimeRentImageService = realTimeRentImageService;
    }

    /* ======================================================================================== */
    /*                             [New 버전] 실시간 월렌트 예약하기                                */
    /* ======================================================================================== */

    @Value("${phone.admin1}")
    private String admin1;

    @Value("${phone.admin2}")
    private String admin2;

    @Value("${phone.admin3}")
    private String admin3;

    @Value("${moren_url}")
    private String moren_url_except_date;

    @Value("${moren.expected_day}")
    private String expected_day;

    // 모렌 대기차 DB로 저장
    @Scheduled(cron = "0 0/10 * * * *")
    public void rent_month_save() {

        realTimeRentImageService.deleteAllInBatch();
        realTimeRentService.deleteAllInBatch();

        String moren_url = moren_url_except_date + DateTime.today_date_only() + "&END=" + DateTime.today_date_only() + "&EXPECTED_DAY=" + expected_day;

        HttpConnection http = new HttpConnection();
        JSONObject responseJson = http.sendGetRequest(moren_url);
        JSONArray list_json_array = (JSONArray) responseJson.get("list");

        long currentRealTimeRentIdx = 1, currentRealTimeRentImageIdx = 1;
        for(int i=0; i<list_json_array.length(); i++){

            JSONObject morenObject = (JSONObject)list_json_array.get(i);
            String order_end = ((String)morenObject.get("order_end"));
            Long carOld = Long.parseLong((String)morenObject.get("carOld"));

            if (!morenObject.get("reserve").equals(null)) {
                continue;
            }

            String carCategory = (String) morenObject.get("carCategory");
            if(carCategory.equals("레이")){
                String carName = (String) morenObject.get("carName");
                for(int index=0; index<carName.length(); index++){
                    if(carName.charAt(index) == '밴'){
                        carCategory = "레이밴";
                        break;
                    }
                }
            }

            int isExpected = -1;
            // n일 이내 반납예정차량
            if ( (Integer)morenObject.get("order_status") == 2 &&
                    order_end.length() >= 19 &&
                    order_end.substring(0, 19).compareTo(DateTime.today_date_and_time()) >= 0 &&  // 시간 고려해서
                    order_end.substring(0, 10).compareTo(DateTime.expected()) <= 0 // 날짜만 고려해서
                ) {
                    isExpected = 1;
                }
            // 현재 가능차
            else if ((Integer)morenObject.get("order_status") == 0){
                    isExpected = 0;
            } else {
                    continue;
            }

            Optional<MonthlyRent> monthlyRentWrapper = monthlyRentService.findByMorenCar(carOld, carOld, carCategory);

            if(monthlyRentWrapper.isPresent()) {
                MonthlyRent monthlyRent = monthlyRentWrapper.get();

                Optional<Discount> discount_object = discountService.findDiscountByCarNo((String) morenObject.get("carNo"));
                double discount_price = 0;
                String discount_description = null;
                if (discount_object.isPresent()) {
                    discount_price = discount_object.get().getDiscount();
                    discount_description = discount_object.get().getDescription();
                }

                RealTimeRentCar realTimeRent = new RealTimeRentCar(currentRealTimeRentIdx++, monthlyRent, (String) morenObject.get("carIdx"), carCategory,
                        (String) morenObject.get("carName"), (String) morenObject.get("carDetail"),(String) morenObject.get("carNo"),
                        (String) morenObject.get("carExteriorColor"), (String) morenObject.get("carGubun"),(String) morenObject.get("carDisplacement"),
                        (String) morenObject.get("carMileaget"), (String) morenObject.get("carColor"),(String) morenObject.get("carOld"),
                        (String) morenObject.get("carEngine"), (String) morenObject.get("carAttribute01"),(String) morenObject.get("order_end"),
                        monthlyRent.getCost_per_km(), (String) morenObject.get("carCode"), discount_price,
                        discount_description, isExpected);

                realTimeRentService.save(realTimeRent);

                if(!morenObject.get("carThumbImages").equals(null)) {

                    JSONArray carJsonArray = (JSONArray) (morenObject.get("carThumbImages"));
                    for (int j = 0; j < carJsonArray.length(); j++) {
                        RealTimeRentCarImage realTimeRentImage = new RealTimeRentCarImage(currentRealTimeRentImageIdx++, realTimeRent, (String) carJsonArray.get(j));
                        realTimeRentImageService.save(realTimeRentImage);
                    }
                }
            } else {
                System.out.println("Error ! 문제 발생 ! 차량이름 : " + carCategory + ", " + morenObject.get("carNo"));
            }
        }
    }



    @GetMapping("/rent/month/new")
    public String rent_month(ModelMap model) {

        List<RealTimeRentCar> morenDTOList = realTimeRentService.findByIsExpected(0);
        List<RealTimeRentCar> morenDTOListExpected = realTimeRentService.findByIsExpected(1);

        RealTimeDTO realTimeDTO = new RealTimeDTO("전체", "2000km", "한달");

        model.put("morenDTOList", morenDTOList);
        model.put("morenDTOListExpected", morenDTOListExpected);
        model.put("realTimeDTO", realTimeDTO);
        model.put("byCarName",  Comparator.comparing(RealTimeRentCar::getCarName));
        model.put("byOrderEnd",  Comparator.comparing(RealTimeRentCar::getOrderEnd));

        return "rent_month/main";
    }


    // TODO: 기존 DTO 지우기
    // 조건별로 차종 데이터 전달
    @GetMapping("/rent/month/lookup/{carType}/{kilometer}/{rentTerm}")
    public String rent_month_lookup(ModelMap model, @PathVariable String carType, @PathVariable String kilometer, @PathVariable String rentTerm) {

        List<RealTimeRentCar> morenDTOList = realTimeRentService.findByCarGubunAndIsExpected(carType, 0);
        List<RealTimeRentCar> morenDTOListExpected = realTimeRentService.findByCarGubunAndIsExpected(carType,1);

        model.put("morenDTOList", morenDTOList);
        model.put("morenDTOListExpected", morenDTOListExpected);

        // 한달 <-> 12개월, 24개월 : 약정 주행거리 디폴트  설정
        String [] above_year_field = {"12개월", "24개월"};
        String [] yearly_kilometer_field = {"20000km", "30000km", "40000km", "기타_long"};
        String [] monthly_kilometer_field = {"2000km", "2500km", "3000km", "4000km", "기타"};
        String kilometerUpdated = kilometer;

        if (ArrayUtils.contains(above_year_field, rentTerm) ){
            if (!ArrayUtils.contains(yearly_kilometer_field, kilometer) ){
                kilometerUpdated = "20000km";
            }
        } else if (rentTerm.equals("한달")) {
            if(!ArrayUtils.contains(monthly_kilometer_field, kilometer)){
                kilometerUpdated = "2000km";
            }
        }
        RealTimeDTO realTimeDTO = new RealTimeDTO(carType, kilometerUpdated, rentTerm);

        model.put("realTimeDTO", realTimeDTO);
        model.put("byCarName",  Comparator.comparing(RealTimeRentCar::getCarName));
        model.put("byOrderEnd",  Comparator.comparing(RealTimeRentCar::getOrderEnd));

        return "rent_month/main";
    }


    // 차량 상세 페이지
    @RequestMapping(value = "/rent/month/detail/{rentTerm}/{carIdx}/{rentIdx}/{kilometer}/{discount}/{rentStatus}", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    public String rent_month_detail(HttpServletResponse res, ModelMap model, @PathVariable String carIdx,@PathVariable String rentTerm, @PathVariable Long rentIdx, @PathVariable String kilometer,  @PathVariable double discount,@PathVariable String rentStatus) throws IOException {

        String cost_per_km = null;
        String credit = null;
        String deposit = null;

        String moren_url = moren_url_except_date + DateTime.today_date_only() + "&END=" + DateTime.today_date_only() + "&EXPECTED_DAY=" + expected_day;

        List<String> carList = new ArrayList<>();
        Optional<MonthlyRent> monthlyRentOptional = monthlyRentService.findById(rentIdx);
        if(monthlyRentOptional.isPresent()) {
            MonthlyRent monthlyRent = monthlyRentOptional.get();
            model.put("monthlyRent", monthlyRent);

            // 세이브카 db에서 해당 차 객체 가져오기
            switch (rentTerm) {
                case "한달":
                    carList.add(monthlyRent.getImg_url());
                    cost_per_km = monthlyRent.getCost_per_km();
                    credit = monthlyRent.getCredit();
                    deposit = monthlyRent.getDeposit();
                    model.put("priceObject", monthlyRent);
                    break;
                case "12개월":
                    Optional<YearlyRent> yearlyRentOptional = yearlyRentService.findById(rentIdx);
                    YearlyRent yearlyRent = yearlyRentOptional.get();
                    carList.add(yearlyRent.getImg_url());
                    cost_per_km = yearlyRent.getCost_per_km();
                    credit = yearlyRent.getCredit();
                    deposit = yearlyRent.getDeposit();
                    model.put("priceObject", yearlyRent);
                    break;
                case "24개월":
                    Optional<TwoYearlyRent> twoYearlyRentOptional = twoYearlyRentService.findById(rentIdx);

                    if (!twoYearlyRentOptional.isPresent()) {
                        res.setContentType("text/html; charset=UTF-8");
                        PrintWriter out = res.getWriter();
                        out.println("<script>alert('24개월 이용 불가한 차량입니다.'); </script>");
                        out.flush();

                        carList.add(monthlyRent.getImg_url());
                        cost_per_km = monthlyRent.getCost_per_km();
                        credit = monthlyRent.getCredit();
                        deposit = monthlyRent.getDeposit();
                        model.put("priceObject", monthlyRent);

                        rentTerm = "한달";
                        kilometer = "2000km";
                    } else {
                        TwoYearlyRent twoYearlyRent = twoYearlyRentOptional.get();
                        carList.add(twoYearlyRent.getImg_url());
                        cost_per_km = twoYearlyRent.getCost_per_km();
                        credit = twoYearlyRent.getCredit();
                        deposit = twoYearlyRent.getDeposit();
                        model.put("priceObject", twoYearlyRent);
                    }
                    break;
            }
        }

        HttpConnection http = new HttpConnection();
        JSONObject responseJson = http.sendGetRequest(moren_url);
        JSONArray list_json_array = (JSONArray) responseJson.get("list");

        for(int i=0; i<list_json_array.length(); i++){

            JSONObject morenObject = (JSONObject)list_json_array.get(i);

            if(morenObject.get("carIdx").equals(carIdx)){

                if (!morenObject.get("carThumbImages").equals(null)) {
                    carList = new ArrayList<>();

                    JSONArray carJsonArray = (JSONArray) (morenObject.get("carThumbImages"));
                    for (int j = 0; j < carJsonArray.length(); j++) {
                        carList.add((String) carJsonArray.get(j));
                    }
                }

                MorenDTO morenDto = new MorenDTO((String) morenObject.get("carIdx"), (String) morenObject.get("carCategory"), (String) morenObject.get("carName"), (String) morenObject.get("carDetail"),
                        (String) morenObject.get("carNo"), (String) morenObject.get("carExteriorColor"), (String) morenObject.get("carGubun"),
                        (String) morenObject.get("carDisplacement"), (String) morenObject.get("carMileaget"), (String) morenObject.get("carColor"),
                        (String) morenObject.get("carOld"), (String) morenObject.get("carEngine"), (String) morenObject.get("carAttribute01"),
                        0, (String) morenObject.get("order_end"), rentIdx, carList, discount, null, cost_per_km,
                        credit, (String) morenObject.get("carCode"), kilometer, deposit, rentTerm, null);

                model.put("morenDto", morenDto);
                break;

            }
        }

        model.put("today_format", DateTime.today_date_only());
        model.put("rentStatus", rentStatus);

        return "rent_month/detail";
    }


    // 예약신청하기 새 창 띄우기
    @PostMapping("/rent/month/detail/form/reservation")
    public String rent_month_detail_form_reservation(ModelMap model, @ModelAttribute MorenDTO morenDTO) {

        model.put("morenDTO",morenDTO);
        model.put("today_format",DateTime.today_date_only());

        return "rent_month/reservation_form";
    }


    // 견적서보기 새 창 띄우기
    @PostMapping("/rent/month/detail/form/estimate")
    public String rent_month_detail_form_estimate(ModelMap model, @ModelAttribute MorenDTO morenDTO) {
        model.put("morenDTO",morenDTO);

        return "rent_month/estimate_form";
    }



    //    @RequestMapping(value = "/rent/month/moren/reservation", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @PostMapping("/rent/month/moren/reservation")
    @ResponseBody
    public void moren_reservation(@RequestBody MorenReservationDTO dto) {

        Long reservationId = morenReservationService.saveDTO(dto);

        reservationController.send_message(admin1+", "+admin2+", "+admin3, dto.getReservationPhone(),
                "[현재 대여가능차량 예약이 신청되었습니다.]\n"
                        + "▼ 계약 확인하기" + "\n"
                        + "https://savecar.kr/admin/moren/reservation/detail/" + reservationId + "\n\n"

                        + "▼ 문의자 정보" + "\n"
                        + "문의자 이름: " + dto.getReservationName() + "\n"
                        + "연락처: " + dto.getReservationPhone() + "\n"
                        + "보험연령: " + dto.getSelectAge() + "\n"
                        + "생년월일: " + dto.getReservationAge() + "\n\n"

                        + "▼ 차량 정보" + "\n"
                        + "차량명: " + dto.getCarName() + "\n"
                        + "차량번호: " + dto.getCarNo() + "\n\n"

                        + "▼ 대여 정보" + "\n"
                        + "대여일자: " + dto.getReservationDate() + "\n"
                        + "대여시간: " + dto.getReservationTime() + "\n"
                        + "렌트기간: " + dto.getRentTerm() + "\n"
                        + "약정주행거리: " + dto.getKilometer() + "\n"
                        + "방문/배차: " + dto.getPickupPlace() + "\n"
                        + "배차요청주소: " + dto.getAddress() + "\n"
                        + "배차요청상세주소: " + dto.getAddressDetail() + "\n"
                        + "신용증빙: " + dto.getReservationGuarantee() + "\n"
                        + "총렌트료(부포): " + dto.getCarAmountTotal() + "\n"
                        + "보증금: " + dto.getCarDeposit() + "\n\n",

                "[예약 대기 신청이 완료되었습니다]" + "\n"
                        + "▼ 문의자 정보" + "\n"
                        + "문의자 이름: " + dto.getReservationName() + "\n"
                        + "연락처: " + dto.getReservationPhone() + "\n"
                        + "보험연령: " + dto.getSelectAge() + "\n"
                        + "생년월일: " + dto.getReservationAge() + "\n\n"

                        + "▼ 차량 정보" + "\n"
                        + "차량명: " + dto.getCarName() + "\n"
                        + "차량번호: " + dto.getCarNo() + "\n\n"

                        + "▼ 대여 정보" + "\n"
                        + "대여일자: " + dto.getReservationDate() + "\n"
                        + "대여시간: " + dto.getReservationTime() + "\n"
                        + "렌트기간: " + dto.getRentTerm() + "\n"
                        + "약정주행거리: " + dto.getKilometer() + "\n"
                        + "방문/배차: " + dto.getPickupPlace() + "\n"
                        + "배차요청주소: " + dto.getAddress() + "\n"
                        + "배차요청상세주소: " + dto.getAddressDetail() + "\n"
                        + "필요증빙: " + dto.getReservationGuarantee() + "\n"
                        + "총렌트료(부포): " + dto.getCarAmountTotal() + "\n"
                        + "보증금: " + dto.getCarDeposit() + "\n\n");
    }

}