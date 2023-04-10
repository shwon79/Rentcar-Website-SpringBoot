package kr.carz.savecar.controller.Utils;

import kr.carz.savecar.domain.RealTimeRentCar;
import kr.carz.savecar.service.RealTimeRentCarService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class ExternalAPIController {

    private final RealTimeRentCarService realTimeRentCarService;

    @Autowired
    public ExternalAPIController(RealTimeRentCarService realTimeRentCarService) {
        this.realTimeRentCarService = realTimeRentCarService;
    }

    @RequestMapping(value = "/api/price/monthly", produces = "application/json; charset=UTF-8;", method = RequestMethod.GET)
    @ResponseBody
    public void api_price_monthly_carGubun_isExpected(HttpServletResponse res, @RequestParam("carGubun")String carGubun,
                                                      @RequestParam("isExpected")int isExpected) throws IOException {
        List<RealTimeRentCar> realTimeRentCarList = realTimeRentCarService.findByCarGubunAndIsExpectedAndIsLongTerm(carGubun, isExpected, 0);
        JSONArray jsonArray = new JSONArray();
        for (RealTimeRentCar c : realTimeRentCarList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("carId", c.getCarIdx());
            jsonObject.put("carName", c.getCarName());
            jsonObject.put("carCategory", c.getCarCategory());
            jsonObject.put("carColor", c.getCarExteriorColor());
            jsonObject.put("carAttribute", c.getCarAttribute01());
            jsonObject.put("carOld", c.getCarOld());
            jsonObject.put("carDetail", c.getCarDetail());
            jsonObject.put("carOilType", c.getCarEngine());
            jsonObject.put("carNo", c.getCarNo());

            JSONObject jsonObjectPrice = new JSONObject();
            jsonObjectPrice.put("2000km", c.getMonthlyRent().getCost_for_2k());
            jsonObjectPrice.put("2500km", c.getMonthlyRent().getCost_for_2_5k_price());
            jsonObjectPrice.put("3000km", c.getMonthlyRent().getCost_for_3k_price());
            jsonObjectPrice.put("4000km", c.getMonthlyRent().getCost_for_4k_price());
            jsonObject.put("carPrice", jsonObjectPrice);


            jsonArray.put(jsonObject);
        }

        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html; charset=UTF-8");
        PrintWriter pw = res.getWriter();
        pw.print(jsonArray);
        pw.flush();
        pw.close();
    }

}