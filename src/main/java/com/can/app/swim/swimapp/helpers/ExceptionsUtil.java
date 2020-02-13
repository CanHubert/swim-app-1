package com.can.app.swim.swimapp.helpers;

import java.util.function.Supplier;

public class ExceptionsUtil {

    public static Supplier<? extends RuntimeException>
            throwRuntimeException(String message){
        return ()-> new RuntimeException(message);
    }
}
