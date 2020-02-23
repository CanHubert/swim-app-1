package com.can.app.swim.swimapp.helpers.email;

import com.can.app.swim.swimapp.entity.User;
import com.can.app.swim.swimapp.enums.EmailName;

import java.util.HashMap;
import java.util.Map;

public class MailValues {

    private final EmailName emailName;
    private final EmailReceiver receiver;
    private Map<String, String> map ;

    public MailValues(EmailName emailName,EmailReceiver receiver){
        this.emailName = emailName;
        this.receiver = receiver;
        createMapForObject(receiver);
    }

//    static public MailValues createMailValues(EmailName emailName, EmailReceiver receiver){
//        MailValues mailValues = new MailValues(emailName,receiver);
//        return this;
//    }

    public MailValues createMapForObject(EmailReceiver receiver){
        this.map = new HashMap<>();
       switch (emailName){
           case WELCOME : {
               User user = receiver.getUser();
               map.put("user", user.getFullName());
           }
           default: {
               //do nothing
           }
       }

        return this;
    }

    public final Map<String, String> getMap(){
        return map;
    }

}
