# Madcamp 3주차 자유과제
## 팀원
* [이혜민](https://github.com/IamHyemin)
* [정서현](http://www.github.com/ciao-seohyeon)

## 설명
게임 속 NPC 가 등장하여 시뮬레이션 게임의 스토리를 진행한다.  
사용자의 사진을 업로드하여 얼굴을 인식하고, 이에 다양한 악세사리를 합성하는 어플리케이션

- [x] 로그인 화면 구현
- [x] 시뮬레이션 게임 구현
- [x] Facedetector 를 이용한 얼굴 인식
- [x] 해당 계정의 게임 내역을 볼 수 있음

### 1. 로그인 화면
- 서버와 데이타베이스를 연동하여 기본적인 회원가입, 로그인 화면을 구현함

### 2. 시뮬레이션 게임 구현
- 게임 NPC 가 스토리를 진행
- NPC 가 주는 다양한 선택지를 사용자가 선택
- 사용자의 선택지를 기반으로 얼굴 인식 실행
- 인식 결과는 해당 계정의 데이타베이스에 저장

### 3. Facedetector 를 이용한 얼굴 인식
- google gms vision 에서 제공하는 facedetector 이용
- 눈, 코, 입 등 얼굴 요소의 위치를 얻음
- 해당 위치들을 이용해 악세사리들을 얼굴에 합성

### 4. 해당 계정의 게임 내역을 볼 수 있음
- 데이타베이스에 저장한 게임 결과를 불러옴
- 게임 내역을 선택하면, 과거 얼굴 합성 사진들을 볼 수 있음
