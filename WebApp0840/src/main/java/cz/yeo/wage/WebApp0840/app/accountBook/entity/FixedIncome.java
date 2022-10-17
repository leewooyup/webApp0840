package cz.yeo.wage.WebApp0840.app.accountBook.entity;

import cz.yeo.wage.WebApp0840.app.base.entity.BaseEntity;
import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@ToString(callSuper = true)
public class FixedIncome extends BaseEntity {
    @ManyToOne
    private SiteUser siteUser;
    private String fixedIncomeType;
    private Integer fixedIncome;
    private String fixedIncomeMemo;
}
