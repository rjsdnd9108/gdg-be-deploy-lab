package com.gdg.studyapi;

/**
 * 스터디 모집글 — 1회차 명세의 StudyDetail과 같은 모양.
 * data/studies.json의 한 칸이 이 객체 하나가 된다.
 */
public record Study(
        long studyId,
        String title,
        String content,
        int capacity,
        StudyStatus status
) {

    /** 목록 조회용 요약 — 1회차 명세의 Study (content 제외) */
    public StudySummary toSummary() {
        return new StudySummary(studyId, title, capacity, status);
    }
}
