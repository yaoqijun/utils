package org.yqj.boot.demo;

import com.google.common.base.Throwables;

/**
 * Created by yaoqijun.
 * Date:2016-06-05
 * Email:yaoqj@terminus.io
 * Descirbe:
 */
public class MainTestContent {
    public static void main(String[] args) {
//        System.out.println(Objects.equal(null, null));
//        List<Integer> sortIntegers = Lists.newArrayList(12,52,33,142,355,61,71);
//        System.out.println(sortIntegers.stream().parallel().sorted().collect(Collectors.toList()));

        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("illegal status run time");
        System.out.println(Throwables.getStackTraceAsString(illegalArgumentException));
    }
}
