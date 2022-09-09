package cz.yeo.wage.WebApp0840.app.base.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

// # 파일 업로드를 위한 Entity
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class GenFile extends BaseEntity {
    private String relTypeCode;
    private int relId;
    private String typeCode;
    private String type2Code;
    private String fileExtTypeCode;
    private String fileExtType2Code;
    private int fileSize;
    private int fileNo;
    private String fileExt;
    private String fileDir;
    private String originFileName;
}
