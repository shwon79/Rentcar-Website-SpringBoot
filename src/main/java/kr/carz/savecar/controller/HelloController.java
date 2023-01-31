package kr.carz.savecar.controller;

import kr.carz.savecar.controller.Utils.Rent24Connection;
import kr.carz.savecar.domain.*;
import kr.carz.savecar.dto.Rent24EventVO;
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
    private final RealTimeRentCarService realTimeRentCarService;
    private final LongTermRentService longTermRentService;
    private final LongTermRentImageService longTermRentImageService;
    private final SubscribeService subscribeService;
    private final SubscribeImageService subscribeImageService;

    @Autowired
    public HelloController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService, TwoYearlyRentService twoYearlyRentService,
                           ShortRentService shortRentService, CampingCarPriceService campingCarPriceService,
                           ValuesForWebService valuesForWebService, ImagesService imagesService,
                           RealTimeRentCarService realTimeRentCarService, LongTermRentService longTermRentService,
                           LongTermRentImageService longTermRentImageService, SubscribeService subscribeService,
                           SubscribeImageService subscribeImageService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.twoYearlyRentService = twoYearlyRentService;
        this.shortRentService = shortRentService;
        this.campingCarPriceService = campingCarPriceService;
        this.valuesForWebService = valuesForWebService;
        this.imagesService = imagesService;
        this.realTimeRentCarService = realTimeRentCarService;
        this.longTermRentService = longTermRentService;
        this.longTermRentImageService = longTermRentImageService;
        this.subscribeService = subscribeService;
        this.subscribeImageService = subscribeImageService;
    }


    @GetMapping(value={"/","/index"})
    public String index(Model model) throws IOException {

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
        List<RealTimeRentCar> morenDTOList = realTimeRentCarService.findByIsExpected(0);
        List<RealTimeRentCar> fiveMorenDTOList = morenDTOList.subList(0, 5);
        List<LongTermRent> fourOldLongTermRentList = longTermRentService.findTop4ByNewOldOrderBySequenceAsc("중고차장기");
        List<LongTermRent> fourNewLongTermRentList = longTermRentService.findTop4ByNewOldOrderBySequenceAsc("신차장기");
        List<Subscribe> fourSubscribeList = subscribeService.findTop4ByOrderBySequenceAsc();
        List<List<LongTermRentImage>> oldlongTermRentImageList = new ArrayList<>();
        List<List<LongTermRentImage>> newlongTermRentImageList = new ArrayList<>();
        List<List<SubscribeImage>> subscribeImageList = new ArrayList<>();

        for(LongTermRent longTermRent : fourOldLongTermRentList){
            oldlongTermRentImageList.add(longTermRentImageService.findByLongTermRent(longTermRent));
        }
        for(LongTermRent longTermRent : fourNewLongTermRentList){
            newlongTermRentImageList.add(longTermRentImageService.findByLongTermRent(longTermRent));
        }
        for(Subscribe subscribe : fourSubscribeList) {
            subscribeImageList.add(subscribeImageService.findBySubscribe(subscribe));
        }

        // rent24(https://rent-24.co.kr/) 에서 event data 가져옴
        Rent24Connection rent24Connection = new Rent24Connection();
        List<Rent24EventVO> rent24EventList = rent24Connection.getEventCars();

        model.addAttribute("fiveMorenDTOList", fiveMorenDTOList);
        model.addAttribute("fourOldLongTermRentList", fourOldLongTermRentList);
        model.addAttribute("oldlongTermRentImageList", oldlongTermRentImageList);
        model.addAttribute("fourNewLongTermRentList", fourNewLongTermRentList);
        model.addAttribute("newlongTermRentImageList", newlongTermRentImageList);
        model.addAttribute("fourSubscribeList", fourSubscribeList);
        model.addAttribute("subscribeImageList", subscribeImageList);
        model.addAttribute("rent24EventList", rent24EventList);
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


    @GetMapping("/price/{period}/{category2}")
    public String price_month(Model model, @PathVariable String period, @PathVariable String category2) {

        List<MonthlyRent> monthlyRentList = monthlyRentService.findByCategory2(category2);
        Collections.sort(monthlyRentList);
        model.addAttribute("monthlyRentList", monthlyRentList);

        switch (period) {
            case "yearly":
                return "rent_price/yearly";
            case "twoYearly":
                monthlyRentList = monthlyRentService.findByCategory2AndTwoYearlyRentIsNotNull(category2);
                model.addAttribute("monthlyRentList", monthlyRentList);
                return "rent_price/twoYearly";
            default:
                return "rent_price/month";
        }
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
