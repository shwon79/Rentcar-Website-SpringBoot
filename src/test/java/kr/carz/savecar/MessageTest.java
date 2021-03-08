package kr.carz.savecar;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class MessageTest {

    @Test
    public void sendSms() {

        String api_key = "NCS0P5SFAXLOJMJI";
        String api_secret = "FLLGUBZ7OTMQOXFSVE6ZWR2E010UNYIZ";
        Message coolsms = new Message(api_key, api_secret);
        HashMap<String, String> params = new HashMap<String, String>();

        params.put("to", "01058283328");
        params.put("from", "01058283328");
        params.put("type", "SMS");
        params.put("text", "@@님이 예약을 완료하였습니다."); //메시지 내용
        params.put("app_version", "test app 1.2");

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString()); //전송 결과 출력
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
    }
}
