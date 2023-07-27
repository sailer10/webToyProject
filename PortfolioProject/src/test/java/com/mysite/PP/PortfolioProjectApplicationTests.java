package com.mysite.PP;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.PP.question.QuestionService;

@SpringBootTest
class PortfolioProjectApplicationTests {

	// 스프링의 DI(Dependency Injection) 기능. 스프링이 객체를 대신 생성해줌    
    @Autowired
    private QuestionService questionService;
    
	@Test
	void testJpa() {
		/*
		Optional<Answer> oq = this.answerRepository.findById(1);
		assertTrue(oq.isPresent());
		Answer a = oq.get();
		assertEquals(2, a.getQuestion().getId());
		*/
		/*
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q  = oq.get();
		
		List<Answer> answerList = q.getAnswerList();
		
		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 실행됩니다.", answerList.get(0).getContent());
		*/
		for (int i = 1; i <= 300; i++) {
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "내용이 음슴";
            this.questionService.create(subject, content, null);
        }
    	
	}

}
