# GIPHY android app clone

## 1. 프로젝트 소개
- GIF이미지들을 검색할 수 있는 서비스인 GIPHY의 안드로이드 어플리케이션을 따라 만들어보는 프로젝트.

## 2. 프로젝트 기본 정보
- 사용언어 : Kotlin
- minSdk 버전 : 19
- targetSdk 버전 : 28

## 3. 설치/실행 방법
1. 설치방법
- 프로젝트 master 브랜치 클론 또는 zip파일로 다운로드 하여 압축 해제

2. 실행방법
- 안드로이드 스튜디오가 있다면 해당 프로젝트 클론 후 실행
- 안드로이드 스튜디오가 없을 경우 https://github.com/pleasantlife/GiphyClone/blob/master/giphyclone_demo_debug.apk 파일 다운로드 후 실행

## 4. 구현한 페이지
- 인기있는 GIF들을 리스트 형태로 보여주는 페이지(복제 프로젝트 앱의 메인 페이지)
- 인기 검색어와 사용자가 최근에 검색한 검색어들을 보여주는 페이지(검색 페이지)
- 검색결과에 따른 GIF들과 연관 검색어들을 각각의 리스트로 보여주는 페이지(검색 결과 페이지)
- 특정 GIF 아이템에 대한 상세보기 및 클리핑(Favorite on/off)이 가능한 페이지(상세 페이지)
- 사용자가 클리핑한 GIF들을 모아서 리스트 형태로 보여주는 페이지(찜해둔 목록 페이지)

## 5. 사용한 API 및 라이브러리
- GIPHY Developers API
> 사용 목적
> - 인기있는 GIF/스티커 리스트
> - 인기있는 검색어 리스트
> - 검색어에 따른 연관검색어 리스트
> - 검색결과 GIF/스티커 리스트

- Google의 라이브러리들 (레이아웃 등 기본적으로 사용한 라이브러리는 언급 제외)
> 사용 라이브러리
> - android-lifecycle-viewmodel : 뷰모델을 통해 데이터와 뷰간의 분리를 위하여 사용
> - android-livedata : 데이터의 변화에 따라 실시간으로 UI등이 변경되도록 하기 위하여 사용
> - android-navigation : 화면 하단 네비게이션바와 프래그먼트 스택 컨트롤을 위하여 사용
> - android-paging : 리스트에 데이터 표출 시 스크롤 정도에 따라 데이터를 나눠 받기 위하여 사용

- 기타 라이브러리
> - Retrofit : API 통신을 위하여 사용
> - RxJava : Retrofit의 call어댑터로 API 통신을 위하여 사용
> - OkhttpLoggingInterceptor : API 통신 중 발생하는 로그 수집을 위하여 사용
> - Gson : API 통신시 json 데이터와 POJO클래스간의 연동처리를 위하여 사용 
> - Glide: GIF 이미지와 아이콘들을 OOM없이 출력하기 위하여 사용 


