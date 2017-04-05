package spring.guides.service.impl;

import org.springframework.stereotype.Service;

import spring.guides.service.DemoService;

/**
 * 演示服务实现。
 *
 * @author dannong
 * @since 2017年04月05日 11:45
 */
@Service("demoService")
public class DemoServiceImpl implements DemoService {

    private static final String TEMPLATE = "Hello, %s!";

    @Override
    public String sayHello(String name) {
        return String.format(TEMPLATE, name);
    }

}
