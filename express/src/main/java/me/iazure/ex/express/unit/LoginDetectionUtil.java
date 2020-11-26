package me.iazure.ex.express.unit;


import me.iazure.ex.express.entity.PresonInfo;
import me.iazure.ex.express.service.PresonInfoService;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginDetectionUtil {
    @Autowired
    private PresonInfoService presonInfoService;


    public PresonInfo delection(String studentID){
        System.out.println(studentID);
        return presonInfoService.getPresonByStudentId(studentID);

    }


}
