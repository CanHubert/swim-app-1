package com.can.app.swim.swimapp.helpers;

import java.util.HashMap;
import java.util.Map;

public class MailProperties {

    private Map<String, String> map ;

     public MailProperties(){
         map= new HashMap<>();
    }

    public void add(String key, Object value){
        map.put(key, value.toString());
    }

    public Map<String, String> getMap(){
        return map;
    }
}
