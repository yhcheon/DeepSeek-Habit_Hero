# Roadmap

향후 개발 계획과 아이디어를 우선순위별로 관리합니다.
진행 상황은 `[ ]` → `[x]` 로 체크합니다.

## 🔥 High Priority (다음 스프린트 / 즉시 구현)

### v1.1 후보
- [x] **프로필/설정 화면 (PRF-01)** — 히어로 변경, 닉네임 변경 (Task 18 완료)
- [x] **모닝 팝업 (ONB-02)** — 응원 카드 + 요약 + CTA (Task 19 완료, 테스트 모드: 매번 표시)
- [ ] **모닝 팝업 "하루 1회" 복원** — `ShouldShowMorningPopupUseCase` 연결 (한 줄 교체)
- [ ] **성장 시스템 재정의 (Task 20)** — 1조각=1레벨, 100조각=Tier 진화, XP 제거, 습관별 연속일
- [ ] **프로필 화면에 백업/복원 진입점 추가** — STAT-02 완료 시 연결
- [ ] **Tier/Rank 성장 UI (RWD-01)** — 진화 애니메이션, 레벨업 연출
- [ ] **패널티 시스템 (RWD-02)** — 자정 기준 미실천 판정 (조각 -1, Rank -1, 스트릭 리셋)
- [ ] **하단 네비게이션 4탭** — Quests / Focus / Stats / Ranks

## 📅 Medium Priority (단기 계획)
- [ ] **주간/월간 통계 (STAT-01)** — 달성률 그래프, 스트릭 추이
- [ ] **랭킹 화면 (RNK-01)** — 가상 NPC 비교 로컬 랭킹
- [ ] **히어로 패시브 효과 재정의** — XP 제거에 따라 패시브 효과 변경 필요 (Q2)
- [ ] **HBT-03 스와이프 리스케줄** — 카드 좌우 스와이프
- [ ] **다국어 (i18n)** — KO/EN 완전 지원

## 💡 Backlog & Ideas (장기 / 아이디어 단계)
- [ ] **2차 고도화: 연속일 마일스톤 보상** — 10일/50일/100일 달성 시 보상 지급 (논의 후 개발)
- [ ] **로컬 백업/복원 (STAT-02)** — JSON export/import (SAF)
- [ ] **아바타 의상/커스터마이징** — Tier 진화 시 외형 변화
- [ ] **Potion 시스템 확장** — 스타터 Potion 용도 정의
- [ ] **위젯** — Android 홈 화면 (오늘의 퀘스트 요약)
- [ ] **알림** — 습관 리마인더, 패널티 경고
- [ ] **다크 모드 정식 지원** — DESIGN.md §2.2 토큰 활용
- [ ] **Compose Test v2 마이그레이션** — `createComposeRule` deprecated 경고 해결
- [ ] **Q-G 확정** — 신화 이후 추가 성장 (무한 랭크 vs 종결)
- [ ] **Q-H 확정** — 습관 삭제 시 과거 이력 보존 정책

## ✅ 완료 (Done)

### v1.1 — 성장 시스템 재정의 (2026-11-12, Task 20, 기획 확정)
- [x] 회의 확정: 1조각=1레벨, 100조각=Tier 진화, XP 제거, 습관별 연속일 도입
- [x] 문서 개정: REQUIREMENTS / DETAILED_SPEC / DESIGN / UI_SPEC / CHANGELOG / ROADMAP
- [ ] 구현: 조각→레벨→Tier 진화 로직
- [ ] 구현: 습관별 연속일 계산 + HabitCard 표시
- [ ] 구현: XP 필드 미사용 처리 (DB 유지)
- [ ] 2차 고도화: 연속일 마일스톤 보상 (10/50/100일) — 논의 후 개발

### v1.1 — ONB-02 모닝 팝업 (2026-11-12, Task 19)
- [x] **ShouldShowMorningPopupUseCase**: lastOpenDate vs 오늘 비교 순수 함수 + 경계값 테스트 6개
- [x] **ProfileDao/Repository**: `updateLastOpenDate()` / `markTodayOpened()` 추가
- [x] **MorningPopupScreen UI**: 아바타 + Tier/Rank + 응원 + 진행 바(rank/100) + 요약 2열 + CTA 2개 + Compose 테스트 3개
- [x] **MainActivity 연결**: 완전 재실행 시 팝업 → CTA/Remind → `lastOpenDate = 오늘` 저장 → 대시보드
- [x] **테스트 모드**: 매번 팝업 표시 (한 줄 복원 가능)
- [x] **폰 수동 검증**: 완전 재실행 시 팝업 / 백그라운드 복귀 시 팝업 안 뜸 (의도)

### v1.1 — PRF-01 프로필/설정 (2026-11-12, Task 18)
- [x] **ProfileDao**: `updateNickname()`, `updateHero()` 추가 + 테스트 5개
- [x] **ProfileRepository**: `updateNickname()`, `updateHero()` 위임 (빈 문자열/프로필 없음 방어) + 경계값 테스트
- [x] **ProfileScreen UI**: 닉네임 입력(1~20자) + 히어로 카드 2종 + 저장/뒤로가기 + Compose 테스트 4개
- [x] **MainActivity 연결**: 설정 아이콘 → ProfileScreen, 저장 시 DB 반영 + 화면 복귀
- [x] **폰 수동 검증**: 변경 → 종료 → 재실행 → 유지 확인

### v1.0.0 (2026-11-12)
(이하 v1.0.0 완료 항목은 이전과 동일)
