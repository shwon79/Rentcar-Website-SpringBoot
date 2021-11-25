package kr.carz.savecar.controller;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.*;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ArrayUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class RealtimeRentController {

    private final MonthlyRentService monthlyRentService;
    private final YearlyRentService yearlyRentService;
    private final TwoYearlyRentService twoYearlyRentService;
    private final DiscountService discountService;
    private final MorenReservationService morenReservationService;

    @Autowired
    public RealtimeRentController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService, TwoYearlyRentService twoYearlyRentService,
                                  DiscountService discountService, MorenReservationService morenReservationService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.twoYearlyRentService = twoYearlyRentService;
        this.discountService = discountService;
        this.morenReservationService = morenReservationService;
    }

    /* ======================================================================================== */
    /*                             [New 버전] 실시간 월렌트 예약하기                                */
    /* ======================================================================================== */

    private final String expected_day = "5";
    private final DateTime dateTime = new DateTime(expected_day);
    private final String moren_url = "https://www.moderentcar.co.kr/api/mycar/cars.php?COMPANY_ID=1343&START=" + dateTime.today_date_only() + "&END=" + dateTime.today_date_only() + "&EXPECTED_DAY=" + expected_day;

    @GetMapping("/rent/month/new")
    public String rent_month(ModelMap model) {

        List<MorenDTO> morenDTOList = new ArrayList<>();
        List<MorenDTO> morenDTOListExpected = new ArrayList<>();

        HttpConnection http = new HttpConnection();
        JSONObject responseJson = http.sendGetRequest(moren_url);
        JSONArray list_json_array = (JSONArray) responseJson.get("list");

        // list 안에 데이터
        for(int i=0; i<list_json_array.length(); i++){

            // json object 가져오기
            JSONObject morenObject = (JSONObject)list_json_array.get(i);
            String order_end = ((String)morenObject.get("order_end"));
            Long carOld = Long.parseLong((String)morenObject.get("carOld"));

            // n일 이내 반납예정차량
            if ( (Integer)morenObject.get("order_status") == 2 &&
                    order_end.length() >= 19 &&
                    order_end.substring(0, 19).compareTo(dateTime.today_date_and_time()) >= 0 &&  // 시간 고려해서
                    order_end.substring(0, 10).compareTo(dateTime.expected()) <= 0 // 날짜만 고려해서
                ) {


                if (morenObject.get("reserve").equals(null)) {
                    // 차량 이미지
                    List<String> carList = new ArrayList<>();

                    if (!morenObject.get("carThumbImages").equals(null)) {
                        JSONArray carJsonArray = (JSONArray) (morenObject.get("carThumbImages"));
                        for (int j = 0; j < carJsonArray.length(); j++) {
                            carList.add((String) carJsonArray.get(j));
                        }
                    }
                    try {
                        // 자체 db에서 가격 정보 가져오기
                        MonthlyRent monthlyRent2 = monthlyRentService.findByMorenCar(carOld, carOld, (String)morenObject.get("carCategory"));

                        Optional<Discount> discount_object = discountService.findDiscountByCarNo((String)morenObject.get("carNo"));
                        String discount_price = null;
                        String discount_description = null;
                        if(discount_object.isPresent()) {
                            discount_price = discount_object.get().getDiscount();
                            discount_description = discount_object.get().getDescription();
                        }

                        MorenDTO moren = new MorenDTO((String) morenObject.get("carIdx"), (String) morenObject.get("carCategory"), (String) morenObject.get("carName"),
                                (String) morenObject.get("carNo"), (String) morenObject.get("carExteriorColor"), (String) morenObject.get("carGubun"),
                                (String) morenObject.get("carDisplacement"), (String) morenObject.get("carMileaget"), (String) morenObject.get("carColor"),
                                (String) morenObject.get("carOld"), (String) morenObject.get("carEngine"), (String) morenObject.get("carAttribute01"),
                                monthlyRent2.getCost_for_2k(), (String) morenObject.get("order_end"), monthlyRent2.getId(), carList, discount_price, discount_description,
                                monthlyRent2.getCost_per_km(), monthlyRent2.getCredit(), (String) morenObject.get("carCode"), null, null, null);
                        morenDTOListExpected.add(moren);

                    } catch (Exception e) {
                        System.out.println("Error ! 차량이름 모렌과 맞출 것 !"+ morenObject.get("carCategory"));
                    }
                }
            }
            // 현재 가능차
            else if ((Integer)morenObject.get("order_status") == 0){

                if (morenObject.get("reserve").equals(null)) {
                    // 차량 이미지
                    List<String> carList = new ArrayList<>();

                    if (!morenObject.get("carThumbImages").equals(null)) {
                        JSONArray carJsonArray = (JSONArray) (morenObject.get("carThumbImages"));
                        for (int j = 0; j < carJsonArray.length(); j++) {
                            carList.add((String) carJsonArray.get(j));
                        }
                    }
                    try {
                        // 자체 db에서 가격 정보 가져오기
                        MonthlyRent monthlyRent2 = monthlyRentService.findByMorenCar(carOld, carOld, (String)morenObject.get("carCategory"));

                        Optional<Discount> discount_object = discountService.findDiscountByCarNo((String) morenObject.get("carNo"));
                        String discount_price = null;
                        String discount_description = null;
                        if(discount_object.isPresent()) {
                            discount_price = discount_object.get().getDiscount();
                            discount_description = discount_object.get().getDescription();
                        }

                        MorenDTO moren = new MorenDTO((String) morenObject.get("carIdx"), (String) morenObject.get("carCategory"), (String) morenObject.get("carName"),
                                (String) morenObject.get("carNo"), (String) morenObject.get("carExteriorColor"), (String) morenObject.get("carGubun"),
                                (String) morenObject.get("carDisplacement"), (String) morenObject.get("carMileaget"), (String) morenObject.get("carColor"),
                                (String) morenObject.get("carOld"), (String) morenObject.get("carEngine"), (String) morenObject.get("carAttribute01"),
                                monthlyRent2.getCost_for_2k(), (String) morenObject.get("order_end"), monthlyRent2.getId(), carList, discount_price, discount_description,
                                monthlyRent2.getCost_per_km(), monthlyRent2.getCredit(), (String) morenObject.get("carCode"), null, null, null);
                        morenDTOList.add(moren);

                    } catch (Exception e) {
                        System.out.println("Error ! 차량이름 모렌과 맞출 것 ! 차량이름 : " + morenObject.get("carCategory"));
                    }
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
        model.put("byOrderEnd",  Comparator.comparing(MorenDTO::getOrderEnd));

        return "rent_month2";
    }


    // 조건별로 차종 데이터 전달
    @PostMapping("/rent/month/lookup")
    public String rent_month_lookup(ModelMap model, @ModelAttribute RealTimeDTO realTimeDto) {

        // 모렌 데이터 객체 생성
        List<MorenDTO> morenDTOList = new ArrayList<>();
        List<MorenDTO> morenDTOListExpected = new ArrayList<>();

        HttpConnection http = new HttpConnection();
        JSONObject responseJson = http.sendGetRequest(moren_url);
        JSONArray list_json_array = (JSONArray) responseJson.get("list");

        // list 안에 데이터
        for(int i=0; i<list_json_array.length(); i++){

            // json object 가져오기
            JSONObject morenObject = (JSONObject)list_json_array.get(i);
            String order_end = ((String)morenObject.get("order_end"));

            // 예약가능차량
            if ((Integer)morenObject.get("order_status") == 0 ||
                    ((Integer)morenObject.get("order_status") == 2 &&
                    order_end.length() > 10 &&
                    order_end.substring(0, 19).compareTo(dateTime.today_date_and_time()) >= 0 &&
                    order_end.substring(0, 10).compareTo(dateTime.expected()) <= 0)){

                if (morenObject.get("reserve").equals(null)) {

                    // 차종별
                    if (realTimeDto.getCarType().equals("전체")  || (realTimeDto.getCarType().equals(morenObject.get("carGubun")) && (Integer)morenObject.get("carLocal") != 1) || (realTimeDto.getCarType().equals("수입") && (Integer)morenObject.get("carLocal") == 1 ) ) {

                        Long carOld = Long.parseLong((String)morenObject.get("carOld"));

                        // 차량 이미지
                        List<String> carList = new ArrayList<>();

                        if (!morenObject.get("carThumbImages").equals(null)) {
                            JSONArray carJsonArray = (JSONArray) (morenObject.get("carThumbImages"));
                            for (int j = 0; j < carJsonArray.length(); j++) {
                                carList.add((String) carJsonArray.get(j));
                            }
                        }

                        try {
                            // 자체 db에서 가격 정보 가져오기
                            MonthlyRent monthlyRent2 = monthlyRentService.findByMorenCar(carOld, carOld, (String) morenObject.get("carCategory"));
                            YearlyRent yearlyRent = yearlyRentService.findByMorenCar(carOld, carOld, (String) morenObject.get("carCategory"));
                            TwoYearlyRent twoYearlyRent = twoYearlyRentService.findByMorenCar(carOld, carOld, (String) morenObject.get("carCategory"));

                            String kilometer_cost = null;
                            Long dbid = Long.parseLong("0");
                            String cost_per_km = null;
                            String credit = null;

                            // 렌트기간별
                            if (realTimeDto.getRentTerm().equals("한달")) {

                                // 키로수별
                                if (realTimeDto.getKilometer().equals("2000km")) {
                                    kilometer_cost = monthlyRent2.getCost_for_2k();
                                } else if (realTimeDto.getKilometer().equals("2500km")) {
                                    kilometer_cost = monthlyRent2.getCost_for_2_5k();
                                } else if (realTimeDto.getKilometer().equals("3000km")) {
                                    kilometer_cost = monthlyRent2.getCost_for_3k();
                                } else if (realTimeDto.getKilometer().equals("4000km")) {
                                    kilometer_cost = monthlyRent2.getCost_for_4k();
                                } else if (realTimeDto.getKilometer().equals("기타")) {
                                    kilometer_cost = monthlyRent2.getCost_for_others();
                                } else {
                                    kilometer_cost = monthlyRent2.getCost_for_2k();
                                }

                                dbid = monthlyRent2.getId();
                                cost_per_km = monthlyRent2.getCost_per_km();
                                credit = monthlyRent2.getCredit();

                            } else if (realTimeDto.getRentTerm().equals("12개월")) {

                                // 키로수별
                                if (realTimeDto.getKilometer().equals("20000km")) {
                                    kilometer_cost = yearlyRent.getCost_for_20k();
                                } else if (realTimeDto.getKilometer().equals("30000km")) {
                                    kilometer_cost = yearlyRent.getCost_for_30k();
                                } else if (realTimeDto.getKilometer().equals("40000km")) {
                                    kilometer_cost = yearlyRent.getCost_for_40k();
                                } else if (realTimeDto.getKilometer().equals("기타_long")) {
                                    kilometer_cost = yearlyRent.getCost_for_others();
                                } else {
                                    kilometer_cost = yearlyRent.getCost_for_20k();
                                }

                                dbid = yearlyRent.getId();
                                cost_per_km = yearlyRent.getCost_per_km();
                                credit = yearlyRent.getCredit();

                            } else if (realTimeDto.getRentTerm().equals("24개월")) {

                                // 키로수별
                                if (realTimeDto.getKilometer().equals("20000km")) {
                                    kilometer_cost = twoYearlyRent.getCost_for_20Tk();
                                } else if (realTimeDto.getKilometer().equals("30000km")) {
                                    kilometer_cost = twoYearlyRent.getCost_for_30Tk();
                                } else if (realTimeDto.getKilometer().equals("40000km")) {
                                    kilometer_cost = twoYearlyRent.getCost_for_40Tk();
                                } else if (realTimeDto.getKilometer().equals("기타_long")) {
                                    kilometer_cost = twoYearlyRent.getCost_for_others();
                                } else {
                                    kilometer_cost = twoYearlyRent.getCost_for_20Tk();
                                }

                                dbid = twoYearlyRent.getId();
                                cost_per_km = twoYearlyRent.getCost_per_km();
                                credit = twoYearlyRent.getCredit();

                            }

                            Optional<Discount> discount_object = discountService.findDiscountByCarNo((String) morenObject.get("carNo"));
                            String discount_price = null;
                            String discount_description = null;
                            if(discount_object.isPresent() && realTimeDto.getRentTerm().equals("한달")) {
                                discount_price = discount_object.get().getDiscount();
                                discount_description = discount_object.get().getDescription();
                            }

                            MorenDTO moren = new MorenDTO((String) morenObject.get("carIdx"), (String) morenObject.get("carCategory"), (String) morenObject.get("carName"),
                                    (String) morenObject.get("carNo"), (String) morenObject.get("carExteriorColor"), (String) morenObject.get("carGubun"),
                                    (String) morenObject.get("carDisplacement"), (String) morenObject.get("carMileaget"), (String) morenObject.get("carColor"),
                                    (String) morenObject.get("carOld"), (String) morenObject.get("carEngine"), (String) morenObject.get("carAttribute01"),
                                    kilometer_cost, (String) morenObject.get("order_end"), dbid, carList, discount_price, discount_description, cost_per_km,
                                    credit, (String) morenObject.get("carCode"), null, null, null);

                            if ((Integer)morenObject.get("order_status") == 0) {
                                morenDTOList.add(moren);
                            } else {
                                morenDTOListExpected.add(moren);
                            }

                        } catch (Exception e) {
                            System.out.println("Error ! 차량이름 모렌과 맞출 것 !" + morenObject.get("carCategory"));
                        }
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
        model.put("byOrderEnd",  Comparator.comparing(MorenDTO::getOrderEnd));

        return "rent_month2";
    }


    // 차량 상세 페이지
    @RequestMapping(value = "/rent/month/detail/{rentTerm}/{carIdx}/{rentIdx}/{kilometer}/{discount}/{rentStatus}", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    public String rent_month_detail(HttpServletResponse res, ModelMap model, @PathVariable String carIdx,@PathVariable String rentTerm, @PathVariable Long rentIdx, @PathVariable String kilometer,  @PathVariable String discount,@PathVariable String rentStatus) throws IOException {

        String cost_per_km = null;
        String credit = null;
        String deposit = null;

        // 세이브카 db에서 해당 차 객체 가져오기
        if (rentTerm.equals("한달")){
            Optional<MonthlyRent> monthlyRentOptional = monthlyRentService.findById(rentIdx);
            MonthlyRent monthlyRent = monthlyRentOptional.get();
            cost_per_km = monthlyRent.getCost_per_km();
            credit = monthlyRent.getCredit();
            deposit = monthlyRent.getDeposit();
            model.put("priceObject", monthlyRent);
        } else if (rentTerm.equals("12개월")){
            Optional<YearlyRent> yearlyRentOptional = yearlyRentService.findById(rentIdx);
            YearlyRent yearlyRent = yearlyRentOptional.get();
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

                Optional<MonthlyRent> monthlyRentOptional = monthlyRentService.findById(rentIdx);
                MonthlyRent monthlyRent = monthlyRentOptional.get();
                cost_per_km = monthlyRent.getCost_per_km();
                credit = monthlyRent.getCredit();
                deposit = monthlyRent.getDeposit();
                model.put("priceObject", monthlyRent);

                rentTerm = "한달";
                kilometer = "2000km";
            } else {
                TwoYearlyRent twoYearlyRent = twoYearlyRentOptional.get();
                cost_per_km = twoYearlyRent.getCost_per_km();
                credit = twoYearlyRent.getCredit();
                deposit = twoYearlyRent.getDeposit();
                model.put("priceObject", twoYearlyRent);
            }
        }

        HttpConnection http = new HttpConnection();
        JSONObject responseJson = http.sendGetRequest(moren_url);
        JSONArray list_json_array = (JSONArray) responseJson.get("list");

        // list 안에 데이터
        for(int i=0; i<list_json_array.length(); i++){

            JSONObject morenObject = (JSONObject)list_json_array.get(i);

            if(morenObject.get("carIdx").equals(carIdx)){

                // 차량 이미지
                List<String> carList = new ArrayList<>();

                if (!morenObject.get("carThumbImages").equals(null)) {
                    JSONArray carJsonArray = (JSONArray) (morenObject.get("carThumbImages"));
                    for (int j = 0; j < carJsonArray.length(); j++) {
                        carList.add((String) carJsonArray.get(j));
                    }
                    model.put("lenOfPictures", carJsonArray.length());
                } else {
                    model.put("lenOfPictures", 0);
                }

                MorenDTO morenDto = new MorenDTO((String) morenObject.get("carIdx"), (String) morenObject.get("carCategory"), (String) morenObject.get("carName"),
                        (String) morenObject.get("carNo"), (String) morenObject.get("carExteriorColor"), (String) morenObject.get("carGubun"),
                        (String) morenObject.get("carDisplacement"), (String) morenObject.get("carMileaget"), (String) morenObject.get("carColor"),
                        (String) morenObject.get("carOld"), (String) morenObject.get("carEngine"), (String) morenObject.get("carAttribute01"),
                        null, (String) morenObject.get("order_end"), rentIdx, carList, discount, null, cost_per_km,
                        credit, (String) morenObject.get("carCode"), kilometer, deposit, rentTerm);

                model.put("morenDto", morenDto);
                break;

            }
        }

        model.put("today_format", dateTime.today_date_only());
        model.put("rentStatus", rentStatus);

        return "rent_month2_detail";
    }


    // 예약신청하기 새 창 띄우기
    @PostMapping("/rent/month/detail/form/reservation")
    public String rent_month_detail_form_reservation(ModelMap model, @ModelAttribute MorenDTO morenDTO) {

        model.put("morenDTO",morenDTO);
        model.put("today_format",dateTime.today_date_only());

        return "rent_month2_reservation_form";
    }


    // 견적서보기 새 창 띄우기
    @PostMapping("/rent/month/detail/form/estimate")
    public String rent_month_detail_form_estimate(ModelMap model, @ModelAttribute MorenDTO morenDTO) {
        model.put("morenDTO",morenDTO);

        return "rent_month2_estimate_form";
    }



    @PostMapping("/rent/month/moren/reservation")
    @ResponseBody
    public void moren_reservation(HttpServletResponse res, @RequestBody MorenReservationDTO morenReservationDTO) throws IOException {

        morenReservationService.saveDTO(morenReservationDTO);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", 1);

        String api_key = "NCS0P5SFAXLOJMJI";
        String api_secret = "FLLGUBZ7OTMQOXFSVE6ZWR2E010UNYIZ";
        Message coolsms = new Message(api_key, api_secret);
        HashMap<String, String> params = new HashMap<String, String>();
        HashMap<String, String> params2 = new HashMap<String, String>();


        /* 세이브카에 예약확인 문자 전송 */
        params.put("to", "01058283328"); // 01033453328 추가
        params.put("from", "01052774113");
        params.put("type", "LMS");

        /* 고객에게 예약확인 문자 전송 */
        params2.put("to", morenReservationDTO.getReservationPhone()); // 여러가지 번호형태 테스트
        params2.put("from", "01052774113");
        params2.put("type", "LMS");


        params.put("text", "[실시간 예약 대기 신청]\n"
                + "문의자 이름: " + morenReservationDTO.getReservationName() + "\n"
                + "연락처: " + morenReservationDTO.getReservationPhone() + "\n"
                + "차량번호: " + morenReservationDTO.getCarNo() + "\n"
                + "대여일자: " + morenReservationDTO.getReservationDate() + "\n"
                + "대여시간: " + morenReservationDTO.getReservationTime() + "\n"
                + "렌트기간: " + morenReservationDTO.getRentTerm() + "\n"
                + "약정주행거리: " + morenReservationDTO.getKilometer() + "\n"
                + "방문/배차: " + morenReservationDTO.getPickupPlace() + "\n"
                + "배차요청주소: " + morenReservationDTO.getAddress() + "\n"   // 방문시에는 어떻게 처리할지 확인
                + "배차요청상세주소: " + morenReservationDTO.getAddressDetail() + "\n"
                + "생년월일: " + morenReservationDTO.getReservationAge() + "\n"
                + "신용증빙: " + morenReservationDTO.getReservationGuarantee() + "\n"
                + "총렌트료(부포): " + morenReservationDTO.getCarAmountTotal() + "\n"
                + "보증금: " + morenReservationDTO.getCarDeposit() + "\n"
                + "요청사항: " + morenReservationDTO.getReservationDetails() + "\n\n");

        params2.put("text", "[세이브카 렌트카 예약 대기 신청이 완료되었습니다]" + "\n"
                + "성함: " + morenReservationDTO.getReservationName() + "\n"
                + "연락처: " + morenReservationDTO.getReservationPhone() + "\n"
                + "차량번호: " + morenReservationDTO.getCarNo() + "\n"
                + "대여일자: " + morenReservationDTO.getReservationDate() + "\n"
                + "렌트기간: " + morenReservationDTO.getRentTerm() + "\n"
                + "약정주행거리: " + morenReservationDTO.getKilometer() + "\n"
                + "방문/배차: " + morenReservationDTO.getPickupPlace() + "\n"
                + "배차요청주소: " + morenReservationDTO.getAddress() + "\n"
                + "배차요청상세주소: " + morenReservationDTO.getAddressDetail() + "\n"
                + "기타증빙사항: " + morenReservationDTO.getReservationGuarantee() + "\n"
                + "총렌트료: " + morenReservationDTO.getCarAmountTotal() + "\n"
                + "보증금: " + morenReservationDTO.getCarDeposit() + "\n"
                + "요청사항: " + morenReservationDTO.getReservationDetails() + "\n\n"

                + "예약금 입금하시면 직원이 확인 후, 예약이 확정됩니다.\n"
                + "계좌번호: 하나은행 810-626121-01404 (주)세이브카\n");

        params.put("app_version", "test app 1.2");
        params2.put("app_version", "test app 1.2");

        /* 세이브카에게 문자 전송 */
        try {
            org.json.simple.JSONObject obj = (org.json.simple.JSONObject) coolsms.send(params);
            System.out.println(obj.toString()); //전송 결과 출력
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }

        /* 고객에게 예약확인 문자 전송 */
        try {
            org.json.simple.JSONObject obj2 = (org.json.simple.JSONObject) coolsms.send(params2);
            System.out.println(obj2.toString()); //전송 결과 출력
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }

}