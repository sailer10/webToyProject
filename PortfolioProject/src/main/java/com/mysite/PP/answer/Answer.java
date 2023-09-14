package com.mysite.PP.answer;

import java.util.List;
import java.util.Set;

import java.time.LocalDateTime;

import com.mysite.PP.Coment.Comment;
import com.mysite.PP.question.Question;
import com.mysite.PP.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(columnDefinition = "TEXT")
	private String content;

	private LocalDateTime createDate;

	private LocalDateTime modifyDate;

	// question엔티티와 연결된 속성. DB에서 ForeignKey 관계
	@ManyToOne
	private Question question;

	@ManyToOne
	private SiteUser author;

	@ManyToMany
	Set<SiteUser> voter;

	@OneToMany(mappedBy = "answer", cascade = CascadeType.REMOVE)
	private List<Comment> comentList;
}