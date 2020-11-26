package me.iazure.ex.express.controller;

import me.iazure.ex.express.aop.PassToken;
import me.iazure.ex.express.aop.UserLoginToken;
import me.iazure.ex.express.dto.FlatsExecution;
import me.iazure.ex.express.entity.Flats;
import me.iazure.ex.express.enums.FlatsStateEnum;
import me.iazure.ex.express.exceptions.FlatsOperationException;
import me.iazure.ex.express.service.FlatsService;
import me.iazure.ex.express.service.impl.OrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FlatsController {
    @Autowired
    private FlatsService flatsService;
    private static Logger logger = LoggerFactory.getLogger(FlatsController.class);

    @RequestMapping(value = "/test" , method = RequestMethod.POST)
    @UserLoginToken
    public String test(){
        return "success";
    }

    @RequestMapping(value = "/getlist",method = RequestMethod.POST)
    @PassToken
    public Map<String,Object> getList(){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        FlatsExecution flatsExecution = null;
        try {
            flatsExecution = flatsService.getFlatsList();
            if (flatsExecution.getState() == FlatsStateEnum.SUCCESS.getState()){
                modelMap.put("data",flatsExecution.getFlatsList());
                modelMap.put("stauts",1);
                modelMap.put("msg","操作成功");
            }else {
                modelMap.put("stauts",0);
                modelMap.put("msg",flatsExecution.getStateInfo());
            }
        }catch (Exception e){
                modelMap.put("stauts",0);
                modelMap.put("msg","获取宿舍列表失败");
                logger.error("获取宿舍列表失败"+e.toString());
                return modelMap;
        }
        return modelMap;
    }

}
