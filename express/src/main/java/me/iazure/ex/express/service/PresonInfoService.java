package me.iazure.ex.express.service;
import me.iazure.ex.express.dto.PresonInfoExecution;
import me.iazure.ex.express.entity.PresonInfo;

public interface PresonInfoService {

//    PersonInfo getPresonByUsername(@Param("username") String username);
    PresonInfo getPresonByStudentId(String studentId);

    PresonInfoExecution getPresonForLogin(String studentId, String password);

}
