package me.iazure.ex.express.dto;

import me.iazure.ex.express.entity.PresonInfo;
import me.iazure.ex.express.enums.PresonInfoEnum;


public class PresonInfoExecution {
    // 结果状态
    private int state;

    // 状态标识
    private String stateInfo;

    //用户信息
    private PresonInfo presonInfo;

    public PresonInfoExecution() {

    }

    // 失败的构造器
    public PresonInfoExecution(PresonInfoEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // 成功的构造器
    public PresonInfoExecution(PresonInfoEnum stateEnum, PresonInfo presonInfo) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.presonInfo = presonInfo;
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

    public PresonInfo getPresonInfo() {
        return presonInfo;
    }

    public void setPresonInfo(PresonInfo presonInfo) {
        this.presonInfo = presonInfo;
    }
}
