package com.unis.db.service;

/**
 * @author xuli
 * @date 2019/4/17
 */
public interface MakeDataService {

    /**
     * 使用线程池
     * @param tableName 表名
     * @param partitionState 是否分区
     * @return T or F
     */
    Boolean useThread(String tableName, boolean partitionState);

    /**
     * 造数据(分区表)
     * @param type 类型
     * @return T or F
     */
    Boolean makeDataByPartition(String type);

    /**
     * 造数据
     * @param type 类型
     * @return T or F
     */
    Boolean makeData(String type);

}
