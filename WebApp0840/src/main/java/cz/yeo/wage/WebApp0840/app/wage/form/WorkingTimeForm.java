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
public class WorkingTimeForm {
    @NotEmpty(message="기본시급은 필수 항목입니다.")
    private String workingDate;

    @NotNull(message="근무시간(시)는 필수 항목입니다.")
    @Min(0)
    @Max(12)
    private Integer workingHours;

    @NotNull(message="근무시간(분)는 필수 항목입니다.")
    @Min(0)
    @Max(59)
    private Integer workingMinutes;

    @NotNull(message="연장근무시간(시)는 필수 항목입니다.")
    @Min(0)
    @Max(12)
    private Integer extendedHours;

    @NotNull(message="연장근무시간(분)는 필수 항목입니다.")
    @Min(0)
    @Max(59)
    private Integer extendedMinutes;

    @NotNull(message="야간근무시간(시)는 필수 항목입니다.")
    @Min(0)
    @Max(12)
    private Integer nightHours;

    @NotNull(message="야간근무시간(분)는 필수 항목입니다.")
    @Min(0)
    @Max(59)
    private Integer nightMinutes;

    @NotEmpty(message="근무형태를 골라주세요.")
    private String workType;
}

