package kr.carz.savecar.controller.RentCar;

import kr.carz.savecar.controller.ReservationController;
import kr.carz.savecar.controller.Utils.HttpConnection;
import kr.carz.savecar.controller.Utils.Rent24Connection;
import kr.carz.savecar.dto.*;
import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.*;
import org.springframework.ui.Model;
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
public class RealtimeRentController implements RentInterface {

    private final MonthlyRentService monthlyRentService;
    private final DiscountService discountService;
    private final RealTimeRentCarService realTimeRentService;
    private final RealTimeRentCarImageService realTimeRentImageService;
    private final ExpectedDayService expectedDayService;
    private final Rent24Service rent24Service;

    @Autowired
    public RealtimeRentController(MonthlyRentService monthlyRentService,
                                  DiscountService discountService, RealTimeRentCarService realTimeRentService,
                                  RealTimeRentCarImageService realTimeRentImageService, ExpectedDayService expectedDayService,
                                  Rent24Service rent24Service) {
        this.monthlyRentService = monthlyRentService;
        this.discountService = discountService;
        this.realTimeRentService = realTimeRentService;
        this.realTimeRentImageService = realTimeRentImageService;
        this.expectedDayService = expectedDayService;
        this.rent24Service =  rent24Service;
    }

    /* ======================================================================================== */
    /*                             [New 버전] 실시간 월렌트 예약하기                                */
    /* ======================================================================================== */

    @Value("${phone.admin2}")
    private String admin2;

    @Value("${phone.admin3}")
    private String admin3;

    @Value("${moren_url}")
    private String moren_url_except_date;

    // 모렌 대기차 DB로 저장
    @Scheduled(cron = "0 0/10 * * * *")
    public void rent_month_save() throws IOException {
        rent_month_save_update();
        update_rent24_data();
    }

    Comparator<RealTimeRentCar> comparator = new Comparator<RealTimeRentCar>() {
        @Override
        public int compare(RealTimeRentCar a, RealTimeRentCar b) {
            return Long.compare(b.getRealTimeRentId(), a.getRealTimeRentId());
        }
    };
    Comparator<RealTimeRentCarImage> comparatorImage = new Comparator<RealTimeRentCarImage>() {
        @Override
        public int compare(RealTimeRentCarImage a, RealTimeRentCarImage b) {
            return Long.compare(b.getImageId(), a.getImageId());
        }
    };

    public void update_rent24_data() throws IOException {
        // rent24(https://rent-24.co.kr/) 에서 event data 가져옴
        Rent24Connection rent24Connection = new Rent24Connection();
        List<Rent24EventVO> rent24EventList = rent24Connection.getEventCars();

        rent24Service.deleteAllInBatch();
        Long id = 1L;
        for(Rent24EventVO event : rent24EventList){
            Rent24 rent24 = new Rent24(id++, event.getCarTitle(), event.getPeriod(), event.getDeposit(), event.getRentPrice(), event.getImgUrl());
            rent24Service.save(rent24);
        }
    }

