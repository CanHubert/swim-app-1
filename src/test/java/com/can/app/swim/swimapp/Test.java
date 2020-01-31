package com.can.app.swim.swimapp;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {

        String text = "dzien dobry ${user}! co tam u Ciebie ${user}";

        Map<String,String> map = new HashMap<>();

        map.put("user", "Hubert Can");

        for(String k : map.keySet())
        {
            text = text.replace("${"+k+"}", map.get(k));
        }

        System.out.println(text);
    }
}
