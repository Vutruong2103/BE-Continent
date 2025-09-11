package com.example.continent.application.mapper_;

public class A_Todo {
    /**
     * 1. Hiện tại ngoại trừ UserMapper có sử dụng update kiểu mapping thì không có cái nào sử dụng update mapping. 
     * 2. Nên tập dụng tối đa update từ mapping để tránh sai sót và thiếu những trường cần update 1 entity.
     * 
     * 3. UserMapping thiếu logic cập nhật thông tin từ user thì các trường như lastModifiedBy và lastModifiedDate không cập nhật ở mapper ?
     *  Điều này sẽ dẫn đến sai logic khi đã cập nhật enitity nhưng những trường này chưa cập nhật thì sẽ sai về mặt logic. 
     *         
     * =====> Cần chú ý đến logic cập nhật dưới DB khi update thông tin của bất cứ entity nào extends AbstractAuditingEntity  
     * => Chưa OK.
     */

     // ************************************* NGHIÊM TRỌNG************************************* //
     // tên của field( trường của các class) và tên method thì chữ cái Đầu viết trường, các chữ bắt đầu từ từ tiếp theo sẽ viết hoa (id, lastModifiedBy, getUserById, ect.)
     // tên của class, interface, abstract class, (Đối tượng nói chung) thì viết Hoa chữ cái đầu tiên của các chữ (ví dụ ConNguoi, Role)
     // Hằng số, tên các enum các biết không thay đổi trong suốt vòng đời của chương trình thì viết hoa toàn bộ các chữ (
     // ví dụ : public static final String ROLE_NAME = "ADMIN"; public static final int MAX_SIZE = 100;)
     // 
     // Xem class : ContinentMapper - ContinentDto


  
}
