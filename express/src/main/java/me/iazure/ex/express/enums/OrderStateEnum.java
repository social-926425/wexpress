package me.iazure.ex.express.enums;

public enum  OrderStateEnum {

    SUCCESS(1, "操作成功"), INNER_ERROR(-1001,
    "内部系统错误"), NULL_ORDER(-1003, "order信息为空"),
    NULL_OPENID(-1003, "openid,size,page不能为空"),NO_AUTHORITY(-1004,"您没有权限"),
    NO_MAG(-1005,"id或状态不能为空"),NO_SELECT(-1006,"查询不能信息为空"),
    NO_NUMBER(-1007,"取件码不能为空"),NO_OPENID(-1008,"openId不能为空")
    ,NO_COMPANY(-1009,"快递公司不能为空"),NO_NAME(-1010,"姓名不能为空"),
    NO_PHONE(-1011,"电话号码不能为空"),
    NO_APARTMENT(-1011,"公寓id不能为空"),NO_TYPE(-1011,"取件类型不能为空"),
    NO_INFO(-1012,"没有查询到相关信息"),
    NO_STATUS(-1013,"id或者status不能为空"),
    NO_PAGEANDSIZE(-1014,"size或者page不能为空"),CONTENTTYPR_ERRE(-1015,"Content-Type不是application/json;charset=UTF-8格式")


    ;

    private int state;
    private String stateInfo;


    private OrderStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static OrderStateEnum stateOf(int state) {
        for (OrderStateEnum stateEnum : values()) {
            if (stateEnum.getState() == state) {
                return stateEnum;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

}
