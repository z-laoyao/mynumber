package ink.num.controller;

import ink.num.model.User;
import ink.num.service.UserService;
import ink.num.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/3/24 11:26:11
 */
@Controller
//@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("login")
    @ResponseBody
    public RespBean login( @RequestBody Map<String,String> map) {
        if (!RespBean.verificationCode.equals(map.get("captcha"))){
            return RespBean.builder()
                    .code("500")
                    .msg("验证码错误")
                    .build();
        }
        User user = User.builder().name(map.get("name")).password(map.get("password")).build();
        return userService.login(user);
    }

    @PutMapping("login")
    @ResponseBody
    public RespBean loginPut(HttpSession sess) {
        Object user = sess.getAttribute("user");
        if (user != null) {
            return RespBean.builder()
                    .data(user)
                    .code("200")
                    .msg("登录成功")
                    .build();
        }
        return RespBean.builder()
                .code("500")
                .msg("请登录")
                .build();

    }

    @PostMapping("register")
    public String register(User user) {
        System.out.println(user);
       return userService.register(user);

    }

    @GetMapping("logout")
    public String logout(HttpSession sess) {
        sess.removeAttribute("user");
        sess.removeAttribute("admin");
        return "/login.html";

    }

    @GetMapping("/captcha")
    public void logout(HttpServletResponse resp) throws IOException {
        int code = (int) (Math.random() * 8999 + 1000);
        RespBean.verificationCode = "" + code;
        verificationCode("" + code, resp.getOutputStream());
    }

    @GetMapping("/user/list")
    @ResponseBody
    public RespBean getAll(HttpServletResponse resp) throws IOException {
        List<User> users  = userService.getAll();
        return RespBean.builder()
                .code("0")
                .msg("查询成功")
                .data(users)
                .alarm(RespBean.ALARM)
                .build();
    }
    @GetMapping("/user/del/{id}")
    @ResponseBody
    public RespBean del(HttpServletResponse resp,@PathVariable Integer id) throws IOException {
        Integer users  = userService.delete(id);
        return RespBean.builder()
                .code("0")
                .msg("删除成功")
                .data(users)
                .alarm(RespBean.ALARM)
                .build();
    }
    @GetMapping("/user/updateStatus/{id}")
    @ResponseBody
    public RespBean updateStatus(HttpServletResponse resp,@PathVariable Integer id) throws IOException {
        Integer users  = userService.updateStatus(id);
        return RespBean.builder()
                .code("0")
                .msg("审核通过")
                .data(users)
                .alarm(RespBean.ALARM)
                .build();
    }
    @GetMapping("/user/updatePassword/{id}")
    @ResponseBody
    public RespBean updatePassword(HttpServletResponse resp,@PathVariable Integer id) throws IOException {
        Integer users  = userService.updatePassword(id);
        return RespBean.builder()
                .code("0")
                .msg("审核通过")
                .data(users)
                .alarm(RespBean.ALARM)
                .build();
    }


    private static void verificationCode(String name, OutputStream out) throws IOException {
        //1 准备画板
        BufferedImage bin =
                new BufferedImage(170, 40, BufferedImage.TYPE_INT_BGR);
        //2 准备画笔
        Graphics2D g = (Graphics2D) bin.getGraphics();
        // 3选背景颜色  白色
        g.setColor(Color.WHITE);
        //4 画背景
        g.fillRect(1, 1, 168, 38);
        //5 选字体颜色
        g.setColor(Color.BLACK);
        //6 创建一个font 指定字体样式
        Font font = new Font(null, Font.BOLD, 30);
        g.setFont(font);
        //7 选中字
        for (int i = 0; i < name.length(); i++) {
            g.drawString(name.charAt(i) + "", 10 + i * 40, 30);
            g.drawLine(0, 0, 200, 40);
        }
        //8 画线
        for (int i = 1; i <= 10; i++) {
            //Color(int r, int g, int b)红色、绿色和蓝色所占成分，(0 - 255)
            g.setColor(new Color((int) (Math.random() * 255),
                    (int) (Math.random() * 255),
                    (int) (Math.random() * 255)));
            g.drawLine((int) (Math.random() * 85),
                    (int) (Math.random() * 40),
                    (int) (Math.random() * 85 + 85),
                    (int) (Math.random() * 40));//随机x1 y1 x2 y2
        }
        //10 把内存中的图片的信息刷新到目的文件中
        ImageIO.write(bin, "JPEG", out);
    }

}
