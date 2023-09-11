package com.mysite.PP.Coment;

import java.time.LocalDateTime;
import java.util.Set;

import com.mysite.PP.answer.Answer;
import com.mysite.PP.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDateTime createDateTime;
	
	private LocalDateTime modifyDate;
	
	@ManyToOne
	private SiteUser author;
	
	@ManyToOne
	private Answer answer;
	
	@ManyToMany
	Set<SiteUser> voter;
}
