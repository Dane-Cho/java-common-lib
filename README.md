# Prompt Spring Based Common Library

스프링 기반 백엔드 개발의 편의성을 위해서 자주 사용하는 모듈들을 라이브러리화 하였다.

### Requirement
Java SDK 8 이상  
0.0.x -> Spring Framework 4.x 이상  
0.1.x -> Spring Framework 5.x 이상

### Maven Dependency 설정
~~~ xml
<repositories>
    <repository>
        <id>prompt-thirdparty</id>
        <url>http://repo.prompt.co.kr/content/repositories/thirdparty</url>
    </repository>
</repositories>
 
<dependencies>
    <dependency>
        <groupId>com.prompt</groupId>
        <artifactId>prompt-common</artifactId>
        <version>0.1.0</version>
    </dependency>
</dependencies>
~~~