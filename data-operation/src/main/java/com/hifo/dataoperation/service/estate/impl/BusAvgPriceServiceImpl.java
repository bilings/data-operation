package com.hifo.dataoperation.service.estate.impl;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hifo.dataoperation.base.MybatisPlusService;
import com.hifo.dataoperation.entity.ExtEmployee;
import com.hifo.dataoperation.controller.ApiResult;
import com.hifo.dataoperation.mapper.estate.AvgPriceMapper;
import com.hifo.dataoperation.mapper.base.Sequence;
import com.hifo.dataoperation.mapper.base.Table;
import com.hifo.dataoperation.entity.BusEstate;
import com.hifo.dataoperation.entity.BusEstateAvgPrice;
import com.hifo.dataoperation.vo.BusEstateAvgPriceExtend;
import com.hifo.dataoperation.pagination.Pagination;
import com.hifo.dataoperation.service.base.BasicService;
import com.hifo.dataoperation.service.estate.BusAvgPriceService;
import com.hifo.dataoperation.service.estate.BusEstateService;
import com.hifo.dataoperation.service.setting.DictionaryService;
import com.hifo.dataoperation.service.upload.UploadService;
import com.hifo.dataoperation.util.*;
import com.hifo.dataoperation.vo.BusEstateAvgPriceFailVO;
import com.hifo.dataoperation.vo.BusEstateAvgPriceVO;
import lombok.extern.java.Log;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 张宗朋
 * @description
 * @create 16:17
 */
@Log
@Service
public class BusAvgPriceServiceImpl extends MybatisPlusService<AvgPriceMapper, BusEstateAvgPriceExtend> implements BusAvgPriceService {

    @Autowired
    private BusEstateService estateService;
    @Autowired(required = false)
    private AvgPriceMapper avgPriceMapper;
    @Autowired
    private BasicService basicService;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private UploadService uploadService;

    @Override
    public IPage list(Pagination page, BusEstateAvgPriceExtend busEstateAvgPrice) {
        busEstateAvgPrice.setOrganizationId(basicService.getLoginEmployee().getOrganizationId());
        page.setRecords(avgPriceMapper.query(page, busEstateAvgPrice));
        return page;
    }

    /**
     * 新增均价
     *
     * @return boolean
     */
    @Override
    @Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
    public ApiResult avgPriceServiceAdd(BusEstateAvgPriceExtend busEstateAvgPrice) {
        return ApiResult.success(insert(busEstateAvgPrice, Sequence.seq_bus_estate_avg_price, Table.bus_estate_avg_price));
    }

    @Override
    public void confirm(String id, float price) {
        BusEstateAvgPriceExtend busEstateAvgPrice = avgPriceMapper.selectById(id);
        busEstateAvgPrice.setAvgPriceManual(price);
        updateById(busEstateAvgPrice);
    }

    @Override
    public void visibility(String[] ids, String visibility) {
        for (int i = 0; i < ids.length; i++) {
            BusEstateAvgPriceExtend busEstateAvgPrice = avgPriceMapper.selectById(ids[i]);
            busEstateAvgPrice.setVisibility(visibility);
            updateById(busEstateAvgPrice);
        }
    }

    @Override
    public void check(String[] ids, String isAudit) {
        for (int i = 0; i < ids.length; i++) {
            BusEstateAvgPriceExtend busEstateAvgPrice = avgPriceMapper.selectById(ids[i]);
            busEstateAvgPrice.setIsAudit(isAudit);
            updateById(busEstateAvgPrice);
        }
    }

    /**
     * 判断是否为正确楼栋（用于新增判断）
     *
     * @param vo 均价
     * @return 返回空表示没有错误信息
     */
    private Object getErrMsg(BusEstateAvgPriceVO vo) {
        // 有ID，那么必须存在
        if (!StringUtil.isNull(vo.getId())) {
            // 通过ID，判断均价是否存在
            BusEstateAvgPrice avgPrice1 = avgPriceMapper.selectById(vo.getId());
            if (avgPrice1 == null) {
                return "根据ID未找到匹配楼盘均价";
            }
        } else {
            // 判断数据有效性
            if (vo == null) {
                return "空楼盘价格";
            }
            if (StringUtil.isNull(vo.getCityName())) {
                return "缺少必要信息：城市名称";
            }
            if (StringUtil.isNull(vo.getDistrictName())) {
                return "缺少必要信息：行政区名称";
            }
            if (StringUtil.isNull(vo.getEstateName())) {
                return "缺少必要信息：楼盘名称";
            }
            if (StringUtil.isNull(vo.getQuality())) {
                return "缺少必要信息：物业品质";
            }
            if (StringUtil.isNull(vo.getCalDate())) {
                return "缺少必要信息：计算日期";
            }
            //根据城市，行政区，楼盘名称。查询出楼盘
            BusEstate busEstate = new BusEstate();
//            busEstate.setName(vo.getEstateName());
            busEstate.setCityName(vo.getCityName());
            busEstate.setDistrictName(vo.getDistrictName());
//            busEstate.setOrganizationId(basicService.getLoginEmployee().getOrganizationId());
            List<BusEstate> estates = estateService.queryByParam(busEstate);
            if (estates == null || estates.size() == 0) {
                return "没有找到对应的楼盘";
            } else if (estates.size() > 1) {
                return "找到对应的楼盘数量大于等于2个";
            } else {
                //给该楼均价加楼盘ID
//                vo.setEstateId(estates.get(0).getId());
            }
        }
        return vo;
    }

