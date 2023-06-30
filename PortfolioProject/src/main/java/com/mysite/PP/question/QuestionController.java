package com.mysite.PP.question;

import com.mysite.PP.answer.AnswerForm;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

// @RequestMapping를 사용하면 이 클래스의 모든 매핑은 앞에 자동으로 ("/question")이 들어감
@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {
	
	private final QuestionService questionService;
	
    @GetMapping("/list")
    public String list(Model model) { 
    	// model 객체는 따로 생성할 필요없이 매개변수로 지정해두면
    	// 스프링부트가 자동으로 Model 객체를 생성함
    	
    	List<Question> questionList = this.questionService.getList();
    	model.addAttribute("questionList", questionList);
    	
    	// "question_list" 는 question_list.html 파일을 의미함
    	// question_list == question/list 로 할당됨
        return "question_list";
    }
    
    // id 값을 얻기위해 @PathVariable 에너테이션 사용함.
    // @GetMapping 에서 value 변수는 생략 가능
    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
    	Question question = this.questionService.getQuestion(id);
    	model.addAttribute("question", question);
    	return "question_detail";
    }
    
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }
    
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
    	// BindingResult 매개변수는 항상 @Valid 매개변수 바로 뒤에 위치해야 한다. 만약 2개의 매개변수의 위치가 정확하지 않다면 @Valid만 적용이 되어 입력값 검증 실패 시 400 오류가 발생한다.
    	if (bindingResult.hasErrors()) {
            return "question_form";
        }
    	this.questionService.create(questionForm.getSubject(), questionForm.getContent());
    	return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
    }
}