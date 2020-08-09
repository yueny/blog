package com.mtons.mblog.shiro;

import com.mtons.mblog.base.enums.StatusType;
import com.mtons.mblog.vo.AccountProfile;
import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.vo.RolePermissionVO;
import com.mtons.mblog.service.atom.bao.UserService;
import com.mtons.mblog.service.atom.bao.UserRoleService;
import com.mtons.mblog.service.manager.IAccountProfileService;
import com.mtons.mblog.service.manager.IUserManagerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AccountRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private IUserManagerService userManagerService;
    @Autowired
    private IAccountProfileService accountProfileService;

    public AccountRealm() {
        super(new AllowAllCredentialsMatcher());
        setAuthenticationTokenClass(UsernamePasswordToken.class);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        AccountProfile profile = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        if (profile != null) {
            UserBO user = userService.find(profile.getId());
            if (user != null) {
                // 获取用户对应角色所拥有的权限，然后将这些权限放到SimpleAuthorizationInfo的权限认证书中
                SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
                List<RolePermissionVO> roles = userManagerService.findListRolesByUserId(user.getId());

                //赋予角色
                roles.forEach(role -> {
                    authorizationInfo.addRole(role.getName());

                    //赋予权限
                    role.getPermissions().forEach(permission -> authorizationInfo.addStringPermission(permission.getName()));
                });
                return authorizationInfo;
            }
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        AccountProfile profile = getAccount(userManagerService, token);

        if (profile.getStatus() == StatusType.CLOSED.getValue()) {
            throw new LockedAccountException(profile.getName());
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(profile, token.getCredentials(), getName());
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("profile", profile);
        return info;
    }

    protected AccountProfile getAccount(IUserManagerService userManagerService, AuthenticationToken token) {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        UserBO userBO = userManagerService.getLogin(upToken.getUsername(), String.valueOf(upToken.getPassword()));

        return accountProfileService.find(userBO.getId());
    }
}
