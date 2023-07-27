package com.mysite.PP.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/***
 * 
 * @author tpdlf
 * @summary 입력값 검증을 위한 form 클래스. 전달한 입력값 바인딩시에도 사용
 */
@Getter
@Setter
public class QuestionForm {
    @NotEmpty(message="제목은 필수항목입니다.")
    @Size(max=200)
    private String subject;

    @NotEmpty(message="내용은 필수항목입니다.")
    private String content;
}