package cz.yeo.wage.WebApp0840.app.accountBook.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FixedSpendingForm {
    @NotEmpty(message="지출유형을 선택해주세요.")
    private String fixedSpendingType;

    @NotNull(message="고정지출(원)을 입력해주세요.")
    private Integer fixedSpending;

    private String fixedSpendingMemo;
}
