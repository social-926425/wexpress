package me.iazure.ex.express.enums;

public enum  FlatsStateEnum {
    SUCCESS(1, "操作成功"), INNER_ERROR(-1001,"内部系统错误");

    private int state;
    private String stateInfo;


    private FlatsStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }


    public static FlatsStateEnum stateOf(int state) {
        for (FlatsStateEnum stateEnum : values()) {
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
