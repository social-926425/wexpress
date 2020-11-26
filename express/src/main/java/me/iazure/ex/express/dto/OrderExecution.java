package me.iazure.ex.express.dto;

import me.iazure.ex.express.entity.Order;
import me.iazure.ex.express.enums.OrderStateEnum;

import java.util.List;

public class OrderExecution {
    // 结果状态
    private int state;

    // 状态标识
    private String stateInfo;

    // 商品数量
    private int count;

    // 操作的award（增删改商品的时候用）
    private Order order;


//    private OrderSelectResult order;

    private List<OrderSelectResult> orderList;

    public OrderExecution() {
    }

    // 失败的构造器
    public OrderExecution(OrderStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // 成功的构造器
    public OrderExecution(OrderStateEnum stateEnum, Order order) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.order = order;
    }
    // 成功的构造器
    public OrderExecution(OrderStateEnum stateEnum, List<OrderSelectResult> orderList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.orderList = orderList;
    }
    // 成功的构造器
    public OrderExecution(OrderStateEnum stateEnum, List<OrderSelectResult> orderList,int count) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.orderList = orderList;
        this.count = count;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderSelectResult> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderSelectResult> orderList) {
        this.orderList = orderList;
    }
}
