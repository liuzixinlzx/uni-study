package com.lzx.uniserver.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lzx.uniserver.interceptor.AuthorizationException;
import com.lzx.uniserver.interceptor.LoginRequired;
import com.lzx.uniserver.service.UserService;
import com.lzx.uniserver.tool.Result;
import com.lzx.uniserver.tool.TokenUtil;
import com.lzx.uniserver.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author alex wong
 * @since 2020-08-06
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public Result getList(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Result res = new Result();
        if(username == null || password == null){
            return res.fail("用户名或密码没填写，登陆失败！");
        }

        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername, username);
        lqw.eq(User::getPassword, password);
        if(username == null || password == null){
            return null;
        }
        List lUser = userService.list(lqw);

        if(lUser.size() == 0 ){
            return res.fail("用户名或密码错误，登陆失败！");
        }else {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            User user = (User)lUser.get(0);
            user.setPassword("");
            resultMap.put("userInfo", user);
            resultMap.put("accessToken", TokenUtil.createJwtToken(user.getId() + "")); // 存入token
            return res.success(resultMap);
        }
    }

    @LoginRequired
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Result editUser(HttpServletRequest request, @RequestBody User user){
        Result res = new Result();

        String accessToken = request.getHeader("accessToken");

        Claims claims = TokenUtil.parseJWT(accessToken);
        String id = claims.getId();
        user.setId(Integer.parseInt(id));
        boolean bRet = userService.updateById(user);
        if(bRet) {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            User user1 = userService.getById(id);
            resultMap.put("userInfo", user1);
            return res.success(resultMap);
        }else {
            return res.fail("fail");
        }
    }

    @LoginRequired
    @RequestMapping(value = "/editPassword", method = RequestMethod.POST)
    public Result editPassword(HttpServletRequest request, @RequestBody Map<String, String> map){
        Result res = new Result();

        String accessToken = request.getHeader("accessToken");

        String oldPassword = map.get("oldPassword");
        String newPassword = map.get("newPassword");
        Claims claims = TokenUtil.parseJWT(accessToken);
        String id = claims.getId();

        User user = userService.getById(Integer.parseInt(id));
        if (!user.getPassword().equals(oldPassword)){
            return res.fail("原密码错误！");
        }

        User user1 = new User();
        user1.setId(Integer.parseInt(id));
        user1.setPassword(newPassword);
        boolean bRet = userService.updateById(user1);
        if(bRet) {
            return res.success("success");
        }else {
            return res.fail("fail");
        }
    }
}

