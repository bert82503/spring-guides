package spring.guides.dubbo.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import spring.guides.dubbo.model.UserVO;
import spring.guides.dubbo.service.TryService;

/**
 * 尝试服务实现。
 *
 * @author dannong
 * @since 2017年10月09日
 */
@Service("tryService")
public class TryServiceXmlImpl implements TryService {

  private static final Logger logger = LoggerFactory.getLogger(TryServiceXmlImpl.class);

  @Override
  public int multi(int x, int y) {
    return x * y;
  }

  @Override
  public int update(List<UserVO> userList) {
    if (CollectionUtils.isEmpty(userList)) {
      return 0;
    }

    logger.info("userList: {}", userList);
    return userList.size();
  }

}
