package com.xw.atcrowdfunding.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

//流程监听器
public class NoListener implements ExecutionListener {
    public void notify(DelegateExecution execution) throws Exception {
        System.out.println("审批拒绝...");
    }
}
