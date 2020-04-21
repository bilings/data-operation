package com.hifo.dataoperation.util;


/**
 * 表
 */
public enum TableEnum {
    BUILDING_TABLE("building"),
    BUILDING_LOG_TABLE("warehouse_building_log"),
    COMMUNITY_LOG_TABLE("warehouse_community_log"),
    ROOM_STANDARD_TABLE("warehouse_room_standard"),
    BUILDING_STANDARD_TABLE("warehouse_building_standard"),
    CASEINFO_STANDARD_TABLE("warehouse_caseinfo_standard"),
    COMMUNITY_STANDARD_TABLE("warehouse_community_standard"),
    COMMUNITY_BUILDING_TABLE("warehouse_building_standard"),
    COMMUNITY_SERVICE_TABLE("warehouse_community_service"),
    COMMUNITY_PRICE_TABLE("warehouse_community_service"),
    COMMUNITY_ROOM_TABLE("warehouse_room_service");

    private String name;

    public String getName() {
        return name;
    }

    TableEnum(String name) {
        this.name = name;
    }


    }
