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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MonthlyRentController {

    private final MonthlyRentService monthlyRentService;
    private final YearlyRentService yearlyRentService;
    private final TwoYearlyRentService twoYearlyRentService;
    private final ReservationService reservationService;

    @Autowired
    public MonthlyRentController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService, TwoYearlyRentService twoYearlyRentService,
                                 ReservationService reservationService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.twoYearlyRentService = twoYearlyRentService;
        this.reservationService = reservationService;
    }






    @GetMapping("/rent/month")
    public String rent_month() {
        return "rent_month";
    }

    @RequestMapping("/rent/month/{category1}/{category2}")
    public String handleRequest(ModelMap model, @PathVariable("category1") String category1, @PathVariable("category2") String category2) throws Exception {
        model.put("category1", category1);
        model.put("category2", category2);

        return "rent_month";
    }



    /* ======================================================================================== */
    /*                               [New 버전] 실시간 견적내기                                    */
    /* ======================================================================================== */

    @GetMapping("/rent/month/test")
    public String rent_month(ModelMap model) {

        // 모두의 렌터카 데이터 가져오기
        HttpURLConnection conn;
        JSONObject responseJson;

        // 총 대수
        Integer total_num = 0;

        // 모렌 데이터 객체 생성
        List<MorenDto> morenDtoList = new ArrayList<MorenDto>();

        // 오늘 날짜
        Date time = new Date();
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd");
        String today_format = format.format(time);

        try {
            String today_url = "https://www.moderentcar.co.kr/api/mycar/cars.php?COMPANY_ID=1343&START=" + today_format + "&END=" + today_format;
            URL url = new URL(today_url);

            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            //conn.setDoOutput(true);

            JSONObject commands = new JSONObject();

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
                        // 예약 안 잡히고, 서있는 차만 
                        if ((Integer) ((JSONObject)list_json_array.get(i)).get("order_status") == 0){

                            total_num += 1;

                            JSONObject morenObject = (JSONObject)list_json_array.get(i);
                            Long carOld = Long.parseLong((String)((JSONObject)list_json_array.get(i)).get("carOld"));

                            try {
                                // 자체 db에서 가격 정보 가져오기
                                MonthlyRent monthlyRent2 = monthlyRentService.findByMorenCar(carOld,carOld, (String)((JSONObject)list_json_array.get(i)).get("carCategory"));

                                MorenDto moren = new MorenDto((String)morenObject.get("carIdx"),(String)morenObject.get("carCategory"),(String)morenObject.get("carName"),
                                                                (String)morenObject.get("carNo"),(String)morenObject.get("carExteriorColor"),(String)morenObject.get("carGubun"),
                                                                (String)morenObject.get("carDisplacement"),(String)morenObject.get("carMileaget"),(String)morenObject.get("carColor"),
                                                                (String)morenObject.get("carOld"),(String)morenObject.get("carEngine"),(String)morenObject.get("carAttribute01"),
                                                                monthlyRent2.getCost_for_2k(), (String)morenObject.get("order_end"), monthlyRent2.getId());
                                morenDtoList.add(moren);

                            } catch (Exception e){
                                System.out.println("Error ! 차량이름 모렌과 맞출 것 !");
                                continue;
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
        model.put("morenDtoList", morenDtoList);

        // 라디오버튼 디폴트 데이터 전달
        model.put("carType", "전체");
        model.put("kilometer", "2000km");
        model.put("reservation", "possible");

        // 총 대수 전달
        model.put("total_num", total_num);

        return "rent_month2";
    }



    // 조건별로 차종 데이터 전달
    @PostMapping("/rent/month/lookup")
    public String rent_month_lookup(ModelMap model, @ModelAttribute RealTimeDto realTimeDto) {

        // 모두의 렌터카 데이터 가져오기
        HttpURLConnection conn;
        JSONObject responseJson;

        // 총 대수
        Integer total_num = 0;

        // 모렌 데이터 객체 생성
        List<MorenDto> morenDtoList = new ArrayList<MorenDto>();

        // 오늘 날짜
        Date time = new Date();
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd");
        String today_format = format.format(time);

        try {
            String today_url = "https://www.moderentcar.co.kr/api/mycar/cars.php?COMPANY_ID=1343&START=" + today_format + "&END=" + today_format;
            URL url = new URL(today_url);

            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            //conn.setDoOutput(true);

            JSONObject commands = new JSONObject();

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


                        // 예약가능차량
                        if ((realTimeDto.getReserve_able().equals("possible") && (Integer)((JSONObject)list_json_array.get(i)).get("order_status") == 0)
                            || (realTimeDto.getReserve_able().equals("expected") && ((JSONObject)list_json_array.get(i)).get("order_status").equals("2"))){

                            // 차종별
                            if(realTimeDto.getCarType().equals("전체") || realTimeDto.getCarType().equals((String)morenObject.get("carGubun"))) {

                                total_num += 1;

                                Long carOld = Long.parseLong((String) ((JSONObject) list_json_array.get(i)).get("carOld"));

                                try {
                                    // 자체 db에서 가격 정보 가져오기
                                    MonthlyRent monthlyRent2 = monthlyRentService.findByMorenCar(carOld, carOld, (String) ((JSONObject) list_json_array.get(i)).get("carCategory"));
                                    String kilometer_cost = monthlyRent2.getCost_for_2k();

                                    // 키로수별
                                    if (realTimeDto.getKilometer().equals("2000km")){
                                        kilometer_cost = monthlyRent2.getCost_for_2k();
                                    } else if (realTimeDto.getKilometer().equals("2500km")){
                                        kilometer_cost = monthlyRent2.getCost_for_2_5k();
                                    } else if (realTimeDto.getKilometer().equals("3000km")){
                                        kilometer_cost = monthlyRent2.getCost_for_3k();
                                    } else if (realTimeDto.getKilometer().equals("4000km")){
                                        kilometer_cost = monthlyRent2.getCost_for_4k();
                                    }

                                    MorenDto moren = new MorenDto((String) morenObject.get("carIdx"), (String) morenObject.get("carCategory"), (String) morenObject.get("carName"),
                                            (String) morenObject.get("carNo"), (String) morenObject.get("carExteriorColor"), (String) morenObject.get("carGubun"),
                                            (String) morenObject.get("carDisplacement"), (String) morenObject.get("carMileaget"), (String) morenObject.get("carColor"),
                                            (String) morenObject.get("carOld"), (String) morenObject.get("carEngine"), (String) morenObject.get("carAttribute01"),
                                            kilometer_cost, (String) morenObject.get("order_end"), monthlyRent2.getId());
                                    morenDtoList.add(moren);

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
        model.put("morenDtoList", morenDtoList);

        // 라디오버튼 데이터 전달
        model.put("carType", realTimeDto.getCarType());
        model.put("kilometer", realTimeDto.getKilometer());
        model.put("reservation", realTimeDto.getReserve_able());

        // 총 대수 전달
        model.put("total_num", total_num);

        return "rent_month2";
    }



    // 차량 상세 페이지
    @GetMapping(value = "/rent/month/detail/{carIdx}/{monthlyrentIdx}/{kilometer}")
    public String rent_month_detail(ModelMap model, @PathVariable String carIdx, @PathVariable Long monthlyrentIdx, @PathVariable String kilometer) throws IOException {

        // 세이브카 db에서 해당 차 객체 가져오기
        Optional<MonthlyRent> monthlyRentOptional = monthlyRentService.findById(monthlyrentIdx);
        MonthlyRent monthlyRent = monthlyRentOptional.get();
        model.put("monthlyRent", monthlyRent);

        // 모두의 렌터카 데이터 가져오기
        HttpURLConnection conn;
        JSONObject responseJson;

        // 모렌 데이터 객체 생성
        List<MorenDto> morenDtoList = new ArrayList<MorenDto>();


        // 오늘 날짜
        Date time = new Date();
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd");
        String today_format = format.format(time);
        model.put("today_format", today_format);

        try {
            String today_url = "https://www.moderentcar.co.kr/api/mycar/cars.php?COMPANY_ID=1343&START=" + today_format + "&END=" + today_format;
            URL url = new URL(today_url);

            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            //conn.setDoOutput(true);

            JSONObject commands = new JSONObject();

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

                        MorenDto morenDto = new MorenDto((String) morenObject.get("carIdx"), (String) morenObject.get("carCategory"), (String) morenObject.get("carName"),
                                (String) morenObject.get("carNo"), (String) morenObject.get("carExteriorColor"), (String) morenObject.get("carGubun"),
                                (String) morenObject.get("carDisplacement"), (String) morenObject.get("carMileaget"), (String) morenObject.get("carColor"),
                                (String) morenObject.get("carOld"), (String) morenObject.get("carEngine"), (String) morenObject.get("carAttribute01"),
                                kilometer_cost, (String) morenObject.get("order_end"), monthlyrentIdx);

                        model.put("morenDto", morenDto);
                        break;

                    }

                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }


        return "rent_month2_detail";
    }






    /* ======================================================================================== */
    /*                               [이전 버전] 실시간 견적내기                                    */
    /* ======================================================================================== */


    // 차종 api
    @RequestMapping(value = "/rent/month/{period}", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public void get_monthly_rent_category1(HttpServletResponse res, HttpServletRequest req, @PathVariable String period) throws IOException {

        HashSet<String> categoryList = new HashSet<String>();

        if (period.equals("rentMonth")) {
            List<MonthlyRent> monthlyRents = monthlyRentService.findMonthlyRents();

            for (int i = 0; i < monthlyRents.size(); i++) {
                categoryList.add(monthlyRents.get(i).getCategory1());
            }
        } else if (period.equals("rentYear")) {
            List<YearlyRent> yearlyRents = yearlyRentService.findYearlyRents();

            for (int i = 0; i < yearlyRents.size(); i++) {
                categoryList.add(yearlyRents.get(i).getCategory1());
            }
        } else if (period.equals("rent2Year")) {
            List<TwoYearlyRent> twoYearlyRents = twoYearlyRentService.findTwoYearlyRents();

            for (int i = 0; i < twoYearlyRents.size(); i++) {
                categoryList.add(twoYearlyRents.get(i).getCategory1());
            }
        } else {
            throw new NullPointerException();
        }


        List<String> categoryHashToList = new ArrayList(categoryList);
        Collections.sort(categoryHashToList);

        JSONArray jsonArray = new JSONArray();
        for (String c : categoryHashToList) {
            jsonArray.put(c);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }

    //차 분류api
    @RequestMapping(value = "/rent/month/{period}/{category1}", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public void get_monthly_rent_category2(HttpServletResponse res, @PathVariable String period, @PathVariable String category1) throws IOException {


        List<String> categoryList2 = new ArrayList();


        if (period.equals("rentMonth")) {
            List<MonthlyRent> monthlyRents = monthlyRentService.findCategory2OfMonthlyRents(category1);

            for (int i = 0; i < monthlyRents.size(); i++) {
                if (!categoryList2.contains(monthlyRents.get(i).getCategory2())) {
                    categoryList2.add(monthlyRents.get(i).getCategory2());
                }
            }
        } else if (period.equals("rentYear")) {
            List<YearlyRent> yearlyRents = yearlyRentService.findCategory2OfMonthlyRents(category1);

            for (int i = 0; i < yearlyRents.size(); i++) {
                if (!categoryList2.contains(yearlyRents.get(i).getCategory2())) {
                    categoryList2.add(yearlyRents.get(i).getCategory2());
                }
            }
        } else if (period.equals("rent2Year")) {
            List<TwoYearlyRent> twoYearlyRents = twoYearlyRentService.findByCategory1(category1);

            for (int i = 0; i < twoYearlyRents.size(); i++) {
                if (!categoryList2.contains(twoYearlyRents.get(i).getCategory2())) {
                    categoryList2.add(twoYearlyRents.get(i).getCategory2());
                }
            }
        } else {
            throw new NullPointerException();
        }

        JSONArray jsonArray = new JSONArray();

        for (String c : categoryList2) {
            jsonArray.put(c);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }

    // 차명 api
    @RequestMapping(value = "/rent/month/{period}/name/{category1}/{category2}", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public void get_monthly_rent_name(HttpServletResponse res, @PathVariable String period, @PathVariable String category1, @PathVariable String category2) throws IOException {

        HashSet<String> categoryList = new HashSet<String>();


        if (period.equals("rentMonth")) {
            List<MonthlyRent> monthlyRents = monthlyRentService.findNameOfMonthlyRents(category1, category2);

            for (int i = 0; i < monthlyRents.size(); i++) {
                categoryList.add(monthlyRents.get(i).getName());
            }
        } else if (period.equals("rentYear")) {
            List<YearlyRent> yearlyRents = yearlyRentService.findNameOfYearlyRents(category1, category2);

            for (int i = 0; i < yearlyRents.size(); i++) {
                categoryList.add(yearlyRents.get(i).getName());
            }
        } else if (period.equals("rent2Year")) {
            List<TwoYearlyRent> twoYearlyRents = twoYearlyRentService.findNameOfTwoYearlyRents(category1, category2);

            for (int i = 0; i < twoYearlyRents.size(); i++) {
                categoryList.add(twoYearlyRents.get(i).getName());
            }
        } else {
            throw new NullPointerException();
        }


        List<String> categoryHashToList = new ArrayList(categoryList);
        Collections.sort(categoryHashToList);


        JSONArray jsonArray = new JSONArray();
        for (String c : categoryHashToList) {
            jsonArray.put(c);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }

    //가격 구하는 api
    @RequestMapping(value = "/rent/month/{period}/price/{carName}/{mileage}", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public void get_monthly_price(HttpServletResponse res, @PathVariable String period, @PathVariable String carName, @PathVariable String mileage) throws IOException {


        JSONArray jsonArray = new JSONArray();

        if (period.equals("rentMonth")) {
            MonthlyRent rentCar = monthlyRentService.findPrice(carName);

            if (mileage.equals("2500")) {
                jsonArray.put(rentCar.getCost_for_2_5k());
            } else if (mileage.equals("2000")) {
                jsonArray.put(rentCar.getCost_for_2k());
            } else if (mileage.equals("3000")) {
                jsonArray.put(rentCar.getCost_for_3k());
            } else if (mileage.equals("4000")) {
                jsonArray.put(rentCar.getCost_for_4k());
            } else if (mileage.equals("기타주행거리")) {
                jsonArray.put(rentCar.getCost_for_others());
            }

            jsonArray.put(rentCar.getDeposit());
            jsonArray.put(rentCar.getAge_limit());

        } else if (period.equals("rentYear")) {
            YearlyRent rentCar = yearlyRentService.findPrice(carName);

            if (mileage.equals("20000")) {
                jsonArray.put(rentCar.getCost_for_20k());
            } else if (mileage.equals("30000")) {
                jsonArray.put(rentCar.getCost_for_30k());
            } else if (mileage.equals("40000")) {
                jsonArray.put(rentCar.getCost_for_40k());
            } else if (mileage.equals("기타주행거리")) {
                jsonArray.put(rentCar.getCost_for_others());
            }

            jsonArray.put(rentCar.getDeposit());
            jsonArray.put(rentCar.getAge_limit());

        } else if (period.equals("rent2Year")) {
            TwoYearlyRent rentCar = twoYearlyRentService.findPrice(carName);

            if (mileage.equals("20000")) {
                jsonArray.put(rentCar.getCost_for_20Tk());
            } else if (mileage.equals("30000")) {
                jsonArray.put(rentCar.getCost_for_30Tk());
            } else if (mileage.equals("40000")) {
                jsonArray.put(rentCar.getCost_for_40Tk());
            } else if (mileage.equals("기타주행거리")) {
                jsonArray.put(rentCar.getCost_for_others());
            }

            jsonArray.put(rentCar.getDeposit());
            jsonArray.put(rentCar.getAge_limit());

        } else {
            throw new NullPointerException();
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }


}