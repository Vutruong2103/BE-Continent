package com.example.continent.application.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

/**
 * @author: Vutq
 *
 * @param <T>: Kiểu dữ liệu của ID (Long, String, v.v.)
 * @serialVersionUID: Định danh phiên bản để đảm bảo tính tương thích khi tuần tự hóa.
 * @MappedSuperclass: Chỉ định lớp này là lớp cha cho các thực thể JPA.
 * @EntityListeners(AuditingEntityListener.class): Kích hoạt tính năng tự động ghi nhận thông tin tạo và sửa đổi.
 * @SuperBuilder: Cung cấp khả năng xây dựng đối tượng với các lớp con.
 * @NoArgsConstructor: Tạo constructor không tham số.
 * @AllArgsConstructor: Tạo constructor với tất cả các tham số.
 * @Instant: Lưu trữ thời gian theo chuẩn UTC.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class AbstractAuditingEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    //Người tạo
    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    private String createdBy;

    //Ngày tạo
    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private Instant createdDate = Instant.now();

    //Người sửa cuối
    @LastModifiedBy
    @Column(name = "last_modified_by", length = 50)
    private String lastModifiedBy;

    //Ngày sửa cuối
    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate = Instant.now();

    //Xóa mềm
    @Column(name = "deleted")
    private Boolean deleted;
}

