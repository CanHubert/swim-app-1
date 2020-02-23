package com.can.app.swim.swimapp.enums;

public enum EmailName {
    WELCOME("Register");

    private final String templateName;

    EmailName(String templateName){
        this.templateName = templateName;
    }

    public String getTemplateName() {
        return templateName;
    }
}
