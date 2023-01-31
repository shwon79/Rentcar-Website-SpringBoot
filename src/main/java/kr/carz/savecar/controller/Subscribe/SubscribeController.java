package kr.carz.savecar.controller.Subscribe;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.dto.LongTermRentDTO;
import kr.carz.savecar.service.LongTermRentImageService;
import kr.carz.savecar.service.LongTermRentService;
import kr.carz.savecar.service.SubscribeImageService;
import kr.carz.savecar.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class SubscribeController {

    private final SubscribeService subscribeService;
    private final SubscribeImageService subscribeImageService;

    @Autowired
    public SubscribeController(SubscribeService subscribeService, SubscribeImageService subscribeImageService) {
        this.subscribeService = subscribeService;
        this.subscribeImageService = subscribeImageService;
    }

    @GetMapping("/rent/subscribe")
    public String rent_subscribe(Model model) {

        List<Subscribe> subscribeList = subscribeService.findAllByOrderBySequenceAsc();
        List<List<SubscribeImage>> imageList = new ArrayList<>();

        for(Subscribe subscribe : subscribeList){
            imageList.add(subscribeImageService.findBySubscribe(subscribe));
        }

        model.addAttribute("subscribeList", subscribeList);
        model.addAttribute("imageList", imageList);

        return "rent_subscribe/main";
    }

    @GetMapping("/rent/subscribe/{subscribeId}")
    public String rent_subscribe_detail(Model model, @PathVariable Long subscribeId) {

        Optional<Subscribe> subscribeOptional = subscribeService.findById(subscribeId);
        if(subscribeOptional.isPresent()){
            Subscribe subscribe = subscribeOptional.get();
            List<SubscribeImage> subscribeImageList = subscribeImageService.findBySubscribe(subscribe);
            model.addAttribute("subscribe", subscribe);
            model.addAttribute("subscribeImageList", subscribeImageList);
        }
        return "rent_subscribe/detail";
    }
}