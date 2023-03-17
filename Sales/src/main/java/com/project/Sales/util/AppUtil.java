package com.project.Sales.util;

import org.springframework.stereotype.Component;

import java.util.Random;


@Component
public class AppUtil {

    public Long generateRandomCode(){
        Random rnd = new Random();
        Long number = (long) rnd.nextInt(999999);
        return  number;
    }
}

