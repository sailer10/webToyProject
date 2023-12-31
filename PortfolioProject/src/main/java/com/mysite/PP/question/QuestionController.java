package com.mysite.PP.question;

import java.security.Principal;

import com.mysite.PP.user.SiteUser;
import com.mysite.PP.user.UserService;
import com.mysite.PP.answer.Answer;
import com.mysite.PP.answer.AnswerForm;
import com.mysite.PP.answer.AnswerService;

import jakarta.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.data.domain.Page;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

// @RequestMapping를 사용하면 이 클래스의 모든 매핑은 앞에 자동으로 ("/question")이 들어감
@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

	private final QuestionService questionService;
	private final AnswerService answerService;
	private final UserService userService;

	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw) {
		// model 객체는 따로 생성할 필요없이 매개변수로 지정해두면 스프링부트가 자동으로 Model 객체를 생성함
		// 검색어가 없다면 모든 질문내용을 리턴
		Page<Question> paging = this.questionService.getList(page, kw);
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		// "question_list" 는 question_list.html 파일을 의미함
		return "question_list";
	}

//    @GetMapping(value = "/detail/{id}")
//    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm, 
//    		@RequestParam(value="page", defaultValue="0") int page) {
//    	Question question = this.questionService.getQuestion(id);
//    	Page<Answer> paging = this.answerService.getAnswer(id, page);
//    	model.addAttribute("question", question);
//    	model.addAttribute("paging", paging);
//    	return "question_detail";
//    }
	// id 값을 얻기위해 @PathVariable 에너테이션 사용함.
	// @GetMapping 에서 "value = "는 생략 가능
	// 댓글 페이징
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm,
			@RequestParam(value = "page", defaultValue = "0") int page) {
		// QuestionService의 getQuestion 메서드를 호출하여 Question객체를 템플릿에 전달
		Question question = this.questionService.getQuestion(id);
		Page<Answer> paging = this.answerService.getList(question, page);

		model.addAttribute("question", question);
		model.addAttribute("paging", paging);

		return "question_detail";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		return "question_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
		// @Valid 애너테이션을 적용하면 QuestionForm 의 @NotEmpty, @Size 응으로 설정한 검증기능이 작동한다.
		// BindingResult 매개변수는 항상 @Valid 매개변수 바로 뒤에 위치해야 한다. 만약 2개의 매개변수의 위치가 정확하지 않다면
		// @Valid만 적용이 되어 입력값 검증 실패 시 400 오류가 발생한다.

		if (bindingResult.hasErrors()) {
			return "question_form";
		}
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
		return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
		Question question = this.questionService.getQuestion(id);
		if (!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		return "question_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal,
			@PathVariable("id") Integer id) {
		if (bindingResult.hasErrors()) {
			return "question_form";
		}
		Question question = this.questionService.getQuestion(id);
		if (!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
		return String.format("redirect:/question/detail/%s", id);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		if (!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		}
		this.questionService.delete(question);
		return "redirect:/";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String questionVote(Principal principal, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.questionService.vote(question, siteUser);
		return String.format("redirect:/question/detail/%s", id);
	}
}