package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
	List<Tag> findTagsByTasksId(Long tutorialId); // returns all Tags related to Tutorial
}
