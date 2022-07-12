package kr.carz.savecar.controller.Admin;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import kr.carz.savecar.controller.ShortTermRentCar.RealtimeRentController;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ExcelController {

    private final MonthlyRentService monthlyRentService;
    private final YearlyRentService yearlyRentService;
    private final RealTimeRentCarService realTimeRentCarService;
    private final RealTimeRentCarImageService realTimeRentImageService;
    private final RealtimeRentController realtimeRentController;

    @Autowired
    public ExcelController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService,
                           RealTimeRentCarService realTimeRentCarService, RealTimeRentCarImageService realTimeRentImageService,
                           RealtimeRentController realtimeRentController) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.realTimeRentCarService = realTimeRentCarService;
        this.realTimeRentImageService = realTimeRentImageService;
        this.realtimeRentController = realtimeRentController;
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

        return "rentcar_price_excelList";

    }


}