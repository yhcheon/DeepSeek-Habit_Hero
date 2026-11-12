# Design System — Habit Hero

> 버전: 2.0 (2026-11-12)
> 근거: `code.html` 프로토타입 실측값
> 상위 문서: 없음 (이 문서가 디자인 토큰의 원천)

---

## 1. 브랜드 컨셉

- **컨셉**: "미루기 악당을 극복하고 나만의 만화 주인공을 히어로로 성장시키는 습관 게이머"
- **스타일**: Material 3 + Anime Gamification + Cyberpunk Hunter 톤
- **모드**: 라이트 모드 기본 (다크 모드는 v1.1에서)

---

## 2. 컬러 팔레트 (Material 3 토큰)

### 2.1 라이트 테마 (기본)

| 토큰 | HEX | 용도 |
|---|---|---|
| **primary** | `#004AC6` | 핵심 브랜드 (히어로 코발트) |
| **primary-container** | `#2563EB` | 강조 액션, FAB, 선택 상태 |
| **on-primary** | `#FFFFFF` | primary 위 텍스트 |
| **on-primary-container** | `#EEEFFF` | primary-container 위 텍스트 |
| **secondary** | `#9D4300` | 스트릭(불꽃), 경고 |
| **secondary-container** | `#FD761A` | 스트릭 배지, 화염 |
| **on-secondary** | `#FFFFFF` | secondary 위 텍스트 |
| **tertiary** | `#006242` | 성공(완료), 체크 |
| **tertiary-container** | `#007D55` | 완료 카드 배경 |
| **on-tertiary** | `#FFFFFF` | tertiary 위 텍스트 |
| **error** | `#BA1A1A` | 삭제, 오류 |
| **error-container** | `#FFDAD6` | 오류 배경 |
| **background** | `#F8F9FF` | 앱 배경 |
| **on-background** | `#0B1C30` | 배경 위 텍스트 |
| **surface** | `#F8F9FF` | 카드 배경 |
| **surface-container-lowest** | `#FFFFFF` | 최상위 카드 |
| **surface-container-low** | `#EFF4FF` | 보조 카드 |
| **surface-container** | `#E5EEFF` | 중간 컨테이너 |
| **surface-container-high** | `#DCE9FF` | 강조 컨테이너 |
| **surface-container-highest** | `#D3E4FE` | 최상위 컨테이너 |
| **on-surface** | `#0B1C30` | 표면 위 텍스트 |
| **on-surface-variant** | `#434655` | 부가 텍스트 |
| **outline** | `#737686` | 테두리 |
| **outline-variant** | `#C3C6D7` | 약한 테두리 |

### 2.2 다크 테마 (v1.1 예정, 참고)

| 토큰 | HEX | 용도 |
|---|---|---|
| **primary** | `#C0C1FF` | 네온 라벤더 |
| **secondary** | `#FFB95F` | 사이버 앰버 |
| **tertiary** | `#4EDEA3` | 네온 민트 |
| **background** | `#0B1326` | 딥 네이비 |
| **surface** | `#0B1326` | 다크 슬레이트 |

---

## 3. 타이포그래피 (Plus Jakarta Sans + Noto Sans KR)

| 토큰 | 폰트 | 크기 | 굵기 | 줄높이 | 용도 |
|---|---|---|---|---|---|
| **display-lg** | Plus Jakarta Sans | 40px | 800 | 48px | 대형 히어로 텍스트 |
| **display-lg-mobile** | Plus Jakarta Sans | 32px | 800 | 40px | 모바일 대형 |
| **headline-lg** | Plus Jakarta Sans | 28px | 700 | 36px | 화면 타이틀 |
| **headline-md** | Plus Jakarta Sans | 22px | 700 | 28px | 섹션 헤더 |
| **headline-sm** | Plus Jakarta Sans | 18px | 600 | 24px | 카드 타이틀 |
| **body-lg** | Noto Sans KR | 16px | 400 | 24px | 본문 |
| **body-md** | Noto Sans KR | 14px | 400 | 20px | 서브 본문 |
| **body-sm** | Noto Sans KR | 12px | 400 | 16px | 캡션 |
| **label-lg** | Plus Jakarta Sans | 14px | 700 | 20px | 주요 버튼 |
| **label-md** | Plus Jakarta Sans | 12px | 700 | 16px | 뱃지 |
| **label-sm** | Plus Jakarta Sans | 11px | 800 | 14px | 소형 뱃지 |

