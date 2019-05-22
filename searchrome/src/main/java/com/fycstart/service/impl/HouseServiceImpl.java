package com.fycstart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fycstart.bass.HouseSort;
import com.fycstart.bass.HouseStatus;
import com.fycstart.bass.ServiceMultiResult;
import com.fycstart.bass.ServiceResult;
import com.fycstart.entity.*;
import com.fycstart.mapper.*;
import com.fycstart.service.*;
import com.fycstart.utils.LoginUserUtil;
import com.fycstart.web.dto.HouseDTO;
import com.fycstart.web.dto.HouseDetailDTO;
import com.fycstart.web.dto.HousePictureDTO;
import com.fycstart.web.form.DatatableSearch;
import com.fycstart.web.form.HouseForm;
import com.fycstart.web.form.PhotoForm;
import com.fycstart.web.form.RentSearch;
import com.google.common.collect.Maps;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 房屋信息表 服务实现类
 * </p>
 *
 * @author fycstart
 * @since 2019-04-29
 */
@Service
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements HouseService {

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HouseDetailMapper houseDetailMapper;

    @Autowired
    private HousePictureMapper housePictureMapper;

    @Autowired
    private HouseTagMapper houseTagMapper;

    @Autowired
    private HouseSubscribeMapper houseSubscribeMapper;


    @Autowired
    private HouseDetailService houseDetailService;

    @Autowired
    private HousePictureService housePictureService;

    @Autowired
    private HouseTagService houseTagService;

    @Autowired
    private SubwayMapper subwayMapper;

    @Autowired
    private SubwayStationMapper subwayStationMapper;

    @Autowired
    private SearchService searchService;


    @Value("${qiniu.cdn.prefix}")
    private String cdnPrefix;


    /**
     * 保存房源信息
     *
     * @param houseForm
     * @return
     */
    @Override
    public ServiceResult<HouseDTO> saveHouse(HouseForm houseForm) {
        HouseDetail detail = new HouseDetail();
        wrapperDetailInfo(detail, houseForm);


        House house = new House();
        //转换实体
        modelMapper.map(houseForm, house);

        Date now = new Date();
        house.setCreateTime(now);
        house.setLastUpdateTime(now);
        house.setAdminId(LoginUserUtil.getLoginUserId());
        //保存房源信息
        boolean save = this.save(house);
        if (!save) {
            throw new RuntimeException("保存房源信息失败");
        }

        //保存房源详细信息
        detail.setHouseId(house.getId());
        boolean detailHouseIsFlag = houseDetailService.save(detail);

        if (!detailHouseIsFlag) {
            throw new RuntimeException("保存房源详细信息失败");
        }

        //保存图片信息
        List<HousePicture> pictures = generatePictures(houseForm, house.getId());
        boolean pictureIsFlag = housePictureService.saveBatch(pictures);

        if (!pictureIsFlag) {
            throw new RuntimeException("保存房源图片信息失败");
        }


        //实体转换
        HouseDTO houseDTO = modelMapper.map(house, HouseDTO.class);
        HouseDetailDTO houseDetailDTO = modelMapper.map(detail, HouseDetailDTO.class);

        houseDTO.setHouseDetail(houseDetailDTO);

        List<HousePictureDTO> pictureDTOS = new ArrayList<>();
        pictures.forEach(housePicture -> pictureDTOS.add(modelMapper.map(housePicture, HousePictureDTO.class)));
        houseDTO.setPictures(pictureDTOS);
        houseDTO.setCover(this.cdnPrefix + houseDTO.getCover());

        List<String> tags = houseForm.getTags();
        if (tags != null && !tags.isEmpty()) {
            List<HouseTag> houseTags = new ArrayList<>();
            for (String tag : tags) {
                houseTags.add(new HouseTag(house.getId(), tag));
            }
            //保存房屋标签信息
            boolean houseTagIsFlag = houseTagService.saveBatch(houseTags);
            if (!houseTagIsFlag) {
                throw new RuntimeException("保存房源标签信息失败");
            }
            houseDTO.setTags(tags);
        }

        return new ServiceResult<HouseDTO>(true, null, houseDTO);
    }

    /**
     * 后台查询
     *
     * @param datatableSearch
     * @return
     */
    @Override
    public ServiceMultiResult<HouseDTO> queryHouse(DatatableSearch datatableSearch) {

        QueryWrapper<House> houseQueryWrapper = new QueryWrapper<>();
        int current = (datatableSearch.getStart() / datatableSearch.getLength()) + 1;
        //分页参数
        Page<House> houseIPage = new Page<>(current, datatableSearch.getLength());

        //分页查询
        IPage<House> selectPage = houseMapper.selectPage(houseIPage, houseQueryWrapper);

        //转换
        List<House> records = selectPage.getRecords();
        List<HouseDTO> houseDTOS = new ArrayList<>(records.size());
        records.forEach(item -> {
            HouseDTO houseDTO = new HouseDTO();
            BeanUtils.copyProperties(item, houseDTO);
            houseDTOS.add(houseDTO);
        });
        ServiceMultiResult<HouseDTO> houseDTOServiceMultiResult = new ServiceMultiResult<>(selectPage.getTotal(), houseDTOS);

        return houseDTOServiceMultiResult;
    }


    /**
     * 前台查询
     *
     * @param rentSearch
     * @return
     */
    @Override
    public ServiceMultiResult<HouseDTO> userQueryHouse(RentSearch rentSearch) {

        if (rentSearch.getKeywords() != null && !rentSearch.getKeywords().isEmpty()) {
            //查询主表主键信息
            ServiceMultiResult<Integer> serviceResult = null;
            try {
                serviceResult = searchService.query(rentSearch);
                if (serviceResult.getTotal() == 0) {
                    return new ServiceMultiResult<>(0, new ArrayList<>());
                }
            } catch (IOException e) {
                e.printStackTrace();
                return new ServiceMultiResult<>(0, new ArrayList<>());
            }
            return new ServiceMultiResult<HouseDTO>(serviceResult.getTotal(), wrapperHouseResult(serviceResult.getResult()));
        }

        return simpleQuery(rentSearch);
    }


    //修改审核状态
    @Override
    @Transactional
    public ServiceResult updateStatus(Integer id, int status) throws IOException {

        House house = houseMapper.selectById(id);
        if (house == null) {
            return ServiceResult.notFound();
        }

        if (house.getStatus() == status) {
            return new ServiceResult(false, "状态没有发生变化");
        }

        if (house.getStatus() == HouseStatus.RENTED.getValue()) {
            return new ServiceResult(false, "已出租的房源不允许修改状态");
        }

        if (house.getStatus() == HouseStatus.DELETED.getValue()) {
            return new ServiceResult(false, "已删除的资源不允许操作");
        }

        House updateHouse = new House();
        BeanUtils.copyProperties(house, updateHouse);
        updateHouse.setStatus(status);
        houseMapper.updateById(updateHouse);

        // 上架更新索引 其他情况都要删除索引
        if (status == HouseStatus.PASSES.getValue()) {
            searchService.paddingIndex(id.toString());
        } else {
            searchService.remove(id.toString());
        }
        return ServiceResult.success();
    }

    @Override
    public ServiceResult<HouseDTO> queryHouseById(Long id) {

        House house = houseMapper.selectById(id);
        if (house == null) {
            return ServiceResult.notFound();
        }

        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("house_id", id);
        HouseDetail detail = houseDetailMapper.selectOne(queryWrapper);


        List<HousePicture> pictures = housePictureMapper.selectList(queryWrapper);

        HouseDetailDTO detailDTO = modelMapper.map(detail, HouseDetailDTO.class);
        List<HousePictureDTO> pictureDTOS = new ArrayList<>();
        for (HousePicture picture : pictures) {
            HousePictureDTO pictureDTO = modelMapper.map(picture, HousePictureDTO.class);
            pictureDTOS.add(pictureDTO);
        }


        List<HouseTag> tags = houseTagMapper.selectList(queryWrapper);
        List<String> tagList = new ArrayList<>();
        for (HouseTag tag : tags) {
            tagList.add(tag.getName());
        }

        HouseDTO result = modelMapper.map(house, HouseDTO.class);
        result.setHouseDetail(detailDTO);
        result.setPictures(pictureDTOS);
        result.setTags(tagList);
        Integer loginUserId = LoginUserUtil.getLoginUserId();
        // 已登录用户
        if (loginUserId > 0) {

            QueryWrapper<HouseSubscribe> houseSubscribeQueryWrapper = new QueryWrapper<>();
            houseSubscribeQueryWrapper.eq("house_id", id);
            houseSubscribeQueryWrapper.eq("user_id", loginUserId);

            HouseSubscribe subscribe = houseSubscribeMapper.selectOne(houseSubscribeQueryWrapper);
            if (subscribe != null) {
                result.setSubscribeStatus(subscribe.getStatus());
            }
        }

        return ServiceResult.of(result);
    }

    @Override
    public ServiceResult updateHouse(HouseForm houseForm) throws IOException {
        House house = this.houseMapper.selectById(houseForm.getId());
        if (house == null) {
            return ServiceResult.notFound();
        }

        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("house_id", house.getId());

        HouseDetail detail = this.houseDetailMapper.selectOne(queryWrapper);
        if (detail == null) {
            return ServiceResult.notFound();
        }

        ServiceResult wrapperResult = wrapperDetailInfo(detail, houseForm);
        if (wrapperResult != null) {
            return wrapperResult;
        }

        houseDetailService.save(detail);

        List<HousePicture> pictures = generatePictures(houseForm, houseForm.getId());
        housePictureService.saveBatch(pictures);

        if (houseForm.getCover() == null) {
            houseForm.setCover(house.getCover());
        }

        modelMapper.map(houseForm, house);
        house.setLastUpdateTime(new Date());
        this.save(house);

        if (house.getStatus() == HouseStatus.PASSES.getValue()) {
            searchService.paddingIndex(house.getId().toString());
        }

        return ServiceResult.success();
    }


    /**
     * 前台数据库查询
     *
     * @param rentSearch
     * @return
     */
    private ServiceMultiResult<HouseDTO> simpleQuery(RentSearch rentSearch) {

        QueryWrapper<House> houseQueryWrapper = new QueryWrapper<>();
        houseQueryWrapper.orderBy(true, false, rentSearch.getOrderBy());
        //标记
        boolean isFlag = HouseSort.DISTANCE_TO_SUBWAY_KEY.equals(rentSearch.getOrderBy());
        /**
         * 查询条件
         */
        houseQueryWrapper.eq("status", HouseStatus.PASSES.getValue())
                .eq("city_en_name", rentSearch.getCityEnName())
                //有地铁
                .gt(isFlag, HouseSort.DISTANCE_TO_SUBWAY_KEY, -1);
        //计算当前页
        int current = (rentSearch.getStart() / rentSearch.getSize()) + 1;

        Page<House> housePage = new Page<>(current, rentSearch.getSize());

        //分页查询
        IPage<House> houseIPage = houseMapper.selectPage(housePage, houseQueryWrapper);
        List<HouseDTO> houseDTOS = new ArrayList<>();

        List<Integer> houseIds = new ArrayList<>();
        Map<Integer, HouseDTO> idToHouseMap = Maps.newHashMap();
        /**
         * 整理主表数据
         */
        houseIPage.getRecords().forEach(house -> {
            HouseDTO houseDTO = modelMapper.map(house, HouseDTO.class);
            houseDTO.setCover(this.cdnPrefix + house.getCover());
            houseDTOS.add(houseDTO);
            houseIds.add(house.getId());
            idToHouseMap.put(house.getId(), houseDTO);
        });


        wrapperHouseList(houseIds, idToHouseMap);
        return new ServiceMultiResult<HouseDTO>(houseIPage.getTotal(), houseDTOS);
    }

    /**
     * 整理返回的HouseDto信息
     *
     * @param houseIds
     * @return
     */
    private List<HouseDTO> wrapperHouseResult(List<Integer> houseIds) {
        List<HouseDTO> result = new ArrayList<>();

        Map<Integer, HouseDTO> idToHouseMap = new HashMap<>();
        QueryWrapper<House> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", houseIds);
        //查询主表信息
        List<House> houses = houseMapper.selectList(queryWrapper);
        houses.forEach(house -> {
            HouseDTO houseDTO = modelMapper.map(house, HouseDTO.class);
            houseDTO.setCover(this.cdnPrefix + house.getCover());
            idToHouseMap.put(house.getId(), houseDTO);
        });

        wrapperHouseList(houseIds, idToHouseMap);

        // 矫正顺序
        for (Integer houseId : houseIds) {
            result.add(idToHouseMap.get(houseId));
        }
        return result;
    }


    /**
     * 渲染详细信息 及 标签
     *
     * @param houseIds
     * @param idToHouseMap
     */
    private void wrapperHouseList(List<Integer> houseIds, Map<Integer, HouseDTO> idToHouseMap) {

        List<HouseDetail> details = houseDetailService.findAllByHouseIdIn(houseIds);
        /**
         * 整理详细数据
         */
        details.forEach(houseDetail -> {
            HouseDTO houseDTO = idToHouseMap.get(houseDetail.getHouseId());
            HouseDetailDTO houseDetailDTO = modelMapper.map(houseDetail, HouseDetailDTO.class);
            houseDTO.setHouseDetail(houseDetailDTO);

        });

        /**
         * 拼接房源标签信息
         */
        QueryWrapper<HouseTag> houseTagQueryWrapper = new QueryWrapper<>();
        houseTagQueryWrapper.in("house_id", houseIds);
        List<HouseTag> houseTags = houseTagMapper.selectList(houseTagQueryWrapper);

        houseTags.forEach(houseTag -> {
            HouseDTO house = idToHouseMap.get(houseTag.getHouseId());
            house.getTags().add(houseTag.getName());
        });
    }


    /**
     * 房源详细信息对象填充
     *
     * @param houseDetail
     * @param houseForm
     * @return
     */
    private ServiceResult<HouseDTO> wrapperDetailInfo(HouseDetail houseDetail, HouseForm houseForm) {
        //查询地铁
        Subway subway = subwayMapper.selectById(houseForm.getSubwayLineId());
        if (subway == null) {
            return new ServiceResult<>(false, "Not valid subway line!");
        }
        //查询地铁站
        SubwayStation subwayStation = subwayStationMapper.selectById(houseForm.getSubwayStationId());
        if (subwayStation == null || !subway.getId().equals(subwayStation.getSubwayId())) {
            return new ServiceResult<>(false, "Not valid subway station!");
        }

        houseDetail.setSubwayLineId(subway.getId());
        houseDetail.setSubwayLineName(subway.getName());

        houseDetail.setSubwayStationId(subwayStation.getId());
        houseDetail.setSubwayStationName(subwayStation.getName());

        houseDetail.setDescription(houseForm.getDescription());
        houseDetail.setAddress(houseForm.getDetailAddress());
        houseDetail.setLayoutDesc(houseForm.getLayoutDesc());
        houseDetail.setRentWay(houseForm.getRentWay());
        houseDetail.setRoundService(houseForm.getRoundService());
        houseDetail.setTraffic(houseForm.getTraffic());
        return null;

    }


    /**
     * 图片对象列表信息填充
     *
     * @param form
     * @param houseId
     * @return
     */
    private List<HousePicture> generatePictures(HouseForm form, Integer houseId) {
        List<HousePicture> pictures = new ArrayList<>();
        if (form.getPhotos() == null || form.getPhotos().isEmpty()) {
            return pictures;
        }

        for (PhotoForm photoForm : form.getPhotos()) {
            HousePicture picture = new HousePicture();
            picture.setHouseId(houseId);
            picture.setCdnPrefix(cdnPrefix);
            picture.setPath(photoForm.getPath());
            picture.setWidth(photoForm.getWidth());
            picture.setHeight(photoForm.getHeight());
            pictures.add(picture);
        }
        return pictures;
    }
}
