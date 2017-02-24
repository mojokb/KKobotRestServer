package com.co2lm.botrest.domain;

import java.util.Arrays;

/**
 * @author Myouhgwhan Lee on 2017. 2. 14..
 */
public class Keyboard {

    String type; // required, [text|buttons]
    String[] buttons; //Optional

    public String[] getButtons() {
        return buttons;
    }

    public void setButtons(String[] buttons) {
        this.buttons = buttons;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "Keyboard{" +
                "type='" + type + '\'' +
                ", buttons=" + Arrays.toString(buttons) +
                '}';
    }
}
