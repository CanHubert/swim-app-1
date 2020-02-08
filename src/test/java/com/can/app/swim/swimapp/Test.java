package com.can.app.swim.swimapp;

import com.can.app.swim.swimapp.entity.Country;
import com.can.app.swim.swimapp.enums.EnumRole;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Country country = new Country();
        country.setName("sadasdas");

        System.out.println(country.getName());
       // enumTest();
    }

    private static void enumTest(){
        String enumText = "mod";

        EnumRole role =  EnumRole.getByName(enumText);
        System.out.println(role);
    }

    private static void replaceTest() {
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
