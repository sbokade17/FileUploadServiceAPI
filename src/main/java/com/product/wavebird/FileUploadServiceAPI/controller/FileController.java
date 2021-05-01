package com.product.wavebird.FileUploadServiceAPI.controller;

import com.product.wavebird.FileUploadServiceAPI.data.FileEntity;
import com.product.wavebird.FileUploadServiceAPI.dto.FileDto;
import com.product.wavebird.FileUploadServiceAPI.exception.PetAppException;
import com.product.wavebird.FileUploadServiceAPI.repository.FileRepository;
import com.product.wavebird.FileUploadServiceAPI.util.ObjectMapperUtils;
import com.product.wavebird.FileUploadServiceAPI.util.PetDetailsServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@RequestMapping("/file")
public class FileController {

    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    FileRepository fileRepository;

    @PostMapping("/upload")
    public FileDto uploadImage(@RequestParam("file")MultipartFile multipartFile) throws IOException{
        FileDto fileDto = new FileDto();
        fileDto.setFileByte(compressBytes(multipartFile.getBytes()));
        fileDto.setName(multipartFile.getOriginalFilename());
        fileDto.setType(multipartFile.getContentType());
        fileDto.setUserId(PetDetailsServiceUtil.getLoggedInUserId());
        FileEntity file = fileRepository.save(ObjectMapperUtils.map(fileDto, FileEntity.class));
        return ObjectMapperUtils.map(file, FileDto.class);
    }

    @GetMapping("/{fileId}")
    public FileDto getFileById(@PathVariable("fileId") Long fileId) throws IOException{
        try{
            final FileEntity fileEntity = fileRepository.findById(fileId).get();
            FileDto fileDto = ObjectMapperUtils.map(fileEntity, FileDto.class);
            fileDto.setFileByte(decompressBytes(fileDto.getFileByte()));
            return fileDto;
        }catch (RuntimeException e){
            throw new PetAppException(HttpStatus.NOT_FOUND, e.getMessage());
        }


    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity deleteFileById(@PathVariable("fileId") Long fileId) throws IOException{
        try{
            fileRepository.deleteById(fileId);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted");
        }catch (RuntimeException e){
            throw new PetAppException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    private byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }

    private byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        return outputStream.toByteArray();
    }
}
