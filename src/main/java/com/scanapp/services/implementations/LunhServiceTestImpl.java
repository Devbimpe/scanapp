package com.scanapp.services.implementations;

import com.scanapp.services.LunhServiceTest;
import org.springframework.stereotype.Service;

/**
 * Created by Longbridge on 09/08/2018.
 */
@Service
public class LunhServiceTestImpl implements LunhServiceTest{

        public void main(String[] args) {
            validateCreditCardNumber("12345678903555");
            String imei = "012850003580200";
            validateCreditCardNumber(imei);
        }

        public void validateCreditCardNumber(String str) {

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
            } else {
                System.out.println(str + " is an invalid credit card number");
            }
        }


}
