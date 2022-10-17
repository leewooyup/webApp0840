package cz.yeo.wage.WebApp0840.app.accountBook.entity;

import cz.yeo.wage.WebApp0840.app.base.entity.BaseEntity;
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
public class DailyPatternItemPrice extends BaseEntity {
    @ManyToOne
    private DailyPattern dailyPattern;
    private Integer dailyConsumptionPrice;
}
