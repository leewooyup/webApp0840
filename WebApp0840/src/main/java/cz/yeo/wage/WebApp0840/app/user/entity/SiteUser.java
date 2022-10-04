package cz.yeo.wage.WebApp0840.app.user.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@ToString(callSuper = true)
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20, nullable = false, unique = true)
    private String username;

    @Column(length = 255, nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String userImgRelPath;

    @Column
    @ColumnDefault("9160")
    private Double baseWage;

    @Column
    @ColumnDefault("false")
    private boolean isRegistered;

    @Column
    @ColumnDefault("0")
    private Integer annual;

    @Column
    private Integer payday;

    @Column
    private Date workStartDate;
}
