# DETAILED_SPEC

# 기능사항 상세 설명서 (Detailed Functional Specification) v4.1

> **문서 버전**: 4.1 (2026-11-12 개정)
> **문서 상태**: ✅ **Final** (Task 21: XP 폐지 반영)
> **상위 문서**: REQUIREMENTS.md v3.1

## 0. 문서 목적 및 주요 변경 요약 (v4.1)
UI 시안(국/영문) 전면 반영. **Task 21: XP 시스템 완전 폐지, 조각 단일 재화로 통일.**
조각/랭크/스트릭/스와이프/모닝팝업/프로필/랭킹 시스템 통합.

## 1. 온보딩 모듈 (ONB)
### ① ONB-01: 앱 최초 실행 및 히어로 선택
- 2종 히어로 중 택 1 (Rune/Aria). Starter Pack 지급. Skip 시 Rune 기본값.
### ② ONB-02: 모닝 팝업 (앱 진입 응원)
- 당일 최초 진입 시 1회 노출. 응원 카드 + 오늘의 조각/스트릭 요약. CTA: `Start Quests!`

## 2. 습관 관리 모듈 (HBT)
### ① HBT-01: 습관 항목 추가 (최대 30개)
- 명칭/빈도/목표/아이콘/카테고리 설정. 30개 도달 시 추가 차단.
### ② HBT-02: 습관 항목 조회 (메인)
- 오늘의 퀘스트, 진행률, 습관 카드(스트릭 뱃지), Next Rank Teaser, 하단 FAB.
### ③ HBT-03: 수정/삭제/스와이프 리스케줄
- 카드 스와이프로 일정 변경 모달(`SWIPE TO RESCHEDULE`). 삭제 시 이력 보존 여부 미결(Q-H).

## 3. 미루기 방지 및 실행 모듈 (ACT)
### ① ACT-01: "5분만 하기" 타이머
- FAB/탭 진입. 백그라운드 유지(타임스탬프). 완료 시 체크인 유도.
### ② ACT-02: 습관 완료 체크인 및 조각 획득
- 조각 +1(일일 10개 상한), 스트릭 +1.
- 상한 초과 시: 완료 처리, 조각 미적립. 리셋 기준: **자정(00:00)**.

## 4. 육성 및 보상 모듈 (RWD)
### ① RWD-01: 히어로 등급(Tier) 및 랭크(Rank) 시스템
- **Tier**: B(시작) → A → S → 신화 (조각 100 누적 시 진화)
- **Q-A 확정**: 진화 시 **Rank만 0 리셋, 누적 조각 유지**
### ② RWD-02: 미실천 패널티 시스템
- 자정 기준 실천 0개 → 조각 -1, Rank -1, 스트릭 리셋.
- 안전장치: 음수 방지, 하위 Tier 강등 불가, **앱 미실행일에도 적용(Q-D 확정)**.
### ③ 스트릭 (연속 달성) 시스템
- 하루 1개 이상 실천 시 +1, 미실천 시 0 리셋. 7/14/30일 마일스톤 (보상 재정의 필요 — ROADMAP 참조).
### ④ 캐릭터 패시브 효과
- **Task 21 폐지**: 게임플레이 개입 없음. 히어로는 표시용 문자열(`"RUNE"`/`"ARIA"`)로만 유지.

## 5. 통계 및 로컬 관리 모듈 (STAT)
### ① STAT-01: 주간/월간 달성 리포트 (Medium 상향)
- 달성률, 스트릭 추이 시각화.
### ② STAT-02: 로컬 데이터 백업 및 복원 (Low)
- JSON Export/Import (SAF). 스키마 검증.

## 6. 랭킹 모듈 (RNK) — 신규
### ① RNK-01: 로컬 랭킹 시스템 (High)
- **Q-RNK 확정**: 가상 NPC와의 비교 랭킹. **주간 조각 합계 기준** (Task 21 재정의).

## 7. 프로필/설정 모듈 (PRF) — 신규
### ① PRF-01: 프로필 및 히어로 변경 (High)
- 히어로 변경 (표시용, 게임플레이 개입 없음), 닉네임 변경, 데이터 백업/복원 진입점.

## 8. 데이터 모델 (Room DB)
- `ProfileEntity`: id, nickname, hero, tier, rank, totalFragments, streak, lastOpenDate
- `HabitEntity`: id, profileId, name, cycle, category
- `CheckInEntity`: id, habitId, date (UNIQUE), completedAt, streakAtTime

