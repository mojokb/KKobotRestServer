package com.co2lm.botrest.KakaoYellow;

import com.co2lm.botrest.aws.AwsService;
import com.co2lm.botrest.domain.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author Myouhgwhan Lee on 2017. 3. 14..
 */
@RestController
public class KakaoRestController {

    @Autowired
    AwsService awsService;

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

        /**
         * 사용자가 사진을 던질시 사진의 경로와 사용자의 user_key를 memory db에 삽입하고,
         * 사용자가 올리 사진을 다시 반환하여, 등록되었고 비교중이니 5초후에 닮았어? 라고 외쳐봐.. 라는 메세지
         */

        if("photo".equals(userMessage.getType())) {
            //텍스트
            message.setText("사진이 잘 등록되었고, 10초만 기다렸다 닮았어? 라고 외쳐봐!");

            //사용자가 보낸 사진 설정
            photo.setUrl(userMessage.getContent());
            //사용자가 보낸 사진 memoryDB 에 올림


            photo.setWidth(300);photo.setHeight(300);
            message.setPhoto(photo);

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
                System.out.println(" picture.toString() " + picture.toString());
                for(Element pic: picture){
                    System.out.println(" pic.attr(\"alt\") " + pic.attr("alt"));
                    if("정우성(1)".equals(pic.attr("alt"))){
                        System.out.println(" pic.attr(\"src\") " + pic.attr("src"));

                        photo.setUrl(pic.attr("src"));
                        photo.setWidth(300);photo.setHeight(300);
                        message.setPhoto(photo);

                        messageButton.setLabel("정우성을 알고 싶어?");
                        messageButton.setUrl("http://namu.wiki/w/정우성");
                        message.setMessage_button(messageButton);
                        //메세지를 삽입하자
                        resultMessage.setMessage(message);
                    }
                }

            } else {
                message.setText("일단 정우성이랑 닮았는지 사진 먼저 보내봐 ");
                //메세지를 삽입하자
                resultMessage.setMessage(message);
            }
            return resultMessage;
        }

    }


}
