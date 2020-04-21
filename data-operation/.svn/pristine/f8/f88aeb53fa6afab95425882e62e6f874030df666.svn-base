package com.hifo.dataoperation.mapper.estate;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hifo.dataoperation.vo.BusRoomVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 房号
 *
 * @author jinzhichen
 * @date 2019/4/13 15:28
 */
public interface BusRoomMapper extends BaseMapper<BusRoomVO> {

    /**
     * 查询房号列表信息-组装成页面数据格式
     *
     * @param vo
     * @return
     */
    List<BusRoomVO> busRooms(@Param("busRoom") BusRoomVO vo);

    /**
     * 根据单元名称和楼栋id查询房号列表信息
     *
     * @param vo
     * @return
     */
    List<BusRoomVO> busRoomList(@Param("vo") BusRoomVO vo);

    /**
     * 根据楼盘id统计房号数量
     *
     * @param vo
     * @return
     */
    List<BusRoomVO> selectCountByEstId(@Param("vo") BusRoomVO vo);

    /**
     * 根据楼层数范围查询房号列表
     *
     * @param vo
     * @return
     */
    List<BusRoomVO> selectRooms(@Param("vo") BusRoomVO vo);

    /**
     * 根据行政区名称和楼盘名称查询楼栋id，导入时调用
     *
     * @param vo
     * @return
     */
    List<BusRoomVO> selectBuildingId(@Param("vo") BusRoomVO vo);
}
