# Changelog

모든 주요 변경 사항은 이 문서에 기록됩니다.
[Keep a Changelog](https://keepachangelog.com/ko/1.1.0/) 형식을 따르며,
[Semantic Versioning](https://semver.org/lang/ko/)을 준수합니다.

## [Unreleased]
### Added
- **성장 시스템 재정의 (Task 20)**:
  - **1조각 = 1레벨**, **100조각 누적 = Tier 진화** (B→A→S→MYTH), 진화 시 Rank 0 리셋 + 누적 조각 유지
  - **XP 제거**: 화면/입력에서 완전 제거. `xp`/`rewardXp` DB 필드는 마이그레이션 회피 위해 유지 (값 0)
  - **습관별 "연속 N일 달성"** 도입: `HabitCard`에 `🔥 N일` 표시 (XP 배지 대체)
  - **`CalculateHabitStreakUseCase`** 신규: 습관별 체크인 날짜로 연속일 계산 (경계값 9개 테스트)
  - **`CompleteHabitUseCase`** 개편: Tier 진화 판정 (`tierEvolved`, `newTier`, `newRank` 반환)
  - **`ProfileDao.updateTierRank`**, **`ProfileRepository.updateTierRank`** 추가
  - **`CheckInDao.getCheckedDatesByHabit`** 추가
  - **Tier 진화 시 축하 화면**: 기존 `CelebrationScreen` 재사용 ("🎉 A급 진화!")
  - **`AddHabitDialog`**: "보상 XP" 입력 필드 제거, `onConfirm(name)` 시그니처
  - **`HabitRepository.addHabit(name)`**: XP=0 저장
  - **`HabitCard` 레이아웃**: 습관명 1줄 축약(maxLines=1, Ellipsis) + 배지/카테고리를 이름 아래 줄에 배치
  - **미구현 (2차 고도화)**: 연속일 마일스톤 보상 (10일/50일/100일 등)
- **모닝 팝업 (ONB-02)**: 앱 실행 시 응원 카드 표시 (현재 테스트 모드 — 매번 표시)
  - `MorningPopupScreen` + `ShouldShowMorningPopupUseCase` + `markTodayOpened` + MainActivity 연결
- **프로필/설정 화면 (PRF-01)**: 닉네임 + 히어로(RUNE/ARIA) 변경 + 재시작 유지
- (예정) 주간/월간 통계 리포트 (STAT-01)
- (예정) 로컬 데이터 JSON 백업/복원 (STAT-02)

### Changed
- **성장 시스템 재정의**: XP(표시용 누적 경험치) 폐지 → 습관별 연속 달성일로 대체
  - REQUIREMENTS §1.3 용어 정의 개정
  - DETAILED_SPEC §4 RWD-01/ACT-02 개정
  - DESIGN.md §7.1 HabitCard 배지 스펙 개정
  - UI_SPEC §4 HabitCard 스펙 개정

### Fixed
- **모닝 팝업 테스트 "히어로" 문구 중복 매칭** → testTag + useUnmergedTree
- **긴 습관 이름 시 배지 안 보임** → 이름 1줄 축약 + 배지 아래 줄 배치
- **`TimerScreenTest` 완료 버튼 테스트 오류** → `remainingSeconds = 0` 상태로 수정
- **`FakeHabitDao`/`FakeProfileDao` 컴파일 부채** → 신규 DAO 메서드 반영

## [1.0.0] - 2026-11-12
(이하 v1.0.0 완료 항목은 이전과 동일)

## [0.1.0] - 2026-11-11
### Added
- 기획 및 UI 프로토타입 단계 완료

### Changed
- 앱 정식 명칭: Habit Keeper → **Habit Hero**

## [0.0.1] - 2026-11-11
### Added
- 프로젝트 초기 스캐폴딩
