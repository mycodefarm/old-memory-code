package com.jimo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jimo.model.Men;

public interface MenRepository extends JpaRepository<Men, Integer> {

	public List<Men> getAllMenByAge(Integer age);
}
