package com.mtons.mblog.web.controller.admin;

import com.mtons.mblog.bo.PermissionTree;
import com.mtons.mblog.service.atom.jpa.PermissionService;
import com.mtons.mblog.web.controller.BaseBizController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author - langhsu
 * @create - 2018/5/18
 */
@RestController
@RequestMapping("/admin/permission")
public class PermissionController extends BaseBizController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/tree")
    public List<PermissionTree> tree() {
        return permissionService.tree();
    }
}
