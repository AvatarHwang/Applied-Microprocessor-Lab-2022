# Applied-Microprocessor-Lab-2022
facedetection

이 문서는 Android 4.4 (KitKat)의 기기에서 OpenCV를 활용하여 얼굴인식(face detection) 기능을 Android Studio Dolphin (2021.3.1 patch 1)에서 구현한 것이다.

### Download NDK 17

• OpenCV 함수를 사용하기 위해서는 gnustl로 빌드 필요
• Android Studio에서 NDK 18 이후부터는 libc++만 사용됨
• NDK r17c를 다음 링크에서 다운로드: https://github.com/android/ndk/wiki/Unsupported-Downloads

### NDK 저장경로 확인

• Android Studio>File>Project Structure>SDK Location에서 본인의 NDK 경로 확인
• 다운로드 받은 ndk 17을 해당 경로로 이동시키고 폴더명을 17.2.4988734로 변경

### NDK 경로 설정

 • Project view>”your_project”>local.properties 에서 NDK 경로 추가
 
