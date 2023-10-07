# wanted-pre-onboarding-backend
wanted-pre-onboarding-backend assignment

## 요구사항 분석

1. 엔티티 모델링
 * 사용자 (User)
   - UserId, UserName을 속성으로 가짐
   - Primary Key로 UserId 사용
 * 회사 (Company)
   - CompanyId와 CompanyName을 속성으로 가짐
   - Primary Key로 CompanyId 사용
   - CompanyId는 등록 시 변경될 수 없음
   - Foreign Key로 지정되기 위하여 Company Name은 Unique로 지정되어야 함
  
 * 채용공고 (Recruitment)
   - RecruiteId, CompanyId, CompanyName, Country, Region, RecruitePosition, RecruiteReward, TechStack, RecruiteBody을 속성으로 가짐
   - Primary Key로 RecruiteId를 사용
   - 회사와 1:N 관계, Foreign Key로 CompanyId와 CompanyName 사용
   - 외래키 옵션은 Cascade로, 회사 데이터 삭제 시 채용공고 데이터도 삭제
  
 * 지원내역 (Appliement)
   - RecruiteId, UserId를 속성으로 가짐
   - Primary Key로 RecruiteId, UserId를 사용
   - 채용공고와 1:N 관계, UserId와 1:N 관계, Foreign Key로 RecruiteId, UserId를 사용
   - 외래키 옵션은 Cascade로, 채용공고 데이터 삭제 혹은 사용자 데이터 삭제 시 지원내역 데이터도 삭제
   - "사용자는 1회만 지원가능"의 제약조건에 의해 RecruiteId와 UserId는 Unique여야 함
     
2. 서비스별 요구사항
   1. 채용공고 등록
      - 회사는 채용공고 엔티티 구조를 사용하여 채용공고 등록
      - 회사 정보가 데이터베이스에 존재하지 않는 경우, 오류 출력 (회사 등록이 필요함)
      - 채용공고의 FK, PK가 채워지지 않은 경우, 오류 출력 (속성 ***가 채워지지 않음)
   2. 채용공고 수정
   3. 채용공고 삭제
   4. 채용공고 목록 불러오기
     1. 채용공고 목록 확인
     2. 채용공고 검색
   5. 채용 상세 페이지 (채용내용과 해당회사가 올린 다른 채용공고 포함) 출력   
   6. 사용자의 채용공고 지원
 
## 구현과정

1. 회사, 채용공고 엔티티 구현
----
2. 채용공고 등록 (회사등록), 채용공고 수정, 채용공고 삭제 기능 구현
----
