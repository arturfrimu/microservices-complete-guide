package com.arturfrimu.customer.commons;

import java.util.Random;

public class CommonRandom {

    private final static Random random = new Random();

    public static String randomString(int length) {
        int leftLimit = '0'; // numeral '0'
        int rightLimit = 'z'; // letter 'z'

        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            if (Character.isLetterOrDigit(randomLimitedInt)) {
                buffer.append((char) randomLimitedInt);
            } else {
                i--;
            }
        }
        return buffer.toString();
    }
}
