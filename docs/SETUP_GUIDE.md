
# Habit Hero: 개발 및 실행 환경 구축 가이드 v3.0

> **문서 상태**: ✅ 확정 (2026-11-11 개정)
> **대상 독자**: 신규 합류 개발자, QA
> **개정 사유**: 최신 Android Studio(Quail 1) 및 AGP 9.x, KSP, Room 환경 반영

## 0. 사전 준비물 체크리스트
- [ ] JDK 17 (Android Studio 내장 JBR 사용 권장)
- [ ] Android Studio Quail 1 (2026.1.1) 이상
- [ ] 디스크 여유 공간 20GB 이상 (SDK 및 에뮬레이터 포함)
- [ ] RAM 16GB 이상 권장 (에뮬레이터 사용 시)

## 1. 개발 환경 표준 사양
- **OS**: Windows 10/11, macOS Ventura 이상, Linux
- **JDK**: OpenJDK 17 (Android Studio 내장 `jbr` 사용)
- **Android SDK**: Compile 37 / Target 37 / Min 26
- **Build Tools**: 36.0.0
- **Build System**: Gradle 9.6.0, AGP 9.5.1
- **Kotlin**: 2.2.10
- **Compose BOM**: 2026.02.01
- **Room**: 2.8.5 (KSP 사용)

## 2. 개발 환경 설치 및 설정
### 2.1 JDK 17 설정
- Android Studio 내장 JDK(`jbr`)를 사용하므로 별도 설치 불필요.
- 터미널(CLI)에서 빌드 시 `JAVA_HOME` 환경 변수가 필요합니다.
    - `JAVA_HOME` = `D:\Android\Android Studio\jbr` (예시)
    - `Path`에 `%JAVA_HOME%\bin` 추가

### 2.2 Android Studio 및 SDK 설정
- Android Studio Quail 1 이상 설치.
- **SDK Manager** 실행 후 다음 항목 설치:
    - **SDK Platforms**: Android 16 (API 36), Android 15 (API 35)
    - **SDK Tools**: Build-Tools 36.0.0, Platform-Tools, Emulator, Command-line Tools
- `local.properties` 파일에 SDK 경로 지정 (예: `sdk.dir=D:/Android/Sdk`)

## 3. 프로젝트 가져오기 및 빌드
- Android Studio Open → 루트 폴더(`D:\work\DeepSeek\HabitHero`) 선택 → Gradle Sync 대기.
- **Gradle JDK** 설정: `Settings > Build Tools > Gradle > Gradle JDK` → **jbr-17** 선택.
- CLI 빌드: `.\gradlew.bat clean assembleDebug`

## 4. 실행 환경 구축 및 앱 실행
### 4.1 실제 스마트폰 (권장)
- 폰 설정 > 휴대전화 정보 > 빌드번호 7회 터치 → 개발자 옵션 활성화.
- **USB 디버깅** ON → PC 연결 → 폰에서 "USB 디버깅 허용" 승인.
- Android Studio 상단 기기 목록에서 폰 선택 → Run(▶).

### 4.2 에뮬레이터 (선택)
- Device Manager → Pixel 8 이상 → API 36 시스템 이미지 다운로드 → Run.

## 5. CLI 기반 빌드/설치
- `.\gradlew.bat assembleDebug` (빌드)
- `.\gradlew.bat installDebug` (설치)
- `& "D:\Android\Sdk\platform-tools\adb.exe" devices` (기기 확인)

## 6. 트러블슈팅 (경험 기반)
| 증상 | 원인 | 해결 |
|---|---|---|
| `SDK location not found` | `local.properties` 미설정 또는 경로 오류 | `sdk.dir=D:/Android/Sdk` 형식으로 수정 (슬래시 권장) |
| `JAVA_HOME is not set` | 시스템 환경 변수 미설정 | JDK 17 경로를 `JAVA_HOME`에 등록 후 터미널 재시작 |
| `Using kotlin.sourceSets DSL...` | KSP ↔ AGP 9 Built-in Kotlin 충돌 | KSP 2.3.6+ 및 Kotlin 2.3.x로 상향 또는 `android.disallowKotlinSourceSets=false` 임시 설정 |
| `Compose Preview failed...` | `isIncludeAndroidResources=false` | `testOptions { unitTests { isIncludeAndroidResources = true } }` 추가 |
| `Device Unauthorized` | 폰에서 USB 디버깅 미승인 | USB 재연결 후 팝업에서 "허용" 체크 |

## 7. 부록: 유용한 명령어 & 경로
- **Gradle 캐시**: `%USERPROFILE%\.gradle`
- **SDK 경로**: `D:\Android\Sdk`
- **AVD 경로**: `%USERPROFILE%\.android\avd`
- **adb 위치**: `D:\Android\Sdk\platform-tools\adb.exe`

