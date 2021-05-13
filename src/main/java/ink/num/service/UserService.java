package ink.num.service;

import ink.num.model.User;
import ink.num.util.RespBean;

import java.util.List;

public interface UserService {
    RespBean login(User user);

    String register(User user);

    List<User> getAll();

    Integer delete(Integer id);

    Integer updateStatus(Integer id);

    Integer updatePassword(Integer id);
}
