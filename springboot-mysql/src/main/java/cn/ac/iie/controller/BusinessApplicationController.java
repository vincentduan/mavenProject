package cn.ac.iie.controller;

import cn.ac.iie.service.BusinessApplicationService;
import cn.ac.iie.service.UserPriService;
import cn.ac.iie.utils.ResponseVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vincent
 * @time 2019-08-06 17:41
 */
@RestController
@RequestMapping("businessApplication")
public class BusinessApplicationController {

    @Autowired
    BusinessApplicationService businessApplicationService;
    @Autowired
    UserPriService userPriService;

    // 插入数据
    @GetMapping("batch")
    public Object batch() {
        businessApplicationService.batch();
        return ResponseVOUtil.success("SUCCESS");
    }

    // 根据业务方向的到userID
    @GetMapping("getUserByDirection/{direction}")
    public Object getUserByDirection(@PathVariable("direction") String direction) {
        List<String> userByDirection = businessApplicationService.getUserByDirection(direction);
        return ResponseVOUtil.success(userByDirection);
    }

    // 根据组织架构得到UserId
    @GetMapping("getUserByOrgName/{orgName}")
    public Object getUserByOrgName(@PathVariable("orgName") String orgName) {
        List<String> userByOrgName = businessApplicationService.getUserByOrgName(orgName);
        return ResponseVOUtil.success(userByOrgName);
    }

    // 根据职务得到UserId
    @GetMapping("getUserByDutyDesc/{dutyDesc}")
    public Object getUserByDutyDesc(@PathVariable("dutyDesc") String dutyDesc) {
        List<String> userByOrgName = businessApplicationService.getUserByDutyDesc(dutyDesc);
        return ResponseVOUtil.success(userByOrgName);
    }

    // 根据业务方向的到所有权限ID
    @GetMapping("{direction}/priIDs")
    public Object getPriIdByDirection(@PathVariable("direction") String direction) {
        List<String> userByDirection = businessApplicationService.getUserByDirection(direction);
        Map<String, Integer> result = new HashMap<>();
        for (String userId : userByDirection) {
            List<String> priByUserId = userPriService.getPriByUserId(userId);
            for (String pri : priByUserId) {
                result.put(pri, result.get(pri) == null ? 1 : result.get(pri) + 1);
            }
        }

        return ResponseVOUtil.success(result);
    }

}
