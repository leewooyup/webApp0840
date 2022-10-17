package cz.yeo.wage.WebApp0840.app.accountBook.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FixedIncomeForm {
    @NotEmpty(message="수입유형을 선택해주세요.")
    private String fixedIncomeType;

    @NotNull(message="고정수입(원)을 입력해주세요.")
    private Integer fixedIncome;

    private String fixedIncomeMemo;
}
