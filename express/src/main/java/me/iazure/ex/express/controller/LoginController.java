package me.iazure.ex.express.controller;

import com.alibaba.fastjson.JSONObject;
import me.iazure.ex.express.aop.PassToken;
import me.iazure.ex.express.dto.PresonInfoExecution;
import me.iazure.ex.express.entity.PresonInfo;
import me.iazure.ex.express.enums.PresonInfoEnum;
import me.iazure.ex.express.service.PresonInfoService;
import me.iazure.ex.express.service.impl.OrderServiceImpl;
import me.iazure.ex.express.unit.HttpServletRequestJson;
import me.iazure.ex.express.unit.HttpServletRequestUtil;
import me.iazure.ex.express.unit.TokenUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
public class LoginController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private PresonInfoService presonInfoService;


    @PassToken
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    private Map<String , Object> login(HttpServletRequest request) throws Exception {
        Map<String,Object> modelMap = new HashMap<String,Object>();
        String studentId=null;
        String password=null;
        try{
            JSONObject jsonObject = HttpServletRequestJson.getData(request);
            if (jsonObject!=null){
                studentId = jsonObject.getString("username");
                password = jsonObject.getString("password");
                if (!"".equals(studentId)&&!"".equals(password)){
                    PresonInfoExecution presonInfoExecution = presonInfoService.getPresonForLogin(studentId,password);
                    if (presonInfoExecution.getState()== PresonInfoEnum.SUCCESS.getState()){
                        modelMap.put("status",1);
                        modelMap.put("token", TokenUtil.getToken(presonInfoExecution.getPresonInfo()));
                        modelMap.put("msg",presonInfoExecution.getStateInfo());
                    }else {
                        modelMap.put("status",500);
                        modelMap.put("msg",presonInfoExecution.getStateInfo());
                    }
                }else {
                    modelMap.put("status",-1);
                    modelMap.put("msg","账号或密码不能为空!");
                }
            }else {
                modelMap.put("status",-3);
                modelMap.put("msg","系统错误");
                logger.error("无法获取到jsonObject");
            }
        }catch (Exception e){
            modelMap.put("status",0);
            modelMap.put("msg","系统错误");
            logger.error("无法获取到jsonObject");
            return modelMap;
        }


        return modelMap;
    }
}
