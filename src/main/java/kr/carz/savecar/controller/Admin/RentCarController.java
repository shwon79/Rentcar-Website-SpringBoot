package kr.carz.savecar.controller.Admin;

import kr.carz.savecar.controller.ShortTermRentCar.RealtimeRentController;
import kr.carz.savecar.domain.*;
import kr.carz.savecar.dto.*;
import kr.carz.savecar.service.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
public class RentCarController {
    private final MonthlyRentService monthlyRentService;
    private final YearlyRentService yearlyRentService;
    private final TwoYearlyRentService twoYearlyRentService;
    private final S3Service s3Service;
    private final ExpectedDayService expectedDayService;
    private final RealTimeRentCarService realTimeRentCarService;
    private final RealTimeRentCarImageService realTimeRentImageService;
    private final RealtimeRentController realtimeRentController;

    @Autowired
    public RentCarController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService,
                             TwoYearlyRentService twoYearlyRentService, RealTimeRentCarService realTimeRentCarService,
                             S3Service s3Service, ExpectedDayService expectedDayService,
                             RealTimeRentCarImageService realTimeRentImageService, RealtimeRentController realtimeRentController) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.twoYearlyRentService = twoYearlyRentService;
        this.s3Service = s3Service;
        this.expectedDayService = expectedDayService;
        this.realTimeRentCarService = realTimeRentCarService;
        this.realTimeRentImageService = realTimeRentImageService;
        this.realtimeRentController = realtimeRentController;
    }

    @PutMapping(value = "/admin/rentcar/realtime/sequence")
    @ResponseBody
    public void put_rentcar_realtime_sequence(HttpServletResponse res, @RequestBody RealTimeVO realTimeVO) throws IOException {

        JSONObject jsonObject = new JSONObject();

        int problemFlg = 0;
        for(RealTimeSeqIsLongTermVO realTimeSeqIsLongTermVO : realTimeVO.getImageTitleList()){

            Optional<RealTimeRentCar> realTimeRentCarOptional = realTimeRentCarService.findById(realTimeSeqIsLongTermVO.getImageId());
            if (realTimeRentCarOptional.isPresent()) {

                RealTimeRentCar realTimeRentCar = realTimeRentCarOptional.get();
                realTimeRentCar.setSequence(realTimeSeqIsLongTermVO.getSequence());
                realTimeRentCar.setIsLongTerm(realTimeSeqIsLongTermVO.getIsLongTerm());
                realTimeRentCarService.save(realTimeRentCar);
            } else problemFlg = 1;
        }

        if(problemFlg == 0) jsonObject.put("result", 1);
        else jsonObject.put("result", 0);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }

    @GetMapping("/admin/rentcar/realtime/list")
    public ModelAndView get_rent_car_realtime_menu() {

        ModelAndView mav = new ModelAndView();

        List<RealTimeRentCar> realTimeRentCarList = realTimeRentCarService.findByIsExpected(0);
        Collections.sort(realTimeRentCarList, (a, b) -> a.getSequence() - b.getSequence());

        mav.addObject("realTimeRentCarList", realTimeRentCarList);

        mav.setViewName("admin/rentcar_realtime_list");

        return mav;
    }

    @GetMapping("/admin/rentcar/realtime/expected/list")
    public ModelAndView get_rent_car_realtime_expected_menu() {

        ModelAndView mav = new ModelAndView();

        List<RealTimeRentCar> realTimeRentCarList = realTimeRentCarService.findByIsExpected(1);
        Collections.sort(realTimeRentCarList, (a, b) -> a.getSequence() - b.getSequence());

        mav.addObject("realTimeRentCarListExpected", realTimeRentCarList);
        mav.setViewName("admin/rentcar_realtime_expected_list");

        return mav;
    }

    @GetMapping("/admin/rentcar/price/monthly/menu/{category2}")
    public ModelAndView get_rent_car_price_monthly_menu(@PathVariable String category2) {

        ModelAndView mav = new ModelAndView();

        List<MonthlyRent> monthlyRentList = monthlyRentService.findByCategory2(category2);
        Collections.sort(monthlyRentList);

        mav.addObject("monthlyRentList", monthlyRentList);
        mav.addObject("category2", category2);

        mav.setViewName("admin/rentcar_price_monthly_menu");

        return mav;
    }

    @GetMapping("/admin/rentcar/price/monthly/detail/{monthlyId}")
    public ModelAndView get_rent_car_price_monthly_detail(@PathVariable Long monthlyId) {

        ModelAndView mav = new ModelAndView();

        Optional<MonthlyRent> monthlyRentWrapper = monthlyRentService.findById(monthlyId);

        if(monthlyRentWrapper.isPresent()){
            mav.addObject("monthlyRent", monthlyRentWrapper.get());
        }

        mav.setViewName("admin/rentcar_price_monthly_detail");

        return mav;
    }


    @GetMapping("/admin/rentcar/price/register")
    public ModelAndView get_rent_car_price_register() {

        ModelAndView mav = new ModelAndView();

        mav.setViewName("admin/rentcar_price_register");

        return mav;
    }

    @GetMapping("/admin/rentcar/price/upload")
    public ModelAndView get_rent_car_price_upload() {

        ModelAndView mav = new ModelAndView();

        mav.setViewName("admin/rentcar_price_upload");

        return mav;
    }



    @PutMapping("/admin/rentcar/price/monthly/{monthlyId}")
    @ResponseBody
    public void put_rent_car_price_monthly(HttpServletResponse res, @RequestBody MonthlyRentDTO monthlyRentDTO, @PathVariable Long monthlyId) throws IOException {

        JSONObject jsonObject = new JSONObject();

        Optional<MonthlyRent> monthlyRentWrapper = monthlyRentService.findById(monthlyId);
        if(monthlyRentWrapper.isPresent()){
            monthlyRentService.updateAllPriceByDTO(monthlyRentDTO, monthlyRentWrapper.get());
            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    // 일괄수정
    @PutMapping("/admin/rentcar/price/monthly/{column}/{value}")
    @ResponseBody
    public void put_rent_car_price_monthly_kilometer_percentage(HttpServletResponse res, @PathVariable String column, @PathVariable double value) throws Exception {

        JSONObject jsonObject = new JSONObject();

        List<MonthlyRent> monthlyRentList = monthlyRentService.findAllMonthlyRents();

        switch (column){
            case "보증금":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.setDeposit(String.valueOf(value));
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "21세":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.setAge_limit(String.valueOf(value));
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "2500km":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.setCost_for_2_5k(value);
                    monthlyRent.setCost_for_2_5k_price(Math.round(monthlyRent.getCost_for_2k() * value / 1000) * 1000);
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "3000km":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.setCost_for_3k(value);
                    monthlyRent.setCost_for_3k_price(Math.round(monthlyRent.getCost_for_2k() * value / 1000) * 1000);
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "4000km":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.setCost_for_4k(value);
                    monthlyRent.setCost_for_4k_price(Math.round(monthlyRent.getCost_for_2k() * value / 1000) * 1000);
                    monthlyRentService.save(monthlyRent);
                }
                break;
            default:
                throw new Exception("column not mathced");
        }

        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    // 차종별 일괄수정
    @PutMapping("/admin/rentcar/price/monthly/{column}/{carType}/{value}")
    @ResponseBody
    public void put_rent_car_price_monthly_kilometer_percentage_by_carType(HttpServletResponse res, @PathVariable String column, @PathVariable String carType, @PathVariable double value) throws Exception {

        JSONObject jsonObject = new JSONObject();

        List<MonthlyRent> monthlyRentList = monthlyRentService.findByCategory2(carType);

        switch (column){
            case "보증금":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.setDeposit(String.valueOf(value));
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "21세":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.setAge_limit(String.valueOf(value));
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "2500km":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.setCost_for_2_5k(value);
                    monthlyRent.setCost_for_2_5k_price(Math.round(monthlyRent.getCost_for_2k() * value / 1000) * 1000);
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "3000km":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.setCost_for_3k(value);
                    monthlyRent.setCost_for_3k_price(Math.round(monthlyRent.getCost_for_2k() * value / 1000) * 1000);
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "4000km":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.setCost_for_4k(value);
                    monthlyRent.setCost_for_4k_price(Math.round(monthlyRent.getCost_for_2k() * value / 1000) * 1000);
                    monthlyRentService.save(monthlyRent);
                }
                break;
            default:
                throw new Exception("column not matched");
        }

        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }

    @PutMapping(value="/admin/rentcar/price/monthly/image/{monthlyId}", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public void put_rent_car_price_monthly_with_image(MonthlyRentVO monthlyRentVO, @PathVariable Long monthlyId) throws IOException {

        Optional<MonthlyRent> monthlyRentWrapper = monthlyRentService.findById(monthlyId);
        if(monthlyRentWrapper.isPresent()){

            String imgPath = s3Service.upload(monthlyRentVO.getFile());
            monthlyRentVO.setImg_url(imgPath);

            monthlyRentService.updateAllPriceByVO(monthlyRentVO, monthlyRentWrapper.get());
        }

    }


    @PostMapping("/admin/rentcar/price/upload")
    public String uploadExcel(HttpServletResponse res, @RequestParam("file") MultipartFile file, Model model)
            throws IOException {

        realTimeRentImageService.deleteAllInBatch();
        realTimeRentCarService.deleteAllInBatch();
        monthlyRentService.deleteAllInBatch();
        yearlyRentService.deleteAllInBatch();

        List<RentCarVO> dataList = new ArrayList<>();

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }

        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        String imgPath = "https://ibb.co/4fDBY1y";


        for (int i = 2; i < worksheet.getPhysicalNumberOfRows(); i++) {

            Row row = worksheet.getRow(i);

            // 공통
            RentCarVO rentCarVO = new RentCarVO();
            rentCarVO.setCategory1(row.getCell(0).getStringCellValue());
            rentCarVO.setCategory2(row.getCell(1).getStringCellValue());
            rentCarVO.setName(row.getCell(2).getStringCellValue());
            rentCarVO.setNameMoren(row.getCell(3).getStringCellValue());
            rentCarVO.setStart((long)row.getCell(4).getNumericCellValue());
            rentCarVO.setEnd((long)row.getCell(5).getNumericCellValue());

            // 월렌트
            rentCarVO.setDeposit_monthly(String.valueOf(row.getCell(6).getNumericCellValue()));
            rentCarVO.setCost_for_2k(row.getCell(7).getNumericCellValue());
            rentCarVO.setCost_for_2_5k_price(row.getCell(8).getNumericCellValue());
            rentCarVO.setCost_for_3k_price(row.getCell(9).getNumericCellValue());
            rentCarVO.setCost_for_4k_price(row.getCell(10).getNumericCellValue());
            rentCarVO.setCost_for_2_5k(row.getCell(11).getNumericCellValue());
            rentCarVO.setCost_for_3k(row.getCell(12).getNumericCellValue());
            rentCarVO.setCost_for_4k(row.getCell(13).getNumericCellValue());
            rentCarVO.setCost_for_others(row.getCell(14).getStringCellValue());
            rentCarVO.setAge_limit(String.valueOf(row.getCell(15).getNumericCellValue()));
            rentCarVO.setCost_per_km_monthly(String.valueOf(row.getCell(16).getNumericCellValue()));
            rentCarVO.setCredit_monthly(String.valueOf(row.getCell(17).getNumericCellValue()));

            // 12개월렌트
            rentCarVO.setDeposit_yearly(String.valueOf(row.getCell(18).getNumericCellValue()));
            rentCarVO.setCost_for_20k_price(row.getCell(19).getNumericCellValue());
            rentCarVO.setCost_for_30k_price(row.getCell(20).getNumericCellValue());
            rentCarVO.setCost_for_40k_price(row.getCell(21).getNumericCellValue());
            rentCarVO.setCost_for_20k(row.getCell(22).getNumericCellValue());
            rentCarVO.setCost_for_30k(row.getCell(23).getNumericCellValue());
            rentCarVO.setCost_for_40k(row.getCell(24).getNumericCellValue());
            rentCarVO.setCost_per_km_yearly(String.valueOf(row.getCell(25).getNumericCellValue()));
            rentCarVO.setCredit_yearly(String.valueOf(row.getCell(26).getNumericCellValue()));

            Long yearRentId = yearlyRentService.saveByRentCarVO(rentCarVO, imgPath);
            Optional<YearlyRent> yearlyRentWrapper = yearlyRentService.findByid(yearRentId);
            if(yearlyRentWrapper.isPresent()){
                monthlyRentService.saveByRentCarVO(rentCarVO, yearlyRentWrapper.get(), null, imgPath);
            }

            dataList.add(rentCarVO);
        }

        realtimeRentController.rent_month_save_update();
        model.addAttribute("datas", dataList);

        return "admin/rentcar_price_excelList";
    }


    @PostMapping(value="/admin/rentcar/price", consumes=MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public void post_rent_car_price(HttpServletResponse res, RentCarVO rentCarVO) throws IOException {

        JSONObject jsonObject = new JSONObject();

//        String imgPath = s3Service.upload(rentCarVO.getFile());
        String imgPath = "https://ibb.co/4fDBY1y";

        Long yearRentId = yearlyRentService.saveByRentCarVO(rentCarVO, imgPath);
        Optional<YearlyRent> yearlyRentWrapper = yearlyRentService.findByid(yearRentId);

        if(rentCarVO.getIsTwoYearExist() == 1) {
            Long twoYearRentId = twoYearlyRentService.saveByRentCarVO(rentCarVO, imgPath);
            Optional<TwoYearlyRent> twoYearlyRentWrapper = twoYearlyRentService.findByid(twoYearRentId);
            monthlyRentService.saveByRentCarVO(rentCarVO, yearlyRentWrapper.get(), twoYearlyRentWrapper.get(), imgPath);
        } else {
            monthlyRentService.saveByRentCarVO(rentCarVO, yearlyRentWrapper.get(), null, imgPath);
        }

        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    @DeleteMapping("/admin/rentcar/price/{monthlyId}")
    @ResponseBody
    public void delete_rent_car_price(HttpServletResponse res, @PathVariable Long monthlyId) throws IOException {

        JSONObject jsonObject = new JSONObject();

        Optional<MonthlyRent> monthlyRentWrapper = monthlyRentService.findById(monthlyId);

        if(monthlyRentWrapper.isPresent()) {
            MonthlyRent monthlyRent = monthlyRentWrapper.get();
            YearlyRent yearlyRent = monthlyRent.getYearlyRent();
            monthlyRentService.delete(monthlyRent);
            yearlyRentService.delete(yearlyRent);
            if(monthlyRent.getTwoYearlyRent() != null){
                TwoYearlyRent twoYearlyRent = monthlyRent.getTwoYearlyRent();
                twoYearlyRentService.delete(twoYearlyRent);
            }
            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }




    @GetMapping("/admin/rentcar/price/yearly/menu/{category2}")
    public ModelAndView get_rent_car_price_yearly_menu(@PathVariable String category2) {

        ModelAndView mav = new ModelAndView();

        List<MonthlyRent> monthlyRentList = monthlyRentService.findByCategory2(category2);
        Collections.sort(monthlyRentList);

        mav.addObject("monthlyRentList", monthlyRentList);
        mav.addObject("category2", category2);

        mav.setViewName("admin/rentcar_price_yearly_menu");

        return mav;
    }


    @GetMapping("/admin/rentcar/price/yearly/detail/{yearlyId}")
    public ModelAndView get_rent_car_price_yearly_detail(@PathVariable Long yearlyId) {

        ModelAndView mav = new ModelAndView();

        Optional<YearlyRent> yearlyRentWrapper = yearlyRentService.findById(yearlyId);

        if(yearlyRentWrapper.isPresent()){
            mav.addObject("yearlyRent", yearlyRentWrapper.get());
        }

        mav.setViewName("admin/rentcar_price_yearly_detail");

        return mav;
    }



    @PutMapping("/admin/rentcar/price/yearly/{yearlyId}")
    @ResponseBody
    public void put_rent_car_price_yearly(HttpServletResponse res, @RequestBody YearlyRentDTO yearlyRentDTO, @PathVariable Long yearlyId) throws IOException {

        JSONObject jsonObject = new JSONObject();

        Optional<YearlyRent> yearlyRentWrapper  = yearlyRentService.findById(yearlyId);
        if(yearlyRentWrapper.isPresent()){

            yearlyRentService.updateAllPriceByDTO(yearlyRentDTO, yearlyRentWrapper.get());

            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    // 일괄 수정
    @PutMapping("/admin/rentcar/price/yearly/{column}/{value}")
    @ResponseBody
    public void put_rent_car_price_yearly_kilometer_percentage(HttpServletResponse res, @PathVariable String column, @PathVariable double value) throws Exception {

        JSONObject jsonObject = new JSONObject();

        List<MonthlyRent> monthlyRentList = monthlyRentService.findAllMonthlyRents();

        switch (column){
            case "보증금":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.getYearlyRent().setDeposit(String.valueOf(value));
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "20000km":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.getYearlyRent().setCost_for_20k(value);
                    monthlyRent.getYearlyRent().setCost_for_20k_price(Math.round(monthlyRent.getCost_for_2k() * value / 1000) * 1000);
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "30000km":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.getYearlyRent().setCost_for_30k(value);
                    monthlyRent.getYearlyRent().setCost_for_30k_price(Math.round(Math.round(monthlyRent.getCost_for_2k() * monthlyRent.getCost_for_3k() / 1000) * 1000 * value / 1000) * 1000);
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "40000km":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.getYearlyRent().setCost_for_40k(value);
                    monthlyRent.getYearlyRent().setCost_for_40k_price(Math.round(Math.round(monthlyRent.getCost_for_2k() * monthlyRent.getCost_for_4k() / 1000) * 1000 * value / 1000) * 1000);
                    monthlyRentService.save(monthlyRent);
                }
                break;
            default:
                throw new Exception("column not mathced");
        }

        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    // 차종별 일괄 수정
    @PutMapping("/admin/rentcar/price/yearly/{column}/{carType}/{value}")
    @ResponseBody
    public void put_rent_car_price_yearly_kilometer_percentage_by_carType(HttpServletResponse res, @PathVariable String column, @PathVariable String carType, @PathVariable double value) throws Exception {

        JSONObject jsonObject = new JSONObject();

        List<MonthlyRent> monthlyRentList = monthlyRentService.findByCategory2(carType);

        switch (column){
            case "보증금":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.getYearlyRent().setDeposit(String.valueOf(value));
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "20000km":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.getYearlyRent().setCost_for_20k(value);
                    monthlyRent.getYearlyRent().setCost_for_20k_price(Math.round(monthlyRent.getCost_for_2k() * value / 1000) * 1000);
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "30000km":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.getYearlyRent().setCost_for_30k(value);
                    monthlyRent.getYearlyRent().setCost_for_30k_price(Math.round(Math.round(monthlyRent.getCost_for_2k() * monthlyRent.getCost_for_3k() / 1000) * 1000 * value / 1000) * 1000);
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "40000km":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.getYearlyRent().setCost_for_40k(value);
                    monthlyRent.getYearlyRent().setCost_for_40k_price(Math.round(Math.round(monthlyRent.getCost_for_2k() * monthlyRent.getCost_for_4k() / 1000) * 1000 * value / 1000) * 1000);
                    monthlyRentService.save(monthlyRent);
                }
                break;
            default:
                throw new Exception("column not mathced");
        }

        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }

    @GetMapping("/admin/rentcar/price/twoYearly/menu/{category2}")
    public ModelAndView get_rent_car_price_twoYearly_menu(@PathVariable String category2) {

        ModelAndView mav = new ModelAndView();

        List<MonthlyRent> monthlyRentList = monthlyRentService.findByCategory2AndTwoYearlyRentIsNotNull(category2);
        Collections.sort(monthlyRentList);

        mav.addObject("monthlyRentList", monthlyRentList);

        mav.setViewName("admin/rentcar_price_twoYearly_menu");

        return mav;
    }

    @GetMapping("/admin/rentcar/price/twoYearly/detail/{twoYearlyId}")
    public ModelAndView get_rent_car_price_twoYearly_detail(@PathVariable Long twoYearlyId) {

        ModelAndView mav = new ModelAndView();

        Optional<TwoYearlyRent> twoYearlyRentWrapper = twoYearlyRentService.findById(twoYearlyId);

        if(twoYearlyRentWrapper.isPresent()){
            mav.addObject("twoYearlyRent", twoYearlyRentWrapper.get());
        }

        mav.setViewName("admin/rentcar_price_twoYearly_detail");

        return mav;
    }



    @PutMapping("/admin/rentcar/price/twoYearly/{twoYearlyId}")
    @ResponseBody
    public void put_rent_car_price_twoYearly(HttpServletResponse res, @RequestBody TwoYearlyRentDTO twoYearlyRentDTO, @PathVariable Long twoYearlyId) throws IOException {

        JSONObject jsonObject = new JSONObject();

        Optional<TwoYearlyRent> twoYearlyRentWrapper  = twoYearlyRentService.findById(twoYearlyId);
        if(twoYearlyRentWrapper.isPresent()){

            twoYearlyRentService.updateAllPriceByDTO(twoYearlyRentDTO, twoYearlyRentWrapper.get());

            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }



    @PutMapping("/admin/rentcar/price/twoYearly/{column}/{value}")
    @ResponseBody
    public void put_rent_car_price_twoYearly_kilometer_percentage(HttpServletResponse res, @PathVariable String column, @PathVariable double value) throws Exception {

        JSONObject jsonObject = new JSONObject();

        List<MonthlyRent> monthlyRentList = monthlyRentService.findAllByTwoYearlyRentIsNotNull();

        switch (column){
            case "보증금":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.getTwoYearlyRent().setDeposit(String.valueOf(value));
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "20000km":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.getTwoYearlyRent().setCost_for_20Tk(value);
                    monthlyRent.getTwoYearlyRent().setCost_for_20Tk_price(Math.round(monthlyRent.getCost_for_2k() * value / 1000) * 1000);
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "30000km":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.getTwoYearlyRent().setCost_for_30Tk(value);
                    monthlyRent.getTwoYearlyRent().setCost_for_30Tk_price(Math.round(Math.round(monthlyRent.getCost_for_2k() * monthlyRent.getCost_for_3k() / 1000) * 1000 * value / 1000) * 1000);
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "40000km":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.getTwoYearlyRent().setCost_for_40Tk(value);
                    monthlyRent.getTwoYearlyRent().setCost_for_40Tk_price(Math.round(Math.round(monthlyRent.getCost_for_2k() * monthlyRent.getCost_for_4k() / 1000) * 1000 * value / 1000) * 1000);
                    monthlyRentService.save(monthlyRent);
                }
                break;
            default:
                throw new Exception("column not mathced");
        }

        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    @GetMapping("/admin/rentcar/expectedDay/menu")
    public ModelAndView get_rent_car_expectedDay_menu() {

        ModelAndView mav = new ModelAndView();

        List<ExpectedDay> expectedDayList = expectedDayService.findAll();
        mav.addObject("expectedDay", expectedDayList.get(0));

        mav.setViewName("admin/rentcar_expected_day");

        return mav;
    }

    @PutMapping(value="/admin/rentcar/expectedDay/{expectedDay}/{expectedDayDisplayed}")
    @ResponseBody
    public void put_rent_expectedDay(HttpServletResponse res, @PathVariable String expectedDay, @PathVariable String expectedDayDisplayed) throws IOException {

        JSONObject jsonObject = new JSONObject();

        List<ExpectedDay> expectedDayList = expectedDayService.findAll();
        expectedDayList.get(0).setExpectedDay(expectedDay);
        expectedDayList.get(0).setExpectedDayDisplayed(expectedDayDisplayed);
        expectedDayService.save(expectedDayList.get(0));

        new DateTime(expectedDay);

        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }
}
