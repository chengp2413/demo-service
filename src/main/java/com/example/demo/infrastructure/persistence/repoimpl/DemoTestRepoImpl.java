package com.example.demo.infrastructure.persistence.repoimpl;

import com.example.demo.domain.po.DemoTestPO;
import com.example.demo.infrastructure.persistence.mapper.DemoTestMapper;
import com.example.demo.domain.repository.DemoTestRepo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Demo测试表
 * DEMO_TEST 服务实现类
 *
 * @author chengp
 * @date 2022-05-18
 */
@Service
public class DemoTestRepoImpl extends ServiceImpl<DemoTestMapper, DemoTestPO> implements DemoTestRepo {

}
