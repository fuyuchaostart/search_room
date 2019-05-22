package com.fycstart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fycstart.bass.ServiceResult;
import com.fycstart.entity.Subway;
import com.fycstart.mapper.SubwayMapper;
import com.fycstart.service.SubwayService;
import com.fycstart.web.dto.SubwayDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
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
public class SubwayServiceImpl extends ServiceImpl<SubwayMapper, Subway> implements SubwayService {

    @Autowired
    private SubwayMapper subwayMapper;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<SubwayDTO> findAllSubwayByCity(String cityEnName) {

        QueryWrapper<Subway> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(cityEnName != null, "city_en_name", cityEnName);
        List<Subway> subways = subwayMapper.selectList(queryWrapper);
        List<SubwayDTO> subwayDTOS = new ArrayList<>();
        subways.forEach(subWay -> {
            SubwayDTO subwayDTO = new SubwayDTO();
            BeanUtils.copyProperties(subWay, subwayDTO);
            subwayDTOS.add(subwayDTO);
        });

        return subwayDTOS;
    }


    @Override
    public ServiceResult<SubwayDTO> findSubway(Long subwayId) {
        if (subwayId == null) {
            return ServiceResult.notFound();
        }
        Subway subway = subwayMapper.selectById(subwayId);
        if (subway == null) {
            return ServiceResult.notFound();
        }
        return ServiceResult.of(modelMapper.map(subway, SubwayDTO.class));
    }
}
