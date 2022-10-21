package cz.yeo.wage.WebApp0840.app.accountBook.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DailyPatternForm {
    @NotEmpty(message="[소비패턴명]을 입력해주세요.")
    private String dailyPatternName;

    @NotEmpty(message="[소비유형]을 선택해주세요.")
    private String[] dailyConsumptionTypes;

    @NotEmpty(message="[소비가격]을 입력해주세요.")
    private Integer[] dailyConsumptionPrices;
}
