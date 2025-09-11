package com.example.continent.domain.model_;

public class A_Todo {
    /**
     * Cái này không ổ lắm. 
     * 1. @Column(name = "tên_của_cột") không nên để mặc định vì có thể sai ngữ pháp hoặc cấu trúc tên.
     *     ví dụ :
     * 
     * a. có name :     @Column(name = "last_modified_date") 
     *                  private Instant lastModifiedDate
     *          => Kết quả dưới DB : last_modified_date (dể đọc)
     * 
     * b. ko name :     @Column() 
     *                  private Instant lastModifiedDate  
     *          => Kết quả dưới DB : lastModifiedDate (khó đọc)
     * 
     * ===============================================================================================================
     * 
     * 2. Việc đánh   @Table(uniqueConstraints = { @UniqueConstraint(columnNames = "... "))
     *          cùng lúc với việc đánh dấu 1 cột @Column(unique = true) là dư thừa. Có thể bị đôi ra lỗi với nhiều csdl. 
     * 
     *      a.Nên dùng uniqueConstraints cho trường hợp đánh dấu dấu cho nhiều trường cùng 1 lúc 
     *          ví dụ : @Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"code", "email", "cccd"})
     *      b.Nên dùng @Column(unique = true) trong trường hợp cụ thể chính xác cần cột nào với các giá trị yêu cầu kèm theo 
     *          ví dụ : @Column(unique = true, nullable = false ....)
     *      
     *      =================== Nên dùng =======================
     *    Tất cả dự án hiện đại đều dùng các b - @Column(unique = true)
     *    Mọi dự án đều cần rõ ràng về cấu trúc, yêu cầu chính xác, ít lỗi, 
     *    chạy được nhiều csdl đặc biệt khi sử dụng microservice với nhiều nguồn csdl khác nhau.
     *         
     * => Chưa OK lắm.  
     */
}
