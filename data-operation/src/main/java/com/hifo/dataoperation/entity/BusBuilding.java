package com.hifo.dataoperation.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hifo.dataoperation.entity.mongo.Base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


@EqualsAndHashCode(callSuper = false)
@Data
@Document(collection = "building")
public class BusBuilding  extends BaseEntity {

    private Integer organizationId;
    private Integer createId;
    private String createName;

    @ApiModelProperty(value="城市ID",name="cityId")
    private String cityId;
    @ApiModelProperty(value="城市名称",name="cityName")
    private String cityName;
    @ApiModelProperty(value="总楼层",name="totalFloor")
    private String totalFloor;
    @ApiModelProperty(value="地下总楼层",name="underGroundFloor")
    private String underGroundFloor;
    @ApiModelProperty(value="地上总楼层",name="overGroundFloor")
    private String overGroundFloor;
    @ApiModelProperty(value="楼栋名称",name="buildingName")
    private String buildingName;
    @ApiModelProperty(value="别名",name="otherName")
    private String otherName;
    @ApiModelProperty(value="地址",name="address")
    private String address;
    @ApiModelProperty(value="单元数",name="unitNo")
    private String unitNo;
    @ApiModelProperty(value="单元名称（xxx,xxx,xxx）",name="unitName")
    private String unitName;
    @ApiModelProperty(value="楼盘ID",name="communityId")
    private String communityId;
    @ApiModelProperty(value="楼盘名称",name="communityName")
    private String communityName;
    @ApiModelProperty(value="层户数",name="LayerNumber")
    private String layerNumber;
    @ApiModelProperty(value="建筑类别",name="buildingType")
    private String buildingType;
    @ApiModelProperty(value="外墙装饰",name="wallDecoration")
    private String wallDecoration;
    @ApiModelProperty(value="公共装修档次",name="publicDecoration")
    private String publicDecoration;
    @JsonFormat(pattern = "yyyy-MM")
    @DateTimeFormat(pattern = "yyyy-MM")
    @ApiModelProperty(value="竣工日期",name="builtDate")
    private Date builtDate;
    @ApiModelProperty(value="房号总数",name="roomNo")
    private String roomNo;
    @ApiModelProperty(value="标准层室号数",name="roomNoPerFloor")
    private String roomNoPerFloor;
    @ApiModelProperty(value="建筑结构",name="structure")
    private String structure;
    @ApiModelProperty(value="有无电梯，0=无，1=有，2=部分",name="haveLift")
    private String haveLift;
    @ApiModelProperty(value="电梯数",name="liftNo")
    private String liftNo;
    @ApiModelProperty(value="电梯品牌",name="liftBrand")
    private String liftBrand;
    @ApiModelProperty(value="主力面积",name="mainArea")
    private String mainArea;
    @ApiModelProperty(value="是否完善，0=否，1=是",name="isPerfect")
    private String isPerfect;
    @ApiModelProperty(value="价格系数",name="priceCoe")
    private String priceCoe;
    @ApiModelProperty(value="非标准层说明",name="nonStandDesc")
    private String nonStandDesc;
    @ApiModelProperty(value="价格差异说明，描述楼栋价格差异及内部少数房产价格差异特别明显的主要因素及大致差异比例",name="priceDesc")
    private String priceDesc;
    @ApiModelProperty(value="来源",name="dataFrom")
    private String dataFrom;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="最后修改时间",name="updateDate")
    private Date updateDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="最后修改时间",name="updateDate")
    private Date createDate;

    @ApiModelProperty(value="是否可见：0=前端不可见，1=可见",name="visibility")
    private String visibility="1";
    @ApiModelProperty(value="是否删除：0=正常，1=已删除",name="isDeleted")
    private Integer isDeleted=0;

    @ApiModelProperty(value="采集来源ID",name="matchedGuid")
    private  String matchedGuid;
    private  String stdName;
    private  String stdUnit;
    private  String originId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="采集时间",name="采集时间")
    private  Date fetchTime;
    private  String[] originIdSet;
    private  String estateServiceId;
    private  String sr;
    private  String originSource;
    private  String antId;
    private  String stdSr;
    private  String[] nameSet;



}