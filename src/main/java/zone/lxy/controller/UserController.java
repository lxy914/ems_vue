package zone.lxy.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import zone.lxy.domain.Result;
import zone.lxy.domain.User;
import zone.lxy.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    // 返回验证码 base64格式
    @GetMapping("/getImage")
    public String imageCode(HttpServletRequest request) {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(150, 40, 4, 2);
        String code = lineCaptcha.getCode();
        log.info("生成的验证码：{}", code);
        SecurityUtils.getSubject().getSession().setAttribute("code",code);
        return "data:image/png;base64," + Base64Utils.encodeToString(lineCaptcha.getImageBytes());
    }

    // 用户注册
    @PostMapping("/register")
    public Result register(@RequestBody User user, String code, HttpServletRequest request) {
        log.info("用户信息：{}", user);
        log.info("用户输入的验证码：{}", code);
        Result result = new Result();
        try {
            String key = (String) SecurityUtils.getSubject().getSession().getAttribute("code");
            if (key.equalsIgnoreCase(code)) {
                userService.register(user);
                result.setStatus(true);
                result.setMsg("注册成功");
            } else {
                throw new RuntimeException("验证码错误");
            }

        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg("用户名已存在");
        } catch (RuntimeException e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg("注册失败");
        }
        return result;
    }

    // 用户登录
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        log.info("当前登录用户信息：{}", user);
        Result result = new Result();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            subject.login(token);
            log.info("登录成功");
            result.setStatus(true);
            result.setMsg("登录成功");
            result.setData(userService.findByName(user.getUsername()));
        } catch (UnknownAccountException e) {
            log.info("用户名错误");
            result.setStatus(false);
            result.setMsg("用户名错误");
        } catch (IncorrectCredentialsException e) {
            log.info("密码错误");
            result.setStatus(false);
            result.setMsg("密码错误");
        }
        return result;
    }

    // 未登录或为授权时跳转到该连接返回json
    @GetMapping("/authentication")
    public Result authentication(String code) {
        Result result = new Result();
        result.setStatus(false);
        if (code.equals("1")) {
            result.setMsg("您未登录");
        } else if (code.equals("2")) {
            result.setMsg("您未授权");
        }
        return result;
    }

    // 注销用户
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        Result result = new Result();
        result.setStatus(true);
        result.setMsg("退出成功");
        return result;
    }

    // 判断用户是否登录，登录则返回用户信息，
    @GetMapping("/islogin")
    public Result islogin() {
        String principal = (String) SecurityUtils.getSubject().getPrincipal();
        Result result = new Result();
        if (principal != null) {
            result.setStatus(true);
            result.setData(userService.findByName(principal));
        } else {
            result.setStatus(false);
        }

        return result;
    }
}
