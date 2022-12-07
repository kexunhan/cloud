package cn.kolmap;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kxhan
 * @version 1.0
 * @date 2022/12/7 23:14
 */
@RestController
@Api(tags = "测试")
public class TestController {

    @GetMapping("/hello")
    @ApiOperation("查询测试")
    public String testHello(){
        return "hello world";
    }
}
