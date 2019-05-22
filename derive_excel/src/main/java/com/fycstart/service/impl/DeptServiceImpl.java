package com.fycstart.service.impl;

import com.fycstart.entity.Dept;
import com.fycstart.mapper.DeptMapper;
import com.fycstart.service.DeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fycstart
 * @since 2019-05-21
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

}