    public void rent_month_save_update(){

        List<ExpectedDay> expectedDayList = expectedDayService.findAll();
        String expected_day = expectedDayList.get(0).getExpectedDay();
        String moren_url = moren_url_except_date + DateTime.today_date_only() + "&END=" + DateTime.today_date_only() + "&EXPECTED_DAY=" + expected_day;

        HttpConnection http = new HttpConnection();
        JSONObject responseJson = http.sendGetRequest(moren_url);
        JSONArray list_json_array = (JSONArray) responseJson.get("list");

        List<RealTimeRentCar> realTimeRentCarList = new ArrayList<>();
        List<List<RealTimeRentCarImage>> RealTimeRentCarImageWrapper = new ArrayList<>();
        String modified_date_hour_minute = DateTime.today_date_and_hour_minute();

        List<RealTimeRentCar> realTimeRentCarList2 = realTimeRentService.findAll();
        Collections.sort(realTimeRentCarList2, comparator);
        Long currentRealTimeId = realTimeRentCarList2.get(0).getRealTimeRentId();

        List<RealTimeRentCarImage> realTimeRentCarImageList1 = realTimeRentImageService.findAll();
        Collections.sort(realTimeRentCarImageList1, comparatorImage);
        Long currentRealTimeImageId = realTimeRentCarImageList1.get(0).getImageId();

        for(int i=0; i<list_json_array.length(); i++){

            JSONObject morenObject = (JSONObject)list_json_array.get(i);
            String order_end = ((String)morenObject.get("order_end"));
            Long carOld = Long.parseLong((String)morenObject.get("carOld"));

            if (!morenObject.get("reserve").equals(null)) continue;

            String carCategory = (String) morenObject.get("carCategory");
            if(carCategory.equals("레이")){
                String carName = (String) morenObject.get("carName");
                if(carName.contains("밴")){
                    carCategory = "레이밴";
                }
            } else if(carCategory.equals("5시리즈")){
                String carName = (String) morenObject.get("carName");
                if(carName.contains("530i")){
                    carCategory = "530i";
                }
            } else if(carCategory.equals("그랜저")){
                String carName = (String) morenObject.get("carName");
                if(carName.contains("디 올")){
                    carCategory = "디올뉴그랜저";
                }
            }

            int isExpected;
            // n일 이내 반납예정차량
            if ( (Integer)morenObject.get("order_status") == 2 &&
                    order_end.length() >= 19 &&
                    order_end.substring(0, 19).compareTo(DateTime.today_date_and_time()) >= 0 &&  // 시간 고려해서
                    order_end.substring(0, 10).compareTo(DateTime.expected()) <= 0 // 날짜만 고려해서
            ) {
                isExpected = 1;
            }
            // 현재 가능차
            else if ((Integer)morenObject.get("order_status") == 0) isExpected = 0;
            else continue;

            Optional<MonthlyRent> monthlyRentWrapper = monthlyRentService.findByMorenCar(carOld, carOld, carCategory);

            if(monthlyRentWrapper.isPresent()) {
                MonthlyRent monthlyRent = monthlyRentWrapper.get();

                Optional<Discount> discount_object = discountService.findDiscountByCarNo((String) morenObject.get("carNo"));
                double discount_price = 0;
                String discount_description = null;
                int priceDisplay = 1;
                if (discount_object.isPresent()) {
                    Discount discount = discount_object.get();
                    discount_price = discount.getDiscount();
                    discount_description = discount.getDescription();
                    priceDisplay = discount.getPriceDisplay();
                }

                Optional<RealTimeRentCar> existingRealTimeRentCarOptional = realTimeRentService.findByCarCode((String) morenObject.get("carCode"));
                if(existingRealTimeRentCarOptional.isPresent()){
                    RealTimeRentCar existingRealTimeRentCar = existingRealTimeRentCarOptional.get();
                    existingRealTimeRentCar.setCarIdx((String) morenObject.get("carIdx"));
                    existingRealTimeRentCar.setCarCategory(carCategory);
                    existingRealTimeRentCar.setCarName((String) morenObject.get("carName"));
                    existingRealTimeRentCar.setCarDetail((String) morenObject.get("carDetail"));
                    existingRealTimeRentCar.setCarNo((String) morenObject.get("carNo"));
                    existingRealTimeRentCar.setCarExteriorColor((String) morenObject.get("carExteriorColor"));
                    existingRealTimeRentCar.setCarGubun((String) morenObject.get("carGubun"));
                    existingRealTimeRentCar.setCarDisplacement((String) morenObject.get("carDisplacement"));
                    existingRealTimeRentCar.setCarMileaget((String) morenObject.get("carMileaget"));
                    existingRealTimeRentCar.setCarColor((String) morenObject.get("carColor"));
                    existingRealTimeRentCar.setCarOld((String) morenObject.get("carOld"));
                    existingRealTimeRentCar.setCarEngine((String) morenObject.get("carEngine"));
                    existingRealTimeRentCar.setCarAttribute01((String) morenObject.get("carAttribute01"));
                    existingRealTimeRentCar.setOrderEnd((String) morenObject.get("order_end"));
                    existingRealTimeRentCar.setCostPerKm(monthlyRent.getCost_per_km());
                    existingRealTimeRentCar.setDiscount(discount_price);
                    existingRealTimeRentCar.setDescription((discount_description));
                    existingRealTimeRentCar.setIsExpected(isExpected);
                    existingRealTimeRentCar.setPriceDisplay(priceDisplay);
                    existingRealTimeRentCar.setReady_to_return((int) morenObject.get("ready_to_return"));
                    existingRealTimeRentCar.setModified_date_hour_minute(modified_date_hour_minute);
                    existingRealTimeRentCar.setMonthlyRent(monthlyRent);
                    realTimeRentService.save(existingRealTimeRentCar);

                    if(!morenObject.get("carThumbImages").equals(null)) {
                        JSONArray carJsonArray = (JSONArray) (morenObject.get("carThumbImages"));

                        List<RealTimeRentCarImage> existingRealTimeRentCarImageList = realTimeRentImageService.findByRealTimeRent(existingRealTimeRentCar);
                        for(RealTimeRentCarImage image : existingRealTimeRentCarImageList){
                            realTimeRentImageService.deleteById(image.getImageId());
                        }

                        for (int j = 0; j < carJsonArray.length(); j++) {
                            RealTimeRentCarImage image = new RealTimeRentCarImage(++currentRealTimeImageId, existingRealTimeRentCar, (String) carJsonArray.get(j));
                            realTimeRentImageService.save(image);
                        }
                    }
                } else {
                    RealTimeRentCar realTimeRent = new RealTimeRentCar(++currentRealTimeId, monthlyRent, (String) morenObject.get("carIdx"), carCategory,
                            (String) morenObject.get("carName"), (String) morenObject.get("carDetail"),(String) morenObject.get("carNo"),
                            (String) morenObject.get("carExteriorColor"), (String) morenObject.get("carGubun"),(String) morenObject.get("carDisplacement"),
                            (String) morenObject.get("carMileaget"), (String) morenObject.get("carColor"),(String) morenObject.get("carOld"),
                            (String) morenObject.get("carEngine"), (String) morenObject.get("carAttribute01"),(String) morenObject.get("order_end"),
                            monthlyRent.getCost_per_km(), (String) morenObject.get("carCode"), discount_price,
                            discount_description, isExpected, priceDisplay,  (int) morenObject.get("ready_to_return"), modified_date_hour_minute, 1000000, 0);
                    realTimeRentCarList.add(realTimeRent);

                    if(!morenObject.get("carThumbImages").equals(null)) {

                        List<RealTimeRentCarImage> realTimeRentCarImageList = new ArrayList<>();
                        JSONArray carJsonArray = (JSONArray) (morenObject.get("carThumbImages"));
                        for (int j = 0; j < carJsonArray.length(); j++) {
                            RealTimeRentCarImage realTimeRentImage = new RealTimeRentCarImage(++currentRealTimeImageId, realTimeRent, (String) carJsonArray.get(j));
                            realTimeRentCarImageList.add(realTimeRentImage);
                        }
                        RealTimeRentCarImageWrapper.add(realTimeRentCarImageList);
                    }
                }
            }
        }

        realTimeRentService.saveAll(realTimeRentCarList);
        for(List<RealTimeRentCarImage> images : RealTimeRentCarImageWrapper){
            realTimeRentImageService.saveAll(images);
        }
        List<RealTimeRentCar> realTimeRentCarList1 = realTimeRentService.findAll();
        for(RealTimeRentCar realTimeRentCar : realTimeRentCarList1){
            if(!realTimeRentCar.getModified_date_hour_minute().equals(modified_date_hour_minute)){
                List<RealTimeRentCarImage> realTimeRentCarImageList = realTimeRentImageService.findByRealTimeRent(realTimeRentCar);
                for(RealTimeRentCarImage image : realTimeRentCarImageList){
                    realTimeRentImageService.deleteById(image.getImageId());
                }
                realTimeRentService.deleteById(realTimeRentCar.getRealTimeRentId());
            }
        }
    }

