package kr.carz.savecar.controller.Admin;

import kr.carz.savecar.controller.ReservationController;
import kr.carz.savecar.domain.*;
import kr.carz.savecar.dto.*;
import kr.carz.savecar.service.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Controller
public class CampingCarController {
    private final CampingcarReservationService campingcarReservationService;
    private final CalendarTimeService calendarTimeService;
    private final CampingCarPriceService campingCarPriceService;
    private final CalendarDateService calendarDateService;
    private final DateCampingService dateCampingService;
    private final CampingCarPriceRateService campingCarPriceRateService;
    private final ReservationController reservationController;
    private final ImagesService imagesService;
    private final S3Service s3Service;
    private final CampingCarMainTextService campingCarMainTextService;

    @Autowired
    public CampingCarController(CampingcarReservationService campingcarReservationService,
                                CalendarTimeService calendarTimeService, CampingCarPriceService campingCarPriceService,
                                CalendarDateService calendarDateService, DateCampingService dateCampingService,
                                CampingCarPriceRateService campingCarPriceRateService,
                                ReservationController reservationController, ImagesService imagesService,
                                S3Service s3Service, CampingCarMainTextService campingCarMainTextService) {
        this.campingcarReservationService = campingcarReservationService;
        this.calendarTimeService = calendarTimeService;
        this.campingCarPriceService = campingCarPriceService;
        this.calendarDateService = calendarDateService;
        this.dateCampingService = dateCampingService;
        this.campingCarPriceRateService = campingCarPriceRateService;
        this.reservationController = reservationController;
        this.imagesService = imagesService;
        this.s3Service = s3Service;
        this.campingCarMainTextService = campingCarMainTextService;
    }

    @Value("${phone.admin1}")
    private String admin1;

    @Value("${phone.admin2}")
    private String admin2;

    @Value("${phone.admin3}")
    private String admin3;

    @Value("${moren.request_url}")
    private String request_url;


    public String[] getTwoHundredStrings(StringBuffer inputBuff, String someToken) {
        String[] nameArray = new String[200];

        int currentPos = 0;
        int nextPos;

        for (int i = 0; i < 200; i++) {

            nextPos = inputBuff.indexOf(someToken, currentPos);

            if (nextPos < 0) {
                break;
            }

            String nextName = inputBuff.substring(currentPos, nextPos);

            nameArray[i] = nextName;
            currentPos = nextPos+1;
        }

        return nameArray;
    }



    // [관리자 메인페이지] 캠핑카 예약내역 메뉴로 입장
    @GetMapping(value = "/admin/campingcar/menu")
    @ResponseBody
    public ModelAndView get_admin_main(Pageable pageable) {

        ModelAndView mav = new ModelAndView();

        Page<CampingCarReservation> campingCarReservationPage = campingcarReservationService.findAllPageable(pageable);

        mav.addObject("currentPage", pageable.getPageNumber());
        mav.addObject("pageSize", pageable.getPageSize());

        mav.addObject("startPage", (pageable.getPageNumber() / 5) * 5 + 1);
        mav.addObject("endPage", Integer.min((pageable.getPageNumber() / 5 + 1) * 5, campingCarReservationPage.getTotalPages()));

        mav.addObject("totalPages", campingCarReservationPage.getTotalPages());
        mav.addObject("campingCarReservationList", campingCarReservationPage.getContent());

        mav.setViewName("admin/campingcar_menu");

        return mav;
    }

    // [관리자 메인페이지] 캠핑카 예약내역 detail 페이지로 입장
    @GetMapping(value = "/admin/campingcar/detail/{reservationId}")
    @ResponseBody
    public ModelAndView get_admin_campincar_detail(@PathVariable Long reservationId) {

        ModelAndView mav = new ModelAndView();

        Optional<CampingCarReservation> campingCarReservation = campingcarReservationService.findById(reservationId);
        campingCarReservation.ifPresent(carReservation -> mav.addObject("campingCarReservation", carReservation));
        mav.setViewName("admin/campingcar_detail");

        return mav;
    }


