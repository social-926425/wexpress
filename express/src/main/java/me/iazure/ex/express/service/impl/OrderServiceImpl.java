package me.iazure.ex.express.service.impl;

import me.iazure.ex.express.dao.OrderDao;
import me.iazure.ex.express.dto.OpenIdSelect;
import me.iazure.ex.express.dto.OrderExecution;
import me.iazure.ex.express.dto.OrderSelect;
import me.iazure.ex.express.dto.OrderSelectResult;
import me.iazure.ex.express.entity.Order;
import me.iazure.ex.express.enums.OrderStateEnum;
import me.iazure.ex.express.exceptions.OrderOperationException;
import me.iazure.ex.express.service.OrderService;
import me.iazure.ex.express.unit.PageCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    @Transactional
    public OrderExecution changStatus(Integer id, Integer status) {
        logger.debug(String.valueOf(status));
        if (status == 5) {
            try {
                Order order = orderDao.selectById(id);
                if (order.getStatus() == 3) {
                    int effectedNum = orderDao.updataStauts(id, 4);
                    if (effectedNum > 0) {
                        return new OrderExecution(OrderStateEnum.SUCCESS);
                    } else {
                        return new OrderExecution(OrderStateEnum.INNER_ERROR);
                    }
                } else {
                    return new OrderExecution(OrderStateEnum.NO_AUTHORITY);
                }
            } catch (RuntimeException e) {
                logger.error("changStatus->状态修改失败" + e.toString());
                throw new OrderOperationException("changStatus->状态修改失败");
            }
        } else {
            return new OrderExecution(OrderStateEnum.NO_AUTHORITY);
        }
    }

    @Override
    public OrderExecution adminChangStatus(Integer id, Integer status) {



        return null;
    }


    @Override
    @Transactional
    public OrderExecution addOrder(Order order) {
        if (order != null) {

            order.setCreateTime(new Date());
            order.setLastTime(new Date());
            order.setStatus(1);
            order.setPaymentStatus(2);
            order.setLastPreson("自己");
            int type = order.getType();
            if (order.getOpenId() == null) {
                return new OrderExecution(OrderStateEnum.NO_OPENID);
            } else if (order.getNumber() == null) {
                return new OrderExecution(OrderStateEnum.NO_NUMBER);
            } else if (order.getCompany() == null) {
                return new OrderExecution(OrderStateEnum.NO_COMPANY);
            } else if (order.getName() == null) {
                return new OrderExecution(OrderStateEnum.NO_NAME);
            } else if (order.getPhone() == null) {
                return new OrderExecution(OrderStateEnum.NO_PHONE);
            } else if (order.getFlatsId() == null) {
                return new OrderExecution(OrderStateEnum.NO_APARTMENT);
            } else if (type == 0) {
                return new OrderExecution(OrderStateEnum.NO_TYPE);
            } else {
                try {
                    int effectedNum = orderDao.insertOrder(order);
                    if (effectedNum > 0) {
                        return new OrderExecution(OrderStateEnum.SUCCESS, order);
                    } else {
                        return new OrderExecution(OrderStateEnum.INNER_ERROR);
                    }
                } catch (Exception e) {
                    logger.error("addOrder->订单添加失败" + e.toString());
                    throw new OrderOperationException("addOrder->订单添加失败");
                }
            }
        } else {
            return new OrderExecution(OrderStateEnum.NULL_ORDER);
        }
    }

    @Override
    @Transactional
    public OrderExecution getOrderListByOpenId(OpenIdSelect openIdSelect) {
        List<OrderSelectResult> orderList = null;
        if (openIdSelect!=null&&openIdSelect.getOpenId()!=null&&openIdSelect.getSize()!=null&&openIdSelect.getPage()!=null) {
            openIdSelect.setPage(PageCalculator.calculateRowIndex(openIdSelect.getPage(), openIdSelect.getSize()));
            try {
                orderList = orderDao.selectByOpenId(openIdSelect);
                int count = orderDao.selectByOpenIdCount(openIdSelect);
                if (orderList.size() > 0) {
                    return new OrderExecution(OrderStateEnum.SUCCESS, orderList, count);
                } else if (orderList.size() == 0) {
                    return new OrderExecution(OrderStateEnum.NO_INFO);
                } else {
                    return new OrderExecution(OrderStateEnum.INNER_ERROR);
                }
            } catch (Exception e) {
                logger.error("getOrderListByOpenId->获取列表失败" + e.toString());
                throw new OrderOperationException("获取列表失败");
            }
        } else {
            return new OrderExecution(OrderStateEnum.NULL_OPENID);
        }
    }

    @Override
    @Transactional
    public OrderExecution getOrderList(OrderSelect orderSelect) {
        if (orderSelect != null) {
            orderSelect.setRowIndex(PageCalculator.calculateRowIndex(orderSelect.getRowIndex(), orderSelect.getPageSize()));
            List<OrderSelectResult> orderList = null;
            try {
                orderList = orderDao.selectOrderList(orderSelect);
                int count = orderDao.selectOrderListCount(orderSelect);
                System.out.println(orderList.size() > 0);
                if (orderList.size() > 0) {
                    return new OrderExecution(OrderStateEnum.SUCCESS, orderList, count);
                } else if (orderList.size() == 0) {
                    return new OrderExecution(OrderStateEnum.NO_INFO);
                } else {
                    return new OrderExecution(OrderStateEnum.INNER_ERROR);
                }
            } catch (Exception e) {
                logger.error("getOrderList->系统内部错误" + e.toString());
                return new OrderExecution(OrderStateEnum.INNER_ERROR);
            }
        } else {
            return new OrderExecution(OrderStateEnum.NO_SELECT);
        }
    }
}
