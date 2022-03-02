package kr.carz.savecar.controller;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class HelloController {
    private final MonthlyRentService monthlyRentService;
    private final YearlyRentService yearlyRentService;
    private final TwoYearlyRentService twoYearlyRentService;
    private final ShortRentService shortRentService;
    private final CampingCarPriceService campingCarPriceService;
    private final ValuesForWebService valuesForWebService;
    private final ImagesService imagesService;

    @Autowired
    public HelloController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService, TwoYearlyRentService twoYearlyRentService,
                           ShortRentService shortRentService, CampingCarPriceService campingCarPriceService,
                           ValuesForWebService valuesForWebService, ImagesService imagesService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.twoYearlyRentService = twoYearlyRentService;
        this.shortRentService = shortRentService;
        this.campingCarPriceService = campingCarPriceService;
        this.valuesForWebService = valuesForWebService;
        this.imagesService = imagesService;
    }


    @GetMapping(value={"/","/index"})
    public String index(Model model) {

        List<CampingCarPrice> campingCarList = campingCarPriceService.findAllCampingCarPrice();
        List<Images> imagesMainList = new ArrayList<>();

        for(CampingCarPrice campingCar : campingCarList){
            List<Images> mainImage = imagesService.findByCarNameAndIsMain(campingCar, "1");

            if(mainImage.size() == 0){
                imagesMainList.add(new Images((long) -1, campingCar, -1, "", "0", "1"));
            } else {
                imagesMainList.add(mainImage.get(0));
            }
        }
        model.addAttribute("campingCarList", campingCarList);
        model.addAttribute("imagesMainList", imagesMainList);

        return "index";
    }


    @GetMapping("/index/popup/value/{title}")
    @ResponseBody
    public void getPopUpValue(HttpServletResponse res, @PathVariable String title) throws IOException {

        Optional<ValuesForWeb> valueWrapper = valuesForWebService.findValueByTitle(title);

        JSONObject jsonObject = new JSONObject();
        if(valueWrapper.isPresent()){
            ValuesForWeb value = valueWrapper.get();
            jsonObject.put("value", value.getValue());
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    @GetMapping("/index/popup/value/campingCar/{carName}")
    @ResponseBody
    public void getPopUpCampingCarValue(HttpServletResponse res, @PathVariable String carName) throws IOException {

        JSONObject jsonObject = new JSONObject();

        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(carName);
        jsonObject.put("campingCarPrice", campingCarPrice);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }



    @GetMapping("/rent/long_term")
    public String rent_long_term() {
        return "rent_longterm/main";
    }

    @GetMapping("/price/month")
    public String price_month(Model model) {

        List<MonthlyRent> monthlyRentList = monthlyRentService.findAllMonthlyRents();
        model.addAttribute("monthlyRentList", monthlyRentList);

        return "rent_price/month";
    }


    @GetMapping("/price/yearly")
    public String price_yearly(Model model) {

        List<MonthlyRent> monthlyRentList = monthlyRentService.findAllMonthlyRents();

        Collections.sort(monthlyRentList);

        model.addAttribute("monthlyRentList", monthlyRentList);

        return "rent_price/yearly";
    }


    @GetMapping("/price/twoYearly")
    public String price_twoYearly(Model model) {

        List<MonthlyRent> monthlyRentList = monthlyRentService.findByCategory2AndTwoYearlyRentIsNotNull()

        Collections.sort(monthlyRentList);

        model.addAttribute("monthlyRentList", monthlyRentList);

        return "rent_price/twoYearly";
    }


    @GetMapping("/price/short")
    public String price_short(Model model) {

        List<ShortRent> shortRentList = shortRentService.findShortRents();
        List<ShortRent> shortRentListForeign = shortRentService.findShortRentsByCategory1("수입차");
        List<ShortRent> shortRentListNotForeign = shortRentService.findShortRentsByNotCategory1("수입차");

        model.addAttribute("shortRentList", shortRentList);
        model.addAttribute("shortRentListForeign", shortRentListForeign);
        model.addAttribute("shortRentListNotForeign", shortRentListNotForeign);

        return "rent_price/short";
    }


}
