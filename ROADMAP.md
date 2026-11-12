# Roadmap

향후 개발 계획과 아이디어를 우선순위별로 관리합니다.

## 🔥 High Priority (다음 스프린트 / 즉시 구현)

### v1.1 후보
- [x] **프로필/설정 화면 (PRF-01)** — Task 18 완료
- [x] **모닝 팝업 (ONB-02)** — Task 19 완료 (테스트 모드: 매번 표시)
- [x] **성장 시스템 재정의 (Task 20)** — 1조각=1레벨, 100조각=Tier 진화, XP 제거, 습관별 연속일
- [ ] **모닝 팝업 "하루 1회" 복원** — `ShouldShowMorningPopupUseCase` 연결 (한 줄 교체)
- [ ] **프로필 화면에 백업/복원 진입점 추가** — STAT-02 완료 시 연결
- [ ] **Tier/Rank 성장 UI (RWD-01)** — 진화 애니메이션 정식 (현재는 CelebrationScreen 재사용)
- [ ] **패널티 시스템 (RWD-02)** — 자정 기준 미실천 판정
- [ ] **하단 네비게이션 4탭** — Quests / Focus / Stats / Ranks

## 📅 Medium Priority (단기 계획)
- [ ] **주간/월간 통계 (STAT-01)**
- [ ] **랭킹 화면 (RNK-01)**
- [ ] **히어로 패시브 재정의** — XP 폐지로 기존 패시브 효과 변경 필요 (Q2)
- [ ] **HBT-03 스와이프 리스케줄**
- [ ] **다국어 (i18n)** — KO/EN

## 💡 Backlog & Ideas (장기 / 아이디어 단계)
- [ ] **2차 고도화: 연속일 마일스톤 보상** — 10일/50일/100일 달성 시 보상
- [ ] **로컬 백업/복원 (STAT-02)** — JSON export/import
- [ ] **아바타 의상/커스터마이징**
- [ ] **Potion 시스템 확장**
- [ ] **위젯** — Android 홈 화면
- [ ] **알림** — 습관 리마인더
- [ ] **다크 모드 정식 지원**
- [ ] **Compose Test v2 마이그레이션**
- [ ] **Q-G 확정** — 신화 이후 추가 성장
- [ ] **Q-H 확정** — 습관 삭제 시 이력 보존

## ✅ 완료 (Done)

### v1.1 — Task 20: 성장 시스템 재정의 (2026-11-12)
- [x] 회의 확정: 1조각=1레벨, 100조각=Tier 진화, XP 제거, 습관별 연속일
- [x] 문서 개정: CHANGELOG / ROADMAP / REQUIREMENTS / DETAILED_SPEC / DESIGN / UI_SPEC
- [x] `CalculateHabitStreakUseCase` (경계값 9개 테스트)
- [x] `CompleteHabitUseCase` 개편 (Tier 진화 판정, 5개 신규 테스트)
- [x] `ProfileDao.updateTierRank` + `ProfileRepository.updateTierRank`
- [x] `CheckInDao.getCheckedDatesByHabit`
- [x] `HabitCard` 재구성 (XP 배지 제거, 이름 1줄 축약, 배지 아래 줄)
- [x] `DashboardScreen` `habitStreaks` 연결
- [x] `MainActivity` 습관별 streak 계산 + Tier 진화 축하
- [x] `AddHabitDialog` XP 입력 필드 제거
- [x] `HabitRepository.addHabit(name)` 단순화
- [x] 폰 검증 (연속일 표시, 긴 이름 축약)

### v1.1 — Task 19: ONB-02 모닝 팝업 (2026-11-12)
- [x] `ShouldShowMorningPopupUseCase` + 6 boundary tests
- [x] `ProfileDao.updateLastOpenDate` + `ProfileRepository.markTodayOpened`
- [x] `MorningPopupScreen` + 3 Compose tests
- [x] MainActivity 연결 (테스트 모드: 매번 표시)

### v1.1 — Task 18: PRF-01 프로필/설정 (2026-11-12)
- [x] `ProfileDao.updateNickname/updateHero` + tests
- [x] `ProfileRepository.updateNickname/updateHero` + 경계값
- [x] `ProfileScreen` + 4 Compose tests
- [x] MainActivity 연결 + 폰 검증

### v1.0.0 (2026-11-12)
- [x] 개발 환경, DB, UseCase, 화면, 통합, 폰 검증, 릴리스 (상세는 git tag v1.0.0 참조)
