package zone.lxy.service.impl;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zone.lxy.dao.UserDao;
import zone.lxy.domain.User;
import zone.lxy.service.UserService;

import java.util.Date;
import java.util.Objects;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public void register(User user) {
        // 用户状态
        user.setStatus("已激活");
        // 注册时间
        user.setRegisterTime(new Date());
        userDao.save(user);
    }


    @Override
    public User findByName(String name) {
        return userDao.findByName(new User().setUsername(name));
    }
}
