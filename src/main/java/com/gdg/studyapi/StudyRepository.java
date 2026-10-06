package com.gdg.studyapi;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;

/**
 * 스터디 저장소 — DB 대신 data/studies.json을 읽어 메모리에 들고 있는다.
 *
 * 앱이 뜰 때 딱 한 번 읽는다. JSON이 깨져 있으면 여기서 예외가 터지고
 * 앱은 아예 뜨지 않는다(fail-fast). 이상한 데이터로 조용히 돌아가는 것보다
 * 시끄럽게 죽는 편이 낫다 — 보너스 실습에서 Render가 이 배포를 거부하는 이유.
 */
@Repository
public class StudyRepository {

    private static final Logger log = LoggerFactory.getLogger(StudyRepository.class);
    private static final String DATA_FILE = "data/studies.json";

    private final List<Study> studies;

    public StudyRepository(JsonMapper jsonMapper) {
        this.studies = load(jsonMapper);
        log.info("[DATA] {}에서 스터디 {}개를 읽었습니다", DATA_FILE, studies.size());
    }

    private static List<Study> load(JsonMapper jsonMapper) {
        try (InputStream in = new ClassPathResource(DATA_FILE).getInputStream()) {
            return List.of(jsonMapper.readValue(in, Study[].class));
        }
        catch (IOException | JacksonException ex) {
            throw new IllegalStateException(DATA_FILE + "을 읽지 못해 서버를 시작할 수 없습니다", ex);
        }
    }

    public List<Study> findAll() {
        return studies;
    }

    public List<Study> findByStatus(StudyStatus status) {
        return studies.stream()
                .filter(study -> study.status() == status)
                .toList();
    }

    /** 숙제(GET /studies/{studyId})에서 쓰세요. */
    public Optional<Study> findById(long studyId) {
        return studies.stream()
                .filter(study -> study.studyId() == studyId)
                .findFirst();
    }
}
