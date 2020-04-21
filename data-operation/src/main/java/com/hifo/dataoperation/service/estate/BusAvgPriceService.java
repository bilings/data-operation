package com.hifo.dataoperation.service.estate;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hifo.dataoperation.controller.ApiResult;
import com.hifo.dataoperation.entity.BusEstate;
import com.hifo.dataoperation.entity.ExtEmployee;
import com.hifo.dataoperation.mapper.base.Sequence;
import com.hifo.dataoperation.mapper.base.Table;
import com.hifo.dataoperation.vo.BusEstateAvgPriceExtend;
import com.hifo.dataoperation.pagination.Pagination;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author 张宗朋
 * @description
 * @create 16:17
 */
public interface BusAvgPriceService extends IService<BusEstateAvgPriceExtend> {

    /**
     * 分页查询楼盘均价
     *
     * @param page
     * @param busEstateAvgPrice
     * @return
     */
    IPage list(Pagination page, BusEstateAvgPriceExtend busEstateAvgPrice);

    /**
     * 新增均价
     *
     * @param busEstateAvgPrice
     * @return
     */
    ApiResult avgPriceServiceAdd(BusEstateAvgPriceExtend busEstateAvgPrice);

    void confirm(String id, float price);

    void visibility(String[] ids, String visibility);

    void check(String[] ids, String isAudit);

    String updateQuality(BusEstate busEstate);

    /**
     * map list
     *
     * @param vo
     * @return
     */
    List<Map<String, Object>> findMaps(BusEstateAvgPriceExtend vo);

    /**
     * list
     *
     * @param vo
     * @return
     */
    List<BusEstateAvgPriceExtend> findLists(BusEstateAvgPriceExtend vo);

    /**
     * 导入
     *
     * @param file
     * @return
     * @throws Exception
     */
    String importAvgPrice(MultipartFile file) throws Exception;

    /**
     * 新增均价
     *
     * @param vo
     * @param sequence
     * @param table
     * @param emp
     * @return
     */
    BusEstateAvgPriceExtend save(BusEstateAvgPriceExtend vo, Sequence sequence, Table table, ExtEmployee emp);
}
