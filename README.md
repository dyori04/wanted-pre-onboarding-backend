# wanted-pre-onboarding-backend
wanted-pre-onboarding-backend assignment

## 요구사항 분석

### 1. 엔티티 모델링
 * 사용자 (User)
   - UserId, UserName을 속성으로 가짐
   - Primary Key로 UserId 사용
 * 회사 (Company)
   - CompanyId와 CompanyName을 속성으로 가짐
   - Primary Key로 CompanyId 사용
   - CompanyId는 등록 시 변경될 수 없음
   - Foreign Key로 지정되기 위하여 Company Name은 Unique로 지정되어야 함
  
 * 채용공고 (Recruitment)
   - RecruitId, CompanyId, Country, Region, RecruitPosition, RecruitReward, TechStack, RecruitBody을 속성으로 가짐
   - Primary Key로 RecruitId를 사용
   - 회사와 1:N 관계, Foreign Key로 CompanyId 사용
   - 외래키 옵션은 Cascade로, 회사 데이터 삭제 시 채용공고 데이터도 삭제
  
 * 지원내역 (Appliement)
   - RecruitId, UserId를 속성으로 가짐
   - Primary Key로 RecruitId, UserId를 사용
   - 채용공고와 1:N 관계, UserId와 1:N 관계, Foreign Key로 RecruitId, UserId를 사용
   - 외래키 옵션은 Cascade로, 채용공고 데이터 삭제 혹은 사용자 데이터 삭제 시 지원내역 데이터도 삭제
   - "사용자는 1회만 지원가능"의 제약조건에 의해 RecruitId와 UserId는 Unique여야 함
     
### 2. 서비스별 요구사항
   #### 1. 채용공고 등록
   - 회사는 채용공고 엔티티 구조를 사용하여 채용공고 등록
        URL은 /recruitment/advertise, method는 POST
   - 회사 정보가 데이터베이스에 존재하지 않는 경우, 오류 출력 (회사 등록이 필요함)
   - 채용공고의 FK, PK가 채워지지 않은 경우, 오류 출력 (속성 ***가 채워지지 않음)
   - field가 채용공고 엔티티 구조에 존재하지 않을 경우 오류 출력 (속성 ***는 존재하지 않음)
   #### 2. 채용공고 수정
   - 회사명을 제외한 채용공고의 속성을 수정
   - URL은 /recruitment/modify, method는 POST로 수행하며 field와 value를 서버에 전달함
   - field가 채용공고 엔티티 구조에 존재하지 않을 경우 오류 출력 (속성 ***는 존재하지 않음)
   - RecruitId와 회사명 중 하나를 포함하는 경우 오류 출력 (속성 ***는 수정할 수 없음)
   #### 3. 채용공고 삭제
   - RecruitId를 전달받아 채용공고를 삭제함
   - URL은 /recruitment/remove, method는 POST
   - 존재하지 않는 채용공고 Id를 전달받았을 경우 오류 출력 (존재하지 않는 채용공고)
   #### 4. 채용공고 목록 불러오기
   1. 채용공고 목록 확인
       - 전체 채용공고를 불러오되, 채용 상세내용은 제외
       - URL은 /recruitment/listall, method는 GET 
   2. 채용공고 검색
       - 특정 조건에 맞는 채용공고를 불러오되, 채용 상세내용은 제외
       - URL은 /recruit/list/search?, method는 GET
       - field 이름과 value를 Request Parameter로 설정
       - 존재하지 않는 field 이름을 전달받았을 경우, 오류 출력 (속성 ***는 존재하지 않음)
   #### 5. 채용 상세 페이지 (채용내용과 해당회사가 올린 다른 채용공고 포함) 출력   
   - 특정 채용 공고에 대해 채용 내용을 포함한 전체 채용공고문 출력
   - URL은 /recruit/details?, method는 GET
   - 채용공고id를 Request Parameter로 설정
   - 채용공고id의 회사 이름과 동일한 다른 채용공고를 질의하여 "회사가올린다른채용공고" 항목에 id 리스트 제공
   #### 6. 사용자의 채용공고 지원
   - 특정 채용공고에 UserId를 사용하여 채용공고에 지원
   - URL은 /recruit/appliement, method는 POST
   - 채용공고 id가 존재하지 않는 경우, 오류 출력 (채용공고 id ***는 등록되지 않음)
   - user id가 존재하지 않는 경우, 오류 출력 (user id ***는 존재하지 않음)
   - 기존에 지원내역이 존재하는 경우 (동일한 내용의 appliment가 존재하는 경우) 오류 출력 (채용공고id ***에 대한 user ***의 지원내역이 이미 존재함)
## 구현과정

### 1. 회사, 채용공고 엔티티 구현
----
### 2. 채용공고 등록 (회사등록), 채용공고 수정, 채용공고 삭제 기능 구현
----
### 3. 채용공고 목록 불러오기 및 검색 기능 구현
### 4. 채용 상세 페이지 기능 구현
### 5. 채용공고 지원 기능 구현
