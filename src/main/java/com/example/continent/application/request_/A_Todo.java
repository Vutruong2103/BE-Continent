package com.example.continent.application.request_;

public class A_Todo {
    /**
     * Kiến thức bổ sung
     * Thường thì người ta sẽ tạo ra các object request ví dụ như (
     * 1. UserRegisterRequest
     * 2. RoleRegisterRequest 
     * .etc...)
     * 
     * Rồi họ sẽ validate cho từng field của object request chứ không sử dụng trực tiếp DTO. 
     * Vì DTO có khi trả về rất nhiều trường và khả năng sử dụng để làm workflow nên việc sử dụng DTO 
     *      có thể sai sót và gánh nặng lên DTO. 
     * 
     * ****** Ngoài ra cũng nên ghi các logic của các trường trên DTO để tránh trường hợp sử dụng ở đâu vì mục đích gì sẽ bị quên.
     * ****** Hãy nghĩ đến 1 DTO có 50-90 trường trả về.
     * 
     * => OK
     */   
}
