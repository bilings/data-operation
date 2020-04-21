package com.hifo.dataoperation.mapper.estate;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hifo.dataoperation.entity.BusBuilding;
import com.hifo.dataoperation.entity.FillBuilding;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BusBuildingMapper extends  BaseMapper<BusBuilding>{

    int insertSelective(BusBuilding record);

    List<BusBuilding> getBuildingList(@Param("communityId") long communityId, @Param("name") String name, @Param("address") String address, @Param("quality") String quality, @Param("visibility") String visibility);

    void hideBuilding(String[] ids);

    void showBuilding(String[] ids);

    void transferBuilding(@Param("estateId") long estateId, @Param("list") List list);

    BusBuilding findById(Long id);

    void fillSave(@Param("list") List<BusBuilding> list);

    void fillSaveOne(BusBuilding busBuilding);

    void deleteBuilding(@Param("visibility") String visibility, @Param("list") List<BusBuilding> list);

    List<FillBuilding> query(@Param("busBuilding") BusBuilding busBuilding, @Param("start") int start, @Param("limit") int limit);
}