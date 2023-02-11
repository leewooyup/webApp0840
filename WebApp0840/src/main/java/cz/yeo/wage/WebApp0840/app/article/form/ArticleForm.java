package cz.yeo.wage.WebApp0840.app.article.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ArticleForm {
    @Size(max=200)
    private String subject;
    private String subSubject;
    private String content;
}
