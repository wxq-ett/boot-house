package com.etoak.service;

import com.etoak.bean.House;
import com.etoak.bean.HouseVo;
import com.etoak.bean.Page;

public interface HouseService {
    //添加房源
    int addHouse(House house);
    //房源列表查询
    Page<HouseVo> queryList(int pageNum, int pageSize, HouseVo houseVo, String[] rentalList);
    //更新房源
    int updateHouse(House house);
    //根据id刪除房源
    int deleteById(int id);
}
