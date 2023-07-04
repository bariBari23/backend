#!/bin/sh

# 빌드
cd /home/ubuntu/backend

./gradlew clean build -x test

## 이미 실행중인 서버 종료
a=$(ps -ef | grep 'java -jar -Dspring.profiles.active' | grep -v grep | awk '{print $2}')
kill -9 $a

# 실행
nohup java -jar -Dspring.profiles.active=dev ~/backend/build/libs/BoardProject-0.0.1-SNAPSHOT.jar > nohup.out 2> nohup.err < /dev/null &
