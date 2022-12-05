#include <jni.h>
#include <string>
#include "opencv2/opencv.hpp"

cv::CascadeClassifier cascade;

extern "C" void
Java_com_example_ocvtest7_MainActivity_nativeDetectAndDisplay(
        JNIEnv *env,
        jobject,
        jlong addrMat) {
    if (cascade.empty()) {
        cascade.load("/sdcard/haarcascade_frontalface_default.xml");
    }
    if (cascade.empty()) {
        return;
    }
    cv::Mat &src = *(cv::Mat *) addrMat;
    cv::Mat gray, gray2;
    cvtColor(src, gray, cv::COLOR_BGRA2GRAY);
    resize(gray, gray2, cv::Size(640, 360)); // Screen Size: 1920x1080
    std::vector<cv::Rect> faces;
    cascade.detectMultiScale(gray2, faces, 1.2, 3, 0, cv::Size(40, 40));
    for (size_t i = 0; i < faces.size(); i++) {
        cv::Rect rc = faces[i];
        rc.x *= 3;
        rc.y *= 3;
        rc.width *= 3;
        rc.height *= 3;
        rectangle(src, rc, cv::Scalar(255, 0, 255), 2);
    }
}
