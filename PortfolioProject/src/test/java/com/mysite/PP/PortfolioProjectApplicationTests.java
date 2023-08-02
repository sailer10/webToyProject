package com.mysite.PP;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.PP.answer.AnswerService;
import com.mysite.PP.question.Question;
import com.mysite.PP.question.QuestionRepository;
import com.mysite.PP.question.QuestionService;
import com.mysite.PP.user.SiteUser;

@SpringBootTest
class PortfolioProjectApplicationTests {

	// 스프링의 DI(Dependency Injection) 기능. 스프링이 객체를 대신 생성해줌    
    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private AnswerService answerService;
    
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
		
//		for (int i = 1; i <= 300; i++) {
//            String subject = String.format("테스트 데이터입니다:[%03d]", i);
//            String content = "내용이 음슴";
//            this.questionService.create(subject, content, null);
//        }
    	
		Question question = this.questionService.getQuestion(613);
		System.out.println(question.getSubject());
		
		for (int i=1; i<=100; i++) {
			String content = String.format("페이징 테스트용 댓글입니다:[%03d]", i);
			SiteUser siteUser = question.getAuthor();
			this.answerService.create(question, content, siteUser);
		}
	}

}