    @GetMapping("/rent/month/main")
    public String rent_main(Model model) {

        List<RealTimeRentCar> morenDTOList = realTimeRentService.findByIsExpected(0);
        List<RealTimeRentCar> morenDTOListExpected = realTimeRentService.findByIsExpected(1);
        List<ExpectedDay> expectedDayList = expectedDayService.findAll();
        HashSet<String> carGubunSet = new HashSet<>();
        for(RealTimeRentCar realTimeRentCar : morenDTOList) carGubunSet.add(realTimeRentCar.getCarGubun());
        for(RealTimeRentCar realTimeRentCar : morenDTOListExpected) carGubunSet.add(realTimeRentCar.getCarGubun());
        List<String> carGubunList = new ArrayList<>(carGubunSet);
        Collections.sort(carGubunList);
        morenDTOList.sort(Comparator.comparingInt(RealTimeRentCar::getSequence));
        Collections.sort(morenDTOListExpected);
        carGubunList.add(0, "전체");

        RealTimeDTO realTimeDTO = new RealTimeDTO("전체", "2000km", "한달");

        model.addAttribute("carGubunList", carGubunList);
        model.addAttribute("expectedDayDisplayed", expectedDayList.get(0).getExpectedDayDisplayed());
        model.addAttribute("morenDTOList", morenDTOList);
        model.addAttribute("morenDTOListExpected", morenDTOListExpected);
        model.addAttribute("realTimeDTO", realTimeDTO);
        model.addAttribute("byCarName",  Comparator.comparing(RealTimeRentCar::getCarName));
        model.addAttribute("byOrderEnd",  Comparator.comparing(RealTimeRentCar::getOrderEnd));

        return "rent_month/main";
    }


