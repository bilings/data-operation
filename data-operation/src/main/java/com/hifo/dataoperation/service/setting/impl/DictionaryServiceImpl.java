package com.hifo.dataoperation.service.setting.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.hifo.dataoperation.dao.BusCategoryDao;
import com.hifo.dataoperation.dao.BusDictionaryDao;
import com.hifo.dataoperation.entity.BusCategory;
import com.hifo.dataoperation.entity.BusDictionary;
import com.hifo.dataoperation.service.base.BasicService;
import com.hifo.dataoperation.service.setting.DictionaryService;
import com.hifo.dataoperation.vo.DictVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典操作Service
 *
 * @author whc
 * @date 2019/4/1 16:54
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private BasicService basicService;

    @Autowired
    private BusDictionaryDao busDictionaryDao;
    @Autowired
    private BusCategoryDao busCategoryDao;

    @Override
    public List<BusDictionary> query(Long categoryId, String categoryName) {
        Map<String, Object> params = new HashMap<>();
        if (categoryId != null) {
            params.clear();
            params.put("categoryId", categoryId);
            return busDictionaryDao.selectByCondition(params, null, null);
        } else if (categoryId == null && StringUtils.isNotBlank(categoryName)) {
            params.clear();
            params.put("categoryName", categoryName);
            List<BusCategory> list = busCategoryDao.selectByCondition(params, null, null);
            if (CollectionUtils.isEmpty(list)) {
                return null;
            }
            params.clear();
            params.put("categoryId", list.get(0).getCategoryId());
            return busDictionaryDao.selectByCondition(params, null, null);
        }
        return null;
    }



    @Override
    public List<BusDictionary> query() {
        return busDictionaryDao.selectList(BusDictionary.class);
    }

    @Override
    public String id2Text(String id) {
//        try {
//            return basicService.load2Java(Table.hf_dictionary, Field.pk(id), BusDictionary.class).getItem();
//        } catch (Exception e) {
//            return "";
//        }
        return "";
    }

    @Override
    public String ids2Text(String ids) {
        try {
            StringBuilder texts = new StringBuilder();
            String token = ",";
            for (String id : ids.split(token)) {
                if (texts.length() > 0) {
                    texts.append(",").append(id2Text(id));
                } else {
                    texts.append(id2Text(id));
                }
            }
            return texts.toString();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public String texts2Id(String texts, String category) throws Exception {
//        List<BusDictionary> dictionaryList = baseMapper.query(null, category);
//        texts = texts.replace("，", ",");
//        StringTokenizer st = new StringTokenizer(texts, ",");
//        StringBuilder ids = new StringBuilder();
//        while (st.hasMoreTokens()) {
//            String text = st.nextToken();
//            if (StringUtil.isNull(text)) {
//                continue;
//            }
//            String id = null;
//            for (BusDictionary dictionary : dictionaryList) {
//                if (dictionary.getItem().equals(text)) {
//                    id = dictionary.getId().toString();
//                    break;
//                }
//            }
//            if (id == null) {
//                throw new Exception("错误的字典数据: " + texts);
//            }
//            if (ids.length() > 0) {
//                ids.append(",").append(id);
//            } else {
//                ids.append(id);
//            }
//        }
//        return ids.toString();

        return  null;
    }

    @Override
    public List<DictVO> dictItemsList() {
//        return baseMapper.selectListByItems();
        return null;
    }

}
