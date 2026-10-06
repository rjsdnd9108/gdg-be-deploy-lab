package com.gdg.studyapi;

/** 목록 조회 응답의 한 칸 — 1회차 명세의 Study 스키마 */
public record StudySummary(
        long studyId,
        String title,
        int capacity,
        StudyStatus status
) {
}
