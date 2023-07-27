package com.mysite.PP.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.mysite.PP.answer.Answer;
import com.mysite.PP.user.SiteUser;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // 엔티티에는 일반적으로 Setter 메서드 사용 비권장. 롬복 @Builder 에너테이션 사용이 권장됨
@Entity
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 200)
	private String subject;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDateTime createDate;
	
	// mappedBy는 참조 엔티티의 속성명을 의미. 
	// Answer 엔티티에서 Question 엔티티를 참조한 속성명 question을 mappedBy에 전달해야 한다.
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList;
	
	@ManyToOne
	private SiteUser author;

    private LocalDateTime modifyDate;
    
    @ManyToMany
    Set<SiteUser> voter;
}