**한글 폴백**: Noto Sans KR (없으면 시스템 Pretendard)

---

## 4. Spacing & Grid (8dp 베이스라인)

| 토큰 | 값 | 용도 |
|---|---|---|
| **space-xs** | 4dp | 아이콘 오프셋 |
| **space-sm** | 8dp | 인라인 요소 간격 |
| **space-md** | 16dp | 카드 내부 패딩 |
| **space-lg** | 24dp | 카드 간 간격 |
| **space-xl** | 32dp | 섹션 간 간격 |
| **margin** | 16dp | 화면 가장자리 |
| **gutter** | 16dp | 요소 간 |

**카드 내부**: 16dp 패딩
**카드 간**: 8dp 간격

---

## 5. Shape (Rounded Corner)

| 토큰 | 값 | 용도 |
|---|---|---|
| **rounded-sm** | 4dp | 아이콘 배지 |
| **rounded-DEFAULT** | 8dp | 소형 버튼 |
| **rounded-md** | 12dp | 아이콘 컨테이너 |
| **rounded-lg** | 16dp | **카드 (표준)** |
| **rounded-xl** | 24dp | 바텀시트, 대형 카드 |
| **rounded-full** | 9999dp | Pill 버튼, FAB, 배지 |

---

## 6. Elevation (그림자)

| 레벨 | 그림자 | 용도 |
|---|---|---|
| **Level 0** | 없음 | 앱 배경 |
| **Level 1** | `0px 2px 8px -2px rgba(15,23,42,0.06)` | 일반 카드 |
| **Level 2** | `0px 8px 24px -4px rgba(37,99,235,0.08)` | FAB, 다이얼로그 |
| **Level 3** | 완료 카드: `inset 0 0 0 1px rgba(16,185,129,0.25)` | 완료 상태 |
| **터치 피드백** | scale(0.98) | 버튼 누름 |

---

## 7. 핵심 컴포넌트

### 7.1 HabitCard (습관 카드)
- 배경: `surface` (`#F8F9FF`)
- 둥글기: `rounded-lg` (16dp)
- 패딩: 16dp
- **아이콘**: 44dp 정사각형, `rounded-md`, `primary-container` 배경
- **타이틀**: `headline-sm` (18px, 600)
- **스트릭 배지**: Pill, `secondary-container` (`#FD761A`), "🔥 14일"
- **XP 배지**: Pill, `primary-container` (`#2563EB`), "+40 XP"
- **체크 버튼**: 44dp 원형
  - 미완료: `outline-variant` 테두리
  - 완료: `primary` 채우기 + 흰색 체크

### 7.2 FAB (플로팅 액션 버튼)
- **"지금 5분만 하기"**: Extended FAB, `primary-container` (`#2563EB`), 흰색 텍스트
- **"+"**: 원형 FAB, `primary-container`, 44dp

### 7.3 배지
- **Tier 배지**: `on-surface` 배경(85% 투명), 흰색 텍스트, "B급 Lv.12"
- **조각 카운터**: Pill, `secondary-container`, "💎 8 / 10"

### 7.4 다이얼로그 (AlertDialog)
- 둥글기: `rounded-xl` (24dp)
- 배경: `surface-container-lowest` (`#FFFFFF`)
- 타이틀: `headline-md` (22px, 700)
- 버튼: Pill, "취소"(outlined) / "확인"(primary)

---

## 8. 아이콘 & 이모지

- **시스템 아이콘**: Material Symbols Outlined
- **이모지 사용**: 습관 아이콘(💧📖🧘⚡✍️), 조각(💎), 불꽃(🔥), 별(★), 체크(✓)
- **모든 이모지**: 시스템 폰트 사용 (Noto Color Emoji)

---

## 9. 애니메이션

| 요소 | 스펙 |
|---|---|
| **체크 애니메이션** | `spring(dampingRatio = 0.6f, stiffness = Medium)` |
| **FAB 누름** | `scale(0.98)`, 150ms |
| **축하 화면 별** | 1.5초 표시 후 자동 복귀 |
| **타이머 원형** | 색상 전환 (primary → tertiary) |

---

## 10. 참고

- **Compose 구현**: `ui/theme/Theme.kt`, `Color.kt`, `Type.kt`
- **프로토타입**: `code.html` (이전 세션 자료)
- **다크 모드**: v1.1에서 정식 지원 (토큰은 §2.2 참고)
