package com.mysite.PP.question;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.time.LocalDateTime;

import com.mysite.PP.user.SiteUser;
import com.mysite.PP.DataNotFoundException;

import org.springframework.stereotype.Service;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;
/***
 * @author tpdlf
 * @summary @Service 객체는 모듈화, 보안을 위해 사용한다. 컨트롤러에서 직접 리포지터리를 호출하지 않고 service 를 중간에 두어 사용함.
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
    
    public void create(String subject, String content, SiteUser user) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);
        this.questionRepository.save(q);
    }
    
    
    // 페이징을 위한 리스트 호출 메소드
    public Page<Question> getList(int page) {
    	// 최신 날짜순대로 보기위해 리스트 생성
    	List<Sort.Order> sorts = new ArrayList<>();
    	sorts.add(Sort.Order.desc("createDate"));
    	// 이렇게 하면 해당 페이지의 데이터만 조회하도록 쿼리가 변경됨. 게시물 역숙조회를 위해 PageRequest.of의 3번째 파라미터로 Sort 객체를 전달함
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAll(pageable);
    }
    
    public void modify(Question question, String subject, String content) {
    	question.setSubject(subject);
    	question.setContent(content);
    	question.setModifyDate(LocalDateTime.now());
    	this.questionRepository.save(question);
    }
    
    public void delete(Question question) {
    	this.questionRepository.delete(question);
    }
    
    public void vote(Question question, SiteUser siteUser) {
    	question.getVoter().add(siteUser);
    	this.questionRepository.save(question);
    }
}