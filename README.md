 # 수정시 재배포하기
 ---
 
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

/tomcat/bin의 
![](https://images.velog.io/images/woo0_hooo/post/f78fd45e-e686-4970-8428-ed60e2064a6a/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-02-04%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%205.00.56.png)
startup.sh를 실행시키면 서버가 올라간다. 
```./startup.sh```

서버를 내리고 싶으면
```./shutdown.sh```

서버를 올리고 도메인에 접속하면 
![](https://images.velog.io/images/woo0_hooo/post/178db4b2-c6dc-40af-bafa-bdc7b7e01089/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-02-04%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%205.02.22.png)
수정된 버전이 반영되었음을 알 수 있다.
