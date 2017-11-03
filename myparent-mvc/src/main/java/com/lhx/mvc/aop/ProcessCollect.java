package com.lhx.mvc.aop;

import com.lhx.mvc.aop.process.AfterProcess;
import com.lhx.mvc.aop.process.AroundProcess;
import com.lhx.mvc.aop.process.BeforeProcess;

import java.util.ArrayList;
import java.util.List;

/**
 * 切面执行器集合
 */
public class ProcessCollect {
    private List<BeforeProcess> beforeList = new ArrayList<>();


    private List<AroundProcess> aroundList = new ArrayList<>();


    private List<AfterProcess> afterList = new ArrayList<>();


    public void addBeforeProcess(BeforeProcess b) {
        beforeList.add(b);
    }


    public void addAroundProcess(AroundProcess r) {
        aroundList.add(r);
    }


    public void addAfterProcess(AfterProcess a) {
        afterList.add(a);
    }

    public List<BeforeProcess> getBeforeList() {
        return beforeList;
    }

    public void setBeforeList(List<BeforeProcess> beforeList) {
        this.beforeList = beforeList;
    }

    public List<AroundProcess> getAroundList() {
        return aroundList;
    }

    public void setAroundList(List<AroundProcess> aroundList) {
        this.aroundList = aroundList;
    }

    public List<AfterProcess> getAfterList() {
        return afterList;
    }

    public void setAfterList(List<AfterProcess> afterList) {
        this.afterList = afterList;
    }
}
