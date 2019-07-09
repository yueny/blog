/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.modules.service.impl;

import com.mtons.mblog.bo.UserSecurityBO;
import com.mtons.mblog.dao.repository.UserRepository;
import com.mtons.mblog.service.atom.bao.IUserSecurityService;
import com.mtons.mblog.service.comp.IPasswdService;
import com.mtons.mblog.bo.OpenOauthVO;
import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.entity.jpa.UserOauth;
import com.mtons.mblog.entity.jpa.User;
import com.mtons.mblog.modules.service.OpenOauthService;
import com.mtons.mblog.service.util.BeanMapUtils;
import com.mtons.mblog.dao.repository.UserOauthRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 第三方登录授权管理
 * @author langhsu on 2015/8/12.
 */
@Service
@Transactional
public class OpenOauthServiceImpl implements OpenOauthService {
    @Autowired
    private UserOauthRepository userOauthRepository;
    @Autowired
    private UserRepository userMapper;
    @Autowired
    private IPasswdService passwdService;
    @Autowired
    private IUserSecurityService userSecurityService;

    @Override
    public UserBO getUserByOauthToken(String oauth_token) {
        UserOauth thirdToken = userOauthRepository.findByAccessToken(oauth_token);
        Optional<User> po = userMapper.findById(thirdToken.getId());
        return BeanMapUtils.copy(po.get());
    }

    @Override
    public OpenOauthVO getOauthByToken(String oauth_token) {
        UserOauth po = userOauthRepository.findByAccessToken(oauth_token);
        OpenOauthVO vo = null;
        if (po != null) {
            vo = new OpenOauthVO();
            BeanUtils.copyProperties(po, vo);
        }
        return vo;
    }

    @Override
    public OpenOauthVO getOauthByUid(long userId) {
        UserOauth po = userOauthRepository.findByUserId(userId);
        OpenOauthVO vo = null;
        if (po != null) {
            vo = new OpenOauthVO();
            BeanUtils.copyProperties(po, vo);
        }
        return vo;
    }

    @Override
    public boolean checkIsOriginalPassword(long userId) {
        UserOauth po = userOauthRepository.findByUserId(userId);
        if (po != null) {
            Optional<User> optional = userMapper.findById(userId);

            // 判断用户密码 和 登录状态
            if (optional.isPresent()) {
                User user = optional.get();
                UserSecurityBO us = userSecurityService.getByUid(user.getUid());
                String pwd = passwdService.encode(po.getAccessToken(), us.getSalt());

                if (pwd.equals(user.getPassword())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void saveOauthToken(OpenOauthVO oauth) {
        UserOauth po = new UserOauth();
        BeanUtils.copyProperties(oauth, po);
        userOauthRepository.save(po);
    }

	@Override
	public OpenOauthVO getOauthByOauthUserId(String oauthUserId) {
		UserOauth po = userOauthRepository.findByOauthUserId(oauthUserId);
        OpenOauthVO vo = null;
        if (po != null) {
            vo = new OpenOauthVO();
            BeanUtils.copyProperties(po, vo);
        }
        return vo;
	}

}
