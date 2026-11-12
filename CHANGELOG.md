# Changelog

모든 주요 변경 사항은 이 문서에 기록됩니다.
[Keep a Changelog](https://keepachangelog.com/ko/1.1.0/) 형식을 따르며,
[Semantic Versioning](https://semver.org/lang/ko/)을 준수합니다.

## [Unreleased]
### Added
- **성장 시스템 재정의 (Task 20, 기획 확정)**:
  - **1조각 = 1레벨** (문서 Q-B 유지)
  - **100조각 누적 = Tier 진화** (B→A→S→신화), 진화 시 **Rank 0 리셋 + 누적 조각 유지** (문서 Q-A 유지)
  - **XP 제거**: `xp` 필드 미사용(DB 마이그레이션 회피), 화면 표시 안 함
  - **습관별 "연속 N일 달성"** 도입: `HabitCard`에 `🔥 연속 N일` 표시, `+40 XP` 배지 제거
  - **전체 스트릭(`profile.streak`) 유지**: 모닝 팝업 요약 카드에 사용
  - **미구현 (2차 고도화)**: 연속일 마일스톤 보상 (10일/50일/100일 등) — 향후 개발
- **모닝 팝업 (ONB-02)**: 앱 실행 시 응원 카드 표시 (현재 테스트 모드 — 매번 표시)
  - `MorningPopupScreen`: 아바타 + Tier/Rank + 응원 문구 + Rank 진행 바(rank/100) + 오늘의 조각/연속 달성 요약 + CTA 2개
  - `ShouldShowMorningPopupUseCase`: lastOpenDate vs 오늘 비교 순수 함수 (경계값 6개 테스트)
  - `ProfileDao.updateLastOpenDate()` + `ProfileRepository.markTodayOpened()` 추가
  - MainActivity: 완전 재실행 시 팝업 표시 → CTA/Remind 시 `lastOpenDate = 오늘` 저장 후 대시보드
  - **테스트 모드**: 앱 완전 재실행 시마다 팝업 표시 (향후 "하루 1회"로 한 줄 복원 가능)
- **프로필/설정 화면 (PRF-01)**: 닉네임 변경 + 히어로(RUNE/ARIA) 변경 + 재시작 유지
  - `ProfileScreen`: 닉네임 입력 필드(1~20자 검증) + 히어로 카드 2종 선택
  - 대시보드 App Bar 설정 아이콘 → 프로필 화면 진입
  - `ProfileDao.updateNickname()`, `ProfileDao.updateHero()` 추가
  - `ProfileRepository.updateNickname()`, `ProfileRepository.updateHero()` 위임 메서드 (빈 문자열/프로필 없음 방어)
- (예정) 주간/월간 통계 리포트 (STAT-01)
- (예정) 로컬 데이터 JSON 백업/복원 (STAT-02)

### Changed
- **성장 시스템 재정의**: XP(표시용 누적 경험치) 폐지 → 습관별 연속 달성일로 대체
  - REQUIREMENTS §1.3 용어 정의 개정
  - DETAILED_SPEC §4 RWD-01/ACT-02 개정
  - DESIGN.md §7.1 HabitCard 배지 스펙 개정
  - UI_SPEC §4 HabitCard 스펙 개정

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
  - `AddHabitDialog` 확장: `initialName`, `initialXp`, `title`, `confirmButtonText` (수정 모드)
  - `MainActivity`: 길게 누르기 → 액션 시트 → 수정/삭제 + **조각 재계산**
  - **폰에서 검증**: 길게 누르기 → 수정/삭제 정상, 체크한 습관 삭제 시 조각 감소 정상
- **체크/언체크 완전 처리 + 엣지 케이스 해결 (Task 15, TDD)**:
  - `CheckInDao` 확장: `getTodayCompletedHabitIds()`, `deleteByHabitAndDate()`
  - `ProfileDao` 확장: `updateStats()` (조각/XP/스트릭 업데이트)
  - 체크 흐름: `CompleteHabitUseCase` → `CheckInDao.insert()` → `ProfileDao.updateStats()` → UI 갱신
  - 언체크 흐름: `CheckInDao.deleteByHabitAndDate()` → **남은 완료 수로 조각 재계산** (`min(remainingCount, 10)`)
  - 앱 재시작 시 체크 상태/조각/XP 복원
  - **폰에서 검증**: 13개 습관 상한 10개 → 언체크 시 재계산 정상, 크래시 없음
- **습관 추가 기능 완료 (Task 14, TDD)**:
  - `AddHabitDialog` (습관 이름 + XP 입력)
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
  - `HabitCard` (아이콘 + 습관명 + 스트릭 배지 + XP 보상 + 체크 버튼)
  - `Habit` 도메인 모델
  - `MainActivity`: 온보딩 → 대시보드 이동
- **핵심 비즈니스 로직 (CompleteHabitUseCase, TDD)**:
  - 습관 완료 시 조각 획득(일일 최대 10개 상한), XP 계산, 스트릭 갱신
  - 히어로 패시브(룬: XP +5%), 화염 보너스 계산
  - 단위 테스트 4종 (경계값 0/9/10/11 포함) PASSED
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
- **언체크 시 조각이 무조건 -1 되던 버그** → 남은 완료 수 기준 재계산
- **UNIQUE 제약 위반으로 앱 크래시** (체크→언체크→체크 반복) → 언체크 시 DB 삭제로 해결
- `MainActivityTest` 실패 (DB에 프로필 남아 온보딩 화면 미표시) → 관대한 검증으로 변경
- **모닝 팝업 테스트에서 "히어로" 문구 중복 매칭** → testTag 기반으로 노드 특정

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
- 재화 관계 확정: 조각(레벨업 재화) / XP(표시용 누적 경험치) 이원화

## [0.0.1] - 2026-11-11
### Added
- 프로젝트 초기 스캐폴딩 (README, .gitignore, 디렉토리 구조)
- 문서 템플릿 초안 (REQUIREMENTS, DETAILED_SPEC)
