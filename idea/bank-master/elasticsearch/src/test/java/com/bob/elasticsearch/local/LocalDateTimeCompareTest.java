package com.bob.elasticsearch.local;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.apache.http.client.utils.DateUtils;
import org.junit.Test;

/**
 * @author wb-jjb318191
 * @create 2019-04-28 15:53
 */
public class LocalDateTimeCompareTest {

    @Test
    public void testTime() {
        LocalDateTime endTime = LocalDateTime.ofInstant(parsePayTime("2019-04-24 19:21:13").toInstant(), ZoneId.systemDefault()).plusHours(
            24);
        System.out.println(endTime.isAfter(LocalDateTime.now()));
        System.out.println(endTime.toString());
    }

    /**
     * 解析支付时间
     *
     * @param payTime
     * @return
     */
    private Date parsePayTime(String payTime) {
        try {
            return DateUtils.parseDate(payTime, new String[] {"yyyy-MM-dd HH:mm:ss"});
        } catch (Exception e) {
            return DateUtils.parseDate(payTime, new String[] {"yyyyMMddHHmmss"});

        }
    }

}
