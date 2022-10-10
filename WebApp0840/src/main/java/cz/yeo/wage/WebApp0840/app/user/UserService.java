package cz.yeo.wage.WebApp0840.app.user;


import cz.yeo.wage.WebApp0840.app.user.entity.SiteUser;
import cz.yeo.wage.WebApp0840.app.user.exception.SignupEmailDuplicatedException;
import cz.yeo.wage.WebApp0840.app.user.exception.SignupLoginIdDuplicatedException;
import cz.yeo.wage.WebApp0840.util.Util;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser join(String username, String password, String nickname,
                       String email) {
        try {
            SiteUser siteUser = SiteUser.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .nickname(nickname)
                    .email(email)
                    .build();
            userRepository.save(siteUser);
            return siteUser;
        } catch (DataIntegrityViolationException e) {
          if(userRepository.existsByUsername(username)) {
              throw new SignupLoginIdDuplicatedException("이미 사용중인 아이디 입니다.");
          } else {
              throw new SignupEmailDuplicatedException("이미 사용중인 이메일 입니다.");
          }
        }
    }

    public SiteUser findByUsername(String name) {
        return userRepository.findByUsername(name).orElse(null);
    }

    public void setProfileImgByUrl(SiteUser siteUser, String url) {
        String filePath = Util.file.downloading(url, genFileDirPath + "/" + getCurrentProfileImgDirName() + "/" + UUID.randomUUID());
        siteUser.setUserImgRelPath(getCurrentProfileImgDirName() + "/" + new File(filePath).getName());
        userRepository.save(siteUser);
    }

    private String getCurrentProfileImgDirName() {
        return "user/" + Util.date.getCurrentDateFormatted("yyyy_MM_dd");
    }
}
