package me.iazure.ex.express.dao;

import me.iazure.ex.express.dto.OpenIdSelect;
import me.iazure.ex.express.dto.OrderSelect;
import me.iazure.ex.express.dto.OrderSelectResult;
import me.iazure.ex.express.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderDao {

    int updataStauts(@Param("id") Integer id, @Param("status") Integer status);

    int insertOrder(Order order);

    List<OrderSelectResult> selectByOpenId(@Param("openIdSelect") OpenIdSelect openIdSelect);

    int selectByOpenIdCount(@Param("openIdSelect") OpenIdSelect openIdSelect);

    Order selectById(@Param("id") Integer id);

    List<OrderSelectResult> selectOrderList(@Param("orderSelect") OrderSelect orderSelect);

    int selectOrderListCount(@Param("orderSelect") OrderSelect orderSelect);


}
