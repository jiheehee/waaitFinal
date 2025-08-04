

# ERP시스템 waait
<div style="width: 100px; overflow: hidden;">
    <img src="https://github.com/user-attachments/assets/221ee9a3-5c1a-4ec2-8311-9d02b13c9830" alt="WAAIT Logo" style="width: 40%;">
</div>
<br>

"**WE ARE ALL IN THIS TOGETHER**"의 약자인 **WAAIT**은 협업과 소통을 중심으로 한 **사내 ERP 시스템**입니다. <br>
직관적인 UI를 통해 부서 간, 개인 간의 일정을 관리하고, 출퇴근 현황을 손쉽게 기록하며, 효율적인 조직 운영을 지원합니다.
<br>
<br>
<br>

---

<br>

## 📅 프로젝트 개요

* **프로젝트 기간**: 2024.06.15 \~ 2024.08.05
* **개발 인원**: 5명
* **담당 기능**: 일정 관리 기능 (FullCalendar API 기반 CRUD), <BR>
 근태 관리 기능 (출근/퇴근, 상태 자동 분류, 스케줄러 자동 처리)
* **기여 범위**: 기능 기획, DB설계, API 설계 및 구현, 트랜잭션 처리, 실시간 알림 구현

<br>
<br>

## 🧰 기술 스택

* **Language**: Java, JSP, HTML5, CSS3, JavaScript, SQL
* **Framework**: Spring Framework
* **ORM**: MyBatis
* **Database**: Oracle
* **Build Tool**: Maven
* **Server**: Apache Tomcat 8.5
* **Tools**: STS4, GitHub, SQL Developer, DBeaver
* **API/Libraries**: Google FullCalendar, Spring Scheduler, SSE(Server-Sent Events)

<br>
<br>

---
## 🗂 담당 기능 요약
<br>

### 1. 📆 일정관리 (캘린더)

* Google FullCalendar API 연동
* 로그인된 사용자 세션 기반의 **개인화 일정 접근 제어**
* 부서/개인 일정 등록, 수정, 삭제 기능
* 월/주/일 단위 조회 뷰 제공
* **Spring Security** 기반의 권한별 접근 제어

### 2. 🕒 근태관리

* 출근/퇴근 버튼 기반의 근태 등록 기능
* 출근 시간에 따른 상태 자동 분류 (정상/지각/결근)
* **트랜잭션 처리**를 통해 출퇴근 데이터 일관성 유지
* Spring Scheduler를 활용한 **근태 누락 자동 감지 로직 구현**
* 향후 관리자 알림 및 수정 요청 기능까지 확장 고려

### 3. 🔔 실시간 알림 (MyWorkList)

* 오늘의 일정 정보를 추출하여 메인화면에 표시
* **SSE(Server-Sent Events)** 기반의 실시간 일정 알림 기능 구현
* 서버에서 특정 이벤트 발생 시 자동으로 사용자에게 알림 전송

<br>

---
<br>

## 🔒 세션 기반 개인화 접근 제어 구현 흐름

* 로그인 시 Spring Security가 세션에 사용자 ID 저장
* 이후 컨트롤러나 인터셉터에서 세션 검사 → 사용자 맞춤 데이터 접근 허용

```java
HttpSession session = request.getSession(false);
if (session == null || session.getAttribute("userId") == null) {
    return "redirect:/login";
}
String userId = (String) session.getAttribute("userId");
```

<br>



## 🔄 근태 상태 자동 분류 로직 흐름

```java
if (출근시간.isBefore(LocalTime.of(9, 0))) {
    상태 = "정상";
} else if (출근시간.isBefore(LocalTime.of(9, 30))) {
    상태 = "지각";
} else {
    상태 = "결근";
}
```

<br>


## 🔁 트랜잭션 처리 적용 흐름

* 출근/퇴근 데이터는 반드시 한 트랜잭션 내에서 처리되도록 구성

```java
@Transactional
public void updateAttendance(...) {
    // 출근 저장
    // 퇴근 저장
    // 실패 시 전체 롤백
}
```

<br>

