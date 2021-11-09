package kr.carz.savecar.controller;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ArrayUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class RealtimeRentController {

    private final MonthlyRentService monthlyRentService;
    private final YearlyRentService yearlyRentService;
    private final TwoYearlyRentService twoYearlyRentService;
    private final ReservationService reservationService;
    private final DiscountService discountService;

    @Autowired
    public RealtimeRentController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService, TwoYearlyRentService twoYearlyRentService,
                                  ReservationService reservationService, DiscountService discountService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.twoYearlyRentService = twoYearlyRentService;
        this.reservationService = reservationService;
        this.discountService = discountService;
    }


    /* ======================================================================================== */
    /*                              [New 버전] 실시간 월렌트 예약하                                    */
    /* ======================================================================================== */

    private String expected_day = "3";
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private DateFormat df_date_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // order_status Integer version
    @GetMapping("/rent/month/new")
    public String rent_month(ModelMap model) {

        // 모두의 렌터카 데이터 가져오기
        HttpURLConnection conn;
        JSONObject responseJson;

        // 모렌 데이터 객체 생성
        List<MorenDTO> morenDTOList = new ArrayList<MorenDTO>();
        List<MorenDTO> morenDTOListExpected = new ArrayList<MorenDTO>();


        // 오늘 날짜
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String today_date = df.format(cal.getTime());
        String today_date_time = df_date_time.format(cal.getTime());

        // 며칠 이내 반납예정일
        cal.add(Calendar.DATE, Integer.parseInt(expected_day));
        String after_expected_date_format = df.format(cal.getTime());


        try {
            String today_url = "https://www.moderentcar.co.kr/api/mycar/cars.php?COMPANY_ID=1343&START=" + today_date + "&END=" + today_date + "&EXPECTED_DAY=" + expected_day;
            URL url = new URL(today_url);

            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == 400 || responseCode == 401 || responseCode == 500 ) {
                System.out.println(responseCode + " Error!");
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                // list 가져오기
                responseJson = new JSONObject(sb.toString());
                JSONArray list_json_array = (JSONArray) responseJson.get("list");

                // list 안에 데이터
                for(int i=0; i<list_json_array.length(); i++){

                    // json object 가져오기
                    JSONObject morenObject = (JSONObject)list_json_array.get(i);
                    String order_end = ((String)morenObject.get("order_end"));
                    Long carOld = Long.parseLong((String)morenObject.get("carOld"));

                    // 3일 이내 반납예정차량
                    if ( (Integer)morenObject.get("order_status") == 2 &&
                            order_end.length() >= 19 &&
                            order_end.substring(0, 19).compareTo(today_date_time) >= 0 &&  // 시간 고려해서
                            order_end.substring(0, 10).compareTo(after_expected_date_format) <= 0 // 날짜만 고려해서
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
                                if(discount_object.isPresent()) {
                                    System.out.println(discount_object.get().getCarNo());
                                    discount_price = discount_object.get().getDiscount();
                                }

                                MorenDTO moren = new MorenDTO((String) morenObject.get("carIdx"), (String) morenObject.get("carCategory"), (String) morenObject.get("carName"),
                                        (String) morenObject.get("carNo"), (String) morenObject.get("carExteriorColor"), (String) morenObject.get("carGubun"),
                                        (String) morenObject.get("carDisplacement"), (String) morenObject.get("carMileaget"), (String) morenObject.get("carColor"),
                                        (String) morenObject.get("carOld"), (String) morenObject.get("carEngine"), (String) morenObject.get("carAttribute01"),
                                        monthlyRent2.getCost_for_2k(), (String) morenObject.get("order_end"), monthlyRent2.getId(), carList, discount_price);
                                morenDTOListExpected.add(moren);

                            } catch (Exception e) {
                                System.out.println("Error ! 차량이름 모렌과 맞출 것 !");
                                continue;
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
                                if(discount_object.isPresent()) {
                                    System.out.println(discount_object.get().getCarNo());
                                    discount_price = discount_object.get().getDiscount();
                                }

                                MorenDTO moren = new MorenDTO((String) morenObject.get("carIdx"), (String) morenObject.get("carCategory"), (String) morenObject.get("carName"),
                                        (String) morenObject.get("carNo"), (String) morenObject.get("carExteriorColor"), (String) morenObject.get("carGubun"),
                                        (String) morenObject.get("carDisplacement"), (String) morenObject.get("carMileaget"), (String) morenObject.get("carColor"),
                                        (String) morenObject.get("carOld"), (String) morenObject.get("carEngine"), (String) morenObject.get("carAttribute01"),
                                        monthlyRent2.getCost_for_2k(), (String) morenObject.get("order_end"), monthlyRent2.getId(), carList, discount_price);
                                morenDTOList.add(moren);

                            } catch (Exception e) {
                                System.out.println("Error ! 차량이름 모렌과 맞출 것 ! 차량이름 : " + (String) morenObject.get("carCategory"));
                                continue;
                            }
                        }
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }


        // 모렌 데이터 프론트로 전달
        model.put("morenDTOList", morenDTOList);
        model.put("morenDTOListExpected", morenDTOListExpected);


        // 라디오버튼 디폴트 데이터 전달
        model.put("carType", "전체");
        model.put("kilometer", "2000km");
        model.put("rentTerm", "한달");

        return "rent_month2";
    }


    // 조건별로 차종 데이터 전달 -> order_status Integer version
    @PostMapping("/rent/month/lookup")
    public String rent_month_lookup(ModelMap model, @ModelAttribute RealTimeDTO realTimeDto) {

        // 오늘 날짜
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String today_format = df.format(cal.getTime());
        String today_date_time = df_date_time.format(cal.getTime());

        cal.add(Calendar.DATE, Integer.parseInt(expected_day));
        String after_expected_date_format = df.format(cal.getTime());

        // 모렌 데이터 객체 생성
        List<MorenDTO> morenDTOList = new ArrayList<MorenDTO>();
        List<MorenDTO> morenDTOListExpected = new ArrayList<MorenDTO>();

        // 모두의 렌터카 데이터 가져오기
        HttpURLConnection conn;
        JSONObject responseJson;

        try {
            String today_url = "https://www.moderentcar.co.kr/api/mycar/cars.php?COMPANY_ID=1343&START=" + today_format + "&END=" + today_format + "&EXPECTED_DAY=" + expected_day;
            URL url = new URL(today_url);

            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == 400 || responseCode == 401 || responseCode == 500 ) {
                System.out.println(responseCode + " Error!");
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                // list 가져오기
                responseJson = new JSONObject(sb.toString());
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
                            order_end.substring(0, 19).compareTo(today_date_time) >= 0 &&
                            order_end.substring(0, 10).compareTo(after_expected_date_format) <= 0)){

                        if (morenObject.get("reserve").equals(null)) {

                            // 차종별
                            if (realTimeDto.getCarType().equals("전체") || realTimeDto.getCarType().equals((String) morenObject.get("carGubun"))) {

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

                                    // 초기화 방법 다시 생각
                                    String kilometer_cost = "0";
                                    Long dbid = Long.parseLong("0");


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
                                    }

                                    Optional<Discount> discount_object = discountService.findDiscountByCarNo((String) morenObject.get("carNo"));
                                    String discount_price = null;
                                    if(discount_object.isPresent() && realTimeDto.getRentTerm().equals("한달")) {
                                        discount_price = discount_object.get().getDiscount();
                                    }


                                    MorenDTO moren = new MorenDTO((String) morenObject.get("carIdx"), (String) morenObject.get("carCategory"), (String) morenObject.get("carName"),
                                            (String) morenObject.get("carNo"), (String) morenObject.get("carExteriorColor"), (String) morenObject.get("carGubun"),
                                            (String) morenObject.get("carDisplacement"), (String) morenObject.get("carMileaget"), (String) morenObject.get("carColor"),
                                            (String) morenObject.get("carOld"), (String) morenObject.get("carEngine"), (String) morenObject.get("carAttribute01"),
                                            kilometer_cost, (String) morenObject.get("order_end"), dbid, carList, discount_price);

                                    if ((Integer)morenObject.get("order_status") == 0) {
                                        morenDTOList.add(moren);
                                    } else {
                                        morenDTOListExpected.add(moren);
                                    }


                                } catch (Exception e) {
                                    System.out.println("Error ! 차량이름 모렌과 맞출 것 !");
                                    continue;
                                }
                            }
                        }
                    }
                }
            }

        } catch (IOException e){
            e.printStackTrace();
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

        return "rent_month2";
    }



    // 차량 상세 페이지 -> 월렌트만 처리된 상태, 12/24개월도 처리해줄 것
    //              -> 할인가로 넘겨주기 처리해야함
    @GetMapping(value = "/rent/month/detail/{carIdx}/{monthlyrentIdx}/{kilometer}/{rentStatus}")
    public String rent_month_detail(ModelMap model, @PathVariable String carIdx, @PathVariable Long monthlyrentIdx, @PathVariable String kilometer, @PathVariable String rentStatus) throws IOException {

        // 세이브카 db에서 해당 차 객체 가져오기
        Optional<MonthlyRent> monthlyRentOptional = monthlyRentService.findById(monthlyrentIdx);
        MonthlyRent monthlyRent = monthlyRentOptional.get();
        model.put("monthlyRent", monthlyRent);

        // 모두의 렌터카 데이터 가져오기
        HttpURLConnection conn;
        JSONObject responseJson;

        // 오늘 날짜
        Date time = new Date();
        String today_format = df.format(time);
        model.put("today_format", today_format);

        try {
            String today_url = "https://www.moderentcar.co.kr/api/mycar/cars.php?COMPANY_ID=1343&START=" + today_format + "&END=" + today_format + "&EXPECTED_DAY=" + expected_day;
            URL url = new URL(today_url);

            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == 400 || responseCode == 401 || responseCode == 500 ) {
                System.out.println(responseCode + " Error!");
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                // list 가져오기
                responseJson = new JSONObject(sb.toString());
                JSONArray list_json_array = (JSONArray) responseJson.get("list");

                // list 안에 데이터
                for(int i=0; i<list_json_array.length(); i++){

                    JSONObject morenObject = (JSONObject)list_json_array.get(i);
                    String kilometer_cost = monthlyRent.getCost_for_2k();

                    // 키로수별
                    if (kilometer.equals("2000km")){
                        kilometer_cost = monthlyRent.getCost_for_2k();
                    } else if (kilometer.equals("2500km")){
                        kilometer_cost = monthlyRent.getCost_for_2_5k();
                    } else if (kilometer.equals("3000km")){
                        kilometer_cost = monthlyRent.getCost_for_3k();
                    } else if (kilometer.equals("4000km")){
                        kilometer_cost = monthlyRent.getCost_for_4k();
                    } else if (kilometer.equals("기타")){
                        kilometer_cost = monthlyRent.getCost_for_others();
                    }

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

                        Optional<Discount> discount_object = discountService.findDiscountByCarNo((String) morenObject.get("carNo"));
                        String discount_price = null;
                        if(discount_object.isPresent()) {
                            System.out.println(discount_object.get().getCarNo());
                            discount_price = discount_object.get().getDiscount();
                        }

                        MorenDTO morenDto = new MorenDTO((String) morenObject.get("carIdx"), (String) morenObject.get("carCategory"), (String) morenObject.get("carName"),
                                (String) morenObject.get("carNo"), (String) morenObject.get("carExteriorColor"), (String) morenObject.get("carGubun"),
                                (String) morenObject.get("carDisplacement"), (String) morenObject.get("carMileaget"), (String) morenObject.get("carColor"),
                                (String) morenObject.get("carOld"), (String) morenObject.get("carEngine"), (String) morenObject.get("carAttribute01"),
                                kilometer_cost, (String) morenObject.get("order_end"), monthlyrentIdx, carList, discount_price);

                        model.put("morenDto", morenDto);

                        if (kilometer.equals("기타")) {
                            model.put("tax", "상담");
                            model.put("amount_total", "상담");
                            model.put("amount_deposited", "상담");
                        } else {
                            Integer tax = (int) (Integer.parseInt(kilometer_cost) * 0.1);
                            Integer amount_total = (int) (Integer.parseInt(kilometer_cost) * 1.1);
                            Integer amount_deposited = amount_total + 300000 + 100000;

                            model.put("tax", tax);
                            model.put("amount_total", amount_total);
                            model.put("amount_deposited", amount_deposited);
                        }

                        break;

                    }
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        model.put("rentStatus", rentStatus);

        return "rent_month2_detail";
    }




    @GetMapping("/rent/month/moren/reservation")
    public String moren_reservation_test() {

        try {
            String reservation_url = "https://www.moderentcar.co.kr/api/mycar/request.php";
            URL url = new URL(reservation_url);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setDoOutput(true);

            JSONObject jsonData = new JSONObject();
            jsonData.put("COMPANY_ID", "999");
            jsonData.put("CAR_NUM", "00하0000");
            jsonData.put("ORDER_TYPE", "new");
            jsonData.put("ORDER_CUSTOMER_NAME", "테스트");
            jsonData.put("ORDER_CUSTOMER_PHONE", "010-0000-0000");
            jsonData.put("ORDER_START_TIME", "2021-11-08");
            jsonData.put("ORDER_END_TIME", "2021-12-08");

            System.out.println(jsonData.toString());

            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(conn.getOutputStream()));
            printWriter.write(jsonData.toString());
            printWriter.flush();

        } catch (IOException e){
            e.printStackTrace();
        }

        return "rent_month2";
    }