    // [관리자 메인페이지] 캠핑카 예약등록 폼으로 입장
    @GetMapping(value = "/admin/campingcar/register")
    @ResponseBody
    public ModelAndView get_admin_campingcar_register_form() {

        ModelAndView mav = new ModelAndView();

        mav.setViewName("admin/campingcar_reservation_register");

        return mav;
    }



    // [관리자 메인페이지] 캠핑카 기본설정 메뉴로 입장
    @GetMapping(value = "/admin/campingcar/setting/menu")
    @ResponseBody
    public ModelAndView get_setting_main() {

        ModelAndView mav = new ModelAndView();

        List<CampingCarPrice> campingCarList = campingCarPriceService.findAllCampingCarPrice();
        mav.addObject("campingCarList", campingCarList);

        mav.setViewName("admin/campingcar_setting_menu");

        return mav;
    }



    // [관리자 메인페이지] 캠핑카 가격 메뉴로 입장
    @GetMapping(value = "/admin/campingcar/price/menu")
    @ResponseBody
    public ModelAndView get_campingcar_price_main() {

        ModelAndView mav = new ModelAndView();

        List<List<CampingCarPriceRate>> campingCarPriceRateList = new ArrayList<>();
        List<CampingCarPriceRate> campingCarPriceAllList = campingCarPriceRateService.findAllCampingCarPriceRate();

        for(int i=0 ;i<campingCarPriceAllList.size() / 2; i++){
            List<CampingCarPriceRate> campingCarPriceRates = new ArrayList<>();
            campingCarPriceRates.add(campingCarPriceAllList.get(i * 2));
            campingCarPriceRates.add(campingCarPriceAllList.get(i * 2 + 1));

            campingCarPriceRateList.add(campingCarPriceRates);
        }
        mav.addObject("campingCarPriceList", campingCarPriceRateList);
        mav.setViewName("admin/campingcar_price_menu");

        return mav;
    }



    // [관리자 메인페이지] 캠핑카 이미지 메뉴로 입장
    @GetMapping(value = "/admin/campingcar/image/menu")
    @ResponseBody
    public ModelAndView get_camping_car_image_main() throws Exception {

        ModelAndView mav = new ModelAndView();

        List<CampingCarPrice> campingCarPriceAll = campingCarPriceService.findAllCampingCarPrice();

        List<Images> imagesMainList = new ArrayList<>();
        List<List<Images>> imagesExtraList = new ArrayList<>();

        for(CampingCarPrice campingCar : campingCarPriceAll){
            List<Images> imagesListByCarNameMain = imagesService.findByCarNameAndIsMain(campingCar, "1");
            List<Images> imagesListByCarNameExtra = imagesService.findByCarNameAndIsMain(campingCar, "0");

            Collections.sort(imagesListByCarNameExtra);
            imagesExtraList.add(imagesListByCarNameExtra);

            if(imagesListByCarNameMain.size() > 1){
                throw new Exception("The number of Main image must be only one");
            }

            if(imagesListByCarNameMain.size() == 0) {
                imagesMainList.add(new Images((long) -1, campingCar, -1, "", "0", "1"));
            } else {
                imagesMainList.add(imagesListByCarNameMain.get(0));
            }
        }
        mav.addObject("imagesMainList", imagesMainList);
        mav.addObject("imagesExtraList", imagesExtraList);

        mav.setViewName("admin/campingcar_image_menu");

        return mav;
    }


    // [관리자 메인페이지] 캠핑카 본문 메뉴로 입장
    @GetMapping(value = "/admin/campingcar/mainText/menu")
    @ResponseBody
    public ModelAndView get_campingcar_main_text_menu() {

        ModelAndView mav = new ModelAndView();


        List<CampingCarPrice> campingCarPriceAll = campingCarPriceService.findAllCampingCarPrice();

        List<List<CampingCarMainText>> mainTextList = new ArrayList<>();

        for(CampingCarPrice campingCar : campingCarPriceAll){
            List<CampingCarMainText> imagesListByCarName = campingCarMainTextService.findImageByCarName(campingCar);

            Collections.sort(imagesListByCarName);
            mainTextList.add(imagesListByCarName);
        }
        mav.addObject("mainTextList", mainTextList);

        mav.setViewName("admin/campingcar_mainText_menu");

        return mav;
    }



