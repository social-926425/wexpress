package me.iazure.ex.express.controller;

import com.alibaba.fastjson.JSONObject;
import me.iazure.ex.express.aop.PassToken;
import me.iazure.ex.express.aop.UserLoginToken;
import me.iazure.ex.express.dto.OpenIdSelect;
import me.iazure.ex.express.dto.OrderExecution;
import me.iazure.ex.express.dto.OrderSelect;
import me.iazure.ex.express.entity.Order;
import me.iazure.ex.express.entity.PresonInfo;
import me.iazure.ex.express.enums.OrderStateEnum;
import me.iazure.ex.express.service.OrderService;
import me.iazure.ex.express.unit.HttpServletRequestJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    Logger logger = LoggerFactory.getLogger(OrderController.class);


    @RequestMapping(value = "/getorderlist", method = RequestMethod.POST)
    @UserLoginToken
    private Map<String, Object> getOrderList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        PresonInfo presonInfo = (PresonInfo) request.getSession().getAttribute("presoninfo");
        if (presonInfo != null && presonInfo.getType() == 1) {
            OrderSelect orderSelect = new OrderSelect();
            OrderExecution orderExecution = null;
            try {
                JSONObject jsonObject = HttpServletRequestJson.getData(request);
                if (jsonObject != null) {
                    Integer size = jsonObject.getInteger("size");
                    Integer page = jsonObject.getInteger("page");
                    if (size != null && page != null) {
                        orderSelect.setRowIndex(page);
                        orderSelect.setPageSize(size);
                        orderSelect.setFlats(jsonObject.getInteger("flats"));
                        orderSelect.setName(jsonObject.getString("name"));
                        orderSelect.setId(jsonObject.getInteger("id"));
                        orderSelect.setPhone(jsonObject.getString("phone"));
                        orderSelect.setCompany(jsonObject.getString("company"));
                        orderSelect.setStartTime(jsonObject.getDate("starttime"));
                        orderSelect.setEndTime(jsonObject.getDate("endtime"));
                        orderSelect.setType(jsonObject.getInteger("type"));
                        orderExecution = orderService.getOrderList(orderSelect);
                        if (orderExecution.getState() == OrderStateEnum.SUCCESS.getState()) {
                            modelMap.put("status", orderExecution.getState());
                            modelMap.put("data", orderExecution.getOrderList());
                            modelMap.put("count", orderExecution.getCount());
                            modelMap.put("msg", orderExecution.getStateInfo());
                        } else {
                            modelMap.put("status", orderExecution.getState());
                            modelMap.put("msg", orderExecution.getStateInfo());
                        }
                    } else {
                        modelMap.put("status", OrderStateEnum.NO_PAGEANDSIZE.getState());
                        modelMap.put("msg", OrderStateEnum.NO_PAGEANDSIZE.getStateInfo());
                    }
                } else {
                    logger.error("getOrderList->jsonObject解析错误");
                    modelMap.put("status", OrderStateEnum.CONTENTTYPR_ERRE.getState());
                    modelMap.put("msg", OrderStateEnum.CONTENTTYPR_ERRE.getStateInfo());
                }
            } catch (Exception e) {
                logger.error("getOrderList" + e.toString());
                modelMap.put("status", OrderStateEnum.INNER_ERROR.getState());
                modelMap.put("msg", OrderStateEnum.INNER_ERROR.getStateInfo());
                return modelMap;
            }
        }else {
            modelMap.put("status", OrderStateEnum.NO_AUTHORITY.getState());
            modelMap.put("msg",OrderStateEnum.NO_AUTHORITY.getStateInfo());
        }
        return modelMap;
    }


    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    @PassToken
    private Map<String, Object> receive(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            JSONObject jsonObject = HttpServletRequestJson.getData(request);
            if (jsonObject != null) {
                Integer id = jsonObject.getInteger("id");
                Integer status = jsonObject.getInteger("status");
                logger.error("===================="+id+"kkkkkkkkkkkkkkkkkkkkk"+status);
                if (id != null && status != null) {
                    OrderExecution orderExecution = orderService.changStatus(id, status);
                    if (orderExecution.getState() == OrderStateEnum.SUCCESS.getState()) {
                        modelMap.put("stauts", orderExecution.getState());
                        modelMap.put("msg", orderExecution.getStateInfo());
                    } else {
                        modelMap.put("stauts", orderExecution.getState());
                        modelMap.put("msg", orderExecution.getStateInfo());
                    }
                } else {
                    modelMap.put("status", OrderStateEnum.NO_STATUS.getState());
                    modelMap.put("msg", OrderStateEnum.NO_STATUS.getStateInfo());
                }
            } else {
                logger.error("receive->jsonObject解析错误");
                modelMap.put("status", OrderStateEnum.CONTENTTYPR_ERRE.getState());
                modelMap.put("msg", OrderStateEnum.CONTENTTYPR_ERRE.getStateInfo());
            }
        } catch (Exception e) {
            logger.error("receive" + e.toString());
            modelMap.put("status", OrderStateEnum.INNER_ERROR.getState());
            modelMap.put("msg", OrderStateEnum.INNER_ERROR.getStateInfo());
            return modelMap;
        }
        return modelMap;
    }


    @RequestMapping(value = "/getorderlistbyopenid", method = RequestMethod.POST)
    @PassToken
    private Map<String, Object> getOrderListByOpenId(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            JSONObject jsonObject = HttpServletRequestJson.getData(request);
            OpenIdSelect openIdSelect = new OpenIdSelect();
            if (jsonObject != null) {
                openIdSelect.setOpenId(jsonObject.getString("openid"));
                openIdSelect.setSize(jsonObject.getInteger("size"));
                openIdSelect.setPage(jsonObject.getInteger("page"));
                openIdSelect.setStatus(jsonObject.getInteger("status"));
                openIdSelect.setEndTime(jsonObject.getDate("endtime"));
                openIdSelect.setStatusTime(jsonObject.getDate("statustime"));
                OrderExecution orderExecution = orderService.getOrderListByOpenId(openIdSelect);
                if (orderExecution.getState() == OrderStateEnum.SUCCESS.getState()) {
                    modelMap.put("data", orderExecution.getOrderList());
                    modelMap.put("count", orderExecution.getCount());
                    modelMap.put("stauts", 1);
                    modelMap.put("msg", orderExecution.getStateInfo());
                } else {
                    modelMap.put("stauts", orderExecution.getState());
                    modelMap.put("msg", orderExecution.getStateInfo());
                }
            } else {
                logger.error("getOrderListByOpenId->jsonObject解析错误");
                modelMap.put("status", OrderStateEnum.CONTENTTYPR_ERRE.getState());
                modelMap.put("msg", OrderStateEnum.CONTENTTYPR_ERRE.getStateInfo());
            }
        } catch (Exception e) {
            logger.error("receive" + e.toString());
            modelMap.put("status", OrderStateEnum.INNER_ERROR.getState());
            modelMap.put("msg", OrderStateEnum.INNER_ERROR.getStateInfo());
            return modelMap;
        }
        return modelMap;
    }


    @RequestMapping(value = "/addorder", method = RequestMethod.POST)
    @PassToken
    private Map<String, Object> addOrder(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Order order = new Order();
        try {
            JSONObject jsonObject = HttpServletRequestJson.getData(request);
            if (jsonObject != null) {
                int type = 0;
                if (jsonObject.getInteger("type") != null) {
                    type = jsonObject.getInteger("type");
                }
                order.setName(jsonObject.getString("name"));
                order.setCompany(jsonObject.getString("company"));
                order.setOpenId(jsonObject.getString("openid"));
                order.setNumber(jsonObject.getString("number"));
                order.setPhone(jsonObject.getString("phone"));
                order.setFlatsId(jsonObject.getInteger("apartment"));
                order.setHostel(jsonObject.getString("dormitory"));
                order.setType(type);
                order.setDecs(jsonObject.getString("decs"));
                if (order != null) {
                    OrderExecution orderExecution = orderService.addOrder(order);
                    if (orderExecution.getState() == OrderStateEnum.SUCCESS.getState()) {
                        modelMap.put("status", orderExecution.getState());
                        modelMap.put("msg", orderExecution.getStateInfo());
                    } else {
                        modelMap.put("status", orderExecution.getState());
                        modelMap.put("msg", orderExecution.getStateInfo());
                    }
                } else {
                    modelMap.put("status", -2);
                    modelMap.put("msg", "订单不能为空");
                }
            } else {
                logger.error("addorder->jsonObject解析错误");
                modelMap.put("status", OrderStateEnum.CONTENTTYPR_ERRE.getState());
                modelMap.put("msg", OrderStateEnum.CONTENTTYPR_ERRE.getStateInfo());
            }
        } catch (Exception e) {
            logger.error("addorder订单添加失败" + e.toString());
            modelMap.put("status", OrderStateEnum.INNER_ERROR.getState());
            modelMap.put("msg", OrderStateEnum.INNER_ERROR.getStateInfo());
            return modelMap;
        }
        return modelMap;
    }


}
