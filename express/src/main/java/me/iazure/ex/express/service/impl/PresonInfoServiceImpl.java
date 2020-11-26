package me.iazure.ex.express.service.impl;

import me.iazure.ex.express.dao.PresonInfoDao;
import me.iazure.ex.express.dto.PresonInfoExecution;
import me.iazure.ex.express.entity.PresonInfo;
import me.iazure.ex.express.enums.PresonInfoEnum;
import me.iazure.ex.express.exceptions.PresonInfoOperationException;
import me.iazure.ex.express.service.PresonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PresonInfoServiceImpl implements PresonInfoService {
    @Autowired
    private PresonInfoDao presonInfoDao;
    @Override
    public PresonInfo getPresonByStudentId(String studentId) {
        return presonInfoDao.getByStudentID(studentId);
    }

    @Override
    public PresonInfoExecution getPresonForLogin(String studentId, String password) {
        PresonInfo presonInfo = null;
        if (studentId!=null&&password!=null){
            try{
                presonInfo = presonInfoDao.goLogin(studentId, password);
                if (presonInfo !=null){
                    return new PresonInfoExecution(PresonInfoEnum.SUCCESS,presonInfo);
                }else {
                    return new PresonInfoExecution(PresonInfoEnum.INNER_ERROR);
                }
            }catch (Exception e){
                throw new PresonInfoOperationException(PresonInfoEnum.INFO_ERROR);
            }
        }else {
            return new PresonInfoExecution(PresonInfoEnum.NO_INFO);
        }
    }
}
