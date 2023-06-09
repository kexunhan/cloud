package cn.kolmap.controller;

import cn.kolmap.User;
import com.pig4cloud.plugin.excel.annotation.RequestExcel;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : kxhan
 * @create 2023/6/9 16:45
 */
@RestController
@RequestMapping
public class ExportController {

    @GetMapping("/export")
    @ResponseExcel
    public List<User> export (){
        User user = new User();
        user.setAge(11);
        user.setName("lisi");

        User user1 = new User();
        user1.setAge(33);
        user1.setName("wangwu");

        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user);

        return list;
    }

    @PostMapping("/import")
    public void importRole(@RequestExcel List<User> excelVOList, BindingResult bindingResult) {
        System.out.println(excelVOList);

        List<User> target = (List<User>)bindingResult.getTarget();
        System.out.println(target);
    }

}
