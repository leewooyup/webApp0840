package cz.yeo.wage.WebApp0840.app.user.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserCreateForm {
    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String passwordCheck;

    @NotEmpty(message = "닉네임은 필수항목입니다.")
    private String nickname;

    @NotEmpty(message = "닉네임은 필수항목입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;
}
