package com.can.app.swim.swimapp.helpers;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.function.Supplier;

public class ExceptionsUtil {

    public static Supplier<? extends RuntimeException>
            throwRuntimeException(String message){
        return ()-> new RuntimeException(message);
    }

    public static Supplier<? extends  RuntimeException> usernameNotFoundException(String message){
        return ()-> new UsernameNotFoundException(message);
    }
}
