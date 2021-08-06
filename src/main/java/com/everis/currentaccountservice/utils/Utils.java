package com.everis.currentaccountservice.utils;

import java.util.Random;

public class Utils {

    public static String generateAccountNumber() {
        final String SAVINGS_ACCOUNT_PREFIX = "300-";
        Random random = new Random();
        return SAVINGS_ACCOUNT_PREFIX + random.nextInt(999999999);
    }
}
