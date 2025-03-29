package com.wipi.util;

import java.util.UUID;


public class ServiceUtil {

    public static String userPrefix = "user";
    public static String bankAccountIdPrefix = "bankAccount";

    public static String createUserId(){
        return  userPrefix +
                UUID.randomUUID().toString().toUpperCase().
                replace("-", "");
    }
    public static String createBankAccountId(){
        return  bankAccountIdPrefix +
                UUID.randomUUID().toString().toUpperCase().
                replace("-", "");
    }




}
