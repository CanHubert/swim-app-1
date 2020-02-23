package com.can.app.swim.swimapp.helpers.email;

import com.can.app.swim.swimapp.entity.User;

public class EmailReceiver {

    private final User user;

    public EmailReceiver(final User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
