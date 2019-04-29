package com.unis.db.controller.dto;

import javax.validation.constraints.NotNull;

/**
 * 数据库工具使用条件
 * @author xuli
 * @date 2019/4/28
 */
public class UseByConditions {
    /**
     * 算法版本
     */
    @NotNull(message = "算法版本为空")
    private String algorithm;
    /**
     * 线程数
     */
    private int threadNum;


}
