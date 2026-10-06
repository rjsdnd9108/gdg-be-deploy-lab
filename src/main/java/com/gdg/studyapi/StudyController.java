package com.gdg.studyapi;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 1회차에 명세로 합의한 API를 실제로 구현한 컨트롤러.
 *
 *   GET /studies                     → 전체 목록
 *   GET /studies?status=RECRUITING   → 모집 중인 것만
 *
 * 숙제: GET /studies/{studyId} (상세 조회 + 없으면 404 Problem Details)를 여기에 추가한다.
 */
@RestController
public class StudyController {

    private final StudyRepository studyRepository;

    public StudyController(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    @GetMapping("/studies")
    public StudyListResponse getStudies(@RequestParam(name = "status", required = false) StudyStatus status) {
        List<Study> studies = (status == null)
                ? studyRepository.findAll()
                : studyRepository.findByStatus(status);

        return new StudyListResponse(studies.stream().map(Study::toSummary).toList());
    }
}
