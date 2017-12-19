package com.jimo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jimo.model.Contact;
import com.jimo.model.User;

@Repository
public interface ContactDao extends JpaRepository<Contact, Long> {

	@Query(value = "select c from Contact c where c.user=?3 and (c.name=?1 or c.namepinyin=?2)")
	public Page<Contact> findAllByNameOrPinyin(String name, String namepinyin, User user, Pageable pa);

	public Page<Contact> findAllByUser(User user, Pageable pa);

	public List<Contact> findOneByNameAndPhoneAndUser(String name, String phone, User user);

	@Transactional
	public int deleteByIdAndUser(Long id, User user);

}
