package com.fresh.mall.util;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderCodeFactory {

    private static final String ORDER_CODE = "1";
    /**
     * random code
     */
    private static final int[] r = new int[]{7, 9, 6, 2, 8, 1, 3, 0, 5, 4};
    /**
     * total length of user id + random code
     */
    private static final int maxLength = 5;

    /**
     * id + random code encode
     */
    private static String toCode(Long id) {
        String idStr = id.toString();
        StringBuilder idSb = new StringBuilder();
        for (int i = idStr.length() - 1; i >= 0; i--) {
            idSb.append(r[idStr.charAt(i) - '0']);
        }
        return idSb.append(getRandom(maxLength - idStr.length())).toString();
    }

    /**
     * timestamp
     */
    private static String getDateTime() {
        DateFormat sdf = new SimpleDateFormat("HHmmss");
        return sdf.format(new Date());
    }

    /**
     * generate random number
     *
     * @param n length
     */
    private static long getRandom(long n) {
        long min = 1, max = 9;
        for (int i = 1; i < n; i++) {
            min *= 10;
            max *= 10;
        }
        long rangeLong = (((long) (new Random().nextDouble() * (max - min)))) + min;
        return rangeLong;
    }

    /**
     * generate id
     */
    private static synchronized String getCode(Long userId) {
        userId = userId == null ? 10000 : userId;
        return getDateTime() + toCode(userId);
    }

    /**
     * order id
     * @param userId
     */
    public static String getOrderCode(Long userId) {
        return ORDER_CODE + getCode(userId);
    }
}
