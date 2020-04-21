package com.hifo.dataoperation.service.coe;

import com.hifo.dataoperation.entity.coe.FloorCityConfig;
import com.hifo.dataoperation.vo.FloorCityConfigVO;
import org.springframework.data.mongodb.core.query.Query;
import java.util.List;

public interface FloorCityConfigService {

    FloorCityConfig findById(String id);

    FloorCityConfig findByQuery(Query query);

    void delete(String[] ids);

    List<FloorCityConfig> List();

    void save(FloorCityConfig entity);


    void addConfig(FloorCityConfigVO configVO);



}