//    @GetMapping("/rent/month/new/{period}")
//    public String rent_month(ModelMap model, @PathVariable String period) {
//
//        // 모두의 렌터카 데이터 가져오기
//        HttpURLConnection conn;
//        JSONObject responseJson;
//
//
//        // 모렌 데이터 객체 생성
//        List<MorenDTO> morenDtoList = new ArrayList<MorenDTO>();
//
//        // 오늘 날짜
//        Date time = new Date();
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        String today_date = df.format(time);
//
//        try {
//            String today_url = "https://www.moderentcar.co.kr/api/mycar/cars.php?COMPANY_ID=1343&START=" + today_date + "&END=" + today_date;
//            URL url = new URL(today_url);
//
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setConnectTimeout(5000);
//            conn.setReadTimeout(5000);
//            conn.setRequestMethod("GET");
//
//            int responseCode = conn.getResponseCode();
//            if (responseCode == 400 || responseCode == 401 || responseCode == 500 ) {
//                System.out.println(responseCode + " Error!");
//            } else {
//                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                StringBuilder sb = new StringBuilder();
//                String line = "";
//                while ((line = br.readLine()) != null) {
//                    sb.append(line);
//                }
//
//                // list 가져오기
//                responseJson = new JSONObject(sb.toString());
//                JSONArray list_json_array = (JSONArray) responseJson.get("list");
//
//                // list 안에 데이터
//                for(int i=0; i<list_json_array.length(); i++){
//
//                    try {
//                        // 예약 안 잡히고, 서있는 차만
//                        if ((Integer) ((JSONObject)list_json_array.get(i)).get("order_status") == 0){
//
//                            JSONObject morenObject = (JSONObject)list_json_array.get(i);
//                            Long carOld = Long.parseLong((String)((JSONObject)list_json_array.get(i)).get("carOld"));
//
//
//                            // 차량 이미지
//                            List<String> carList = new ArrayList<>();
//
//                            if (!morenObject.get("carThumbImages").equals(null)) {
//                                JSONArray carJsonArray = (JSONArray) (morenObject.get("carThumbImages"));
//                                for (int j = 0; j < carJsonArray.length(); j++) {
//                                    carList.add((String) carJsonArray.get(j));
//                                }
//                            }
//                            try {
//                                // 자체 db에서 가격 정보 가져오기
//                                String default_price = new String();
//
//                                MonthlyRent monthlyRent2 = monthlyRentService.findByMorenCar(carOld,carOld, (String)((JSONObject)list_json_array.get(i)).get("carCategory"));
//                                YearlyRent yearlyRent = yearlyRentService.findByMorenCar(carOld, carOld, (String)((JSONObject)list_json_array.get(i)).get("carCategory"));
//                                TwoYearlyRent twoYearlyRent = twoYearlyRentService.findByMorenCar(carOld, carOld, (String)((JSONObject)list_json_array.get(i)).get("carCategory"));
//
//                                if (period.equals("한달")){
//                                    default_price = monthlyRent2.getCost_for_2k();
//                                } else if(period.equals("12개월")){
//                                    default_price = yearlyRent.getCost_for_20k();
//                                } else if(period.equals("24개월")){
//                                    default_price = twoYearlyRent.getCost_for_20Tk();
//                                }
//
//                                MorenDTO moren = new MorenDTO((String)morenObject.get("carIdx"),(String)morenObject.get("carCategory"),(String)morenObject.get("carName"),
//                                        (String)morenObject.get("carNo"),(String)morenObject.get("carExteriorColor"),(String)morenObject.get("carGubun"),
//                                        (String)morenObject.get("carDisplacement"),(String)morenObject.get("carMileaget"),(String)morenObject.get("carColor"),
//                                        (String)morenObject.get("carOld"),(String)morenObject.get("carEngine"),(String)morenObject.get("carAttribute01"),
//                                        default_price, (String)morenObject.get("order_end"), monthlyRent2.getId(), carList);
//                                morenDtoList.add(moren);
//
//                            } catch (Exception e){
//                                System.out.println("Error ! 차량이름 모렌과 맞출 것 !");
//                                continue;
//                            }
//
//                        }
//                    } catch (ClassCastException e){
//                        continue;
//                    }
//                }
//            }
//
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//
//
//
//        // 모렌 데이터 프론트로 전달
//        model.put("morenDtoList", morenDtoList);
//
//        // 라디오버튼 디폴트 데이터 전달
//        model.put("carType", "전체");
//        if(period.equals("한달")){
//            model.put("kilometer", "2000km");
//        } else {
//            model.put("kilometer", "20000km");
//        }
//        model.put("reservation", "possible");
//        model.put("rentTerm", period);
//
//
//        return "rent_month2";
//    }
//
//




}