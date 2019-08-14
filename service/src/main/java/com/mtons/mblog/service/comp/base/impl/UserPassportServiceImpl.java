package com.mtons.mblog.service.comp.base.impl;

import com.mtons.mblog.base.enums.ErrorType;
import com.mtons.mblog.base.enums.NeedChangeType;
import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.bo.UserSecurityBO;
import com.mtons.mblog.service.atom.bao.IUserSecurityService;
import com.mtons.mblog.service.atom.bao.UserService;
import com.mtons.mblog.service.comp.base.IPasswdService;
import com.mtons.mblog.service.comp.base.IUserPassportService;
import com.yueny.rapid.lang.exception.invalid.InvalidException;
import com.yueny.rapid.lang.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月30日 下午5:02:37
 *
 */
@Service
public class UserPassportServiceImpl implements IUserPassportService {
	@Autowired
	private IPasswdService passwdService;
	@Autowired
	private IUserSecurityService userSecurityService;
	@Autowired
	private UserService userService;

	@Override
	public boolean getMatch(final String uid, final String password) throws InvalidException {
		UserBO po = userService.get(uid);
		if(po == null){
			throw new InvalidException(ErrorType.USER_NOT_EXIST1);
		}

		final String ps = po.getPassword();
		if (StringUtil.isEmpty(ps)) {
			return false;
		}

		UserSecurityBO bo = userSecurityService.getByUid(uid);

		if (!StringUtils.equals(ps, passwdService.encode(password, bo.getSalt()))) {
			// 当前密码不正确
			throw new InvalidException(ErrorType.USER_OR_PASS_ERROR);
		}

		return true;
	}

	@Override
	public boolean modifyPassPort(final String uid, final String oldPassport, final String resetPwd) throws InvalidException{
		if(getMatch(uid, oldPassport)){
			return changePassword(uid, resetPwd);
		}

		return false;
	}

	@Override
	@Transactional
	public boolean changePassword(String uid, String resetPwd) throws InvalidException {
		if (!org.springframework.util.StringUtils.hasLength(uid)) {
			String msg = String.format(ErrorType.INVALID_ERROR.getMessage(), "用户不能为空!");

			throw new InvalidException(ErrorType.INVALID_ERROR.getCode(), msg);
		}
		if (!org.springframework.util.StringUtils.hasLength(resetPwd)) {
			String msg = String.format(ErrorType.INVALID_ERROR.getMessage(), "密码不能为空!");

			throw new InvalidException(ErrorType.INVALID_ERROR.getCode(), msg);
		}

		UserBO po = userService.get(uid);
		if(po == null){
			throw new InvalidException(ErrorType.USER_NOT_EXIST1);
		}

		String salt = passwdService.getSalt();
		// 处理明文密码得到密文
		String pw = passwdService.encode(resetPwd, salt);

		userService.updatePassword(uid, pw);

		UserSecurityBO us = new UserSecurityBO();
		us.setNeedChangePw(NeedChangeType.NO);
		us.setSalt(salt);
		us.setUid(uid);
		return userSecurityService.save(us);
	}

}