## 🕛 근태 누락 자동 감지 로직 흐름 (Spring Scheduler)

* 매일 자정 스케줄링 실행 → 전체 직원의 출근 데이터 점검
* 출근 기록이 없는 경우 자동으로 결근 처리
* 반복 업무 자동화 및 데이터 누락 선제 방지

```java
@Scheduled(cron = "0 0 0 * * *")
public void checkMissingAttendance() {
    // 근태 누락 직원 → 결근 처리
}
```

<br>

---

<br>

## 📌 담당 기능의 차별화 포인트

* 실제 조직 규칙을 기반으로 한 **상태 자동 분류 및 예외처리 설계**
* **개인화된 일정 접근**을 위한 세션 기반 권한 처리
* **트랜잭션 처리 + 스케줄러 + 실시간 알림**까지 결합한 통합 기능 설계

<br>

## 📌 주요 문제 해결 

* **문제**: 근태 입력 시 간헐적 충돌 및 중복 발생
* **해결**: 상태 분류 로직과 트랜잭션 범위를 직접 분석 및 재설계 → 일관성 확보
* **결과**: 데이터 정확도 및 기능 신뢰성 향상

<br>

---

<br>

## ✅ 핵심 기술 요약

| 기술               | 활용 방식                 |
| ---------------- | --------------------- |
| Spring Security  | 로그인, 세션 기반 인증 및 접근 제어 |
| MyBatis          | SQL 매핑 및 쿼리 최적화       |
| Oracle + DBeaver | ERD 설계 및 데이터 검증       |
| FullCalendar API | 일정 시각화 및 CRUD 기능 구현   |
| Spring Scheduler | 근태 누락 자동 감지 기능 구현     |
| SSE              | 실시간 일정 알림 구현          |


<br>

---

<br>

## 🖼 주요 화면 예시

