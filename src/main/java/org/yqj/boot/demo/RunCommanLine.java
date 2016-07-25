package org.yqj.boot.demo;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Stack;

/**
 * Created by yaoqijun.
 * Date:2016-04-27
 * Email:yaoqj@terminus.io
 * Descirbe:
 */
@Component
@Slf4j
public class RunCommanLine implements CommandLineRunner{

    public void run(String... args) throws Exception {
        log.info(" command info run");
        System.out.println("now command line run ");
        System.out.println("args content is " + Lists.newArrayList(args).toString());
    }

    public static Map<String, String> calculateMoney(Double[] moneyArray, Integer[] sourceCount, Double target){
        // count money
        Integer size = moneyArray.length;
        sortArray(moneyArray, sourceCount);

        Double currentMoneyA = 0d;
        Double chaA = Double.MAX_VALUE;
        Integer[] resultA = new Integer[size];

        Double currentMoneyB = 0d;
        Double chaB = Double.MAX_VALUE;
        Integer[] resultB = new Integer[size];

        // build statck info
        Stack<Point> stack = new Stack<>();
        Integer index= 0;
        stack.push(new Point(index, sourceCount[index]));
        Integer[] currentArray = initArray(size);

        // 列举情况计算对应的数量信息
        while (!stack.isEmpty()){
            Point cp = stack.pop();
            if(cp.y == -1) continue;
            currentArray[cp.x] = cp.y;

            // 计算当前金钱的数量信息
            Double cm = 0d;
            for (int i = 0; i<=cp.x; i++){
                cm += (moneyArray[i] * currentArray[i]);
            }

            // 当前cm 数据信息的记录操作
            if(cm<target){
                if((target-cm)<chaB){
                    chaB = (target-cm);
                    currentMoneyB = cm;
                    copy(currentArray, resultB);
                }
            }else {
                if((cm-target)<chaA){
                    chaA = (cm-target);
                    currentMoneyA = cm;
                    copy(currentArray, resultA);
                }
            }

            // push xia yi ge 节点信息
            stack.push(new Point(cp.x, cp.y - 1));

            if(cm < target && cp.x != (size - 1)){
                stack.push(new Point(cp.x + 1, sourceCount[cp.x + 1]));
            }
        }

//        System.out.println("target moeny is " + target);
//
//        System.out.println("A closed count money is "+ currentMoneyA);
//        System.out.println("A first closed is -------------------------------------");
//        for(int i=0; i<size; i++){
//            System.out.println(moneyArray[i] + "   " + resultA[i]);
//        }
//        System.out.println("A first closed is -------------------------------------");
//
//        System.out.println("B closed count money is "+ currentMoneyB);
//        System.out.println("B first closed is -------------------------------------");
//        for(int i=0; i<size; i++){
//            System.out.println(moneyArray[i] + "   " + resultB[i]);
//        }
//        System.out.println("B first closed is -------------------------------------");

        Map<String,String> resultMap = Maps.newHashMap();
        resultMap.put("currentA", currentMoneyA.toString());
        resultMap.put("chaA", chaA.toString());
        resultMap.put("resultA", Lists.newArrayList(resultA).toString());

        resultMap.put("currentB", currentMoneyB.toString());
        resultMap.put("chaB", chaB.toString());
        resultMap.put("resultB", Lists.newArrayList(resultB).toString());

        resultMap.put("sortedMoney", Lists.newArrayList(moneyArray).toString());
        return resultMap;
    }

    private static class Point{
        public int x;
        public int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }

        public Point(){}
    }

    private static Integer[] initArray(Integer size){
        Integer[] initArray = new Integer[size];
        for(int i=0;i<initArray.length; i++){
            initArray[i] = -1;
        }
        return initArray;
    }

    private static void copy(Integer[] from, Integer[] to){
        for(int i=0; i<from.length; i++){
            to[i] = from[i];
        }
    }

    private static void sortArray(Double[] moneyArray, Integer[] sourceCount){
        int size = moneyArray.length;
        for(int i=0; i<size; i++){
            for(int j=i; j<size; j++){
                if(moneyArray[i]<moneyArray[j]){
                    Double k=0d ;
                    // swap money
                    k = moneyArray[i]; moneyArray[i] = moneyArray[j]; moneyArray[j]=k;

                    int l = 0;
                    // swap count
                    l = sourceCount[i]; sourceCount[i] = sourceCount[j]; sourceCount[j] = l;
                }
            }
        }
    }
}
