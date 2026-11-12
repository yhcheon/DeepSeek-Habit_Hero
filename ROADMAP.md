# Roadmap

향후 개발 계획과 아이디어를 우선순위별로 관리합니다.
진행 상황은 `[ ]` → `[x]` 로 체크합니다.

## 🔥 High Priority (다음 스프린트 / 즉시 구현)

### v1.1 후보
- [x] **프로필/설정 화면 (PRF-01)** — 히어로 변경, 닉네임 변경 (Task 18 완료)
- [x] **모닝 팝업 (ONB-02)** — 응원 카드 + 요약 + CTA (Task 19 완료, 테스트 모드: 매번 표시)
- [ ] **모닝 팝업 "하루 1회" 복원** — `ShouldShowMorningPopupUseCase` 연결 (한 줄 교체)
- [ ] **프로필 화면에 백업/복원 진입점 추가** — STAT-02 완료 시 연결
- [ ] **Tier/Rank 성장 UI (RWD-01)** — 진화 애니메이션, 레벨업 연출
- [ ] **패널티 시스템 (RWD-02)** — 자정 기준 미실천 판정 (조각 -1, Rank -1, 스트릭 리셋)
- [ ] **스트릭 마일스톤** — 7/14/30일 화염 보너스 XP
- [ ] **하단 네비게이션 4탭** — Quests / Focus / Stats / Ranks

## 📅 Medium Priority (단기 계획)
- [ ] **주간/월간 통계 (STAT-01)** — 달성률 그래프, 스트릭 추이
- [ ] **랭킹 화면 (RNK-01)** — 가상 NPC 비교 로컬 랭킹
- [ ] **히어로 패시브 효과 계산 반영** — Rune(+5% XP), Aria(+5% 스트릭 회복) (Q2 결정 대기)
- [ ] **HBT-03 스와이프 리스케줄** — 카드 좌우 스와이프
- [ ] **다국어 (i18n)** — KO/EN 완전 지원

## 💡 Backlog & Ideas (장기 / 아이디어 단계)
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

#### 개발 환경 & 스캐폴딩
- [x] 프로젝트 초기 구조 (Android Studio Empty Activity)
- [x] 개발 환경 구축 (AGP 9.5.1, Kotlin 2.2.10, Compose BOM 2026.02.01, Room 2.8.5, KSP)
- [x] 패키지 구조 확립 (`data` / `domain` / `ui`)
- [x] SDK 경로 + `JAVA_HOME` + USB 디버깅 설정

#### 기획 & 디자인
- [x] 프로젝트 문서 체계 확립 (REQUIREMENTS, DETAILED_SPEC, UI_SPEC, DESIGN, SETUP_GUIDE)
- [x] Decision Log 확정 (Q-A, Q-D, Q-XP, Q-RNK, Q-SKIP, Q-TIMER)
- [x] 디자인 시스템 정의 (Material 3 + Anime Gamification)
- [x] UI 시안 확보 (온보딩 / 모닝 팝업 / 메인 대시보드, 국/영문)

#### 로컬 DB (Room, TDD)
- [x] `HabitEntity` / `HabitDao` (CRUD)
- [x] `ProfileEntity` / `ProfileDao` (등급/조각/XP/스트릭/최근 접속일)
- [x] `CheckInEntity` / `CheckInDao` (일일 완료 기록)
- [x] `HabitHeroDatabase` 통합 (version 4, `fallbackToDestructiveMigration`)

#### 핵심 비즈니스 로직 (TDD)
- [x] `CompleteHabitUseCase` — 조각/XP/스트릭 계산 (경계값 0/9/10/11 포함)
- [x] `HabitRepository` / `ProfileRepository`

#### UI 화면 (TDD)
- [x] 첫 Compose 화면 (Scaffold + TopAppBar)
- [x] **온보딩 화면 (ONB-01)** — 닉네임 + 히어로 선택 + Starter Pack
- [x] **메인 대시보드 (HBT-02)** — TopBar + 오늘의 루틴 + FAB 2종
- [x] **HabitCard** — 아이콘 + 습관명 + 스트릭 배지 + XP 보상 + 체크 버튼
- [x] **AddHabitDialog** — 습관 이름 + XP 입력 (수정 모드 지원)
- [x] **HabitActionSheet** — 길게 누르기 → 수정/삭제 메뉴
- [x] **TimerScreen** — 5분 카운트다운 UI (5분 미달 시 완료 버튼 비활성)
- [x] **TimerContainer** — 1초마다 감소, 일시정지/재개
- [x] **CelebrationScreen** — "참 잘했어요!" ★★★★★ (1.5초 후 자동 복귀)

#### 기능 통합 (Task)
- [x] **Task 14: 습관 추가** — `+` FAB → 다이얼로그 → DB 저장 → 리스트 갱신
- [x] **Task 15: 체크/언체크** — 조각/XP 반영 + 상한 10개 + 재계산 + 크래시 해결
- [x] **Task 16: 온보딩 DB 저장** — 앱 재시작 시 프로필 유지
- [x] **Task 17: 습관 수정/삭제** — 길게 누르기 → 액션 시트 → 조각 재계산
- [x] **Task 13: 5분 타이머** — 즉석 습관 + 타이머 + 축하 화면

#### 폰 검증 완료
- [x] 온보딩 → 대시보드 → 습관 추가 → 체크 → 재시작 유지
- [x] 13개 습관 상한 10개 동작
- [x] 길게 누르기 → 수정/삭제
- [x] 5분 타이머 완주 → "참 잘했어요" → 습관 등록 + 조각 +1
- [x] 테스트 29개 PASSED

#### v1.0.0 릴리스 (2026-11-12)
- [x] CHANGELOG.md 최종 정리
- [x] README.md 작성
- [x] git tag v1.0.0
