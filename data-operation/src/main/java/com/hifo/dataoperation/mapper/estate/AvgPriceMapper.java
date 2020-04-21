package com.hifo.dataoperation.mapper.estate;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hifo.dataoperation.entity.BusEstateAvgPrice;
import com.hifo.dataoperation.vo.BusEstateAvgPriceExtend;
import com.hifo.dataoperation.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * AvgPriceMapper
 * @author
 */
public interface AvgPriceMapper extends BaseMapper<BusEstateAvgPriceExtend> {

    /**
     * 分页查询楼盘均价
     *
     * @param page
     * @param busEstateAvgPrice
     * @return
     */
    List<BusEstateAvgPrice> query(Pagination page, @Param("busEstateAvgPrice") BusEstateAvgPriceExtend busEstateAvgPrice);

    /**
     * 分页查询楼盘均价
     *
     * @param page
     * @param busEstateAvgPrice
     * @return
     */
    List<Map<String,Object>> queryMap(Pagination page, @Param("busEstateAvgPrice") BusEstateAvgPriceExtend busEstateAvgPrice);

    /**
     * 批量更新
     *
     * @param list
     */
    void batchUpdate(@Param("list") List<BusEstateAvgPrice> list);

    @Select("SELECT beap.* FROM bus_estate_avg_price beap  WHERE beap.organizationId = #{organizationId} and beap.estateId = #{estateId} and beap.quality = #{quality} and beap.calDate = #{calDate}")
    BusEstateAvgPrice selectByParam(@Param("organizationId") int organizationId, @Param("estateId") long estateId, @Param("quality") String quality, @Param("calDate") String calDate);
}
