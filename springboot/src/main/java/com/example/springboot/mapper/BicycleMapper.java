package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.model.entity.Bicycle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BicycleMapper extends BaseMapper<Bicycle> {

    @Select("select * from bicycle order by id desc")
    List<Bicycle> list();


    List<Bicycle> listCondition(BaseRequest baseRequest);

}
