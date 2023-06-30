package com.mysite.PP;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.PP.answer.Answer;
import com.mysite.PP.answer.AnswerRepository;
import com.mysite.PP.question.Question;
import com.mysite.PP.question.QuestionRepository;

@SpringBootTest
class PortfolioProjectApplicationTests {

	// 스프링의 DI(Dependency Injection) 기능. 스프링이 객체를 대신 생성해줌
    @Autowired
    private QuestionRepository questionRepository;
	
    @Autowired
    private AnswerRepository answerRepository;
    
    @Transactional
	@Test
	void testJpa() {
		/*
		Optional<Answer> oq = this.answerRepository.findById(1);
		assertTrue(oq.isPresent());
		Answer a = oq.get();
		assertEquals(2, a.getQuestion().getId());
		*/
		
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q  = oq.get();
		
		List<Answer> answerList = q.getAnswerList();
		
		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 실행됩니다.", answerList.get(0).getContent());
	}

}
