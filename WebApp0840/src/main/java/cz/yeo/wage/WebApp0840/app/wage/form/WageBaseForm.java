package cz.yeo.wage.WebApp0840.app.wage.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class WageBaseForm {
    @NotEmpty(message="근무지명은 필수 항목입니다.")
    private String workPlaceName;

    @NotNull(message="기본시급은 필수 항목입니다.")
    private Integer baseWage;

    @NotNull(message="연차는 필수 항목입니다.")
    private Integer annual;

    @NotNull(message="월급일은 필수 항목입니다.")
    @Min(1)
    @Max(31)
    private Integer payday;

    @NotEmpty(message="근무시작일은 필수 항목입니다.")
    private String workStartDate;
}
