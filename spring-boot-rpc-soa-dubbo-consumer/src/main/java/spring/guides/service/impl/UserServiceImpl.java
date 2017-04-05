package spring.guides.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import spring.guides.service.DemoService;
import spring.guides.service.UserService;

/**
 * 类概述。
 *
 * @author dannong
 * @since 2017年04月05日 22:35
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource(name = "demoService")
    private DemoService demoService;

    @Override
    public String sayHello(String name) {
        return demoService.sayHello(name);
    }

}
