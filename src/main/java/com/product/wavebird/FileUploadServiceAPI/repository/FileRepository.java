package com.product.wavebird.FileUploadServiceAPI.repository;

import com.product.wavebird.FileUploadServiceAPI.data.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
