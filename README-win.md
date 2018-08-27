# jhipster 개발환경 빌드

backend (springboot) 빌드

~~~bash
./gradlew
~~~

frontend (angular) 빌드

~~~bash
yarn start
~~~

# truffle 환경

## truffle 도구 설치

truffle 설치

~~~bash
npm install truffle -g
~~~

ganache (ethereum testnet) 설치

cli 도구는 npm으로 설치한다.

~~~bash
npm install ganache-cli -g
~~~

gui 도구는 [ganache 페이지](https://truffleframework.com/ganache) 에서 다운로드 후 설치한다.

## truffle 초기화

다음과 같이 초기화를 하면 contracts, migrations, tests 디렉토리와 truffle.js, truffle-config.js 파일이 생성된다.

~~~bash
truffle init
~~~

# Windows 환경에서 빌드시 추가 사항

~~~bash
npm install --global --production windows-build-tools
yarn config set msvs_version 2015 --global
~~~
