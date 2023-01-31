package kr.carz.savecar.controller.Utils;

import kr.carz.savecar.dto.Rent24EventVO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class Rent24Connection {

    public List<Rent24EventVO> getEventCars() throws IOException {

        List<Rent24EventVO> rent24EventVOList = new ArrayList<>();

        String url = "https://rent-24.co.kr/layout/res/home.php?go=main";
        Document doc = Jsoup.connect(url).get();
        Elements elem = doc.select("div[class=\"section02 mc_sec b\"]");
        for(Element e : elem.select("div[class=\"prod_wrapper\"]")){
            String imageUrl = "https://rent-24.co.kr" + e.select("img").attr("src");
            String carTitle = e.select("h2[class=\"p_tit\"]").text();
            String carPrice = e.select("p[class=\"price\"]").text();
            String period = e.select("ul[class=\"p_mid\"] > li").get(0).text();
            String deposit = e.select("ul[class=\"p_mid\"] > li").get(1).text();
            String rentPrice = e.select("div[class=\"p_bot\"] > h2").text();

            Rent24EventVO eventVO = new Rent24EventVO(imageUrl, carTitle, carPrice, period, deposit, rentPrice);
            rent24EventVOList.add(eventVO);
        }

        return rent24EventVOList;
    }

}
