# GPS Data Collector

Ứng dụng Android đơn giản được xây dựng bằng **Kotlin** và **Gradle** để thu thập dữ liệu **GPS** từ thiết bị di động.  
Ứng dụng này dùng để ghi lại dữ liệu vị trí theo thời gian thực và lưu lại để phục vụ cho việc **phân tích và xử lý dữ liệu vị trí**.

## Features

- Thu thập vị trí GPS theo thời gian thực
- Lấy các thông tin vị trí:
  - Latitude
  - Longitude
  - Accuracy
  - Speed
  - Timestamp
- Hiển thị vị trí hiện tại trên màn hình
- Xuất dữ liệu ra file **CSV** để phân tích sau này

## Tech Stack

- **Language:** Kotlin
- **Build Tool:** Gradle
- **Platform:** Android
- **Location API:** Android Location / Fused Location Provider

## Requirements

- Android Studio
- Android SDK
- Thiết bị Android có GPS
## Future Improvements

- Áp dụng Kalman Filter để cải thiện độ chính xác GPS
- Thu thập dữ liệu cảm biến bổ sung (accelerometer, gyroscope)
- Xuất dữ liệu trực tiếp lên server
- Hiển thị đường đi trên bản đồ

## License

This project is for research and educational purposes.
