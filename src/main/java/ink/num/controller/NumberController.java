package ink.num.controller;

import ink.num.model.Number;
import ink.num.service.NumberService;
import ink.num.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Description
 * @Author
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/3/23 23:09:11
 */
@Controller
//@RequestMapping("number")
public class NumberController {
    @Autowired
    private NumberService numberService;

    @PostMapping("/dic/list")
    @ResponseBody
    public RespBean getAll() {
        return numberService.getAll();
    }

    @GetMapping("/dic/search")
    @ResponseBody()
    public RespBean search(String startTime,String endTime) {
        return numberService.search(startTime,endTime);
    }

    @PostMapping("addData")
    public String addData(Number number) {
        numberService.addData(number);
        return "redirect:/html/index.html";
    }

    @GetMapping("updateAlarm")
    public String updateAlarm(String alarm) {
        RespBean.ALARM = alarm;
        return "redirect:/dic/index.html";
    }

    @GetMapping("downExcl.xls")
    public void downExcl(HttpServletResponse resp,String startTime, String endTime) throws IOException {
        numberService.downExcl(resp,startTime,endTime);
    }

}
