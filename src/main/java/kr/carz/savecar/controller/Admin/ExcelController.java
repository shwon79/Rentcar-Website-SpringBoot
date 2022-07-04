package kr.carz.savecar.controller.Admin;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import kr.carz.savecar.domain.ExcelData;
import kr.carz.savecar.dto.RentCarVO;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ExcelController {

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


    @PostMapping(value="/excel/upload", consumes= MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public void uploadExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {

        JSONObject jsonObject = new JSONObject();
        List<ExcelData> dataList = new ArrayList<>();

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

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {

            Row row = worksheet.getRow(i);

            RentCarVO rentCarVO = new RentCarVO();
            rentCarVO.setCategory1(row.getCell(0).getStringCellValue());
            rentCarVO.setCategory2(row.getCell(1).getStringCellValue());


            rentCarVO.setName(row.getCell(2).getStringCellValue());
            rentCarVO.setDeposit_monthly(row.getCell(3).getStringCellValue());
            rentCarVO.setCategory2(row.getCell(4).getStringCellValue());
            rentCarVO.setCategory2(row.getCell(5).getStringCellValue());
            rentCarVO.setCategory2(row.getCell(6).getStringCellValue());
            rentCarVO.setCategory2(row.getCell(7).getStringCellValue());
            rentCarVO.setCategory2(row.getCell(8).getStringCellValue());
            rentCarVO.setCategory2(row.getCell(9).getStringCellValue());
            rentCarVO.setCategory2(row.getCell(10).getStringCellValue());
            rentCarVO.setCategory2(row.getCell(11).getStringCellValue());
            rentCarVO.setCategory2(row.getCell(12).getStringCellValue());
            rentCarVO.setCategory2(row.getCell(13).getStringCellValue());
            rentCarVO.setCategory2(row.getCell(14).getStringCellValue());
            rentCarVO.setCategory2(row.getCell(15).getStringCellValue());
            rentCarVO.setCategory2(row.getCell(16).getStringCellValue());
            rentCarVO.setCategory2(row.getCell(17).getStringCellValue());
            rentCarVO.setCategory2(row.getCell(18).getStringCellValue());
            rentCarVO.setCategory2(row.getCell(19).getStringCellValue());
            rentCarVO.setCategory2(row.getCell(20).getStringCellValue());
            rentCarVO.setCategory2(row.getCell(21).getStringCellValue());
            rentCarVO.setCategory2(row.getCell(22).getStringCellValue());



//            ExcelData data = new ExcelData();
//
//            data.setNum((int) row.getCell(0).getNumericCellValue());
//            data.setName(row.getCell(1).getStringCellValue());
//            data.setEmail(row.getCell(2).getStringCellValue());

            dataList.add(data);
        }

        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();

    }
}