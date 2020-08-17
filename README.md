# kakaopay
kakaopay

#환경
1. java jdk-12.0.2
2. h2database
3. lombok
4. jpa
5. swagger2

#swagger를 통한 api 확인가능
url : http://localhost:8080/swagger-ui.html

#토큰 생성
random하게 생성

#뿌리기api
header 값 :
X-USER-ID 는 뿌리고자하는 사람id 숫자만됨. 
X-ROOM-ID 는 roomid
body값 : 
moneyPush는 pushMoney 값과 pushDivideCount 을 입력

#받기api
header 값 : 
X-USER-ID 는 뿌리고자하는 사람id 숫자만됨. X-ROOM-ID 는 roomid, 
body 값 : 
token 값에는 tokenValue만 입력

#조회api
header 값 : 
X-USER-ID 는 뿌리고자하는 사람id 숫자만됨. X-ROOM-ID 는 roomid, 
body 값 : 
token 값에는 tokenValue만 입력

