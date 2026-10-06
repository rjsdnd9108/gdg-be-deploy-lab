#!/usr/bin/env bash
# Codespace를 처음 만들 때 한 번 실행된다 (devcontainer.json의 postCreateCommand).
# 실패해도 실습은 가능하다 — 첫 docker build가 몇 분 더 걸릴 뿐.
set -u

echo "[warmup] Docker가 준비될 때까지 기다리는 중..."
for _ in $(seq 1 60); do
  docker info >/dev/null 2>&1 && break
  sleep 2
done

if ! docker info >/dev/null 2>&1; then
  echo "[warmup] Docker가 아직 준비되지 않았습니다. 워밍업을 건너뜁니다 (실습엔 지장 없음)."
  exit 0
fi

echo "[warmup] 베이스 이미지 받는 중 (JDK, JRE)..."
docker pull eclipse-temurin:21-jdk
docker pull eclipse-temurin:21-jre

echo "[warmup] Gradle과 의존성을 미리 받아 빌드 캐시에 남기는 중..."
# 정답 Dockerfile의 '조리대' 단계까지만 빌드한다.
# 멤버가 가이드와 같은 줄을 쓰면 이 캐시를 그대로 재사용해서 첫 빌드가 빨라진다.
if docker build --target build -f .devcontainer/Dockerfile.answer -t gdg-warmup .; then
  echo "[warmup] 완료! 세션에서 만나요."
else
  echo "[warmup] 의존성 미리 받기에 실패했습니다. 세션 중 첫 빌드가 조금 느릴 수 있어요."
fi

exit 0
