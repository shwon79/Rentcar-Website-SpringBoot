package kr.carz.savecar.controller;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.MonthlyRentService;
import kr.carz.savecar.service.ReservationService;
import kr.carz.savecar.service.TwoYearlyRentService;
import kr.carz.savecar.service.YearlyRentService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    @Autowired
    public RealtimeRentController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService, TwoYearlyRentService twoYearlyRentService,
                                  ReservationService reservationService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.twoYearlyRentService = twoYearlyRentService;
        this.reservationService = reservationService;
    }


    /* ======================================================================================== */
    /*                              [New 버전] 실시간 월렌트 예약하                                    */
    /* ======================================================================================== */

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
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat df_date_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String today_date = df.format(cal.getTime());
        String today_date_time = df_date_time.format(cal.getTime());



        // 며칠 이내로 할지 설정
        String expected_day = "3";

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

                    // 3일 이내
                    try {

                        // json object 가져오기
                        String order_end = ((String)((JSONObject)list_json_array.get(i)).get("order_end"));

                        if ( ((JSONObject)list_json_array.get(i)).get("order_status").equals("2") &&
                                order_end.length() >= 19 &&
                                order_end.substring(0, 19).compareTo(today_date_time) >= 0 &&  // 시간 고려해서
                                order_end.substring(0, 10).compareTo(after_expected_date_format) <= 0 // 날짜만 고려해서
                        ) {

                            JSONObject morenObject = (JSONObject)list_json_array.get(i);
                            Long carOld = Long.parseLong((String)((JSONObject)list_json_array.get(i)).get("carOld"));

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
                                    MonthlyRent monthlyRent2 = monthlyRentService.findByMorenCar(carOld, carOld, (String) ((JSONObject) list_json_array.get(i)).get("carCategory"));

                                    MorenDTO moren = new MorenDTO((String) morenObject.get("carIdx"), (String) morenObject.get("carCategory"), (String) morenObject.get("carName"),
                                            (String) morenObject.get("carNo"), (String) morenObject.get("carExteriorColor"), (String) morenObject.get("carGubun"),
                                            (String) morenObject.get("carDisplacement"), (String) morenObject.get("carMileaget"), (String) morenObject.get("carColor"),
                                            (String) morenObject.get("carOld"), (String) morenObject.get("carEngine"), (String) morenObject.get("carAttribute01"),
                                            monthlyRent2.getCost_for_2k(), (String) morenObject.get("order_end"), monthlyRent2.getId(), carList);
                                    morenDTOListExpected.add(moren);

                                } catch (Exception e) {

                                    System.out.println("Error ! 차량이름 모렌과 맞출 것 !");
                                    continue;
                                }
                            }

                        }
                    } catch (ClassCastException e){
                        continue;
                    }

                    // 현재 가능차
                    try {

                        // 예약 안 잡히고, 서있는 차만 
                        if ((Integer) ((JSONObject)list_json_array.get(i)).get("order_status") == 0){

                            JSONObject morenObject = (JSONObject)list_json_array.get(i);
                            Long carOld = Long.parseLong((String)((JSONObject)list_json_array.get(i)).get("carOld"));

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
                                    MonthlyRent monthlyRent2 = monthlyRentService.findByMorenCar(carOld, carOld, (String) ((JSONObject) list_json_array.get(i)).get("carCategory"));

                                    MorenDTO moren = new MorenDTO((String) morenObject.get("carIdx"), (String) morenObject.get("carCategory"), (String) morenObject.get("carName"),
                                            (String) morenObject.get("carNo"), (String) morenObject.get("carExteriorColor"), (String) morenObject.get("carGubun"),
                                            (String) morenObject.get("carDisplacement"), (String) morenObject.get("carMileaget"), (String) morenObject.get("carColor"),
                                            (String) morenObject.get("carOld"), (String) morenObject.get("carEngine"), (String) morenObject.get("carAttribute01"),
                                            monthlyRent2.getCost_for_2k(), (String) morenObject.get("order_end"), monthlyRent2.getId(), carList);
                                    morenDTOList.add(moren);

                                } catch (Exception e) {
                                    System.out.println("Error ! 차량이름 모렌과 맞출 것 !");
                                    continue;
                                }
                            }

                        }

                    } catch (ClassCastException e){
                        continue;
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
        model.put("reservation", "possible");
        model.put("rentTerm", "한달");

        return "rent_month2";
    }





    // 조건별로 차종 데이터 전달
    @PostMapping("/rent/month/lookup")
    public String rent_month_lookup(ModelMap model, @ModelAttribute RealTimeDTO realTimeDto) {

        // 모두의 렌터카 데이터 가져오기
        HttpURLConnection conn;
        JSONObject responseJson;

        // 모렌 데이터 객체 생성
        List<MorenDTO> morenDTOList = new ArrayList<MorenDTO>();
        List<MorenDTO> morenDTOListExpected = new ArrayList<MorenDTO>();

        // 오늘 날짜
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String today_format = df.format(cal.getTime());

        // 며칠 이내로 할지 설정
        String expected_day = "3";

        cal.add(Calendar.DATE, Integer.parseInt(expected_day));
        String after_expected_date_format = df.format(cal.getTime());


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

                    try {
                        // json object 가져오기
                        JSONObject morenObject = (JSONObject)list_json_array.get(i);
                        String order_end = ((String)((JSONObject)list_json_array.get(i)).get("order_end"));


                        Integer expected_flg = 1;
                        java.lang.Integer tmp = 0;

                        // 예약가능차량
                        if ((((JSONObject)list_json_array.get(i)).get("order_status").getClass().isInstance(tmp))
                            || (((JSONObject)list_json_array.get(i)).get("order_status").equals("2") &&
                                order_end.length() > 10 &&
                                order_end.substring(0, 10).compareTo(today_format) >= 0 && order_end.substring(0, 10).compareTo(after_expected_date_format) <= 0)
                        ){

                            if (morenObject.get("reserve").equals(null)) {
                                if (((JSONObject) list_json_array.get(i)).get("order_status").getClass().isInstance(tmp)) {
                                    expected_flg = 0;
                                }


                                // 차종별
                                if (realTimeDto.getCarType().equals("전체") || realTimeDto.getCarType().equals((String) morenObject.get("carGubun"))) {

                                    Long carOld = Long.parseLong((String) ((JSONObject) list_json_array.get(i)).get("carOld"));

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
                                        MonthlyRent monthlyRent2 = monthlyRentService.findByMorenCar(carOld, carOld, (String) ((JSONObject) list_json_array.get(i)).get("carCategory"));
                                        YearlyRent yearlyRent = yearlyRentService.findByMorenCar(carOld, carOld, (String) ((JSONObject) list_json_array.get(i)).get("carCategory"));

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
                                            } else {
                                                kilometer_cost = yearlyRent.getCost_for_20k();
                                            }

                                            dbid = yearlyRent.getId();
                                        }


                                        MorenDTO moren = new MorenDTO((String) morenObject.get("carIdx"), (String) morenObject.get("carCategory"), (String) morenObject.get("carName"),
                                                (String) morenObject.get("carNo"), (String) morenObject.get("carExteriorColor"), (String) morenObject.get("carGubun"),
                                                (String) morenObject.get("carDisplacement"), (String) morenObject.get("carMileaget"), (String) morenObject.get("carColor"),
                                                (String) morenObject.get("carOld"), (String) morenObject.get("carEngine"), (String) morenObject.get("carAttribute01"),
                                                kilometer_cost, (String) morenObject.get("order_end"), dbid, carList);

                                        if (expected_flg == 0) {
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

                    } catch (ClassCastException e){
                        System.out.println(e);
                        continue;
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

        if (realTimeDto.getRentTerm().equals("12개월") || realTimeDto.getRentTerm().equals("24개월") ){

            if (!realTimeDto.getKilometer().equals("20000km") && !realTimeDto.getKilometer().equals("30000km")  && !realTimeDto.getKilometer().equals("40000km")){
                model.put("kilometer", "20000km");
            } else {
                model.put("kilometer", realTimeDto.getKilometer());
            }
        } else if (realTimeDto.getRentTerm().equals("한달")) {

            if (!realTimeDto.getKilometer().equals("2000km") && !realTimeDto.getKilometer().equals("2500km") && !realTimeDto.getKilometer().equals("3000km")  && !realTimeDto.getKilometer().equals("4000km")){
                model.put("kilometer", "2000km");
            } else {
                model.put("kilometer", realTimeDto.getKilometer());
            }
        }

        model.put("reservation", realTimeDto.getReserve_able());
        model.put("rentTerm", realTimeDto.getRentTerm());
        model.put("kilometer", realTimeDto.getKilometer());


        return "rent_month2";
    }



    // 차량 상세 페이지
    @GetMapping(value = "/rent/month/detail/{carIdx}/{monthlyrentIdx}/{kilometer}/{rentStatus}")
    public String rent_month_detail(ModelMap model, @PathVariable String carIdx, @PathVariable Long monthlyrentIdx, @PathVariable String kilometer, @PathVariable String rentStatus) throws IOException {

        // 세이브카 db에서 해당 차 객체 가져오기
        Optional<MonthlyRent> monthlyRentOptional = monthlyRentService.findById(monthlyrentIdx);
        MonthlyRent monthlyRent = monthlyRentOptional.get();
        model.put("monthlyRent", monthlyRent);

        // 모두의 렌터카 데이터 가져오기
        HttpURLConnection conn;
        JSONObject responseJson;

        // 모렌 데이터 객체 생성
        List<MorenDTO> morenDTOList = new ArrayList<MorenDTO>();


        // 오늘 날짜
        Date time = new Date();
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd");
        String today_format = format.format(time);
        model.put("today_format", today_format);

        try {
            String expected_day = "3";
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

                        MorenDTO morenDto = new MorenDTO((String) morenObject.get("carIdx"), (String) morenObject.get("carCategory"), (String) morenObject.get("carName"),
                                (String) morenObject.get("carNo"), (String) morenObject.get("carExteriorColor"), (String) morenObject.get("carGubun"),
                                (String) morenObject.get("carDisplacement"), (String) morenObject.get("carMileaget"), (String) morenObject.get("carColor"),
                                (String) morenObject.get("carOld"), (String) morenObject.get("carEngine"), (String) morenObject.get("carAttribute01"),
                                kilometer_cost, (String) morenObject.get("order_end"), monthlyrentIdx, carList);

                        model.put("morenDto", morenDto);

                        Integer tax = (int)(Integer.parseInt(kilometer_cost) * 0.1);
                        Integer amount_total = (int)(Integer.parseInt(kilometer_cost) * 1.1);
                        Integer amount_deposited = amount_total + 300000 + 100000;

                        // 프론트로 금액 정보 전달
                        model.put("tax", tax);
                        model.put("amount_total", amount_total);
                        model.put("amount_deposited", amount_deposited);

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