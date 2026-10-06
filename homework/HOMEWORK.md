# 2회차 숙제 — 기능 하나 추가해서 직접 다시 배포하기

**마감:** 3회차 전날 23:59
**제출:** 동아리 채널 숙제 스레드에 `저장소 링크 + Render 주소` 한 줄
**규칙:** 숙제도 **AI 없이**. 공식 문서·검색·짝은 OK. 막힌 지점은 README의 **에러 노트**에 적어 주세요.

---

## 필수 · `GET /studies/{studyId}` 상세 조회 (40~60분)

1회차 명세에 있던 **상세 조회 API**를 실제로 만듭니다.

### 있을 때 — `200`

```http
GET /studies/1
```
```json
{
  "studyId": 1,
  "title": "Spring Boot 입문 스터디",
  "content": "백엔드 입문자를 위한 스터디입니다. 주 1회 진행합니다.",
  "capacity": 6,
  "status": "RECRUITING"
}
```

### 없을 때 — `404` + 1회차에서 배운 RFC 9457 Problem Details

```http
GET /studies/999
```
```json
{
  "type": "https://gdg.example.com/probs/study-not-found",
  "title": "스터디를 찾을 수 없습니다",
  "status": 404,
  "detail": "999번 스터디는 존재하지 않습니다",
  "instance": "/studies/999"
}
```
`Content-Type`은 `application/problem+json`이어야 합니다.

### 힌트

- 조회는 이미 만들어 둔 `StudyRepository.findById()`를 쓰면 됩니다.
- 컨트롤러는 `StudyController`에 메서드 하나 추가. `@PathVariable`을 찾아보세요.
- 404 응답은 Spring의 **`ProblemDetail`** 로 만들 수 있습니다.
  - 공식 문서에서 `ProblemDetail`, `ErrorResponseException`, `spring.mvc.problemdetails.enabled`를 찾아보세요.
  - `type`은 `URI`로 넣어야 합니다.

### 배포까지 — 오늘 한 순서 그대로

1. 코드 작성 → `git commit`
2. **로컬에서 먼저 확인** (보너스 실습의 교훈)
   ```bash
   docker build -t study-api:{새태그} .
   docker run -d -p 8080:8080 --name hw study-api:{새태그}
   curl -i localhost:8080/studies/1      # 200
   curl -i localhost:8080/studies/999    # 404 + application/problem+json
   ```
3. **아직 한 번도 안 쓴 태그**로 push (예: 보너스를 했으면 `v4`, 안 했으면 `v3`)
4. Render → Settings → Image URL을 새 태그로 바꾸고 → 수동 배포 → Logs에서 `Started` 확인
5. README의 **API** 표에 한 줄 추가, **실행 방법**의 태그 갱신
6. **짝 검증:** 짝이 내 **공개 주소**로 200과 404를 둘 다 호출하고, 응답 캡처를 숙제 스레드에 답글로 달아 줍니다

### 체크리스트

- [ ] `GET /studies/1` → 200, 바디에 `content` 필드가 있다
- [ ] `GET /studies/999` → 404, `Content-Type: application/problem+json`
- [ ] 404 바디의 `type`, `title`, `status`, `detail`이 위 예시와 같은 모양
- [ ] 로컬 `docker run`으로 먼저 확인했다
- [ ] 새 태그로 push했다 (기존 태그를 덮어쓰지 않았다)
- [ ] Render 공개 주소에서 둘 다 동작한다
- [ ] 짝이 확인해 줬다

---

## 선택 (도전) — 하나라도 하면 3회차에 2분 발표 기회

| | 과제 | 배우는 것 |
|---|---|---|
| A | **콜드 스타트 재기:** 20분 동안 아무도 안 부르게 둔 뒤 아래를 **연속 두 번** 실행해서 두 값을 README에 적기<br>`curl -o /dev/null -s -w "%{time_total}\n" {내주소}/studies` | 무료 서버가 잠드는 비용, 숫자로 재는 습관 |
| B | **터미널 한 줄 배포:** Render Settings의 **Deploy Hook** 주소로 `curl -X POST` 해서 배포하기 (`imgURL`로 태그 지정 — Render 문서 참고).<br>⚠️ **Hook 주소는 절대 커밋하지 마세요.** 그 주소만 있으면 누구나 내 서버를 배포할 수 있습니다 | 배포도 결국 HTTP 요청 하나. 비밀값 다루기 (3회차 예고) |
| C | **GitHub Actions:** main에 push하면 이미지 build → Docker Hub push → Deploy Hook 호출까지 자동으로 | CI/CD 맛보기. 토큰과 Hook 주소는 GitHub Secrets에 |
| D | **헬스체크를 똑똑하게:** 스터디가 0개면 `DOWN`을 돌려주는 `HealthIndicator` 만들기 → `drills/studies-empty.json`으로 배포했을 때 Render가 배포를 거부하는지 확인<br>(Spring Boot 4에서는 `org.springframework.boot.health.contributor` 패키지) | 헬스체크가 "살아 있음"뿐 아니라 "맞음"도 보게 만들기 |
| E | **장애 보고서:** 보너스 실습(깨진 배포)이나 숙제 중 겪은 문제를 `homework/INCIDENT_TEMPLATE.md` 양식으로 써서 `INCIDENT.md`로 커밋 | 장애를 기록하는 법. **사람 탓을 쓰지 않는다** |

---

다 끝나면 **Codespace를 중지**해 주세요 (github.com/codespaces → 내 Codespace → Stop).
Render 서비스는 지우지 않아도 됩니다 — 15분 동안 아무도 안 부르면 알아서 잠듭니다.
