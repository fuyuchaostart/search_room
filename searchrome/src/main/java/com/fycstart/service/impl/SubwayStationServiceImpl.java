package com.fycstart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fycstart.bass.ServiceResult;
import com.fycstart.entity.SubwayStation;
import com.fycstart.mapper.SubwayStationMapper;
import com.fycstart.service.SubwayStationService;
import com.fycstart.web.dto.SubwayStationDTO;
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
public class SubwayStationServiceImpl extends ServiceImpl<SubwayStationMapper, SubwayStation> implements SubwayStationService {

    @Autowired
    private SubwayStationMapper subwayStationMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<SubwayStationDTO> findAllStationBySubway(Long subwayId) {
        QueryWrapper<SubwayStation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(subwayId != null, "subway_id", subwayId);

        List<SubwayStation> subwayStations = subwayStationMapper.selectList(queryWrapper);

        List<SubwayStationDTO> subwayStationDTOS = new ArrayList<>(subwayStations.size());
        subwayStations.forEach(subwayStation -> {
            SubwayStationDTO subwayStationDTO = new SubwayStationDTO();
            BeanUtils.copyProperties(subwayStation, subwayStationDTO);
            subwayStationDTOS.add(subwayStationDTO);
        });
        return subwayStationDTOS;
    }

    @Override
    public ServiceResult<SubwayStationDTO> findSubwayStation(Long subwayStationId) {

        if (subwayStationId == null) {
            return ServiceResult.notFound();
        }
        SubwayStation subwayStation = subwayStationMapper.selectById(subwayStationId);
        if (subwayStation == null) {
            return ServiceResult.notFound();
        }
        return ServiceResult.of(modelMapper.map(subwayStation, SubwayStationDTO.class));
    }
}
