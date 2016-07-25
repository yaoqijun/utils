package org.yqj.boot.demo;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by yaoqijun.
 * Date:2016-04-27
 * Email:yaoqj@terminus.io
 * Descirbe:
 */
@SpringBootApplication
@Controller
@Slf4j
public class BootDemoApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .banner(new DemoBanner())
                .sources(BootDemoApplication.class)
                .run(args);
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String hello(){
        return "hello world condition yaoqijun test condition vw hot swap content over again ";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    public String queryIndexHtml(HttpServletResponse response){
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        return  "<html>\n" +
                "<head>\n" +
                "    <title>this is title content</title>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <form action=\"/testForm\" method=\"post\">\n" +
                "        <p> 输入目标金额 ：<input type=\"text\" name=\"targetMoney\" /></p>\n" +
                "        <p>输入对应的金额数量(money, count) 不要瞎输: <textarea name=\"areaname\" cols=\"20\" rows=\"5\"> test area </textarea></p>\n" +
                "        <input type=\"submit\" value=\"Submit\" />\n" +
                "    </form>\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>";
    }

    @RequestMapping(value = "/testForm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String testFormContent(@RequestParam("areaname") String areaname,
                                  @RequestParam("targetMoney") String targetMoney){
        try{
            String[] eachCount = areaname.split("\r\n");
            Double[] money = new Double[eachCount.length];
            Integer[] count = new Integer[eachCount.length];
            for (int i=0; i<eachCount.length; i++) {
                String target = eachCount[i];
                if(target.length() == 0) continue;
                String[]arr = target.split(",");
                money[i] = Double.valueOf(arr[0]);
                count[i] = Integer.valueOf(arr[1]);
            }
            return RunCommanLine.calculateMoney(money, count, Double.valueOf(targetMoney)).toString();
        }catch (Exception e){
            log.error("exception {}", Throwables.getStackTraceAsString(e));
            System.out.println("error");
            return "hehe";
        }
    }
}