    // 캠핑카 reservation 등록 api
    @PostMapping("/admin/campingcar/reservation")
    @ResponseBody
    public void post_camping_car_reservation(HttpServletResponse res, @RequestBody CampingCarReservationDTO campingCarReservationDTO) throws IOException {

        campingcarReservationService.save_campingcar_reservation(campingCarReservationDTO);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    // 캠핑카 예약 확정 - 세이브카 db
    public void admin_camping_car_reservation_confirm_for_savecar(List<DateCamping> dateCampingList, int dateCampingListSize, CampingCarReservation campingCarReservation, CampingCarPrice campingCarPrice){
        for (int i = 0; i < dateCampingListSize; i++) {
            List<CalendarTime> calendarTimeList;
            DateCamping dateCamping = dateCampingList.get(i);

            if (i == 0 && !campingCarReservation.getRentTime().equals("10시")) {
                calendarTimeList = calendarTimeService.findByDateIdAndCarNameAndReserveTimeGreaterThanEqual(dateCamping.getDateId(), campingCarPrice, campingCarReservation.getRentTime());
                List<CalendarTime> calendarTimeForCheckList = calendarTimeService.findByDateIdAndCarNameAndReserveTimeLessThan(dateCamping.getDateId(), campingCarPrice, campingCarReservation.getRentTime());

                int one_cnt = 0;
                for (CalendarTime calendarTime : calendarTimeForCheckList) {
                    if (calendarTime.getReserveComplete().equals("1")) {
                        one_cnt += 1;
                    }
                }

                if (one_cnt != calendarTimeForCheckList.size()) {
                    if(calendarTimeForCheckList.get(0).getReserveComplete().equals("1")){
                        dateCampingList.get(i).setReserved("2");
                    } else {
                        dateCampingList.get(i).setReserved("1");
                    }
                } else {
                    dateCampingList.get(i).setReserved("2");
                }
            } else if (i == dateCampingListSize - 1 && !campingCarReservation.getReturnTime().equals("18시")) {
                calendarTimeList = calendarTimeService.findByDateIdAndCarNameAndReserveTimeLessThanEqual(dateCamping.getDateId(), campingCarPrice, campingCarReservation.getReturnTime());
                List<CalendarTime> calendarTimeForCheckList = calendarTimeService.findByDateIdAndCarNameAndReserveTimeGreaterThan(dateCamping.getDateId(), campingCarPrice, campingCarReservation.getRentTime());

                int one_cnt = 0;
                for (CalendarTime calendarTime : calendarTimeForCheckList) {
                    if (calendarTime.getReserveComplete().equals("1")) {
                        one_cnt += 1;
                    }
                }

                if (one_cnt != calendarTimeForCheckList.size()) {
                    if(calendarTimeForCheckList.get(calendarTimeForCheckList.size()-1).getReserveComplete().equals("1")){
                        dateCampingList.get(i).setReserved("2");
                    } else {
                        dateCampingList.get(i).setReserved("1");
                    }
                } else {
                    dateCampingList.get(i).setReserved("2");
                }
            } else {
                calendarTimeList = calendarTimeService.findCalendarTimeByDateIdAndCarName(dateCamping.getDateId(), campingCarPrice);
                dateCampingList.get(i).setReserved("2");
            }

            for (CalendarTime calendarTime : calendarTimeList) {
                calendarTime.setReserveComplete("1");
                calendarTimeService.save(calendarTime);
            }
        }
    }



    // 캠핑카 예약 취소 - 세이브카 db
    public void admin_camping_car_reservation_cancel_for_savecar(List<DateCamping> dateCampingList, int dateCampingListSize, CampingCarReservation campingCarReservation, CampingCarPrice campingCarPrice){
        for (int i = 0; i < dateCampingListSize; i++) {
            List<CalendarTime> calendarTimeList;
            DateCamping dateCamping = dateCampingList.get(i);

            if (i == 0 && !campingCarReservation.getRentTime().equals("10시")) {
                calendarTimeList = calendarTimeService.findByDateIdAndCarNameAndReserveTimeGreaterThanEqual(dateCamping.getDateId(), campingCarPrice, campingCarReservation.getRentTime());
                List<CalendarTime> calendarTimeForCheckList = calendarTimeService.findByDateIdAndCarNameAndReserveTimeLessThan(dateCamping.getDateId(), campingCarPrice, campingCarReservation.getRentTime());

                int start_chk = 1;
                for (CalendarTime calendarTime : calendarTimeForCheckList) {
                    if (calendarTime.getReserveComplete().equals("1")) {
                        dateCampingList.get(i).setReserved("1");
                        start_chk = 0;
                        break;
                    }
                }
                if (start_chk == 1){
                    dateCampingList.get(i).setReserved("0");
                }
            } else if (i == dateCampingListSize - 1 && !campingCarReservation.getReturnTime().equals("18시")) {
                calendarTimeList = calendarTimeService.findByDateIdAndCarNameAndReserveTimeLessThanEqual(dateCamping.getDateId(), campingCarPrice, campingCarReservation.getReturnTime());
                List<CalendarTime> calendarTimeForCheckList = calendarTimeService.findByDateIdAndCarNameAndReserveTimeGreaterThan(dateCamping.getDateId(), campingCarPrice, campingCarReservation.getReturnTime());

                int start_chk = 1;
                for (CalendarTime calendarTime : calendarTimeForCheckList) {
                    if (calendarTime.getReserveComplete().equals("1")) {
                        dateCampingList.get(i).setReserved("1");
                        start_chk = 0;
                        break;
                    }
                }
                if (start_chk == 1){
                    dateCampingList.get(i).setReserved("0");
                }
            } else {
                calendarTimeList = calendarTimeService.findCalendarTimeByDateIdAndCarName(dateCamping.getDateId(), campingCarPrice);
                dateCampingList.get(i).setReserved("0");
            }

            for (CalendarTime calendarTime : calendarTimeList) {
                calendarTime.setReserveComplete("0");
                calendarTimeService.save(calendarTime);
            }
        }
    }

    // 캠핑카 예약 확정 - 모렌 전송
    public String admin_camping_car_reservation_confirm_for_moren(CampingCarReservationDTO campingCarReservationDTO, String orderType, String orderStartTime, String orderEndTime) {

        String[] splited_response;

        try {
            URL url = new URL(request_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            JSONObject morenJsonObject = new JSONObject();

            CampingCarPrice campingCarPriceForMoren = campingCarPriceService.findCampingCarPriceByCarName(campingCarReservationDTO.getCarType());

            morenJsonObject.put("COMPANY_ID", "1343");
            morenJsonObject.put("CAR_NUM", campingCarPriceForMoren.getCarNum());
            morenJsonObject.put("CAR_CODE", campingCarPriceForMoren.getCarCode());
            morenJsonObject.put("ORDER_TYPE", orderType);
            morenJsonObject.put("ORDER_CONTRACT_TYPE", "2");
            morenJsonObject.put("ORDER_CUSTOMER_NAME", campingCarReservationDTO.getName());
            morenJsonObject.put("ORDER_CUSTOMER_PHONE", campingCarReservationDTO.getPhone());
            morenJsonObject.put("ORDER_START_TIME", orderStartTime);
            morenJsonObject.put("ORDER_END_TIME", orderEndTime);
            morenJsonObject.put("ORDER_CUSTOMER_MEMO", campingCarReservationDTO.getDetail());
            morenJsonObject.put("ORDER_PRICE", campingCarReservationDTO.getTotal());
            morenJsonObject.put("ORDER_PRICE_TAX", "0");
            morenJsonObject.put("ORDER_DEPOSIT", campingCarReservationDTO.getDeposit());
            morenJsonObject.put("ORDER_CDW", "1");

            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(conn.getOutputStream()));
            printWriter.write(morenJsonObject.toString());
            printWriter.flush();

            // 응답
            BufferedReader bufferedReader;
            int status = conn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK){
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line;
            StringBuffer response = new StringBuffer();

            while ((line = bufferedReader.readLine()) != null){
                response.append(line);
            }
            bufferedReader.close();
            System.out.println("응답값 : " + response);

            splited_response = getTwoHundredStrings(response, "\"");

        } catch (Exception e){
            return "Connection Fail";
        }

        return splited_response[9];
    }

    // 캠핑카 예약 취소 - 모렌 전송
    public String admin_camping_car_reservation_cancel_for_moren(CampingCarReservationDTO campingCarReservationDTO, String orderType, String orderStartTime, String orderEndTime){

        try {
            URL url = new URL(request_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            JSONObject morenJsonObject = new JSONObject();

            CampingCarPrice campingCarPriceForMoren = campingCarPriceService.findCampingCarPriceByCarName(campingCarReservationDTO.getCarType());

            StringBuilder sb = new StringBuilder();
            sb.append("CAR_NUM : "+campingCarPriceForMoren.getCarNum());
            sb.append(",CAR_CODE : "+campingCarPriceForMoren.getCarCode());
            sb.append(",ORDER_TYPE : "+orderType);
            sb.append(",ORDER_CODE : "+campingCarPriceForMoren.getCarCode());
            sb.append(",ORDER_CUSTOMER_NAME : "+orderType);

            morenJsonObject.put("COMPANY_ID", "1343");
            morenJsonObject.put("CAR_NUM", campingCarPriceForMoren.getCarNum());
            morenJsonObject.put("CAR_CODE", campingCarPriceForMoren.getCarCode());
            morenJsonObject.put("ORDER_TYPE", orderType);
            morenJsonObject.put("ORDER_CONTRACT_TYPE", "2");
            morenJsonObject.put("ORDER_CODE", campingCarReservationDTO.getOrderCode());
            morenJsonObject.put("ORDER_CUSTOMER_NAME", campingCarReservationDTO.getName());
            morenJsonObject.put("ORDER_CUSTOMER_PHONE", campingCarReservationDTO.getPhone());
            morenJsonObject.put("ORDER_START_TIME", orderStartTime);
            morenJsonObject.put("ORDER_END_TIME", orderEndTime);
            morenJsonObject.put("ORDER_CUSTOMER_MEMO", campingCarReservationDTO.getDetail());
            morenJsonObject.put("ORDER_PRICE", campingCarReservationDTO.getTotal());
            morenJsonObject.put("ORDER_PRICE_TAX", "0");
            morenJsonObject.put("ORDER_DEPOSIT", campingCarReservationDTO.getDeposit());
            morenJsonObject.put("ORDER_CDW", "1");
            morenJsonObject.put("ORDER_CODE", campingCarReservationDTO.getOrderCode());


            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(conn.getOutputStream()));
            printWriter.write(morenJsonObject.toString());
            printWriter.flush();

            // 응답
            BufferedReader bufferedReader;
            int status = conn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK){
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null){
                response.append(line);
            }
            bufferedReader.close();
            System.out.println("응답값 : " + response);

        } catch (Exception e){
            return "Connection Fail";
        }

        return "Connection Success";
    }

