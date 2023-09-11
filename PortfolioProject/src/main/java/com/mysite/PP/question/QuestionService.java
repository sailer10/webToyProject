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

import com.mysite.PP.answer.Answer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/***
 * @author tpdlf
 * @summary @Service 객체는 모듈화, 보안을 위해 사용한다. 컨트롤러에서 직접 리포지터리를 호출하지 않고 service 를
 *          중간에 두어 사용함.
 */
@RequiredArgsConstructor
@Service
public class QuestionService {

	private final QuestionRepository questionRepository;
	private final int QUESTION_PAGING_NUM = 10;

	// 자료 검색을 위한 메소드
	private Specification<Question> search(String kw) {
		return new Specification<>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true);
				Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
				Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
				Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
				return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
						cb.like(q.get("content"), "%" + kw + "%"), // 내용
						cb.like(u1.get("username"), "%" + kw + "%"), // 질문 작성자
						cb.like(a.get("content"), "%" + kw + "%"), // 답변 내용
						cb.like(u2.get("username"), "%" + kw + "%")); // 답변 작성자
			}
		};
	}

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

	// 페이징을 위한 리스트 호출 메소드. 검색하여 찾거나 안하고 찾거나 이 메소드를 이용함
	public Page<Question> getList(int page, String kw) {
		// 최신 날짜순대로 보기위해 리스트 생성
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		// 이렇게 하면 해당 페이지의 데이터만 조회하도록 쿼리가 변경됨. 게시물 역순조회를 위해 PageRequest.of의 3번째 파라미터로
		// Sort 객체를 전달함
		Pageable pageable = PageRequest.of(page, QUESTION_PAGING_NUM, Sort.by(sorts));
		// 검색어를 의미하는 매개변수 kw 를 추가함
		Specification<Question> spec = search(kw);

		//
		// return this.questionRepository.findAll(spec, pageable);
		// 쿼리문을 직접 작성하여 사용한다면 다음과 같이 리턴해 줘야한다.
		return this.questionRepository.findAllByKeyword(kw, pageable);
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