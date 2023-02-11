package cz.yeo.wage.WebApp0840.app.article.entity;

import cz.yeo.wage.WebApp0840.app.article.Answer.entity.Answer;
import cz.yeo.wage.WebApp0840.app.base.entity.BaseEntity;
import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@ToString(callSuper = true)
public class Article extends BaseEntity {
    @Column(length = 200)
    private String subject;

    @Column(length = 30)
    private String subSubject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String proposedImg;

    @ManyToOne
    private SiteUser author;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;
}
