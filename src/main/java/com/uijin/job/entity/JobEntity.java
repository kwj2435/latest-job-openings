package com.uijin.job.entity;

import com.uijin.job.model.JumpitModel;
import com.uijin.job.model.RememberModel;
import com.uijin.job.model.WantedModel;
import com.uijin.job.model.ZighangModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "job")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobEntity {

  @Id
  @Column(name = "job_idx")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long jobIdx; // 기본 키

  @Column(name = "job_id")
  private Long jobId; // 직업 ID

  @Column(length = 10)
  private String domain; // 도메인

  @Column(name = "image_path", length = 500)
  private String imagePath; // 이미지 경로

  @Column(length = 100)
  private String title; // 제목

  @Column(name = "company_name", length = 50)
  private String companyName; // 회사명

  @Column(length = 50)
  private String location; // 위치

  public static JobEntity toEntity(JumpitModel.Position position) {
    return JobEntity.builder()
            .jobId(position.getId())
            .domain("J")
            .imagePath(position.getImagePath())
            .title(position.getTitle())
            .companyName(position.getCompanyName())
            .location(position.getLocations().get(0))
            .build();
  }

  public static JobEntity toEntity(WantedModel.WantedJob position) {
    return JobEntity.builder()
            .jobId(position.getId())
            .domain("W")
            .imagePath(position.getTitleImg().getThumb())
            .title(position.getPosition())
            .companyName(position.getCompany().getName())
            .location(position.getAddress().toString())
            .build();
  }

  public static JobEntity toEntity(RememberModel.RememberJob position) {
    return JobEntity.builder()
            .jobId(position.getId())
            .domain("R")
            .imagePath(position.getThumbnailUrl())
            .title(position.getTitle())
            .companyName(position.getOrganization().getName())
            .location(position.getNormalizedAddress().toString())
            .build();
  }

  public static JobEntity toEntity(ZighangModel.RecruitmentSimple position) {
    return JobEntity.builder()
            .jobId(position.getId())
            .domain("Z")
            .imagePath(position.getMainImageUrl())
            .title(position.getTitle())
            .companyName(position.getCompanyName())
            .location(position.getCompanyAddress())
            .build();
  }
}
