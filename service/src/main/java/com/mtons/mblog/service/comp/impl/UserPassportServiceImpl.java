package com.mtons.mblog.service.comp.impl;

import com.mtons.mblog.base.enums.ErrorType;
import com.mtons.mblog.service.comp.IPasswdService;
import com.mtons.mblog.service.comp.IUserPassportService;
import com.mtons.mblog.entity.User;
import com.mtons.mblog.dao.repository.UserRepository;
import com.yueny.rapid.lang.exception.invalid.InvalidException;
import com.yueny.rapid.lang.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private UserRepository userRepository;

	@Override
	public boolean getMatch(final String uid, final String password) throws InvalidException {
		User po = userRepository.findByUid(uid);
		if(po == null){
			throw new InvalidException(ErrorType.USER_NOT_EXIST1);
		}

		final String ps = po.getPassword();
		if (StringUtil.isEmpty(ps)) {
			return false;
		}

		if (!StringUtils.equals(ps, passwdService.encode(password, ""))) {
			// 当前密码不正确
			throw new InvalidException(ErrorType.USER_OR_PASS_ERROR);
		}

		return true;
	}

	@Override
	public boolean modifyPassPort(final String uid, final String oldPassport, final String resetPwd) throws InvalidException{
		if(getMatch(uid, oldPassport)){
			return passwdService.changePassword(uid, resetPwd);
		}

		return false;
	}

}
