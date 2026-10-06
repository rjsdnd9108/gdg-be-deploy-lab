package com.gdg.studyapi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudyApiApplicationTests {

    @Autowired
    StudyRepository studyRepository;

    @Test
    void studiesJson을_읽어서_뜬다() {
        assertThat(studyRepository.findAll()).isNotEmpty();
    }
}
