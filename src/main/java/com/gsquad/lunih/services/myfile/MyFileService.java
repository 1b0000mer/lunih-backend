package com.gsquad.lunih.services.myfile;

import com.gsquad.lunih.entities.MyFile;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

public interface MyFileService {

    MyFile uploadFile(MultipartFile file, String subFolder, Principal principal) throws Exception;

    MyFile getFileInfo(int id);

    Resource viewFile(int id, Principal principal);

    Resource downloadFile(int id, Principal principal);
}
