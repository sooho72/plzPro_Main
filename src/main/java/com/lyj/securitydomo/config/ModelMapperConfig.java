package com.lyj.securitydomo.config;

import com.lyj.securitydomo.domain.Report;
import com.lyj.securitydomo.domain.Request;
import com.lyj.securitydomo.dto.ReportDTO;
import com.lyj.securitydomo.dto.RequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Report -> ReportDTO 매핑
        modelMapper.createTypeMap(Report.class, ReportDTO.class)
                .addMapping(report -> report.getPost().getPostId(), ReportDTO::setPostId) // postId 매핑
                .addMapping(report -> report.getUser().getUserId(), ReportDTO::setUserId) // userId 매핑
                .addMapping(report -> report.getPost().getTitle(), ReportDTO::setPostTitle); // postTitle 매핑

        modelMapper.createTypeMap(Request.class, RequestDTO.class)
                .addMapping(request -> request.getPost().getPostId(), RequestDTO::setPostId) //Request의 Post 객체에서 postId를 가져와 RequestDTO에 설정
                .addMapping(request -> request.getUser().getUsername(), RequestDTO::setUsername); // User 이름 매핑 추가

        return modelMapper;
    }
}