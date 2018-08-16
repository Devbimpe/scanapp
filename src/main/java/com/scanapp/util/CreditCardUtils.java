package com.scanapp.util;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class CreditCardUtils {

    public  static boolean isCreditCardNumber(String str) {
        str=StringUtils.trim(str);
        if (!NumberUtils.isDigits(str)){
            return false;
        }

        int[] ints = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            ints[i] = Integer.parseInt(str.substring(i, i + 1));
        }
        for (int i = ints.length - 2; i >= 0; i = i - 2) {
            int j = ints[i];
            j = j * 2;
            if (j > 9) {
                j = j % 10 + 1;
            }
            ints[i] = j;
        }
        int sum = 0;
        for (int i = 0; i < ints.length; i++) {
            sum += ints[i];
        }
        if (sum % 10 == 0) {
            System.out.println(str + " is a valid credit card number");
            return true;
        } else {
            System.out.println(str + " is an invalid credit card number");
            return false;
        }
    }


    public static  void main(String[] args) {
        isCreditCardNumber("  12345678903555  ");
        String imei = "012850003580200";
        isCreditCardNumber(imei);
    }

}
