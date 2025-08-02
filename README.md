

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

* **프로젝트 기간**: 2024.06.27 \~ 2024.08.05
* **주요 역할**: 일정관리 및 근태관리 기능 담당
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

## 🖼 주요 화면 예시

| 일정 관리 화면                                                                                     | 근태 등록 화면                                                                                | 실시간 알림 화면                                                                              |
| -------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------- |
| ![calendar](https://github.com/user-attachments/assets/1a39ea34-94fb-4eef-979f-6c16b1b4e9d4) | ![출퇴근](https://github.com/user-attachments/assets/1c463307-7930-4d36-824e-1282f92e984e) | ![알림](https://github.com/user-attachments/assets/420729af-47a9-422e-9c56-939497564c3f) |

---

## 🧑‍💻 Git & 협업 방식

* GitHub 기반의 Branch 전략 운영
* 개인 기능 개발 → 팀 리뷰 → 병합
* ERD/기능정의서/설계 문서 공유 → Notion 및 Google Drive 활용


<br>

---

## 🔚 마무리

해당 프로젝트를 통해1 **업무 규칙 기반 설계**, **개인화 기능 구현**, **반복 업무 자동화** 등 실제 업무 환경에 필요한 기능들을 직접 설계하고 구현해보았습니다. 
<br>
데이터 흐름을 분석하고, 예외를 방지하며, 사용자 중심의 서비스 제공에 집중했습니다.

<br>
<br>
