package com.pnxtest.integrationTest.config;

import com.pnxtest.core.api.Configuration;
import com.pnxtest.core.api.IExecutionConfig;

/**
 * 执行配置的示例
 * 用于pnxtest开始执行前以及所有测试用例跑完后的处理
 */
@Configuration
public class ExecutionConfig implements IExecutionConfig {

    @Override
    public void onExecuteStart() {
        //some global setup...
        //initialize or load some drivers or applications
        //or anything you want to add here
    }

    @Override
    public void onExecuteFinish() {
        //some actions after execution finished
        //or anything you want to add here
    }

}
