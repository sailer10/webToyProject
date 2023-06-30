package com.mysite.PP.question;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import com.mysite.PP.DataNotFoundException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
/***
 * @author tpdlf
 *	설명: Service 객체는 모듈화, 보안을 위해 사용한다. 컨트롤러에서 직접 리포지터리를
 *		호출하지 않고 service 를 중간에 두어 사용함.
 */
@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getList() {
        return this.questionRepository.findAll();
    }
    
    public Question getQuestion(Integer id) {
    	Optional<Question> question = this.questionRepository.findById(id);
    	if (question.isPresent())
    		return question.get();
    	else
    		throw new DataNotFoundException("question not found");
    }
    
    public void create(String subject, String content) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q);
    }
}