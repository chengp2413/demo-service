package com.example.demo.infrastructure.persistence.repoimpl;

import com.example.demo.domain.po.DemoComCzlsbPO;
import com.example.demo.infrastructure.persistence.mapper.DemoComCzlsbMapper;
import com.example.demo.domain.repository.DemoComCzlsbRepo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 操作流水表
 * DEMO_COM_CZLSB 服务实现类
 *
 * @author chengp
 * @date 2022-04-30
 */
@Service
public class DemoComCzlsbRepoImpl extends ServiceImpl<DemoComCzlsbMapper, DemoComCzlsbPO> implements DemoComCzlsbRepo {

}
