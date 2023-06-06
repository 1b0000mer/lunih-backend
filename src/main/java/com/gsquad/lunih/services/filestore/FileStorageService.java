package com.gsquad.lunih.services.filestore;

import com.gsquad.lunih.dtos.FileStoreResult;
import org.springframework.core.io.Resource;

import java.io.File;

public interface FileStorageService {

    FileStoreResult storeFile(File file, String subFolder);

    Resource loadFile(String location);
}