| 캘린더 화면                                                                                     | 공유일정 화면                                                                                | 근태관리 화면                                                                              |
| -------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------- |
| ![캘린더](https://github.com/user-attachments/assets/518ffc62-3f5e-4f7b-bbe3-84d40e2c315f) | ![공유일정](https://github.com/user-attachments/assets/e9c664f4-e290-44f2-94c6-2d350fdb3a49) | ![근태관리](https://github.com/user-attachments/assets/b2ccb8f2-532b-49e3-a720-741063cd61cb) |

<br>
<br>

## ⚙ 프로젝트 구조

```
📦 waaitfinal
 ┣ 📂src
 ┃ ┣ 📂main
 ┃ ┃ ┣ 📂java
 ┃ ┃ ┃ ┗ 📂com.waait
 ┃ ┃ ┃   ┣ 📂common
 ┃ ┃ ┃   ┃   ┗ 공통 상수, 유틸 정의
 ┃ ┃ ┃   ┣ 📂config
 ┃ ┃ ┃   ┃   ┣ SecurityConfig.java       # 개인화 접근제어(권한별 페이지 접근 제한)
 ┃ ┃ ┃   ┃   ┗ InterceptorConfig.java    # 로그인 체크 등 인터셉터 설정
 ┃ ┃ ┃   ┣ 📂controller
 ┃ ┃ ┃   ┃   ┣ ScheduleController.java   # 일정관리 (FullCalendar 연동)
 ┃ ┃ ┃   ┃   ┣ CommuteController.java    # 근태관리 (출퇴근 등록 및 조회)
 ┃ ┃ ┃   ┃   ┣ MyWorkListController.java # MyWorkList 상태 자동 분류
 ┃ ┃ ┃   ┃   ┗ AlarmController.java      # 알림 기능 (SSE 연결)
 ┃ ┃ ┃   ┣ 📂dao
 ┃ ┃ ┃   ┃   ┗ Mapper 인터페이스 (일정, 근태 등 DB 직접 접근)
 ┃ ┃ ┃   ┣ 📂dto
 ┃ ┃ ┃   ┃   ┣ CalendarDTO.java          # 일정 정보 DTO
 ┃ ┃ ┃   ┃   ┣ CommuteDTO.java           # 근태 정보 DTO
 ┃ ┃ ┃   ┃   ┗ WorkListDTO.java          # MyWorkList DTO
 ┃ ┃ ┃   ┣ 📂service
 ┃ ┃ ┃   ┃   ┣ ScheduleService.java      # 일정 CRUD, 달력 형식 반환
 ┃ ┃ ┃   ┃   ┣ CommuteService.java       # 출퇴근 처리 로직, 트랜잭션 포함
 ┃ ┃ ┃   ┃   ┣ MyWorkListService.java    # 상태값에 따라 자동 분류 처리
 ┃ ┃ ┃   ┃   ┗ AlarmService.java         # 알림 전송(SSE) 및 로그 저장
 ┃ ┃ ┃   ┗ 📂security.controller
 ┃ ┃ ┃       ┗ 로그인 및 권한 인증 관련
 ┃ ┃ ┗ 📂resources
 ┃ ┃   ┣ 📂mapper
 ┃ ┃   ┃   ┗ 일정/근태 관련 XML 매퍼 (조건 분기, 날짜 계산 포함)
 ┃ ┃   ┗ 📄application.properties         # DB 연결, 스케줄러 등 전역 설정
 ┣ 📂webapp
 ┃ ┣ 📂resources
 ┃ ┃ ┗ 📂upload/codeReview               # 코드리뷰 파일 업로드
 ┃ ┗ 📂WEB-INF/views
 ┃   ┣ 📂schedule                         # 일정 조회 및 등록 (FullCalendar 적용)
 ┃   ┣ 📂empmanage                        # 근태관리 화면 (출근, 퇴근 버튼 등)
 ┃   ┣ 📂mypage                           # 개인 MyWorkList 관리 화면
 ┃   ┣ 📂common, error 등                 # 공통 템플릿 및 오류 화면
 ┣ 📂test
 ┃ ┗ 📂com.waait.test                    # 단위 테스트
 ┣ 📄pom.xml                              # Spring Boot, MyBatis, Security, SSE 등 의존성 관리
 ┗ 📂target                               # 빌드 산출물
```

<br>

---

<br>

## 🧑‍💻 Git & 협업 방식

* GitHub 기반의 Branch 전략 운영
* 개인 기능 개발 → 팀 리뷰 → 병합
* ERD/기능정의서/설계 문서 공유 → Notion 및 Google Drive 활용

 <BR>
 
 ## 📄 프로젝트 산출물
 
| ![WBS](https://github.com/user-attachments/assets/3b6a6f8f-8fd8-4d8a-aa27-f3d67c324739) | ![테이블정의서](https://github.com/user-attachments/assets/a9a878fc-15e9-4e1a-ace3-0fa1a61b30ce) |
|:--:|:--:|
| WBS | 테이블정의서 |

| ![기능정의서](https://github.com/user-attachments/assets/7c3b30a7-6221-4f51-9ad9-692ac03e19c1) | ![구현정리사항](https://github.com/user-attachments/assets/131f2368-2f5c-4ab2-bfd0-0a8387fe5fc5) |
|:--:|:--:|
| 기능정의서 | 구현정리사항 |






<br>

---

<br>

## 🔚 마무리

해당 프로젝트를 통해 **업무 규칙 기반 설계**, **개인화 기능 구현**, **반복 업무 자동화** 등 실무 환경에 필요한 기능들을 직접 설계하고 구현해보았습니다. <br> <br> 
FullCalendar 기반 일정 관리, SSE 실시간 알림, 스케줄러를 활용한 반복 업무 자동화는 <br> 
단순 기능 구현을 넘어, 실제 업무 흐름과 사용자 입장을 고려해 설계한 점에서 특히 흥미로웠습니다. <br><br>
또한 트랜잭션 제어, 예외 처리, 역할 기반 접근 제어까지 직접 구현하면서 유기적으로 연동되는 구조에 대한 감각을 키울 수 있었고, <br>
향후에는 **관리자 알림 및 수정 요청 기능까지 확장**하며 더 실용적인 시스템으로 발전시킬 계획입니다.

<br>
<br>
<br> 
<br> 
<br> 
<br> 
<br> 
