package me.iazure.ex.express.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import me.iazure.ex.express.aop.PassToken;
import me.iazure.ex.express.aop.UserLoginToken;
import me.iazure.ex.express.entity.PresonInfo;
import me.iazure.ex.express.service.impl.PresonInfoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    PresonInfoServiceImpl presonInfoService;
    private Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        String token = request.getHeader("token");

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        request.setCharacterEncoding("UTF-8");

        response.setContentType("text/html;charset=UTF-8");
        // 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                JSONObject res = new JSONObject();
                PrintWriter out = null ;
                if (token == null) {
                    res.put("status","-1");
                    res.put("msg","缺少token值");
                    response.getWriter().write(res.toString());
                    return false;
                }
                PresonInfo personInfo = verifyToken(token);
                if (personInfo == null) {
                    res.put("status", -1);
                    res.put("msg", "token过期或者有误,请重新获取token");
                    response.getWriter().write(res.toString());
                    return false;
                }
                request.getSession().setAttribute("presoninfo",personInfo);
                return true;
            }
        }
        return true;
    }

    public PresonInfo verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("4561245781215");
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            String password = jwt.getClaim("password").asString();
            String studentid = jwt.getClaim("studentid").asString();
            PresonInfo personInfo = presonInfoService.getPresonByStudentId(studentid);
            if (personInfo == null) {
                return null;
            }
            if (personInfo.getPassword().equals(password)) {
                return personInfo;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
