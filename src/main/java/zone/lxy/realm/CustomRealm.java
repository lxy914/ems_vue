package zone.lxy.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zone.lxy.dao.UserDao;
import zone.lxy.domain.User;
import zone.lxy.service.UserService;

@Component
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserDao userDao;

    // 授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    // 登录认证时会执行该方法
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取用户名
        String principal = (String) authenticationToken.getPrincipal();
        // 根据用户名查询用户信息
        User user = userDao.findByName(new User().setUsername(principal));
        if (user != null) {
            return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), this.getName());
        }
        return null;
    }
}
