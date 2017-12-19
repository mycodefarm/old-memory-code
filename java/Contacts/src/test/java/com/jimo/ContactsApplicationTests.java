package com.jimo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

import com.jimo.dao.ContactDao;
import com.jimo.dao.UserDao;
import com.jimo.model.Contact;
import com.jimo.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactsApplicationTests {

	@Autowired
	private ContactDao cd;
	@Autowired
	private UserDao ud;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testUD() {
		// System.out.println(ud.findOne((long) 3).getContacts().size());
	}

	/**
	 * { "content":[ {"id":123,"title":"blog122","content":"this is blog
	 * content"}, {"id":122,"title":"blog121","content":"this is blog content"}
	 * ], "last":false, "totalPages":9, "totalElements":123, "size":15,
	 * "number":0, "first":true, "sort":[{ "direction":"DESC", "property":"id",
	 * "ignoreCase":false, "nullHandling":"NATIVE", "ascending":false }],
	 * "numberOfElements":15 }
	 */
	@Test
	public void testCD() {
		// Pageable pa = new PageRequest(0, 5, Direction.DESC, "id");
		// Page<Contact> p = cd.findAll(pa);
		// Page<Contact> p = cd.findAllByNameOrNamepinyin("jm", "jm", pa);
		// Page<Contact> p = cd.findAllByName("寂寞", pa);
		// User u = new User();
		// u.setId((long) 3);
		// Page<Contact> p = cd.findAllByUser(u, pa);
		// Page<Contact> p = cd.findAllByNameOrNamepinyinAndUser("小明", "小明", u,
		// pa);
		// Page<Contact> p = cd.findAllByNameOrPinyin("小明", "小明", u, pa);
		// System.out.println(p.getSize() + "," + p.getNumberOfElements() + ","
		// + p.getTotalPages() + ","
		// + p.getContent().size() + "," + p.getNumber());

		// Contact c = cd.findOne((long) 1);
		// System.out.println(c.getUser().getContacts().size());

		// Contact c = new Contact();
		// c.setAddress("成都");
		// c.setEmail("123@qq.com");
		// c.setGender("1");
		// c.setName("小明");
		// c.setNamepinyin("xm");
		// c.setPhone("12345678900");
		User u = new User();
		u.setId((long) 3);
		// c.setUser(u);

		System.out.println(cd.findOneByNameAndPhoneAndUser("寂寞", "1232", u).size());
		// System.out.println(cd.deleteByIdAndUser((long)3, u));
		// System.out.println(cd.save(c));
	}
}
