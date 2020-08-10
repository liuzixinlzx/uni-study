package com.lzx.uniserver.controller;

import com.lzx.uniserver.interceptor.AuthorizationException;
import com.lzx.uniserver.service.UserService;
import com.lzx.uniserver.tool.Result;
import com.lzx.uniserver.tool.TokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public Result checkToken(HttpServletRequest request){
        Result res = new Result();
        // 判断是否存在令牌信息，如果存在，则允许登录
        String accessToken = request.getHeader("accessToken");

        if (null == accessToken) {
            throw new AuthorizationException("请先登录");
        } else if (accessToken.isEmpty()) {
            throw new AuthorizationException("请先登录");
        } else {
            // 从Redis 中查看 token 是否过期
            Claims claims;
            try {
                claims = TokenUtil.parseJWT(accessToken);
            } catch (ExpiredJwtException e) {
                throw new AuthorizationException("请先登录");
            } catch (SignatureException se) {
                throw new AuthorizationException("请先登录");
            } catch (MalformedJwtException e2) {
                throw new AuthorizationException("请先登录");
            }

            String id = claims.getId();
            Object user = userService.getById(Integer.parseInt(id));
            if (user == null) {
//					response.setStatus(401);
                throw new AuthorizationException("请先登录");
            }

            return res.success("success");
        }
    }
}
