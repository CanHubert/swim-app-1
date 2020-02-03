package com.can.app.swim.swimapp.enums;

import org.apache.commons.lang.StringUtils;

public interface BaseEnum<TYPE extends  Enum<TYPE>> {

    static <TYPE extends  Enum<TYPE>> TYPE findByName(Class<TYPE> enumType, String name){
        if(enumType == null || StringUtils.isBlank(name))
        {
            return null;
        }
        else
        {
            return Enum.valueOf(enumType,name);
        }
    }

}