> **Task 21**: `ProfileEntity.xp`, `HabitEntity.rewardXp` 컬럼 제거됨 (DB version 5).

## 9. Decision Log (최종 확정)
- **Q-A (진화 시 조각)**: Rank만 리셋, 누적 유지 ✅
- **Q-B (1조각=1레벨)**: 유지 ✅
- **Q-C (조각 0 음수 방지)**: 0 유지 ✅
- **Q-D (미실행일 패널티)**: 적용 ✅
- **Q-E (날짜 변경)**: 자정(00:00) ✅
- **Q-F (초과분 판정)**: 실천 인정 ✅
- **Q-XP (재화 관계)**: **폐지** (Task 21 — XP 완전 제거, 조각 단일 재화) 🔄
- **Q-RNK (랭킹)**: 가상 NPC 비교 ✅
- **Q-INT (팝업 노출)**: 당일 최초 1회 ✅
- **Q-SKIP (온보딩 스킵)**: Rune 기본값 ✅
- **Q-G, Q-H**: v1.1 이월 ⬜



### Q-TIMER (지금 5분만 하기 컨셉) — 확정 ✅
- **정의**: "지금 5분만 하기" = 즉석 습관 등록 + 5분 타이머 + 완료 시 정식 등록
- **DB 저장 시점**: 5분 완주 + 완료 클릭 시점에만 저장
- **포기/뒤로가기**: 아무것도 저장 안 함 (습관 등록조차 안 됨)
- **5분 미달 완료**: 버튼 비활성화 (강제 5분)
- **완주 후 UX**: 축하 화면 (별 5개) 1~2초 → 메인 자동 복귀
- **기존 체크 버튼**: 유지 (등록된 습관은 수동 완료 가능)





## 설계 문서 — PRF-01 프로필/설정 화면 (A안)

- 목표: 닉네임/히어로를 변경하고 로컬 DB에 영속화. 앱 재시작 후 유지.
- 범위 (In):
    - ProfileScreen 신규 (닉네임 편집, 히어로 카드 2종 선택, 저장)
    - DashboardScreen.onSettingsClick → ProfileScreen 진입 (TODO 채우기)
    - ProfileDao에 updateNickname/updateHero 추가
    - ProfileRepository에 위임 메서드 추가
    - 재시작 유지 검증
- 범위 (Out): 패시브 계산 반영(Task 21 폐지), 백업/복원(STAT-02), Tier 진화 UI(RWD-01)
- 아키텍처: 기존 패턴 유지. ViewModel 도입 안 함. MainActivity의 remember 상태 + 콜백.
    - 화면 전환: when 분기에 `showProfile` 상태 추가 (기존 showTimer/showCelebration과 동일 패턴)
- 기각 대안:
    - 별도 SettingsScreen 분리 → v1.1 탭 구조 도입 시 재검토 (현재 과설계)
    - Navigation Compose 도입 → 현재 when 분기로 충분, 도입은 4탭 작업 시 일괄
- 파일 구조:
    - ui/profile/ProfileScreen.kt (신규)
    - ui/profile/HeroOptionCard.kt (신규, 필요 시 OnboardingScreen 카드 로직 재사용 검토)
    - data/local/dao/ProfileDao.kt (UPDATE @Query 2개 추가)
    - data/repository/ProfileRepository.kt (updateNickname, updateHero)
    - MainActivity.kt (showProfile 상태 + onSettingsClick 연결 + 저장 콜백)
    - test/data/repository/ProfileRepositoryTest.kt (확장)
    - androidTest/ui/profile/ProfileScreenTest.kt (신규)
    - androidTest/data/local/ProfileDaoTest.kt (확장)
- 엣지 케이스:
    - 닉네임 공백/1자 미만 → 저장 버튼 비활성
    - 닉네임 최대 길이 (온보딩 규칙 확인 필요 — 미확인이면 20자 제한 적용)
    - 히어로 미변경(동일 선택) → DB write 생략
    - 저장 중 예외 → 스낵바 (기존 패턴에 스낵바 없으면 Toast)
    - 프로필 null (온보딩 미완료) → ProfileScreen 진입 자체가 불가(대시보드에서만 진입)
- 검증 방법:
    - 단위: ProfileRepositoryTest (updateNickname/updateHero 경계값)
    - UI: ProfileScreenTest (입력→저장 콜백, 버튼 비활성 조건)
    - 폰: 설정 진입 → 닉네임/히어로 변경 → 저장 → 재시작 → 대시보드 반영 확인