    /**
     * 保存均价（新增、更新）
     *
     * @param avgPrice 楼栋对象
     */
    public String update(BusEstateAvgPriceExtend avgPrice) {
        // 组织机构
        avgPrice.setOrganizationId(basicService.getLoginEmployee().getOrganizationId());
        //判断当前月份的均价是否存在，如果存在跟新，不存在新增
        BusEstateAvgPrice avgPriceNew = avgPriceMapper.selectByParam(avgPrice.getOrganizationId(), avgPrice.getEstateId(), avgPrice.getQuality(), avgPrice.getCalDate());
        if (avgPriceNew != null) {
            avgPrice.setId(avgPriceNew.getId());
        } else {
            avgPrice.setIsAudit("1");
            avgPrice.setVisibility("1");
        }
        // 更新
        if (StringUtil.isNull(avgPrice.getId())) {
            insert(avgPrice, Sequence.seq_bus_estate_avg_price, Table.bus_estate_avg_price);
        } else {
            updateById(avgPrice);
        }
        return "新建成功";
    }

    /**
     * 当楼盘修改完物业类型之后，均价物业类型也得要修改
     *
     * @param busEstate 楼盘
     * @return 0
     */
    @Override
    public String updateQuality(BusEstate busEstate) {
        if (busEstate == null) {
            return "楼盘为空";
        }
//        if (StringUtil.isNull(busEstate.getQuality())) {
//            // 逻辑删除之前多余的住宅品质
//            QueryWrapper<BusEstateAvgPriceExtend> queryPrice = new QueryWrapper<>();
//            queryPrice.eq("estateId", busEstate.getId());
//            queryPrice.eq("organizationId", basicService.getLoginEmployee().getOrganizationId());
//            List<BusEstateAvgPriceExtend> listAvgPrice = avgPriceMapper.selectList(queryPrice);
//            for (BusEstateAvgPriceExtend avgPrice : listAvgPrice) {
//                avgPrice.setVisibility("0");
//                avgPriceMapper.updateById(avgPrice);
//            }
//            return "楼盘物业类型为空";
//        }
//        String quality = busEstate.getQuality();
        String flag = ",";
//        if (quality.endsWith(flag)) {
//            quality = quality.substring(0, quality.lastIndexOf(','));
//        }
//        String[] qualitys = quality.split(",");
        // 逻辑删除之前多余的住宅品质
        QueryWrapper<BusEstateAvgPriceExtend> queryPrice = new QueryWrapper<>();
//        queryPrice.eq("estateId", busEstate.getId());
        queryPrice.eq("organizationId", basicService.getLoginEmployee().getOrganizationId());
//        queryPrice.notIn("quality", qualitys);
        List<BusEstateAvgPriceExtend> listAvgPrice = avgPriceMapper.selectList(queryPrice);
        for (BusEstateAvgPriceExtend avgPrice : listAvgPrice) {
            avgPrice.setVisibility("0");
            avgPriceMapper.updateById(avgPrice);
        }

//        for (String q : qualitys) {
//            // 判断当前物业类型在楼盘均价中是否存在并且删除了，如果存在改成均价显示
//            QueryWrapper<BusEstateAvgPriceExtend> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("visibility", "0");
//            queryWrapper.eq("estateId", busEstate.getId());
//            queryWrapper.eq("quality", q);
//            queryWrapper.eq("organizationId", basicService.getLoginEmployee().getOrganizationId());
//            BusEstateAvgPriceExtend busEstateAvgPrice = new BusEstateAvgPriceExtend();
//            busEstateAvgPrice.setEstateId(busEstate.getId());
//            busEstateAvgPrice.setQuality(q);
//            busEstateAvgPrice.setOrganizationId(basicService.getLoginEmployee().getOrganizationId());
//            List<BusEstateAvgPriceExtend> list = avgPriceMapper.selectList(queryWrapper);
//
//            for (BusEstateAvgPriceExtend avgPrice : list) {
//                // 住宅品质存在改成后台可见
//                if (avgPrice.getVisibility().equals(Constant.FALSE)) {
//                    avgPrice.setVisibility("1");
//                    avgPriceMapper.updateById(avgPrice);
//                }
//            }
//        }
        return "";
    }

