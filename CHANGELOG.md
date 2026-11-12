# Changelog

모든 주요 변경 사항은 이 문서에 기록됩니다.
[Keep a Changelog](https://keepachangelog.com/ko/1.1.0/) 형식을 따르며,
[Semantic Versioning](https://semver.org/lang/ko/)을 준수합니다.

## [Unreleased]
### Added
- (예정) 프로필/설정 화면 (PRF-01) — 히어로 변경, 닉네임 변경
- (예정) 모닝 팝업 (ONB-02) — 당일 첫 진입 시 응원 카드
- (예정) 스트릭 마일스톤 (7/14/30일 화염 보너스)
- (예정) 주간/월간 통계 리포트 (STAT-01)
- (예정) 로컬 데이터 JSON 백업/복원 (STAT-02)

## [1.0.3] - 2026-11-12
### Added
- **모닝 팝업 날짜 표시 (Task 22)**:
  - "☀️ 좋은 아침이에요!" 아래에 오늘 날짜 표시 ("2026년 11월 12일 목요일" 형식)
  - `DateTimeFormatter.ofPattern("yyyy년 M월 d일 EEEE", Locale.KOREAN)` 사용
  - 앱 실행 시점의 오늘 날짜 자동 반영

### Changed
- **모닝 팝업 표시 정책 확정 (Task 22)**:
  - "Start Quests!" / "Remind Me Later" **둘 다 저장 안 함** → 앱 실행 시 매번 표시 (테스트 모드)
  - 기존 `lastOpenDate` 기반 1일 1회 로직은 비활성화 (UseCase 파일은 유지, 향후 재활용 대비)
- **자동 백업 비활성화**:
  - `AndroidManifest.xml` `android:allowBackup="false"` 로 변경
  - 앱 삭제 시 데이터 완전 삭제 (재설치 시 자동 복원 방지)
  - `dataExtractionRules`, `fullBackupContent` 속성 제거

### Fixed
- **모닝 팝업이 정상적으로 표시되지 않던 문제**: 자동 백업으로 인한 DB 복원이 원인 → `allowBackup="false"`로 해결

## [1.0.2] - 2026-11-12
### Changed
- **XP 시스템 완전 제거 (Task 21)**:
  - `ProfileEntity.xp` 컬럼 제거, `HabitEntity.rewardXp` 컬럼 제거 (DB version 4 → 5)
  - `CompleteHabitResult.xpEarned`/`newXp` 필드 제거
  - `CompleteHabitUseCase`에서 XP 관련 파라미터/계산 전부 제거
    - `profileXp`, `habitRewardXp`, `heroPassiveXpRate`, `fireBonus` → 삭제
    - 시그니처: `execute(profileFragments, profileStreak, todayFragmentCount)`
  - `ProfileDao.updateStats(fragments, streak)` — `xp` 파라미터 제거
  - `HabitRepository.addHabit(name)` — 내부 `rewardXp` 저장 제거
  - `MainActivity`의 `var xp` 및 `profile.xp` 참조 제거
  - 온보딩 패시브 문구 XP 언급 제거 ("묵묵히 쌓아가는 성실함" / "긍정의 에너지로 이어가는 힘")
- **히어로 패시브 개념 미도입 확정**: Rune/Aria는 표시용 문자열로만 유지, 게임플레이 개입 없음

### Fixed
- **언체크 시 조각 감소 버그 수정 (Task 21)**:
  - 기존: `minOf(remainingCount, 10)` — 오늘 카운트로 누적 조각을 덮어쓰는 버그
  - 수정: `maxOf(fragments - 1, 0)` — 누적 조각에서 정확히 -1 (음수 방지)
  - 습관 삭제 시에도 동일하게 적용

### Docs
- **문서 동기화 (Task 21-followup)**:
  - `docs/CHANGELOG.md`, `docs/ROADMAP.md` 삭제 (루트 버전으로 통합, 중복 제거)
  - `docs/REQUIREMENTS.md` v3.1: XP 폐지 반영
  - `docs/DETAILED_SPEC.md` v4.1: XP 폐지 반영, 데이터 모델 갱신
  - `docs/DESIGN.md`: XP 배지 컴포넌트 제거
  - `docs/UI_SPEC.md` v2.1: XP 언급 제거
  - `README.md`: XP 언급 제거

## [1.0.1] - 2026-11-12
### Added
- 프로필/설정 화면 (PRF-01) — 닉네임/히어로 변경 (Task 18)
- 모닝 팝업 (ONB-02) — 테스트 모드: 매번 표시 (Task 19)
- 성장 시스템 재정의 (Task 20):
  - 1조각 = 1레벨, 100조각 누적 = Tier 진화 (B→A→S→MYTH)
  - 진화 시 Rank 0 리셋, 누적 조각 유지
  - XP 폐지 (화면/입력에서 제거, DB 필드는 유지 → Task 21에서 완전 제거)
  - 습관별 "연속 N일 달성" 도입 (HabitCard에 🔥 N일)
  - `CalculateHabitStreakUseCase` 신규

## [1.0.0] - 2026-11-12
### Added
- **5분 타이머 + 즉석 습관 등록 완료 (Task 13, 컨셉 재정의)**:
  - **"지금 5분만 하기"** = 즉석 습관 입력 + 5분 타이머 + 완료 시 정식 등록
  - `TimerScreen`: 카운트다운 UI (05:00 형식, 원형 배경)
    - **5분 미달 시 "완료" 버튼 비활성화** (강제 5분)
    - 5분 완주 시 "완료하고 조각 받기" 활성화
  - `TimerContainer`: 1초마다 감소, 일시정지/재개 로직
  - **`CelebrationScreen`** (신규): "✨ 참 잘했어요! ✨" + ★★★★★ + "+ 1 조각 💎"
    - **1.5초 후 자동 복귀**
  - `MainActivity` 통합:
    - FAB → `AddHabitDialog` (제목 "지금 5분만 하기", 버튼 "5분 시작")
    - "5분 시작" → **메모리에만 보관** (DB 미저장) + 타이머 실행
    - **5분 완주 + 완료 클릭** → DB 등록 + 체크 + 조각 +1 → 축하 화면 → 대시보드
    - **포기/뒤로가기** → **아무것도 저장 안 됨** (흔적 없음)
  - `TimerScreenTest`, `TimerContainerTest` PASSED
  - **폰에서 검증**: 5분 완주 → "참 잘했어요" → 습관 등록 + 조각 +1 + 자동 복귀
- **습관 수정/삭제 기능 완료 (Task 17, TDD)**:
  - `HabitDao` 확장: `update()`, `delete()`
  - `HabitCard` 확장: `onLongClick` + `combinedClickable`
  - `HabitActionSheet` (습관 이름 + 수정/삭제 메뉴)
  - `AddHabitDialog` 확장: `initialName`, `title`, `confirmButtonText` (수정 모드)
  - `MainActivity`: 길게 누르기 → 액션 시트 → 수정/삭제 + **조각 재계산**
  - **폰에서 검증**: 길게 누르기 → 수정/삭제 정상, 체크한 습관 삭제 시 조각 감소 정상
- **체크/언체크 완전 처리 + 엣지 케이스 해결 (Task 15, TDD)**:
  - `CheckInDao` 확장: `getTodayCompletedHabitIds()`, `deleteByHabitAndDate()`
  - `ProfileDao` 확장: `updateStats()` (조각/스트릭 업데이트)
  - 체크 흐름: `CompleteHabitUseCase` → `CheckInDao.insert()` → `ProfileDao.updateStats()` → UI 갱신
  - 언체크 흐름: `CheckInDao.deleteByHabitAndDate()` → 남은 완료 수로 조각 재계산
  - 앱 재시작 시 체크 상태/조각 복원
  - **폰에서 검증**: 13개 습관 상한 10개 → 언체크 시 재계산 정상, 크래시 없음
- **습관 추가 기능 완료 (Task 14, TDD)**:
  - `AddHabitDialog` (습관 이름)
  - `DashboardScreen`에 "+" FAB → 다이얼로그 연결
  - `MainActivity`가 `HabitRepository.addHabit()`으로 DB 저장 후 리스트 갱신
  - `HabitEntity`에 `category` 필드 추가 (DB version 4)
  - **폰에서 습관 추가 → DB 저장 → 재시작 후 유지 검증**
- **온보딩 → DB 저장 완료 (Task 16)**:
  - `MainActivity`가 `ProfileRepository`로 온보딩 완료 시 DB 저장
  - 앱 시작 시 `LaunchedEffect`로 프로필 조회 → 존재 시 대시보드 자동 진입
  - **폰에서 프로필 유지 검증 완료** (앱 재시작 시 온보딩 스킵)
- **메인 대시보드 완성 (Task 12, TDD)**:
  - `DashboardScreen` (TopBar + 오늘의 루틴 + FAB 2종)
  - `HabitCard` (아이콘 + 습관명 + 스트릭 배지 + 체크 버튼)
  - `Habit` 도메인 모델
  - `MainActivity`: 온보딩 → 대시보드 이동
- **핵심 비즈니스 로직 (CompleteHabitUseCase, TDD)**:
  - 습관 완료 시 조각 획득(일일 최대 10개 상한), 스트릭 갱신
  - 단위 테스트 (경계값 0/9/10/11 포함) PASSED
- **Repository 계층 (TDD)**:
  - `HabitRepository` (`addHabit`, `getAllHabits`)
  - `ProfileRepository` (`saveProfile`, `getProfile`)
- **로컬 DB 스키마 (TDD)**:
  - `HabitEntity` / `HabitDao` / `ProfileEntity` / `ProfileDao` / `CheckInEntity` / `CheckInDao`
  - `HabitHeroDatabase` 통합 (version 4, `fallbackToDestructiveMigration`)
- **온보딩 화면 (TDD)**: `OnboardingScreen` (닉네임 + 히어로 선택) + `OnboardingScreenTest` PASSED
- **개발 환경 구축**: AGP 9.5.1, Kotlin 2.2.10, Compose BOM 2026.02.01, Room 2.8.5, KSP, minSdk 26, JavaVersion 17
- **테스트 폴더 분리 원칙**: `test/` = 순수 Kotlin 로직 / `androidTest/` = Android 런타임 필요 (Room, Compose UI)

### Fixed
- SDK 경로 및 `JAVA_HOME` 미설정 문제 해결
- KSP ↔ AGP 9 Built-in Kotlin 충돌 및 Compose Preview 오류 (`isIncludeAndroidResources = true`)
- `espresso-core` 버전 충돌 해결 (3.7.0 → 3.5.0)
- `androidTest` 소스셋 경로 오류 및 Compose Test import 에러 수정
- `ProfileEntity` 스키마 변경(`lastOpenDate`)에 따른 테스트 동기화
- `TOML` 파일 BOM 삽입으로 인한 `TOML syntax error` 해결
- **UNIQUE 제약 위반으로 앱 크래시** (체크→언체크→체크 반복) → 언체크 시 DB 삭제로 해결
- `MainActivityTest` 실패 (DB에 프로필 남아 온보딩 화면 미표시) → 관대한 검증으로 변경

## [0.1.0] - 2026-11-11
### Added
- **기획 및 UI 프로토타입 단계 완료**:
  - REQUIREMENTS / DETAILED_SPEC / UI_SPEC / DESIGN 초기 버전
  - 온보딩, 모닝 팝업, 메인 대시보드 UI 시안(HTML/Tailwind)
  - Decision Log (Q-A, Q-B, Q-D, Q-XP, Q-RNK, Q-SKIP) 확정
  - **Q-TIMER** (5분 타이머 컨셉) 확정: 즉석 습관 등록 + 5분 완주 + 축하 화면

### Changed
- 앱 정식 명칭: Habit Keeper → **Habit Hero**
- 초기 등급 확정: **Tier B, Rank 0**
- 재화 관계 확정: 조각(레벨업 재화) / XP(표시용 누적 경험치) 이원화 → Task 21에서 XP 완전 폐지

## [0.0.1] - 2026-11-11
### Added
- 프로젝트 초기 스캐폴딩 (README, .gitignore, 디렉토리 구조)
- 문서 템플릿 초안 (REQUIREMENTS, DETAILED_SPEC)
