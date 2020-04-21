package com.etoak.mapper;

import com.etoak.bean.Dict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictMapper {

    //根据groupid查询字典列表
    List<Dict> queryList(@Param("groupId") String groupId);

}
