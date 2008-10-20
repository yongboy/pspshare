package org.gameye.psp.image.action;

import org.apache.commons.lang.StringUtils;
import org.gameye.psp.image.action.base.BaseActionSupport;
import org.gameye.psp.image.config.Constants;
import org.gameye.psp.image.entity.User;
import org.gameye.psp.image.service.IUserService;
import org.gameye.psp.image.utils.MD5;
import org.gameye.psp.image.utils.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;

public class CheckLoginAction extends BaseActionSupport {

	private static final long serialVersionUID = 7448517823423642295L;

	@Autowired
	private IUserService userService;

	@Override
	public String execute() {
		if (user == null) {
			error = "传输对象有误！";
			return INPUT;
		}
		if (StringUtils.isEmpty(user.getId())) {
			error = "用户名不能为空！";
			return INPUT;
		}
		if (!StringHelper.VerifyUserName(user.getId())) {
			error = "用户名输入格式有误！";
			return INPUT;
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			error = "用户密码不能为空！";
			return INPUT;
		}

		User oldUser = userService.getById(user.getId());
		if (oldUser == null || !oldUser.getId().equals(user.getId())) {
			error = "用户名输入有误！";
			return INPUT;
		}

		if (!oldUser.getPassword()
				.equals(new MD5(user.getPassword()).compute())) {
			error = "用户密码输入有误！";
			return INPUT;
		}
		// 把User信息保存进Session中
		getServletRequest().getSession().setAttribute("user", oldUser);

		return SUCCESS;
	}

	private static int minInt = 3;
	private static int maxInt = 20;

	public String Register() {

		if (user == null) {
			error = "传输对象有误！";
			return INPUT;
		}
		if (StringUtils.isEmpty(user.getId())) {
			error = "用户名不能为空！";
			return INPUT;
		}
		if (!StringHelper.VerifyUserName(user.getId())) {
			error = "用户名输入格式有误！";
			return INPUT;
		}

		if (user.getId().length() < minInt || user.getId().length() > maxInt) {
			error = "用户名长度有误！";
			return INPUT;
		}

		if (Constants.forbiddenWords.contains(user.getId())) {
			error = "当前用户名已经存在！";
			return INPUT;
		}

		if (StringUtils.isEmpty(user.getPassword())) {
			error = "用户密码不能为空！";
			return INPUT;
		}

		if (StringUtils.isEmpty(password2)) {
			error = "确认密码不能为空！";
			return INPUT;
		}

		if (!user.getPassword().equals(password2)) {
			error = "您的两次密码输入不一致！";
			return INPUT;
		}

		// 检测用户邮箱

		if (StringUtils.isEmpty(user.getMail())) {
			error = "电子邮箱不能为空！";
			return INPUT;
		}
		if (!StringHelper.VerifyEmail(user.getMail())) {
			error = "电子邮箱输入不合法！";
			return INPUT;
		}

		User oldUser = userService.getById(user.getId());
		if (oldUser != null) {
			error = "当前用户名已经存在！";
			return INPUT;
		}

		userService.add(user);

		return SUCCESS;
	}

	private User user;
	private String error;
	private String password2;

	public String getError() {
		return error;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

}
