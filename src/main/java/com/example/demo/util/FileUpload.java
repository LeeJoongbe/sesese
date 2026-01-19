package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class FileUpload {

    //경로값을 의존성주입 (properties)에서 경로 읽어옴

    @Value("${imgLocation}")
    private String imgLocation;

    public String save (MultipartFile multipartFile) throws IOException {

        if(!multipartFile.isEmpty()) {

            //파일명을 가져와서 파일명을 가지고 uuid변환
            String  originalFilename = multipartFile.getOriginalFilename();

            String newFileName = UUID.randomUUID().toString() + "_" + originalFilename; //폴도저장용/디비저장용
            // 경로 + 파일명

            String fileUploadFullUrl = imgLocation + "/" + newFileName;

            // File 객체를 생성
            File file = new File(fileUploadFullUrl);

            multipartFile.transferTo(file);     //파일저장
//            FileOutputStream  fileOutputStream = new FileOutputStream(fileUploadFullUrl);
//            fileOutputStream.write(multipartFile.getBytes());
//            fileOutputStream.close();

//
//            File file = new File();
//            multipartFile.t


            return newFileName;

        }

        return null;

    }


    public void removefile(String imgName) {

        String delFileUrl = imgLocation + "/" + imgName;

        log.info("물리파일 삭제 경로 : " + delFileUrl);

        File file = new File(delFileUrl);

        if(file.exists()){  //파일이 있다면
            file.delete();
        }




    }

}
