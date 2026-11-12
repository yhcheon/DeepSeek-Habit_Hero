# Roadmap

향후 개발 계획과 아이디어를 우선순위별로 관리합니다.
진행 상황은 `[ ]` → `[x]` 로 체크합니다.

## 🔥 High Priority (다음 스프린트 / 즉시 구현)

### v1.1 후보
- [ ] **모닝 팝업 (ONB-02) 정식화** — 현재 테스트 모드(매번 표시) → 당일 최초 1회 로직 (`lastOpenDate` 활용)
- [ ] **Tier/Rank 성장 UI (RWD-01)** — 진화 애니메이션, 레벨업 연출
- [ ] **패널티 시스템 (RWD-02)** — 자정 기준 미실천 판정 (조각 -1, Rank -1, 스트릭 리셋)
- [ ] **스트릭 마일스톤** — 7/14/30일 달성 시 보너스 (XP 폐지로 보상 재정의 필요: 조각 보너스? Tier 가속?)
- [ ] **하단 네비게이션 4탭** — Quests / Focus / Stats / Ranks

## 📅 Medium Priority (단기 계획)
- [ ] **주간/월간 통계 (STAT-01)** — 달성률 그래프, 스트릭 추이
- [ ] **랭킹 화면 (RNK-01)** — 가상 NPC 비교 로컬 랭킹
- [ ] **HBT-03 스와이프 리스케줄** — 카드 좌우 스와이프
- [ ] **다국어 (i18n)** — KO/EN 완전 지원
- [ ] **문서 동기화 부채 정리** — REQUIREMENTS/DETAILED_SPEC/DESIGN의 XP·패시브 잔재 정리

## 💡 Backlog & Ideas (장기 / 아이디어 단계)
- [ ] **로컬 백업/복원 (STAT-02)** — JSON export/import (SAF)
- [ ] **아바타 의상/커스터마이징** — Tier 진화 시 외형 변화 (Rune=남 / Aria=여 성별 정보 코드 반영 필요)
- [ ] **Potion 시스템 확장** — 스타터 Potion 용도 정의
- [ ] **위젯** — Android 홈 화면 (오늘의 퀘스트 요약)
- [ ] **알림** — 습관 리마인더, 패널티 경고
- [ ] **다크 모드 정식 지원** — DESIGN.md §2.2 토큰 활용
- [ ] **Q-G 확정** — 신화 이후 추가 성장 (무한 랭크 vs 종결)
- [ ] **Q-H 확정** — 습관 삭제 시 과거 이력 보존 정책

## ✅ 완료 (Done)

### v1.0.2 (2026-11-12) — Task 21
- [x] **XP 시스템 완전 제거** — ProfileEntity.xp / HabitEntity.rewardXp / CompleteHabitResult XP 필드 / CompleteHabitUseCase XP 계산 전부 삭제
- [x] **DB version 4 → 5** — fallbackToDestructiveMigration 유지
- [x] **언체크 조각 -1 버그 수정** — `minOf(remainingCount, 10)` → `maxOf(fragments - 1, 0)`
- [x] **히어로 패시브 개념 미도입 확정** — 표시용 문자열만 유지
- [x] **온보딩 패시브 문구 교체** — XP 언급 제거

### v1.0.1 (2026-11-12)
- [x] **프로필/설정 화면 (PRF-01)** — 닉네임/히어로 변경 (Task 18)
- [x] **모닝 팝업 (ONB-02)** — 테스트 모드: 매번 표시 (Task 19)
- [x] **성장 시스템 재정의 (Task 20)**:
  - 1조각 = 1레벨, 100조각 누적 = Tier 진화 (B→A→S→MYTH)
  - 진화 시 Rank 0 리셋, 누적 조각 유지
  - XP 폐지 (화면/입력에서 제거) → Task 21에서 DB 필드까지 완전 제거
  - 습관별 "연속 N일 달성" 도입 (HabitCard에 🔥 N일)
  - `CalculateHabitStreakUseCase` 신규

### v1.0.0 (2026-11-12)
- [x] 프로젝트 초기 구조 및 개발 환경 (AGP 9.5.1, Kotlin 2.2.10, Compose BOM 2026.02.01, Room 2.8.5, KSP)
- [x] 문서 체계 (REQUIREMENTS, DETAILED_SPEC, UI_SPEC, DESIGN, SETUP_GUIDE)
- [x] 로컬 DB (Room version 4)
- [x] 핵심 비즈니스 로직 (CompleteHabitUseCase, HabitRepository, ProfileRepository)
- [x] 온보딩 화면 (ONB-01)
- [x] 메인 대시보드 (HBT-02)
- [x] 습관 CRUD (HBT-01/02/03) — Task 14, 17
- [x] 체크/언체크 (ACT-02) — Task 15
- [x] 5분 타이머 (ACT-01) — Task 13
- [x] 축하 화면 (CelebrationScreen)
- [x] 폰 검증 완료 (29개 테스트 PASSED)
