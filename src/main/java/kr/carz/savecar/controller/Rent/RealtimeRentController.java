package kr.carz.savecar.controller.Rent;

import kr.carz.savecar.controller.ReservationController;
import kr.carz.savecar.controller.Utils.HttpConnection;
import kr.carz.savecar.dto.MorenDTO;
import kr.carz.savecar.dto.MorenReservationDTO;
import kr.carz.savecar.dto.RealTimeDTO;
import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    public RealtimeRentController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService, TwoYearlyRentService twoYearlyRentService,
                                  DiscountService discountService, MorenReservationService morenReservationService, ReservationController reservationController) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.twoYearlyRentService = twoYearlyRentService;
        this.discountService = discountService;
        this.morenReservationService = morenReservationService;
        this.reservationController = reservationController;
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

    @GetMapping("/rent/month/new")
    public String rent_month(ModelMap model) {

        List<MorenDTO> morenDTOList = new ArrayList<>();
        List<MorenDTO> morenDTOListExpected = new ArrayList<>();

        String moren_url = moren_url_except_date + DateTime.today_date_only() + "&END=" + DateTime.today_date_only() + "&EXPECTED_DAY=" + expected_day;

        HttpConnection http = new HttpConnection();
        JSONObject responseJson = http.sendGetRequest(moren_url);
        JSONArray list_json_array = (JSONArray) responseJson.get("list");

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
            // n일 이내 반납예정차량
            if ( (Integer)morenObject.get("order_status") == 2 &&
                    order_end.length() >= 19 &&
                    order_end.substring(0, 19).compareTo(DateTime.today_date_and_time()) >= 0 &&  // 시간 고려해서
                    order_end.substring(0, 10).compareTo(DateTime.expected()) <= 0 // 날짜만 고려해서
            ) {


                try {
                    // 자체 db에서 가격 정보 가져오기
                    MonthlyRent monthlyRent2 = monthlyRentService.findByMorenCar(carOld, carOld, carCategory);

                    Optional<Discount> discount_object = discountService.findDiscountByCarNo((String)morenObject.get("carNo"));
                    String discount_price = null;
                    String discount_description = null;
                    if(discount_object.isPresent()) {
                        discount_price = discount_object.get().getDiscount();
                        discount_description = discount_object.get().getDescription();
                    }

                    MorenDTO moren = new MorenDTO((String) morenObject.get("carIdx"), carCategory, (String) morenObject.get("carName"),
                            (String) morenObject.get("carNo"), (String) morenObject.get("carExteriorColor"), (String) morenObject.get("carGubun"),
                            (String) morenObject.get("carDisplacement"), (String) morenObject.get("carMileaget"), (String) morenObject.get("carColor"),
                            (String) morenObject.get("carOld"), (String) morenObject.get("carEngine"), (String) morenObject.get("carAttribute01"),
                            monthlyRent2.getCost_for_2k(), (String) morenObject.get("order_end"), monthlyRent2.getId(), null, discount_price, discount_description,
                            monthlyRent2.getCost_per_km(), monthlyRent2.getCredit(), (String) morenObject.get("carCode"), null, null, null, null);
                    morenDTOListExpected.add(moren);

                } catch (Exception e) {
                    System.out.println("Error ! 차량이름 모렌과 맞출 것 !"+ carCategory);

                    reservationController.send_error_message_to_employees(admin1,
                            "모렌_차명이 동일하지 않아, 사이트에 해당 차량이 게시되지 않고 있습니다.\n"
                                            + "관리자 페이지에서 모렌_차명을 변경해주시기 바랍니다.\n"
                                            + "* 프라임클럽 차량상세페이지에서 모델정보의 두번째 값과 동일하게 설정해주시기 바랍니다.\n\n"
                                            + "차량명 : " + carCategory + "\n"
                                            + "오류발생일시 : " + DateTime.today_date_and_time() + "\n");
                }
            }
            // 현재 가능차
            else if ((Integer)morenObject.get("order_status") == 0){

                try {
                    // 자체 db에서 가격 정보 가져오기
                    MonthlyRent monthlyRent2 = monthlyRentService.findByMorenCar(carOld, carOld, carCategory);

                    Optional<Discount> discount_object = discountService.findDiscountByCarNo((String) morenObject.get("carNo"));
                    String discount_price = null;
                    String discount_description = null;
                    if(discount_object.isPresent()) {
                        discount_price = discount_object.get().getDiscount();
                        discount_description = discount_object.get().getDescription();
                    }

                    MorenDTO moren = new MorenDTO((String) morenObject.get("carIdx"), carCategory, (String) morenObject.get("carName"),
                            (String) morenObject.get("carNo"), (String) morenObject.get("carExteriorColor"), (String) morenObject.get("carGubun"),
                            (String) morenObject.get("carDisplacement"), (String) morenObject.get("carMileaget"), (String) morenObject.get("carColor"),
                            (String) morenObject.get("carOld"), (String) morenObject.get("carEngine"), (String) morenObject.get("carAttribute01"),
                            monthlyRent2.getCost_for_2k(), (String) morenObject.get("order_end"), monthlyRent2.getId(), null, discount_price, discount_description,
                            monthlyRent2.getCost_per_km(), monthlyRent2.getCredit(), (String) morenObject.get("carCode"), null, null, null, null);
                    morenDTOList.add(moren);

                } catch (Exception e) {
                    System.out.println("Error ! 차량이름 모렌과 맞출 것 ! 차량이름 : " + carCategory);

                    reservationController.send_error_message_to_employees(admin1,
                            "모렌_차명이 동일하지 않아, 사이트에 해당 차량이 게시되지 않고 있습니다.\n"
                                    + "관리자 페이지에서 모렌_차명을 변경해주시기 바랍니다.\n"
                                    + "* 프라임클럽 차량상세페이지에서 모델정보의 두번째 값과 동일하게 설정해주시기 바랍니다.\n\n"
                                    + "차량명 : " + carCategory + "\n"
                                    + "오류발생일시 : " + DateTime.today_date_and_time() + "\n");
                }
            }
        }

        // 모렌 데이터 프론트로 전달
        model.put("morenDTOList", morenDTOList);
        model.put("morenDTOListExpected", morenDTOListExpected);

        // 라디오버튼 디폴트 데이터 전달
        model.put("carType", "전체");
        model.put("kilometer", "2000km");
        model.put("rentTerm", "한달");
        model.put("byCarName",  Comparator.comparing(MorenDTO::getCarName));
        model.put("byOrderEnd",  Comparator.comparing(MorenDTO::getOrderEnd));

        return "rent_month/main";
    }


    // 조건별로 차종 데이터 전달
    @PostMapping("/rent/month/lookup")
    public String rent_month_lookup(ModelMap model, @ModelAttribute RealTimeDTO realTimeDto) {

        // 모렌 데이터 객체 생성
        List<MorenDTO> morenDTOList = new ArrayList<>();
        List<MorenDTO> morenDTOListExpected = new ArrayList<>();

        String moren_url = moren_url_except_date + DateTime.today_date_only() + "&END=" + DateTime.today_date_only() + "&EXPECTED_DAY=" + expected_day;

        HttpConnection http = new HttpConnection();
        JSONObject responseJson = http.sendGetRequest(moren_url);
        JSONArray list_json_array = (JSONArray) responseJson.get("list");

        // list 안에 데이터
        for(int i=0; i<list_json_array.length(); i++){

            // json object 가져오기
            JSONObject morenObject = (JSONObject)list_json_array.get(i);
            String order_end = ((String)morenObject.get("order_end"));

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

            // 예약가능차량
            if ((Integer)morenObject.get("order_status") == 0 ||
                    ((Integer)morenObject.get("order_status") == 2 &&
                            order_end.length() > 10 &&
                            order_end.substring(0, 19).compareTo(DateTime.today_date_and_time()) >= 0 &&
                            order_end.substring(0, 10).compareTo(DateTime.expected()) <= 0)){

                // 차종별
                if (realTimeDto.getCarType().equals("전체")  || (realTimeDto.getCarType().equals(morenObject.get("carGubun")) && (Integer)morenObject.get("carLocal") != 1) || (realTimeDto.getCarType().equals("수입") && (Integer)morenObject.get("carLocal") == 1 ) ) {

                    Long carOld = Long.parseLong((String)morenObject.get("carOld"));

                    try {
                        double kilometer_cost = (float) 1;
                        Long dbid = Long.parseLong("0");
                        String cost_per_km = null;
                        String credit = null;

                        MonthlyRent monthlyRent2 = monthlyRentService.findByMorenCar(carOld, carOld, carCategory);
                        double costFor2k = monthlyRent2.getCost_for_2k();
                        double costFor3k = monthlyRent2.getCost_for_3k();
                        double costFor4k = monthlyRent2.getCost_for_4k();

                        switch(realTimeDto.getRentTerm()){
                            case "한달":

                                switch(realTimeDto.getKilometer()){
                                    case "2500km":
                                        kilometer_cost = costFor2k * monthlyRent2.getCost_for_2_5k();
                                        break;
                                    case "3000km":
                                        kilometer_cost = costFor2k * monthlyRent2.getCost_for_3k();
                                        break;
                                    case "4000km":
                                        kilometer_cost = costFor2k * monthlyRent2.getCost_for_4k();
                                        break;
                                    case "기타":
                                        kilometer_cost = -1;
                                        break;
                                    default:
                                        kilometer_cost = costFor2k;
                                        break;
                                }
                                dbid = monthlyRent2.getId();
                                cost_per_km = monthlyRent2.getCost_per_km();
                                credit = monthlyRent2.getCredit();
                                break;

                            case "12개월":
                                YearlyRent yearlyRent = yearlyRentService.findByMorenCar(carOld, carOld, carCategory);

                                switch(realTimeDto.getKilometer()){
                                    case "30000km":
                                        kilometer_cost = costFor2k * yearlyRent.getCost_for_30k();
                                        break;
                                    case "40000km":
                                        kilometer_cost = costFor3k * yearlyRent.getCost_for_40k();
                                        break;
                                    case "기타_long":
                                        kilometer_cost = (float) -1;
                                        break;
                                    default:
                                        kilometer_cost = costFor4k * yearlyRent.getCost_for_20k();
                                        break;
                                }

                                dbid = yearlyRent.getId();
                                cost_per_km = yearlyRent.getCost_per_km();
                                credit = yearlyRent.getCredit();
                                break;

                            case "24개월":
                                TwoYearlyRent twoYearlyRent = twoYearlyRentService.findByMorenCar(carOld, carOld, carCategory);

                                switch(realTimeDto.getKilometer()){
                                    case "30000km":
                                        kilometer_cost = costFor2k * twoYearlyRent.getCost_for_30Tk();
                                        break;
                                    case "40000km":
                                        kilometer_cost = costFor3k * twoYearlyRent.getCost_for_40Tk();
                                        break;
                                    case "기타_long":
                                        kilometer_cost = (float) -1;
                                        break;
                                    default:
                                        kilometer_cost = costFor4k * twoYearlyRent.getCost_for_20Tk();
                                        break;
                                }

                                dbid = twoYearlyRent.getId();
                                cost_per_km = twoYearlyRent.getCost_per_km();
                                credit = twoYearlyRent.getCredit();
                                break;

                        }

                        Optional<Discount> discount_object = discountService.findDiscountByCarNo((String) morenObject.get("carNo"));
                        String discount_price = null;
                        String discount_description = null;
                        if(discount_object.isPresent() && realTimeDto.getRentTerm().equals("한달")) {
                            discount_price = discount_object.get().getDiscount();
                            discount_description = discount_object.get().getDescription();
                        }

                        MorenDTO moren = new MorenDTO((String) morenObject.get("carIdx"), carCategory, (String) morenObject.get("carName"),
                                (String) morenObject.get("carNo"), (String) morenObject.get("carExteriorColor"), (String) morenObject.get("carGubun"),
                                (String) morenObject.get("carDisplacement"), (String) morenObject.get("carMileaget"), (String) morenObject.get("carColor"),
                                (String) morenObject.get("carOld"), (String) morenObject.get("carEngine"), (String) morenObject.get("carAttribute01"),
                                kilometer_cost, (String) morenObject.get("order_end"), dbid, null, discount_price, discount_description, cost_per_km,
                                credit, (String) morenObject.get("carCode"), null, null, null, null);

                        if ((Integer)morenObject.get("order_status") == 0) {
                            morenDTOList.add(moren);
                        } else {
                            morenDTOListExpected.add(moren);
                        }

                    } catch (Exception e) {
                        System.out.println("Error ! 차량이름 모렌과 맞출 것 !" + carCategory);
                    }
                }
            }
        }

        // 모렌 데이터 프론트로 전달
        model.put("morenDTOList", morenDTOList);
        model.put("morenDTOListExpected", morenDTOListExpected);

        // 라디오버튼 데이터 전달
        model.put("carType", realTimeDto.getCarType());

        // 한달 <-> 12개월, 24개월 : 약정 주행거리 디폴트  설정
        String [] above_year_field = {"12개월", "24개월"};
        String [] yearly_kilometer_field = {"20000km", "30000km", "40000km", "기타_long"};
        String [] monthly_kilometer_field = {"2000km", "2500km", "3000km", "4000km", "기타"};


        if (ArrayUtils.contains(above_year_field, realTimeDto.getRentTerm()) ){
            if (!ArrayUtils.contains(yearly_kilometer_field, realTimeDto.getKilometer()) ){
                model.put("kilometer", "20000km");
            } else {
                model.put("kilometer", realTimeDto.getKilometer());
            }
        } else if (realTimeDto.getRentTerm().equals("한달")) {

            if(!ArrayUtils.contains(monthly_kilometer_field, realTimeDto.getKilometer())){
                model.put("kilometer", "2000km");
            } else {
                model.put("kilometer", realTimeDto.getKilometer());
            }
        }
        model.put("rentTerm", realTimeDto.getRentTerm());
        model.put("byCarName",  Comparator.comparing(MorenDTO::getCarName));
        model.put("byOrderEnd",  Comparator.comparing(MorenDTO::getOrderEnd));

        return "rent_month/main";
    }


    // 차량 상세 페이지
    @RequestMapping(value = "/rent/month/detail/{rentTerm}/{carIdx}/{rentIdx}/{kilometer}/{discount}/{rentStatus}", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    public String rent_month_detail(HttpServletResponse res, ModelMap model, @PathVariable String carIdx,@PathVariable String rentTerm, @PathVariable Long rentIdx, @PathVariable String kilometer,  @PathVariable String discount,@PathVariable String rentStatus) throws IOException {

        String cost_per_km = null;
        String credit = null;
        String deposit = null;

        String moren_url = moren_url_except_date + DateTime.today_date_only() + "&END=" + DateTime.today_date_only() + "&EXPECTED_DAY=" + expected_day;

        List<String> carList = new ArrayList<>();
        Optional<MonthlyRent> monthlyRentOptional = monthlyRentService.findById(rentIdx);
        MonthlyRent monthlyRent = monthlyRentOptional.get();
        model.put("monthlyRent", monthlyRent);

        // 세이브카 db에서 해당 차 객체 가져오기
        if (rentTerm.equals("한달")){
            carList.add(monthlyRent.getImg_url());
            cost_per_km = monthlyRent.getCost_per_km();
            credit = monthlyRent.getCredit();
            deposit = monthlyRent.getDeposit();
            model.put("priceObject", monthlyRent);
        } else if (rentTerm.equals("12개월")){
            Optional<YearlyRent> yearlyRentOptional = yearlyRentService.findById(rentIdx);
            YearlyRent yearlyRent = yearlyRentOptional.get();
            carList.add(yearlyRent.getImg_url());
            cost_per_km = yearlyRent.getCost_per_km();
            credit = yearlyRent.getCredit();
            deposit = yearlyRent.getDeposit();
            model.put("priceObject", yearlyRent);
        } else if (rentTerm.equals("24개월")){
            Optional<TwoYearlyRent> twoYearlyRentOptional = twoYearlyRentService.findById(rentIdx);

            if (!twoYearlyRentOptional.isPresent()){
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

                MorenDTO morenDto = new MorenDTO((String) morenObject.get("carIdx"), (String) morenObject.get("carCategory"), (String) morenObject.get("carName"),
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