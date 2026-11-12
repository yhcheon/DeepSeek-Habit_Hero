# UI_SPEC

# UI Specification — Habit Hero v2.0

> **문서 상태**: ✅ Final Draft (국/영문 시안 반영)
> **상위 문서**: DESIGN.md, DETAILED_SPEC.md

## 1. 화면 인벤토리 (IA)
- **Onboarding**: 히어로 선택 (ONB-01)
- **Morning Popup**: 당일 첫 진입 응원 (ONB-02 / INT-01)
- **Main Dashboard (Quests)**: 오늘의 퀘스트 및 습관 목록 (HBT-02)
- **Bottom Nav**: `Quests` / `Focus` / `Stats` / `Ranks`

## 2. 온보딩: 히어로 선택 (ONB-01)
- **레이아웃**: 상단 Skip, `STEP 1/1` 칩. 캐릭터 카드 2종(Rune/Aria). 카드 내 패시브 특성 표시. Starter Pack 카드. Primary CTA `Start Adventure`.
- **컴포넌트**: 캐릭터 카드 `rounded-lg`, 선택 시 `primary` 테두리. Tier 뱃지 `rounded-full`.

## 3. 모닝 팝업 (INT-01)
- **레이아웃**: Dim 배경. 응원 카드(`HERO MORNING CHEER`). 아바타 + Tier 뱃지. XP 진행 바. 요약 카드 2열(오늘의 조각, 연속 달성). CTA `Start Quests!`, 보조 `Remind Me Later`.

## 4. 메인 대시보드 (HBT-02)
- **App Bar**: 아바타, 닉네임, `Lv.X`, 설정 아이콘.
- **Quest Card**: 진행률 Progress Bar, 필터 탭(All/Active/Completed).
- **Quest Log**: 습관 카드(아이콘, 명칭, 목표 달성도, 🔥연속일수, +XP 뱃지, 원형 체크 버튼).
- **Next Rank Teaser**: `Next Rank: Rank A Master`.
- **FAB**: `⚡ 5-Min Quick Focus`, `+` (습관 추가).

## 5. 공통 컴포넌트
- **체크 애니메이션**: `spring(dampingRatio = 0.6f, stiffness = Medium)`.
- **스와이프 제스처**: Quest Log에서 좌우 스와이프 시 리스케줄.
- **레벨업 애니메이션**: XP 100% 도달 시 골드(`#F59E0B`) 펄스.

