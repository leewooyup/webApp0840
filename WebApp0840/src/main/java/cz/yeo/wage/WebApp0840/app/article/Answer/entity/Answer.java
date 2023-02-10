package cz.yeo.wage.WebApp0840.app.article.Answer.entity;

import cz.yeo.wage.WebApp0840.app.article.entity.Article;
import cz.yeo.wage.WebApp0840.app.base.entity.BaseEntity;
import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@ToString(callSuper = true)
public class Answer extends BaseEntity {
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private Article article;
}
