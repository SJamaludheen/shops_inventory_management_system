package utils;


import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class RandomGen {

    private RandomGen() {

    }

    public static String randomString(int count) {
        return RandomStringUtils.randomAlphanumeric(count);
    }

    public static String randomNumberString(int min, int max) {
        int num = ThreadLocalRandom.current().nextInt(min, max + 1);
        return Integer.toString(num);
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyMMddHHmm");
        Date now = new Date();
        return sdfDate.format(now);
    }
}