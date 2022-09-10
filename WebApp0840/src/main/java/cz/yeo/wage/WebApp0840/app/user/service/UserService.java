package cz.yeo.wage.WebApp0840.app.user.service;


import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import cz.yeo.wage.WebApp0840.app.user.exception.SignupEmailDuplicatedException;
import cz.yeo.wage.WebApp0840.app.user.exception.SignupLoginIdDuplicatedException;
import cz.yeo.wage.WebApp0840.app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(String loginId, String loginPw, String nickname,
                       String email, MultipartFile userImg) {
        String userImgRelPath = "user/" + UUID.randomUUID().toString() + ".png";
        File userImgFile = new File(genFileDirPath + "/" + userImgRelPath);

        userImgFile.mkdirs();

        try {
            userImg.transferTo(userImgFile);
            SiteUser siteUser = SiteUser.builder()
                    .loginId(loginId)
                    .loginPw(passwordEncoder.encode(loginPw))
                    .nickname(nickname)
                    .email(email)
                    .userImgPath(userImgRelPath)
                    .build();
            userRepository.save(siteUser);
        } catch (DataIntegrityViolationException e) {
          if(userRepository.existsByLoginId(loginId)) {
              throw new SignupLoginIdDuplicatedException("이미 사용중인 아이디 입니다.");
          } else {
              throw new SignupEmailDuplicatedException("이미 사용중인 이메일 입니다.");
          }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
