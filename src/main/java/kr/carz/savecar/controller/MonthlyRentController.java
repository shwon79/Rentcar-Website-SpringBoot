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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

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
    public String rent_month(ModelMap model) {

        // 모두의 렌터카 데이터 가져오기
        HttpURLConnection conn;
        JSONObject responseJson;

        try {
            String today_url = "https://www.moderentcar.co.kr/api/mycar/cars.php?COMPANY_ID=1343&START=" + "2021-09-17&END=2021-09-17";
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

                responseJson = new JSONObject(sb.toString());
//                System.out.println(responseJson.get("total"));
//                System.out.println(responseJson.get("start"));
//                System.out.println(((JSONArray) responseJson.get("list")).get(0));

                JSONArray list_json_array = (JSONArray) responseJson.get("list");

                for(int i=0; i<list_json_array.length(); i++){

                    try {
                        if ((Integer) ((JSONObject)list_json_array.get(i)).get("order_status") == 0){
                            System.out.println(((JSONObject)list_json_array.get(i)).get("order_status"));
                        }
                    } catch (ClassCastException e){
                        continue;
                    }
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }


        // 세이브카 db 데이터 가져오기
        List<MonthlyRent> monthlyRentList = monthlyRentService.findMonthlyRents();
        List<String> priceList = new ArrayList<>();

        for (int i=0; i<monthlyRentList.size(); i++){
            priceList.add(monthlyRentList.get(i).getCost_for_2k());
        }

        model.put("monthlyRentList", monthlyRentList);
        model.put("priceList", priceList);

        // 라디오버튼 디폴트 데이터 전달
        model.put("carType", "전체");
        model.put("kilometer", "2000km");
        model.put("reservation", "possible");


        return "rent_month2";
    }



    @PostMapping("/rent/month/realtime")
    public String rent_month_realtime(ModelMap model, @ModelAttribute RealTimeDto realTimeDto) {
        // 모두의 렌터카 데이터 가져오기
//        try {
//            URL url = new URL("https://www.moderentcar.co.kr/api/mycar/cars.php?COMPANY_ID=1343&START=2021-09-01&END=2021-09-02");
//
//            conn = (HttpURLConnection)url.openConnection();
//            conn.setConnectTimeout(5000);
//            conn.setReadTimeout(5000);
//            conn.setRequestMethod("GET");
//            //conn.setDoOutput(true);
//
//            JSONObject commands = new JSONObject();
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
//                responseJson = new JSONObject(sb.toString());
//                System.out.println(responseJson.get("total"));
//                System.out.println(responseJson.get("start"));
////                System.out.println(((JSONArray) responseJson.get("reserve")).get(0));
//
//
//
//
//            }
//
//        } catch (IOException e){
//            e.printStackTrace();
//        }


        // 세이브카 db 데이터 가져오기
        List<MonthlyRent> monthlyRentList = monthlyRentService.findMonthlyRents();
        List<String> priceList = new ArrayList<>();

        for (int i=0; i<monthlyRentList.size(); i++){
            priceList.add(monthlyRentList.get(i).getCost_for_2k());
        }

        model.put("monthlyRentList", monthlyRentList);
        model.put("priceList", priceList);

        // 라디오버튼 데이터 전달
        model.put("carType", realTimeDto.getCarType());
        model.put("kilometer", realTimeDto.getKilometer());
        model.put("reservation", realTimeDto.getReserve_able());


        return "rent_month2";
    }

    @RequestMapping("/rent/month/{category1}/{category2}")
    public String handleRequest(ModelMap model, @PathVariable("category1") String category1, @PathVariable("category2") String category2) throws Exception {
        model.put("category1", category1);
        model.put("category2", category2);

        return "rent_month";
    }


    /* ======================================================================================== */
    /*                                   실시간 차량조회 및 예약                                    */
    /* ======================================================================================== */


    @RequestMapping("/rent/month/realtime/{carType}/{kilometer}")
    public String retrieve_data_by_car_type(ModelMap model, @PathVariable("carType") String carType, @PathVariable("kilometer") String kilometer) throws Exception {

        // 모렌 데이터 가져오기
//        try {
//            URL url = new URL("https://www.moderentcar.co.kr/api/mycar/cars.php?COMPANY_ID=1343&START=2021-09-01&END=2021-09-02");
//
//            conn = (HttpURLConnection)url.openConnection();
//            conn.setConnectTimeout(5000);
//            conn.setReadTimeout(5000);
//            conn.setRequestMethod("GET");
//            //conn.setDoOutput(true);
//
//            JSONObject commands = new JSONObject();
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
//                responseJson = new JSONObject(sb.toString());
//                System.out.println(responseJson.get("total"));
//                System.out.println(responseJson.get("start"));
////                System.out.println(((JSONArray) responseJson.get("reserve")).get(0));
//
//
//
//
//            }
//
//        } catch (IOException e){
//            e.printStackTrace();
//        }
        List<MonthlyRent> monthlyRentList = monthlyRentService.findByCategory2(carType);

        List<String> priceList = new ArrayList<>();

        if (kilometer.equals("2000km")){
            for (int i=0; i<monthlyRentList.size(); i++){
                priceList.add(monthlyRentList.get(i).getCost_for_2k());
            }
        } else if (kilometer.equals("2500km")){
            for (int i=0; i<monthlyRentList.size(); i++){
                priceList.add(monthlyRentList.get(i).getCost_for_2_5k());
            }
        } else if (kilometer.equals("3000km")){
            for (int i=0; i<monthlyRentList.size(); i++){
                priceList.add(monthlyRentList.get(i).getCost_for_3k());
            }
        } else if (kilometer.equals("4000km")){
            for (int i=0; i<monthlyRentList.size(); i++){
                priceList.add(monthlyRentList.get(i).getCost_for_4k());
            }
        }
        model.put("monthlyRentList", monthlyRentList);
        model.put("priceList", priceList);

        return "rent_month2";
    }




    /* ======================================================================================== */
    /*                                          실시간 견적내기                                    */
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

        HttpURLConnection conn = null;
        JSONObject responseJson = null;

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