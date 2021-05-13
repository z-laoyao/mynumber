package ink.num.service.impl;

import ink.num.dao.UserDao;
import ink.num.model.Admin;
import ink.num.model.User;
import ink.num.service.UserService;
import ink.num.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpSession;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private HttpSession session;

    @Override
    public RespBean login(User user) {
        session.removeAttribute("user");
        session.removeAttribute("admin");
        if (user.getName() == null || user.getPassword() == null || "".equals(user.getName()) || "".equals(user.getPassword())) {
                return RespBean.builder()
                    .code("500")
                    .msg("账号密码不能为空")
                    .data(null)
                    .build();
        }
        Admin admin = userDao.getAdminByName(user.getName());
        if (admin != null && admin.getPassword().equals(user.getPassword())) {
            session.setAttribute("admin", admin);
            return RespBean.builder()
                    .code("0")
                    .msg("登录成功")
                    .data(admin)
                    .build();
        }
        User user1 = userDao.getByNameStatus(user.getName());
        if (user1 == null) {
            return RespBean.builder()
                    .code("500")
                    .msg("账号不存在或者没通过审核")
                    .data(null)
                    .build();
        }
        String MD5 = DigestUtils.md5DigestAsHex((user.getPassword() + user1.getSalt()).getBytes());
        if (!user1.getPassword().equals(MD5)) {
            return RespBean.builder()
                    .code("500")
                    .msg("密码错误")
                    .data(null)
                    .build();
        }
        session.setAttribute("user", user1);
        return RespBean.builder()
                .code("0")
                .msg("登录成功")
                .data(user1)
                .build();
    }

    @Override
    public String register(User user) {
        if (user.getSex().equals("on")){
            user.setSex("男");
        }else {
            user.setSex("女");
        }
        user.setSalt(System.currentTimeMillis() + "");
        System.out.println(user);
        user.setPassword(DigestUtils.md5DigestAsHex((user.getPassword() + user.getSalt()).getBytes()));
        User name = userDao.getByName(user.getName());
        if (name != null) {
            session.setAttribute("msg", "账号已经存在");
            return "register";
        }
        Integer integer = userDao.addUser(user);
        if (integer == 0) {
            session.setAttribute("msg", "创建用户失败");
            return "register";
        }
        return "login";
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public Integer delete(Integer id) {

        return userDao.deleteById(id);
    }

    @Override
    public Integer updateStatus(Integer id) {
        return userDao.updateUserStatus(id);
    }

    @Override
    public Integer updatePassword(Integer id) {
        User user = new User();
        user.setId(id);
        user.setSalt("" + System.currentTimeMillis());
        user.setPassword(DigestUtils.md5DigestAsHex(("123" + user.getSalt()).getBytes()));
        return userDao.updatePassord(user);
    }
}
