package me.iazure.ex.express.dto;

import me.iazure.ex.express.entity.Flats;
import me.iazure.ex.express.entity.Order;
import me.iazure.ex.express.enums.FlatsStateEnum;

import java.util.List;

public class FlatsExecution {
    // 结果状态
    private int state;

    // 状态标识
    private String stateInfo;


    // 操作的award（增删改商品的时候用）
    private List<Flats> flatsList;
    public FlatsExecution(){

    }
    // 失败的构造器
    public FlatsExecution(FlatsStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // 成功的构造器
    public FlatsExecution(FlatsStateEnum stateEnum,List<Flats> flatsList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.flatsList = flatsList;
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

    public List<Flats> getFlatsList() {
        return flatsList;
    }

    public void setFlatsList(List<Flats> flatsList) {
        this.flatsList = flatsList;
    }
}
