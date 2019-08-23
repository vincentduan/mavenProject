package cn.ac.iie.controller;

import cn.ac.iie.service.OperatelogService;
import cn.ac.iie.service.UserPriService;
import cn.ac.iie.utils.ResponseVOUtil;
import cn.ac.iie.utils.SortUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author vincent
 * @time 2019-08-03 15:52
 */
@RestController
@RequestMapping("operatelog")
public class OperatelogController {

    @Autowired
    OperatelogService operatelogService;

    @Autowired
    UserPriService userPriService;

    @GetMapping("insert")
    public String insert(@RequestParam("id") int id) {
//        operatelogService.addBatch();
        operatelogService.queryOperateDesc();
        return "SUCCESS";
    }

    // 得到所有操作类型
    @GetMapping("getOperateDesc")
    public Object getDirections() {
        List<String> strings = operatelogService.queryOperateDesc();
        return ResponseVOUtil.success(strings);
    }

    // 根据操作类型，得到所有用户ID
    @GetMapping("getUserByOperateDesc/{operateDesc}")
    public Object getUserIdByOperateDesc(@PathVariable("operateDesc") String operateDesc) {
        List<String> userIdByDirection = operatelogService.getUserIdByOperateDesc(operateDesc);
        return ResponseVOUtil.success(userIdByDirection);
    }

    // 根据操作类型，得到所有权限ID
    @GetMapping("{operateDesc}/priIDs")
    public Object getPriIdByDirection(@PathVariable("operateDesc") String operateDesc) {
        List<String> userIdByDirection = operatelogService.getUserIdByOperateDesc(operateDesc);
        Map<String, Integer> result = new HashMap<>();
        for (String userId : userIdByDirection) {
            List<String> priByUserId = userPriService.getPriByUserId(userId);
            for (String pri : priByUserId) {
                result.put(pri, result.get(pri) == null ? 1 : result.get(pri) + 1);
            }
        }
        Stream<Map.Entry<String, Integer>> st = result.entrySet().stream();
        st.sorted(Comparator.comparing(e -> e.getValue())).forEach(e -> result.put(e.getKey(), e.getValue()));
        return ResponseVOUtil.success(result);
    }

}
