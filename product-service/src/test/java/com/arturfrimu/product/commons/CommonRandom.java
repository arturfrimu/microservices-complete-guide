package com.arturfrimu.product.commons;

import java.math.BigDecimal;
import java.util.Random;

import static java.math.RoundingMode.HALF_UP;

public class CommonRandom {

    private final static Random random = new Random();

    public static <T extends Enum<?>> T randomEnumConstant(Class<T> clazz) {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

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

    public static double randomDouble(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    public static long randomLong(long min, long max) {
        return min + (max - min) * random.nextLong();
    }

    public static BigDecimal randomBigDecimal(double min, double max) {
        return BigDecimal.valueOf(randomDouble(min, max)).setScale(2, HALF_UP);
    }
}
