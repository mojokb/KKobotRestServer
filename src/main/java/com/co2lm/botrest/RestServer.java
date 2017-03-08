package com.co2lm.botrest;

import com.co2lm.botrest.domain.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.io.*;


/**
 * @author Myouhgwhan Lee on 2017. 2. 14..
 */
@RestController
@SpringBootApplication
public class RestServer {


    @RequestMapping(value = "/keyboard")
    public Keyboard keyboard(){
        Keyboard keyboard = new Keyboard();

        keyboard.setType("text");

        //keyboard.setType("buttons");

        //String[] myButtons = {"초5 수학카드", "초6 수학카드", "중1 수학카드", "중2 수학카드", "중3 수학카드"};
        //keyboard.setButtons(myButtons);

        return keyboard;
    }

    @RequestMapping(value = "/message", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public ResultMessage message(@RequestBody UserMessage userMessage){

        ResultMessage resultMessage = new ResultMessage();

        Message message = new Message();
        Photo photo = new Photo();
        MessageButton messageButton = new MessageButton();

        //사용자가 사진을 던질시 텍스트, 던진사진, 이동시킬 링크를 보낸다
        if("photo".equals(userMessage.getType())) {



            //텍스트
            message.setText("당신이 보낸 사진입니다");

            //사용자가 보낸 사진 설정
            photo.setUrl(userMessage.getContent());
            photo.setWidth(300);photo.setHeight(300);
            message.setPhoto(photo);

            //이동시킬 링크(메세지 버튼)
            messageButton.setLabel("이제 수학사이트 공부하러 갈래?");
            messageButton.setUrl("http://www.ebsmath.co.kr");
            message.setMessage_button(messageButton);

            //메세지를 삽입하자
            resultMessage.setMessage(message);
            return resultMessage;

        }else {
            //사용자가 정우성이란 말을 입력했을때
            if("정우성".equals(userMessage.getContent())){
                //JSOUP으로 파싱
                Document doc = null;
                try {
                    doc = Jsoup.connect("http://tenasia.hankyung.com/archives/828634").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements picture = doc.select(".wp-image-828927");
                System.out.println(" picture.size() " + picture.size());
                for(Element pic: picture){

                    if("정우성(1)".equals(pic.attr("alt"))){
                        Photo photo1 = new Photo();
                        photo1.setUrl(pic.attr("src"));
                        photo1.setWidth(300);photo1.setHeight(300);
                        message.setPhoto(photo1);
                        messageButton.setLabel("정우성을 알고 싶어?");
                        messageButton.setUrl("http://namu.wiki/w/정우성");
                        message.setMessage_button(messageButton);
                    }
                }

            } else {
                message.setText("일단 정우성이랑 닮았는지 사진 먼저 보내봐 ");
                resultMessage.setMessage(message);
            }
            return resultMessage;
        }

    }

    public static void main(String[] args) {
        SpringApplication.run(RestServer.class, args);
    }

}
