package com.hifo.dataoperation.entity.mongo;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hifo.dataoperation.entity.mongo.Base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Document(collection = "community")
public class BusEstate extends BaseEntity {


    @ApiModelProperty(value = "城市id", name = "cityId")
    String cityId;

    //    @Excel(name = "城市(必填)")
    @ApiModelProperty(value = "城市", name = "cityName")
    String cityName;

    @ApiModelProperty(value = "建筑结构", name = "buildType")
    String buildType;

    //    @Excel(name = "总户数")
    @ApiModelProperty(value = "总户数", name = "totalRoomCount")
    Integer totalRoomCount;

    @ApiModelProperty(value = "", name = "propertyYears")
    Integer propertyYears;

    @ApiModelProperty(value = "", name = "propertyYears")
    Double grossBuildArea;


    String buildStructure;

    Boolean open;


    @ApiModelProperty(value = "行政区id", name = "districtId")
    String districtId;

    //    @Excel(name = "行政区(必填)")
    @ApiModelProperty(value = "行政区", name = "districtName")
    String districtName;

    Double floorAreaRatio;

    String developerName;

    Double buildingDensity;

    Double greeingRate;

    Boolean isSpecial;

    Boolean isVirtual;

    String buildYear;

    Boolean isDeleted;

    String propertyCompany;

    String address;

    Double propertyFee;

    String communityName;

    String elevatorBrand;

    String communityQuality;

    Object[] otherName;

    Object[] otherAddress;

    String communityBrand;
    String qualityCoefficient;

    String roomStructureType;

    String shape;

    Integer totalBuildingCount;

    String propertyCompanyPhone;

    String specialReason;

    Object[] position;

    Integer totalParkingSpaceCount;

    Double coverageArea;

    Double numberOfParkingSpaces;

    String isWhite;

    String hasElevator;

    String confirm;

    Object[] qualityMainMessage;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最近修改时间", name = "updateDate")
    Date updateDate;
}