    // 조건별로 차종 데이터 전달
    @GetMapping("/rent/month/lookup/{carType}/{kilometer}/{rentTerm}")
    public String rent_month_lookup(ModelMap model, @PathVariable String carType, @PathVariable String kilometer, @PathVariable String rentTerm) {

        List<RealTimeRentCar> realTimeRentCarAllList = realTimeRentService.findAll();
        HashSet<String> carGubunSet = new HashSet<>();

        for(RealTimeRentCar realTimeRentCar : realTimeRentCarAllList) carGubunSet.add(realTimeRentCar.getCarGubun());
        List<String> carGubunList = new ArrayList<>(carGubunSet);
        Collections.sort(carGubunList);
        carGubunList.add(0, "전체");

        List<RealTimeRentCar> morenDTOList;
        List<RealTimeRentCar> morenDTOListExpected;
        if(carType.equals("전체")){
            morenDTOList = realTimeRentService.findByIsExpected(0);
            morenDTOListExpected = realTimeRentService.findByIsExpected(1);
        } else {
            morenDTOList = realTimeRentService.findByCarGubunAndIsExpected(carType, 0);
            morenDTOListExpected = realTimeRentService.findByCarGubunAndIsExpected(carType, 1);
        }
        List<ExpectedDay> expectedDayList = expectedDayService.findAll();
        morenDTOList.sort(Comparator.comparingInt(RealTimeRentCar::getSequence));

        model.put("carGubunList", carGubunList);
        model.put("expectedDayDisplayed", expectedDayList.get(0).getExpectedDayDisplayed());
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
    @RequestMapping(value = "/rent/month/detail/{realTimeRentId}/{rentTerm}/{kilometer}/{discount}", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    public String rent_month_detail(ModelMap model, @PathVariable Long realTimeRentId, @PathVariable String rentTerm, @PathVariable String kilometer, @PathVariable double discount) throws Exception {

        Optional<RealTimeRentCar> realTimeRentCarWrapper = realTimeRentService.findById(realTimeRentId);
        if(realTimeRentCarWrapper.isPresent()){
            RealTimeRentCar realTimeRentCar = realTimeRentCarWrapper.get();
            List<RealTimeRentCarImage> realTimeRentCarImageList = realTimeRentImageService.findByRealTimeRent(realTimeRentCar);
            model.put("realTimeRentCar", realTimeRentCar);
            model.put("realTimeRentCarImageList", realTimeRentCarImageList);
        }else throw new Exception("해당하는 차량이 없습니다.");

        model.put("rentTerm", rentTerm);
        model.put("kilometer", kilometer);
        model.put("discount", discount);
        model.put("today_format", DateTime.today_date_only());

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

}