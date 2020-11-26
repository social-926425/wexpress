package me.iazure.ex.express.enums;

public enum PresonInfoEnum {
    SUCCESS(1, "操作成功"), INNER_ERROR(-1001,
            "内部系统错误"),NO_INFO(-1002,"账号密码不能为空"),INFO_ERROR(-1003,"账号密码错误");

    private int state;
    private String stateInfo;


    private PresonInfoEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static PresonInfoEnum stateOf(int state) {
        for (PresonInfoEnum stateEnum : values()) {
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

