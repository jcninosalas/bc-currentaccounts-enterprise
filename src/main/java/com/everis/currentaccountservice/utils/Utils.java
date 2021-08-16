package com.everis.currentaccountservice.utils;

import com.everis.currentaccountservice.model.CurrentAccount;
import com.everis.currentaccountservice.model.Customer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Utils {

    private static String generateAccountNumber() {
        final String SAVINGS_ACCOUNT_PREFIX = "300-";
        Random random = new Random();
        return SAVINGS_ACCOUNT_PREFIX + random.nextInt(999999999);
    }

    public static CurrentAccount createNewCurrentAccount(Customer customer) {
        CurrentAccount account = new CurrentAccount();
        account.setAccountNumber(Utils.generateAccountNumber());
        account.setCreatedAt(new Date());
        account.setAccountHolders(List.of(customer));
        account.setAccountBalance(new BigDecimal(0));
        account.setIsActive(true);
        account.setMaintenanceFee(3);
        return account;
    }
}
