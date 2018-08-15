package com.scanapp.util;


import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreditCardUtils {

//    public  static boolean isCreditCardNumber(String str) {
//        if (!NumberUtils.isDigits(str)){
//            return false;
//        }
//
//        int[] ints = new int[str.length()];
//        for (int i = 0; i < str.length(); i++) {
//            ints[i] = Integer.parseInt(str.substring(i, i + 1));
//        }
//        for (int i = ints.length - 2; i >= 0; i = i - 2) {
//            int j = ints[i];
//            j = j * 2;
//            if (j > 9) {
//                j = j % 10 + 1;
//            }
//            ints[i] = j;
//        }
//        int sum = 0;
//        for (int i = 0; i < ints.length; i++) {
//            sum += ints[i];
//        }
//        if (sum % 10 == 0) {
//            System.out.println(str + " is a valid credit card number");
//
//            return true;
//        } else {
//            System.out.println(str + " is an invalid credit card number");
//            return false;
//        }
//    }


    public static boolean check(String ccNumber)
    {
        if (!NumberUtils.isDigits(ccNumber)){
            return false;
        }

        int sum = 0;
        boolean alternate = false;
        for (int i = ccNumber.length() - 1; i >= 0; i--)
        {
            int n = Integer.parseInt(ccNumber.substring(i, i + 1));
            if (alternate)
            {
                n *= 2;
                if (n > 9)
                {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        if (sum % 10 == 0) {
            System.out.println(ccNumber + " is a valid credit card number");

            return true;
        } else {
            System.out.println(ccNumber + " is an invalid credit card number");
            return false;
        }
        //return (sum % 10 == 0);
    }




}
