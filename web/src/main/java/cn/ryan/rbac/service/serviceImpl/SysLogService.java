package cn.ryan.rbac.service.serviceImpl;

import cn.ryan.rbac.entity.SysLog;
import cn.ryan.rbac.exeception.BusinessException;
import cn.ryan.rbac.service.ISysLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统日志service
 * @author ryan
 * @create 2019-04-12 16:57
 **/

@Service
@Transactional
public class SysLogService extends BaseService<SysLog> implements ISysLogService {

    @Override
    public void a(SysLog sysLog){
        baseMapper.save(sysLog);
        throw new BusinessException("1");
    }
}
