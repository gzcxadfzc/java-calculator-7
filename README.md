# java-calculator-precourse
# 기능 구현 목록

**사용자 입력 전 형식에 맞는 화면 출력하기**

**문자열을 입력받기**

**커스텀 구분자 확인하기**

**커스텀 구분자가 존재 시 덧셈 연산자로 등록**

**유효한 계산식인지 검증**
- 식이 유효하지 않다면 IllegalArgumentException 발생

**계산식을 읽어서 연산**
- 양수와 구분자들 각 큐에 순서대로 저장
- 양수 큐와 연산자 큐에서 하나씩 요소를 뽑아 구분자에 해당하는 연산 수행

**연산의 결과를 형식에 맞게 출력**