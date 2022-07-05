package kr.carz.savecar.controller.Admin;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import kr.carz.savecar.domain.ExcelData;
import kr.carz.savecar.domain.YearlyRent;
import kr.carz.savecar.dto.RentCarVO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ExcelController {

    private final ReservationService reservationService;
    private final MonthlyRentService monthlyRentService;
    private final YearlyRentService yearlyRentService;
    private final TwoYearlyRentService twoYearlyRentService;
    private final S3Service s3Service;
    private final ExpectedDayService expectedDayService;

    @Autowired
    public ExcelController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService,
                             TwoYearlyRentService twoYearlyRentService, ReservationService reservationService,
                             S3Service s3Service, ExpectedDayService expectedDayService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.twoYearlyRentService = twoYearlyRentService;
        this.reservationService = reservationService;
        this.s3Service = s3Service;
        this.expectedDayService = expectedDayService;
    }


    @GetMapping("/excel")
    public String main() { // 1
        return "admin/excel";
    }


    @PostMapping("/excel/read")
    public String readExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException { // 2

        List<ExcelData> dataList = new ArrayList<>();

        String extension = FilenameUtils.getExtension(file.getOriginalFilename()); // 3

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

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4

            Row row = worksheet.getRow(i);

            ExcelData data = new ExcelData();

            data.setNum((int) row.getCell(0).getNumericCellValue());
            data.setName(row.getCell(1).getStringCellValue());
            data.setEmail(row.getCell(2).getStringCellValue());

            dataList.add(data);
        }

        model.addAttribute("datas", dataList); // 5

        return "admin/excelList";

    }


//    @PostMapping(value="/excel/upload", consumes= MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json; charset=UTF-8")
//    @ResponseBody

    @PostMapping("/excel/upload")
    public String uploadExcel(HttpServletResponse res, @RequestParam("file") MultipartFile file, Model model)
            throws IOException {

//        monthlyRentService.deleteAllInBatch();
//        yearlyRentService.deleteAllInBatch();

        JSONObject jsonObject = new JSONObject();
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
//            ExcelData data = new ExcelData();
//
//            data.setNum((int) row.getCell(0).getNumericCellValue());
//            data.setName(row.getCell(1).getStringCellValue());
//            data.setEmail(row.getCell(2).getStringCellValue());

            dataList.add(rentCarVO);
        }


        model.addAttribute("datas", dataList); // 5

        return "admin/excelList";

//        jsonObject.put("result", 1);
//
//        PrintWriter pw = res.getWriter();
//        pw.print(jsonObject);
//        pw.flush();
//        pw.close();

    }
}