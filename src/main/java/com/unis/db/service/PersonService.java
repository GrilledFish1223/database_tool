package com.unis.db.service;

/**
 * @author xuli
 * @date 2019/4/22
 */
public interface PersonService {
    /**
     * 生成人体数据
     *
     * @param passTime       时间戳
     * @param recordID       主键
     * @param partitionState 是否分区
     * @return 数据
     */
    String makePersonData(long passTime, long recordID, boolean partitionState);
}
