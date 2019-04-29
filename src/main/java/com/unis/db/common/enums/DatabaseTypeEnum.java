package com.unis.db.common.enums;

/**
 * @author xuli
 * @date 2019/3/28
 */
public enum DatabaseTypeEnum {
    /**
     * GreenPlum
     */
    GP("gp"),
    /**
     * PostgreSQL
     */
    PG("pg");
    private String type;

    DatabaseTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    /**
     * 判断是否含有该类型
     * @param type 类型
     * @return boolean
     */
    public static boolean typeExists(String type) {
        for (DatabaseTypeEnum databaseTypeEnum : DatabaseTypeEnum.values()) {
            if (type.equals(databaseTypeEnum.getType())) {
                return true;
            }
        }
        return false;
    }

}
