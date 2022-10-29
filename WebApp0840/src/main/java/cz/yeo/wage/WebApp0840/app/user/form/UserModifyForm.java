package cz.yeo.wage.WebApp0840.app.user.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserModifyForm {
    @NotEmpty(message = "닉네임은 필수항목입니다.")
    private String nickname;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;
}
