package com.lyj.securitydomo.controller;

import com.lyj.securitydomo.dto.ReportDTO;
import com.lyj.securitydomo.dto.UserDTO;
import com.lyj.securitydomo.service.PostService;
import com.lyj.securitydomo.service.ReportService;
import com.lyj.securitydomo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@Log4j2
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final ReportService reportService;
    private final PostService postService;

    /**
     * 관리자 메인 페이지
     */
    @GetMapping("/adminIndex")
    public String adminIndex() {
        log.info("관리자 메인 페이지 요청");
        return "admin/adminIndex";
    }

    /**
     * 신고 목록 조회
     */
    @GetMapping("/reports")
    public String getReports(Model model) {
        log.info("신고 목록 조회 요청");

        // 전체 신고 목록 조회
        List<ReportDTO> reportList = reportService.getAllReports();

        model.addAttribute("reportList", reportList);

        return "admin/reportList"; // 신고 리스트 페이지로 이동
    }

    /**
     * 특정 게시글의 신고 조회
     */
    @GetMapping("/reports/post/{postId}")
    public String getReportsByPostId(@PathVariable Long postId, Model model) {
        log.info("특정 게시글의 신고 조회 요청, postId: {}", postId);

        List<ReportDTO> reports = reportService.getReportsByPostId(postId);

        model.addAttribute("reports", reports);
        model.addAttribute("postId", postId);

        return "admin/reportsByPost"; // 특정 게시글 신고 리스트 페이지로 이동
    }

    /**
     * 신고 상태 토글 처리 (비공개/공개)
     */
    @PostMapping("/reports/{reportId}/toggle-visibility")
    @ResponseBody
    public String toggleVisibility(@PathVariable Long reportId, @RequestBody Map<String, Boolean> requestBody) {
        log.info("신고 상태 토글 요청, reportId: {}", reportId);

        // 요청에서 visible 값 확인
        boolean visible = requestBody.getOrDefault("visible", false);

        // 상태에 따라 처리
        if (visible) {
            log.info("신고 공개 처리: reportId={}", reportId);
            reportService.markAsVisible(reportId);
            Long postId = reportService.getPostIdByReportId(reportId);
            postService.makePostVisible(postId);
        } else {
            log.info("신고 비공개 처리: reportId={}", reportId);
            reportService.markAsHidden(reportId);
            Long postId = reportService.getPostIdByReportId(reportId);
            postService.makePostInvisible(postId);
        }

        return visible ? "신고글과 게시글이 공개 처리되었습니다." : "신고글과 게시글이 비공개 처리되었습니다.";
    }

    /**
     * 모든 사용자 목록 조회
     */
    @GetMapping("/users")
    public String getAllUsers(Model model) {
        log.info("모든 사용자 목록 조회 요청");

        List<UserDTO> userList = userService.getAllUsers();

        model.addAttribute("userList", userList);

        return "admin/users"; // 사용자 리스트 페이지로 이동
    }
}