    // 캠핑카 예약 수정 - 모렌 전송
    public String admin_camping_car_reservation_update_for_moren(CampingCarReservationDTO campingCarReservationDTO, String orderType, String orderStartTime, String orderEndTime){

        try {
            URL url = new URL(request_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            JSONObject morenJsonObject = new JSONObject();

            CampingCarPrice campingCarPriceForMoren = campingCarPriceService.findCampingCarPriceByCarName(campingCarReservationDTO.getCarType());

            morenJsonObject.put("COMPANY_ID", "1343");
            morenJsonObject.put("CAR_NUM", campingCarPriceForMoren.getCarNum());
            morenJsonObject.put("CAR_CODE", campingCarPriceForMoren.getCarCode());
            morenJsonObject.put("ORDER_TYPE", orderType);
            morenJsonObject.put("ORDER_CONTRACT_TYPE", "2");
            morenJsonObject.put("ORDER_CUSTOMER_NAME", campingCarReservationDTO.getName());
            morenJsonObject.put("ORDER_CUSTOMER_PHONE", campingCarReservationDTO.getPhone());
            morenJsonObject.put("ORDER_START_TIME", orderStartTime);
            morenJsonObject.put("ORDER_END_TIME", orderEndTime);
            morenJsonObject.put("ORDER_CUSTOMER_MEMO", campingCarReservationDTO.getDetail());
            morenJsonObject.put("ORDER_PRICE", campingCarReservationDTO.getTotal());
            morenJsonObject.put("ORDER_PRICE_TAX", "0");
            morenJsonObject.put("ORDER_DEPOSIT", campingCarReservationDTO.getDeposit());
            morenJsonObject.put("ORDER_CDW", "1");
            morenJsonObject.put("ORDER_CODE", campingCarReservationDTO.getOrderCode());

            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(conn.getOutputStream()));
            printWriter.write(morenJsonObject.toString());
            printWriter.flush();

            // 응답
            BufferedReader bufferedReader;
            int status = conn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }
            bufferedReader.close();
            System.out.println("응답값 : " + response);

        } catch (Exception e) {
            return "Connection Fail";
        }
        return "Connection Success";
    }



    // 캠핑카 예약 수정, 확정, 취소하기 api
    @PutMapping(value = "/admin/campingcar/reservation/{reservationId}")
    @ResponseBody
    @Transactional
    public void put_admin_campingcar_reservation(HttpServletResponse res, @PathVariable Long reservationId, @RequestBody CampingCarReservationDTO campingCarReservationDTO) throws Exception {

        JSONObject jsonObject = new JSONObject();

        Optional<CampingCarReservation> campingCarReservationWrapper = campingcarReservationService.findById(reservationId);

        CampingCarReservation campingCarReservation = new CampingCarReservation();
        if(campingCarReservationWrapper.isPresent()){
            campingCarReservation = campingCarReservationWrapper.get();
        }

        String[] splitedRentDate = campingCarReservation.getRentDate().split("-");
        String[] splitedReturnDate = campingCarReservation.getReturnDate().split("-");

        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(campingCarReservation.getCarType());
        CalendarDate calendarStartDate = calendarDateService.findCalendarDateByMonthAndDayAndYear(splitedRentDate[1], splitedRentDate[2], splitedRentDate[0]);
        CalendarDate calendarEndDate = calendarDateService.findCalendarDateByMonthAndDayAndYear(splitedReturnDate[1], splitedReturnDate[2], splitedReturnDate[0]);
        List<DateCamping> dateCampingList = dateCampingService.findByCarNameAndDateIdGreaterThanEqualAndDateIdLessThanEqual(campingCarPrice, calendarStartDate, calendarEndDate);

        int dateCampingListSize = dateCampingList.size();
        String taskName;
        String orderType;

        if (campingCarReservation.getReservation() == 0 && campingCarReservationDTO.getReservation() == 1) {
            taskName = "확정";
            orderType = "new";
            admin_camping_car_reservation_confirm_for_savecar(dateCampingList, dateCampingListSize, campingCarReservation, campingCarPrice);

        } else if (campingCarReservation.getReservation() == 1 && campingCarReservationDTO.getReservation() == 0) {
            taskName = "취소";
            orderType = "cancel";
            admin_camping_car_reservation_cancel_for_savecar(dateCampingList, dateCampingListSize, campingCarReservation, campingCarPrice);

        } else {
            taskName = "수정";
            orderType = "update";
        }


        String orderStartTime = campingCarReservationDTO.getRentDate() + " " + campingCarReservationDTO.getRentTime().substring(0,2) + ":00";
        String orderEndTime = campingCarReservationDTO.getReturnDate() + " " + campingCarReservationDTO.getReturnTime().substring(0,2) + ":00";

        if(taskName.equals("확정")) {
            String moren_response = admin_camping_car_reservation_confirm_for_moren(campingCarReservationDTO, orderType, orderStartTime, orderEndTime);
            if(moren_response.equals("Connection Fail")){
                throw new Exception(moren_response);
            } else {
                campingCarReservation.setOrderCode(moren_response);
            }
        } else if(taskName.equals("취소")){

            String moren_response = admin_camping_car_reservation_cancel_for_moren(campingCarReservationDTO, orderType, orderStartTime, orderEndTime);

            if(moren_response.equals("Connection Fail")){
                throw new Exception(moren_response);
            }

        } else {
            if(campingCarReservationDTO.getReservation() == 1) {
                String moren_response = admin_camping_car_reservation_update_for_moren(campingCarReservationDTO, orderType, orderStartTime, orderEndTime);

                if(moren_response.equals("Connection Fail")){
                    throw new Exception(moren_response);
                }
            }
        }
        campingcarReservationService.saveDTO(campingCarReservationDTO);

        if(taskName.equals("확정")) {
            reservationController.send_message(admin1+", "+admin2+", "+admin3,
                                                campingCarReservation.getPhone(),
                                                "[캠핑카 캘린더 예약 " + taskName + "]\n"
                                                    + "성함: " + campingCarReservation.getName() + "\n"
                                                    + "전화번호: " + campingCarReservation.getPhone() + "\n"
                                                    + "차량명: " + campingCarReservation.getCarType() + "\n"
                                                    + "입금자명: " + campingCarReservation.getDepositor() + "\n"
                                                    + "대여날짜: " + campingCarReservation.getRentDate() + "\n"
                                                    + "대여시간: " + campingCarReservation.getRentTime() + "\n"
                                                    + "반납날짜: " + campingCarReservation.getReturnDate() + "\n"
                                                    + "반납시간: " + campingCarReservation.getReturnTime() + "\n"
                                                    + "이용날짜: " + campingCarReservation.getDay() + "\n"
                                                    + "총금액: " + campingCarReservation.getTotal() + "\n"
                                                    + "선결제금액: " + campingCarReservation.getTotalHalf() + "\n"
                                                    + "요청사항: " + campingCarReservation.getDetail() + "\n\n",
                                            "[캠핑카 예약이 " + taskName + "되었습니다.]" + "\n"
                                                    + "성함: " + campingCarReservation.getName() + "\n"
                                                    + "전화번호: " + campingCarReservation.getPhone() + "\n"
                                                    + "차량명: " + campingCarReservation.getCarType() + "\n"
                                                    + "대여날짜: " + campingCarReservation.getRentDate() + "\n"
                                                    + "대여시간: " + campingCarReservation.getRentTime() + "\n"
                                                    + "반납날짜: " + campingCarReservation.getReturnDate() + "\n"
                                                    + "반납시간: " + campingCarReservation.getReturnTime() + "\n"
                                                    + "입금자명: " + campingCarReservation.getDepositor() + "\n"
                                                    + "이용날짜: " + campingCarReservation.getDay() + "\n"
                                                    + "총금액: " + campingCarReservation.getTotal() + "\n"
                                                    + "선결제금액: " + campingCarReservation.getTotalHalf() + "\n"
                                                    + "요청사항: " + campingCarReservation.getDetail() + "\n\n");
        }
        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    // 캠핑카 reservation 삭제 api
    @DeleteMapping("/admin/campingcar/reservation/{reservationId}")
    @ResponseBody
    public void delete_campingcar_reservation(HttpServletResponse res, @PathVariable Long reservationId) throws IOException {

        JSONObject jsonObject = new JSONObject();

        Optional<CampingCarReservation> campingCarReservationWrapper = campingcarReservationService.findById(reservationId);

        if(campingCarReservationWrapper.isPresent()){
            campingcarReservationService.delete(campingCarReservationWrapper.get());
            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }



    @PutMapping("/admin/campingcar/price/by/{carType}")
    @ResponseBody
    public void put_admin_campingcar_price(HttpServletResponse res, @RequestBody CampingCarPriceRateDTO campingCarPriceRateDTO, @PathVariable String carType) throws IOException {

        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(carType);
        CampingCarPriceRate campingCarPriceRate = campingCarPriceRateService.findByCarNameAndSeason(campingCarPrice, campingCarPriceRateDTO.getSeason());
        campingCarPriceRate.setValueByDto(campingCarPriceRateDTO);

        campingCarPriceRateService.save(campingCarPriceRate);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }

    @PutMapping("/admin/campingcar/setting/{carName}")
    @ResponseBody
    public void put_admin_setting(HttpServletResponse res, @RequestBody CampingCarPriceDTO dto, @PathVariable String carName) throws IOException {

        CampingCarPrice foundCampingCar = campingCarPriceService.findCampingCarPriceByCarName(carName);
        campingCarPriceService.saveDTO(foundCampingCar, dto);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    @PostMapping(value="/admin/campingcar/image", consumes=MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public void postAdminCampingCarImage(HttpServletResponse res, ImagesDTO imagesDTO) throws IOException {

        String imgPath = s3Service.upload(imagesDTO.getFile());
        imagesDTO.setUrl(imgPath);

        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(imagesDTO.getCarName());
        imagesService.saveDTO(imagesDTO, campingCarPrice);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    @PutMapping(value="/admin/campingcar/image/title")
    @ResponseBody
    public void putAdminCampingCarImageTitle(HttpServletResponse res, @RequestBody ImagesVO imagesVO) throws IOException  {

        for(ImageTitleVO imageTitleVO : imagesVO.getImageTitleList()){

            Optional<Images> imagesWrapper = imagesService.findImageByImageId(imageTitleVO.getImageId());
            if (imagesWrapper.isPresent()) {

                Images images = imagesWrapper.get();
                images.setTitle(imageTitleVO.getTitle());

                imagesService.save(images);
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    @DeleteMapping("/admin/campingcar/image/{imageId}")
    @ResponseBody
    public void deleteAdminCampingCarImage(@PathVariable Long imageId) {

        imagesService.delete(imageId);

    }


    @PostMapping(value="/admin/campingcar/mainText", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public void postAdminCampingCarMainTextImage(HttpServletResponse res, CampingCarMainTextDTO dto) throws IOException {

        String imgPath = s3Service.upload(dto.getFile());
        dto.setUrl(imgPath);

        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(dto.getCarName());
        campingCarMainTextService.saveDTO(dto, campingCarPrice);

    }


    @PutMapping(value="/admin/campingcar/mainText/title")
    @ResponseBody
    public void putAdminCampingCarMainTextImageTitle(HttpServletResponse res, @RequestBody CampingCarMainTextVO campingCarMainTextVO) throws IOException  {

        for(CampingCarMainTextTitleVO imageTitleVO : campingCarMainTextVO.getCampingCarMainTextTitleList()){

            Optional<CampingCarMainText> imagesWrapper = campingCarMainTextService.findImageByImageId(imageTitleVO.getImageId());
            if (imagesWrapper.isPresent()) {

                CampingCarMainText campingCarMainText = imagesWrapper.get();
                campingCarMainText.setTitle(imageTitleVO.getTitle());

                campingCarMainTextService.save(campingCarMainText);
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    @DeleteMapping("/admin/campingcar/mainText/{imageId}")
    @ResponseBody
    public void deleteAdminCampingCarMainTextImage(@PathVariable Long imageId) {

        campingCarMainTextService.delete(imageId);

    }
}
