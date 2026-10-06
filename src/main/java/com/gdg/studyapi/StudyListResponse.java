package com.gdg.studyapi;

import java.util.List;

/** GET /studies 응답 — 1회차 명세대로 배열을 content로 감싼다 */
public record StudyListResponse(List<StudySummary> content) {
}
