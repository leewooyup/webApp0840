package cz.yeo.wage.WebApp0840.app.user.work.entity;

import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@ToString(callSuper = true)
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private SiteUser siteUser;

    @Column
    private Date workingDate;

    @Column
    private Integer workingHours;

    @Column
    private Integer workingMinutes;

    @Column
    @ColumnDefault("0")
    private Integer extendedHours;

    @Column
    @ColumnDefault("0")
    private Integer extendedMinutes;
}
