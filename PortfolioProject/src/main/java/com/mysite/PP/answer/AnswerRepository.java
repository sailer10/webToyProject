package com.mysite.PP.answer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.PP.question.Question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
	Page<Answer> findAllByQuestion(Question question, Pageable pageable);
	// 답변 페이징을 위한 메소드
}
