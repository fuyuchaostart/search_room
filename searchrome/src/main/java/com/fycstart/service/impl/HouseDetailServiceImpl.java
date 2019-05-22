package com.fycstart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fycstart.entity.HouseDetail;
import com.fycstart.mapper.HouseDetailMapper;
import com.fycstart.service.HouseDetailService;
import com.fycstart.web.dto.HouseDetailDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fycstart
 * @since 2019-04-29
 */
@Service
public class HouseDetailServiceImpl extends ServiceImpl<HouseDetailMapper, HouseDetail> implements HouseDetailService {

    @Autowired
    private HouseDetailMapper houseDetailMapper;


    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<HouseDetail> findAllByHouseIdIn(List<Integer> houseIds) {
        QueryWrapper<HouseDetail> detailQueryWrapper = new QueryWrapper<>();
        detailQueryWrapper.in("house_id", houseIds);
        List<HouseDetail> houseDetails = houseDetailMapper.selectList(detailQueryWrapper);
        return houseDetails;
    }
}
