package cz.yeo.wage.WebApp0840.app.accountBook.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DailyPatternForm {
    @NotEmpty(message="하루소비패턴 명을 입력해주세요.")
    private String dailyPatternName;

    private String[] dailyConsumptionTypes;
    private Integer[] dailyConsumptionPrices;
}
