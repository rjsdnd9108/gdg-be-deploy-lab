# 스터디 모집 API · GDG BE 2회차 배포 실습

1회차에 **명세로 합의한** 스터디 모집 게시판 API를 실제로 구현한 Spring Boot 앱입니다.
오늘은 이 앱을 **Docker 이미지로 굽고**, **Render에 배포해서** 공개 주소를 받습니다.

> **오늘의 규칙 — AI 없이**
> Copilot·ChatGPT·Claude는 끄고 진행합니다. 실습 가이드, 공식 문서, 검색, 짝, 진행자는 OK.
> 막히면 아래 **에러 노트**에 한 줄 적어 주세요. 그게 오늘의 실습입니다.

---

## 오늘 손대는 파일은 세 개뿐

| 파일 | 언제 | 할 일 |
|---|---|---|
| `Dockerfile` | STEP 3 | **저장소 맨 위에 새로 만들어서** 직접 작성 |
| `src/main/resources/data/studies.json` | STEP 4 | **내 이름이 들어간 스터디** 하나 추가 |
| `README.md` (이 파일) | STEP 5 | 아래 **실행 방법**과 **배포 주소** 채우기 |

Java 코드(`src/main/java`)는 오늘 건드리지 않습니다. 숙제에서 처음 엽니다.

## 폴더 안내

```
.
├── src/main/java/com/gdg/studyapi/
│   ├── StudyController.java     # GET /studies
│   ├── StudyRepository.java     # 시작할 때 studies.json을 읽어 둔다 (깨져 있으면 안 뜬다)
│   └── RequestLogFilter.java    # 요청마다 "[REQ] GET /studies -> 200 (12ms)" 로그
├── src/main/resources/
│   ├── application.yml          # server.port: ${PORT:8080}
│   └── data/studies.json        # ← STEP 4
├── step1/study-api-teammate.jar # STEP 1: 팀원이 보내 준 jar
├── drills/                      # 보너스 실습용 (지금은 열지 마세요)
├── homework/                    # 숙제 안내
└── .devcontainer/               # Codespace 설정 (정답 Dockerfile도 여기 숨어 있음)
```

## API

| 요청 | 응답 |
|---|---|
| `GET /studies` | 전체 스터디 목록 |
| `GET /studies?status=RECRUITING` | 모집 중인 것만 (`RECRUITING` / `CLOSED`) |
| `GET /actuator/health` | 서버가 살아 있으면 `{"status":"UP"}` |

응답 예시 (1회차 명세의 example과 같은 모양):

```json
{
  "content": [
    { "studyId": 1, "title": "Spring Boot 입문 스터디", "capacity": 6, "status": "RECRUITING" }
  ]
}
```

`studies.json`에 스터디를 추가할 때는 **`studyId`가 겹치지 않게**, `status`는 `RECRUITING`이나 `CLOSED` 중 하나로 쓰세요.
쉼표 하나만 빠져도 서버가 안 뜹니다. (그게 일부러 그렇게 만든 거예요.)

---

## 실행 방법

> STEP 5에서 채웁니다. **짝이 이 칸만 보고 내 서버를 띄울 수 있어야 합니다.**

- 이미지 이름:
- 실행 명령:
  ```bash

  ```
- 확인 주소:

## 배포 주소

> STEP 5에서 Render 배포가 끝나면 채웁니다.

-

---

## 에러 노트

> 막힐 때마다 한 줄. 숙제 제출 때 같이 봅니다. **막힌 기록은 감점이 아니라 가점입니다.**

| STEP | 증상 | 로그 한 줄 | 원인 | 조치 |
|---|---|---|---|---|
| 예) 3 | `curl` 연결 거부 | `Connection refused` | `docker run`에 `-p`를 안 붙임 | `-p 8080:8080` 추가 |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
