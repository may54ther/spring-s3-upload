# spring-s3-upload

## 🛠️사용 기술

- Java 11
- Spring Boot
- Gradle
- Mybatis
- MariaDB

#### Tools
- intellij IDEA

#### Gradle Dependencies
``` gradle
dependencies {
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    implementation platform('software.amazon.awssdk:bom:2.19.28')
    implementation 'software.amazon.awssdk:s3'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-jdbc'
    implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.2'
    implementation 'com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5'
    runtimeOnly 'org.mariadb.jdbc:mariadb-java-client'
    compileOnly 'org.springframework.boot:spring-boot-devtools'
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```
* jasypt 
   * Amazon Web Service Secret Key 암호화를 위해 사용됨
   * 복호화를 위해 설정 필요: `VM Option` > `-Djasypt.encryptor.password={암호화_키}`

<br>

## 👩🏻‍💻 기능
#### 업로드 (Upload)
1. 단일 업로드 
2. 다중 업로드
#### 다운로드 (Download)
#### 삭제 (Delete)
1. 단일 삭제
2. 다중 삭제
