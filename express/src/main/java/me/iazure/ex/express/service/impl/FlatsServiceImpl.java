package me.iazure.ex.express.service.impl;

import me.iazure.ex.express.dao.FlatsDao;
import me.iazure.ex.express.dto.FlatsExecution;
import me.iazure.ex.express.entity.Flats;
import me.iazure.ex.express.enums.FlatsStateEnum;
import me.iazure.ex.express.exceptions.FlatsOperationException;
import me.iazure.ex.express.service.FlatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FlatsServiceImpl implements FlatsService {
    @Autowired
    private FlatsDao flatsDao;
    @Override
    public FlatsExecution getFlatsList() {
        List<Flats> flatsList = null;
        try{
            flatsList = flatsDao.queryFlatsList();
            if (flatsList.size()>0){
                return new FlatsExecution(FlatsStateEnum.SUCCESS,flatsList);
            }else {
                return new FlatsExecution(FlatsStateEnum.INNER_ERROR);
            }
        }catch (Exception e){
            throw new FlatsOperationException("获取列表失败"+e.toString());
        }
    }
}
