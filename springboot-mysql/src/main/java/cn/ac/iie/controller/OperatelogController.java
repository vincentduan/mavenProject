package cn.ac.iie.controller;

import cn.ac.iie.service.OperatelogService;
import cn.ac.iie.utils.ResponseVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author vincent
 * @time 2019-08-03 15:52
 */
@RestController
@RequestMapping("operatelog")
public class OperatelogController {

    @Autowired
    OperatelogService operatelogService;

    @GetMapping("insert")
    public String insert(@RequestParam("id") int id) {
//        operatelogService.addBatch();
        operatelogService.queryOperateDesc();
        return "SUCCESS";
    }

    // 得到所有操作类型
    @GetMapping("getDirections")
    public Object getDirections() {
        List<String> strings = operatelogService.queryOperateDesc();
        return ResponseVOUtil.success(strings);
    }

    // 根据操作类型，得到所有用户ID
    @GetMapping("direction/userIDs")
    public Object getUserIdByDirection(@RequestParam("direction") String direction) {
        List<String> userIdByDirection = operatelogService.getUserIdByDirection(direction);
        return ResponseVOUtil.success(userIdByDirection);
    }

}
