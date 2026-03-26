# User là người sử dụng hệ thống, có thể đăng ký, đăng nhập và tham gia vào các dự án. Mỗi người dùng có thể được phân công thực hiện nhiều công việc khác nhau trong các dự án.
# Project là một dự án được tạo ra để quản lý một nhóm công việc cụ thể. Mỗi dự án có các thông tin như tên dự án, mô tả, thời gian bắt đầu và thời gian kết thúc. Một dự án có thể bao gồm nhiều công việc (Task) và có thể có nhiều người dùng tham gia.
# Task là các công việc cụ thể thuộc một dự án. Mỗi task được giao cho một user chịu trách nhiệm thực hiện. Task thường bao gồm các thông tin như tên công việc, mô tả, trạng thái, thời hạn hoàn thành và người được giao.
## 1. Tổng quan hệ thống
Hệ thống quản lý công việc (Task Management System) cho phép người dùng tạo và quản lý Project, tạo Task và phân công công việc cho các thành viên. Hệ thống hỗ trợ theo dõi tiến độ, trạng thái và mức độ ưu tiên của từng Task.
---
## 2. Thực thể chính

### 2.1 User

User là người sử dụng hệ thống.

Một User có thể:
- Tạo Project
- Tham gia nhiều Project
- Được gán nhiều Task
- Cập nhật trạng thái Task

Mỗi User có:
- userId
- username
- email
- password
- role (ADMIN, MANAGER, MEMBER)
- trạng thái hoạt động

---

### 2.2 Project

Project là một tập hợp các Task.

Mỗi Project:
- Có một owner (User)
- Có nhiều thành viên
- Có nhiều Task
- Có trạng thái (PLANNING, ACTIVE, COMPLETED, ARCHIVED)

Quy tắc:
- Chỉ owner hoặc MANAGER mới được tạo Task
- Project COMPLETED không được thêm Task mới

---

### 2.3 Task

Task là một công việc cụ thể trong Project.

Mỗi Task:
- Thuộc về một Project
- Có thể được gán cho một User
- Có trạng thái (TODO, IN_PROGRESS, REVIEW, DONE, CANCELLED)
- Có mức độ ưu tiên (LOW, MEDIUM, HIGH, URGENT)
- Có deadline

---

## 3. Quan hệ giữa các thực thể

User (1) ---- (n) Project (owner)

Project (1) ---- (n) Task

User (1) ---- (n) Task (assignee)

---

## 4. Luồng nghiệp vụ chính

### 4.1 Tạo Project

- User tạo Project
- User trở thành owner
- Project mặc định trạng thái PLANNING

---

### 4.2 Tạo Task

- Task phải thuộc một Project
- Task mặc định trạng thái TODO
- Có thể chưa assign User
- Có thể thiết lập deadline và priority

---

### 4.3 Cập nhật Task

Luồng trạng thái hợp lệ:

TODO → IN_PROGRESS → REVIEW → DONE  
TODO → CANCELLED  
IN_PROGRESS → CANCELLED

Không cho phép:
- DONE quay lại trạng thái trước
- CANCELLED chỉnh sửa nội dung

---

### 4.4 Gán Task cho User

- Chỉ assign User thuộc Project
- Task chỉ có một assignee tại một thời điểm

---

### 4.5 Hoàn thành Project

- Project chỉ có thể chuyển COMPLETED khi tất cả Task là DONE hoặc CANCELLED

---

## 5. Quy tắc nghiệp vụ quan trọng

- ID phải duy nhất
- Task phải thuộc Project
- User chỉ thao tác trong Project mình tham gia
- Không xóa Project nếu còn Task đang IN_PROGRESS
- Deadline quá hạn → Task được đánh dấu overdue

---

## 6. Vòng đời Task

1. TODO
2. IN_PROGRESS
3. REVIEW
4. DONE
5. CANCELLED

---

## 7. Mục tiêu hệ thống

- Quản lý công việc rõ ràng
- Theo dõi tiến độ minh bạch
- Phân quyền hợp lý
- Giảm xung đột và trùng lặp công việc

