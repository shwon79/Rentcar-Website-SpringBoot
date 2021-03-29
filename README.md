 ### 웹사이트 주소 : itscar.cafe24.com         
 
 
 # 수정시 재배포하기
 
 ## war 추출
 
 ### Artifacts 설정
 file > Project Structure에서 Artifacts를 클릭 
 ![](https://images.velog.io/images/woo0_hooo/post/abcc3282-8417-48c9-9a8e-b9f7b4c403d3/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-02-04%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%204.46.48.png)
 여기서 Web Application : Archive를 클릭해주는데, 
 archive는 .war로 압축한 결과를 exploded는 압축 해제된 결과를 저장함
 ![](https://images.velog.io/images/woo0_hooo/post/8dfeb37d-d90e-4012-b4c8-9ae0156eac3b/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-02-04%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%204.47.31.png)
 여기서 output directory의 이름이나 war파일의 이름은 원하는데로 수정하고, apply -> OK!
 
 ### build artifacts
 위와 같이 저장해주었으면 이제 ![](https://images.velog.io/images/woo0_hooo/post/b2229bfe-c2c0-4cf8-9e28-c18406ab0c08/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-02-04%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%204.52.41.png)
 Build > Build Artifacts 클릭 !
 ![](https://images.velog.io/images/woo0_hooo/post/6d412314-dbd8-427f-9812-2be0291f700b/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-02-04%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%204.53.06.png)
 아까 설정한 Artifacts를 클릭하고 build한다. 
 
 ![](https://images.velog.io/images/woo0_hooo/post/58effdae-b829-4455-92ef-61d6dc969c8f/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-02-04%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%204.54.02.png)
 
 그럼 out 폴더에 위와 같이 war 파일이 추출됐음을 알 수 있다. 
    
      
 ## 카페24 tomcat 서버에 올리기 
 ### ftp를 이용해서 추출한 war 파일 업로드
 File zila를 사용했는데, 아무거나 사용해도 상관없다.
 ![](https://images.velog.io/images/woo0_hooo/post/684cf8c4-83aa-4df9-b3b3-4febaa70417b/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-02-04%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%204.57.08.png)
 - Host : 카페24id@카페24대표도메인
 - 사용자명 : 카페24 id
 - 비밀번호 : 카페24에서 설정한 MySQL 비밀번호
 - 포트 : 21
 
이렇게 연결에 성공하면, ![](https://images.velog.io/images/woo0_hooo/post/65c9b3e7-92b0-441c-8910-bf5d931594f7/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-02-04%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%204.58.46.png)
/tomcat/webapps 아래에 빌드한 war 파일을 업로드한다. 



# tomcat 구동
terminal에서 서버에 접속한다. Host와 비밀번호는 ftp접속했을때와 같음    
ssh [계정id]@[호스팅서버주소]

/tomcat/bin의 
![](https://images.velog.io/images/woo0_hooo/post/f78fd45e-e686-4970-8428-ed60e2064a6a/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-02-04%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%205.00.56.png)
startup.sh를 실행시키면 서버가 올라간다. 
```./startup.sh```

서버를 내리고 싶으면
```./shutdown.sh```

서버를 올리고 도메인에 접속하면 
![](https://images.velog.io/images/woo0_hooo/post/178db4b2-c6dc-40af-bafa-bdc7b7e01089/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-02-04%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%205.02.22.png)





# 로그 정리하는 법
tomcat의 로그 위치는 tomcat/logs 에 위치하며 기본적으로 catalina.out 전체로그와 catalina.YYYY-MM-DD.log 형식의 로그 파일로 구분 됩니다.


[root@umj7 techcs1651]$ cd tomcat/logs/    
[root@umj7 logs]$ ls -l     

tomcat을 운영중에 로그파일이 자동으로 정리되지 않기 때문에 일정 주기에 맞추어 로그를 삭제해야 불필요한 용량을 차지하는 것을 방지 할 수 있으며 용량부족으로 인한 오류를 미연에 방지 할 수 있습니다.    

먼저 로그 정리를 위해서는 먼저 tomcat을 중지 이후에 정리(삭제)를 하셔야 정상적으로 동작 할 수 있습니다.     
[root@umj7 techcs1651]$ ./tomcat/bin/shutdown.sh


tomcat 정지 이후에 로그파일을 따로 ftp로 백업 하시거나 서버에서 직접 삭제 할 수 있습니다.    
[root@umj7 techcs1651]$ cd tomcat/logs/    
[root@umj7 logs]$ rm ./catalina.out   


catalina.out 을 정리(삭제) 하셨다면 tomcat을 다시 시작합니다.   
[root@umj7 techcs1651]$ ./tomcat/bin/startup.sh    






# db 내용 수정하기
## MySQL 외부 IP 접근설정
[카페 24 호스팅센터](https://hosting.cafe24.com/?controller=myservice_hosting_main)에서 내 현재 ip를 추가한다.

<img width="420" alt="스크린샷 2021-03-04 오후 9 32 52" src="https://user-images.githubusercontent.com/49271553/109964568-2efaf400-7d31-11eb-922f-986500cae2d6.png">

## db 접근 툴 이용
mysql workbench와 같은 시각화가 가능한 db 접근 툴을 이용해서 접근한다. 

<img width="698" alt="스크린샷 2021-03-04 오후 9 34 20" src="https://user-images.githubusercontent.com/49271553/109964729-636eb000-7d31-11eb-8df4-0beea625d37b.png">
위와 같이 설정, 패스워드는 db 패스워드 입력하면 연결됨을 확인할 수 있다.

원하는 데이터를 수정/삽입/삭제하면 된다.
