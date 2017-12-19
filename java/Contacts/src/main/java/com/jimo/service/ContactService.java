package com.jimo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jimo.dao.ContactDao;
import com.jimo.model.Contact;
import com.jimo.model.User;
import com.jimo.util.ChineseToPinyin;
import com.jimo.util.Result;
import com.jimo.util.ResultHelper;

@Service
public class ContactService {
	@Autowired
	private ContactDao cd;

	public Result getPageContacts(String name, Integer page, User user) {
		Pageable pa = new PageRequest(page, 5, Direction.DESC, "id");
		Page<Contact> p = null;
		if (name == null || "".equals(name)) {
			p = cd.findAllByUser(user, pa);
		} else {
			p = cd.findAllByNameOrPinyin(name, name, user, pa);// 有查询条件
		}
		return ResultHelper.success(p);
	}

	public Result addContact(Contact c, boolean add) {
		List<Contact> cc = cd.findOneByNameAndPhoneAndUser(c.getName(), c.getPhone(), c.getUser());
		if (add) {
			if (cc.size() > 0) {
				return ResultHelper.error("不能添加，已存在该联系人(姓名和手机号相同)");
			}
		} else {
			for (Contact ca : cc) {
				if (ca.getId() != c.getId()) {
					return ResultHelper.error("不能修改，已存在该联系人(姓名和手机号相同)");
				}
			}
		}
		c.setNamepinyin(ChineseToPinyin.getFirstLettersLo(c.getName()));// 将中文名字转为首字母拼音
		cd.save(c);
		return ResultHelper.success();
		// return ResultHelper.error("保存失败");
	}

	public Result delContact(Long id, User user) {
		int re = cd.deleteByIdAndUser(id, user);
		if (re > 0) {
			return ResultHelper.success();
		} else {
			return ResultHelper.error("删除失败");
		}
	}
}