    public void insertAvgPrice(BusEstateAvgPriceExtend busEstateAvgPrice, SimpleDateFormat simpleDateFormat) {
        busEstateAvgPrice.setCalDate(simpleDateFormat.format(new Date()));
        busEstateAvgPrice.setAvgPriceSys(0f);
        busEstateAvgPrice.setAvgPriceManual(0f);
        busEstateAvgPrice.setAvgPriceCoe(0f);
        busEstateAvgPrice.setIsAudit("1");
        busEstateAvgPrice.setVisibility("1");
        insert(busEstateAvgPrice, Sequence.seq_bus_estate_avg_price, Table.bus_estate_avg_price);
    }

    @Override
    public List<Map<String, Object>> findMaps(BusEstateAvgPriceExtend vo) {
        // 字典map
        var dictMap = new HashMap<Integer, String>(50);
//        var maps = dictionaryService.listMaps(new QueryWrapper<>());
//        for (var i = 0; i < maps.size(); i++) {
//            dictMap.put((Integer) maps.get(i).get("id"), (String) maps.get(i).get("item"));
//        }
        Pagination page = new Pagination();
        page.setLimit(50000);
        vo.setOrganizationId(basicService.getLoginEmployee().getOrganizationId());
        page.setRecords(avgPriceMapper.queryMap(page, vo));
        List<Map<String, Object>> list = page.getRecords();
        list.stream().forEach(e -> {
            var quality = (String) e.get("quality");
            if (StringUtil.isNotEmpty(quality)) {
                try {
                    e.put("quality", dictMap.get(Integer.parseInt(quality)));
                } catch (Exception ex) {
                    log.warning(ex.getMessage());
                }
            }
            var buildingType = (String) e.get("buildingType");
            if (StringUtil.isNotEmpty(buildingType)) {
                try {
                    e.put("buildingType", dictMap.get(Integer.parseInt(buildingType)));
                } catch (Exception ex) {
                    log.warning(ex.getMessage());
                }
            }
            e.put("isAudit", "1".equals(e.get("isAudit")) ? "已审核" : "未审核");
        });
        return list;
    }

    @Override
    public List<BusEstateAvgPriceExtend> findLists(BusEstateAvgPriceExtend vo) {
        Pagination page = new Pagination();
        page.setLimit(50000);
        vo.setOrganizationId(basicService.getLoginEmployee().getOrganizationId());
        page.setRecords(avgPriceMapper.query(page, vo));
        return page.getRecords();
    }

    @Override
    public String importAvgPrice(MultipartFile file) throws Exception {
        // excel转list
        List<BusEstateAvgPriceVO> list = ExcelUtil.importExcel(file, 1, 1, BusEstateAvgPriceVO.class);
        var size = list.size();
        if (size == 0) {
            return "空文件";
        }
        List<BusEstateAvgPriceFailVO> errorList = new ArrayList<>();
        BusEstateAvgPriceFailVO vo = new BusEstateAvgPriceFailVO();
        for (var i = 0; i < size; i++) {
            var avgPrice = list.get(i);
            try {
                Object errMsg = getErrMsg(avgPrice);
                if (errMsg instanceof String) {
                    vo = new BusEstateAvgPriceFailVO();
                    EntityUtils.copyProperties(avgPrice, vo);
                    vo.setReason(errMsg.toString());
                    errorList.add(vo);
                    continue;
                } else {
                    avgPrice = (BusEstateAvgPriceVO) errMsg;
                }
                BusEstateAvgPriceExtend avgPriceExtend = new BusEstateAvgPriceExtend();
                EntityUtils.copyProperties(avgPrice, avgPriceExtend);
                if (avgPriceExtend.getId() != null) {
                    updateById(avgPriceExtend);
                    continue;
                }
                avgPriceExtend.setVisibility("2");
                insert(avgPriceExtend, Sequence.seq_bus_estate_avg_price, Table.bus_estate_avg_price);
            } catch (Exception e) {
                vo.setReason(e.getMessage());
                errorList.add(vo);
            }
        }
        // 下载导入的错误数据excel
        var errorSize = errorList.size();
        File failedFile = uploadService.downloadFailedFile(new ExportParams("导入错误均价信息", "失败数据"), BusEstateAvgPriceFailVO.class, errorList);
        // 记录上传日志
        uploadService.save(file.getOriginalFilename(), size - errorSize, errorSize, failedFile.getAbsolutePath(), "均价");
        return failedFile.getAbsolutePath();
    }

    @Override
    public BusEstateAvgPriceExtend save(BusEstateAvgPriceExtend vo, Sequence sequence, Table table, ExtEmployee emp) {
        insert(vo, sequence, table, emp);
        return vo;
    }

}
