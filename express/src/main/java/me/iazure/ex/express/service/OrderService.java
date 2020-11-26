package me.iazure.ex.express.service;

import me.iazure.ex.express.dto.OpenIdSelect;
import me.iazure.ex.express.dto.OrderExecution;
import me.iazure.ex.express.dto.OrderSelect;
import me.iazure.ex.express.entity.Order;
import me.iazure.ex.express.exceptions.OrderOperationException;

public interface OrderService {

    OrderExecution changStatus(Integer id, Integer status);

    OrderExecution adminChangStatus(Integer id, Integer status);

    OrderExecution addOrder(Order order);

    OrderExecution getOrderListByOpenId(OpenIdSelect openIdSelect);

    OrderExecution getOrderList(OrderSelect orderSelect);

}
