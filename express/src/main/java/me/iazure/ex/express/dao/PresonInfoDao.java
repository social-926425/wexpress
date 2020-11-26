package me.iazure.ex.express.dao;


import me.iazure.ex.express.entity.PresonInfo;
import org.apache.ibatis.annotations.Param;

public interface PresonInfoDao {

        PresonInfo getId(int id);

        PresonInfo getByStudentID(@Param("studentId") String studentId);

        PresonInfo goLogin(@Param("studentId") String studentId,@Param("password") String password);


}
