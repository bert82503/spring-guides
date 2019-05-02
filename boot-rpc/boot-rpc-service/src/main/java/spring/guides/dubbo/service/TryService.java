package spring.guides.dubbo.service;

import java.util.List;

import spring.guides.dubbo.model.UserVO;

/**
 * 尝试服务。
 *
 * @author dannong
 * @since 2017年10月09日
 */
public interface TryService {

  /**
   * 乘法运算。
   *
   * @param x 左乘数
   * @param y 右乘数
   * @return 运算结果
   */
  int multi(int x, int y);

  /**
   * 批量更新用户信息。
   *
   * @param userList 用户信息列表
   * @return 更新的用户数
   */
  int update(List<UserVO> userList);

}
