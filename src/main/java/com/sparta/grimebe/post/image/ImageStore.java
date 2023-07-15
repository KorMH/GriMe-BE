package com.sparta.grimebe.post.image;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ImageStore {

    private static final Tika tika = new Tika();

    @Value("${file.dir}")
    private String fileDir;

    public String getPath(String fileName) {
        return fileDir + fileName;
    }

    // IOException 발생 가능한 경우
    // 디스크 관련 문제: 디스크 가득 참, 디스크 권한 문제 또는 디스크 I/O 오류와 같이 디스크에서 파일을 쓰거나 읽는 데 문제가 있는 경우 파일 전송 프로세스 중에 IOException이 발생할 수 있습니다.
    // 네트워크 관련 문제: 원격 서버에서 파일을 업로드하거나 다운로드하는 것과 같이 파일 전송에 네트워크 위치가 관련된 경우 네트워크 연결 문제, 시간 초과 또는 네트워크 오류로 인해 'IOException'이 발생할 수 있습니다.
    // 파일 시스템 문제: 파일 시스템 손상이나 불일치와 같은 파일 시스템 자체에 문제가 있는 경우 파일 전송 작업 중에 IOException이 발생할 수 있습니다.
    // 동시 액세스: 여러 스레드 또는 프로세스가 동일한 파일에 동시에 액세스하고 충돌 또는 경합이 있는 경우 파일 전송 중에 IOException이 발생할 수 있습니다.
    // IOException은 CheckException 트랜잭션 롤백 X
    public String storeFile(MultipartFile multipartFile) {

        if (multipartFile.isEmpty()) {
            throw new IllegalArgumentException("File Not Found");
        }
        if (!isImageFile(multipartFile)){
            throw new IllegalArgumentException("Not Image File");
        }

        String originalFileName = multipartFile.getOriginalFilename();
        log.info("originalFileName = {}", originalFileName);
        try {

            String storeFileName = createStoreFileName(originalFileName);
            File folder = new File(fileDir);
            // if (!folder.exists()){
            //     folder.mkdirs();
            // }
            multipartFile.transferTo(new File(getPath(storeFileName)));
            return storeFileName;
        } catch (IOException e) {
            log.error("Failed to store {} cause: {}",originalFileName, e);
            throw new RuntimeException("Failed to store file", e);
        }
    }

    private String createStoreFileName(String originalFileName) {
        String ext = extractExt(originalFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFileName) {
        int pos = originalFileName.lastIndexOf(".");
        String ext = originalFileName.substring(pos+1);
        return ext;
    }

    private boolean isImageFile(MultipartFile multipartFile) {
        try {
            String mimeType = tika.detect(multipartFile.getInputStream());
            if (!mimeType.startsWith("image")){
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException("유효하지 않은 파일입니다.", e);
        }
        return true;
    }
}
