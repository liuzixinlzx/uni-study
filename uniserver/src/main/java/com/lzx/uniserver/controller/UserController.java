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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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

    @Value("${download.path}")
    private String downloadPath;

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

    /* 上传头像 */
    @LoginRequired
    @RequestMapping(value = "/uploadHead", method = RequestMethod.POST)
    public Result uploadHead(HttpServletRequest request, @RequestParam("file")MultipartFile file) {
        Result res = new Result();
        String accessToken = request.getHeader("accessToken");
        Claims claims = TokenUtil.parseJWT(accessToken);
        String id = claims.getId();

        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);

        String path = downloadPath + id;
        File dest = new File(path + "/" + fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }

        try {
            file.transferTo(dest); //保存文件
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return res.success("success");
    }

    /* 下载头像 */
    @LoginRequired
    @RequestMapping(value = "/downloadHead", method = RequestMethod.GET)
    public String downloadHead(HttpServletRequest request,HttpServletResponse response) {
        Result res = new Result();
        String accessToken = request.getHeader("accessToken");
        Claims claims = TokenUtil.parseJWT(accessToken);
        String id = claims.getId();

        User user = userService.getById(id);
        String headUrl = user.getHeadurl();
        String fileName = headUrl.substring(headUrl.lastIndexOf("/") + 1);
        String filePathName = downloadPath + id + "/" + fileName;

        File path = null;
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File(filePathName)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (FileNotFoundException e1) {
            //e1.getMessage()+"系统找不到指定的文件";
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return "success";
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

