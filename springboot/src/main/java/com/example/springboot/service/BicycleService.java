package com.example.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.springboot.controller.request.BicyclePageRequest;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.BicycleMapper;
import com.example.springboot.model.entity.Bicycle;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BicycleService {

    @Autowired
    BicycleMapper bicycleMapper;


    public List<Bicycle> list() {
        return bicycleMapper.list();
    }


    public PageInfo<Bicycle> page(BicyclePageRequest baseRequest) {
        QueryWrapper<Bicycle> wrapper = new QueryWrapper<>();
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(baseRequest.getBrand())) {
            map.put("brand", baseRequest.getBrand());
        }
        if (StringUtils.isNotBlank(baseRequest.getModel())) {
            map.put("model", baseRequest.getModel());
        }
        wrapper.allEq(map, false);
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Bicycle> list = bicycleMapper.selectList(wrapper);
        return new PageInfo<Bicycle>(list);
        //return new PageInfo<>(bicycleMapper.listByCondition(baseRequest));
    }

    public Bicycle getBicycle(Integer id) {
        Bicycle bicycle = bicycleMapper.selectById(id);
        return bicycle;
    }

    public void updateBicycle(Bicycle bicycle) {
        try {
            bicycleMapper.updateById(bicycle);
        } catch (Exception e) {
            throw new ServiceException("自行车数据更新错误", e);
        }
    }

    public void deleteBicycle(Integer id) {
        try {
            bicycleMapper.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("自行车数据删除时错误", e);
        }
    }

    public void addBicycle(Bicycle bicycle) {
        try {
            bicycleMapper.insert(bicycle);
        } catch (Exception e) {
            throw new ServiceException("插入新自行车数据时错误", e);
        }
    }

    public PageInfo<Bicycle> rentPage(BicyclePageRequest baseRequest) {
        QueryWrapper<Bicycle> wrapper = new QueryWrapper<>();
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(baseRequest.getBrand())) {
            map.put("brand", baseRequest.getBrand());
        }
        if (StringUtils.isNotBlank(baseRequest.getModel())) {
            map.put("model", baseRequest.getModel());
        }
        wrapper.allEq(map, false).gt("amount", 0);
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Bicycle> list = bicycleMapper.selectList(wrapper);
        return new PageInfo<Bicycle>(list);
    }

    public void minusBicycleAmount(Integer id, Integer amount) {
        UpdateWrapper<Bicycle> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id).setSql("amount=amount-" + amount);
        bicycleMapper.update(null, updateWrapper);
    }

    public void addBicycleAmount(Integer id, Integer amount) {
        UpdateWrapper<Bicycle> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id).setSql("amount=amount+" + amount);
        bicycleMapper.update(null, updateWrapper);
    }

}
