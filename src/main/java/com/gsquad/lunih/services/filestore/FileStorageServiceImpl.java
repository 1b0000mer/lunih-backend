package com.gsquad.lunih.services.filestore;

import com.gsquad.lunih.configs.FileStorageProperties;
import com.gsquad.lunih.dtos.FileStoreResult;
import com.gsquad.lunih.exceptions.FileStorageException;
import com.gsquad.lunih.exceptions.NotFoundException;
import com.gsquad.lunih.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.apache.commons.io.FileUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service
@Transactional
public class FileStorageServiceImpl implements FileStorageService {

    private final Path fileDownloadDirStorageLocation;

    private final Path fileUploadDirStorageLocation;

//    @Value("${file.upload_dir}")
//    private String path;

    @Autowired
    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
        try {
            this.fileDownloadDirStorageLocation = Paths.get(fileStorageProperties.getDownloadDir()).toAbsolutePath().normalize();
            if (Files.notExists(fileDownloadDirStorageLocation)) {
                File file = fileDownloadDirStorageLocation.toFile();
                file.mkdir();
            }
        } catch (Exception e) {
            throw new ServerException("Could not init downloads directory");
        }

        try {
            this.fileUploadDirStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
            if (Files.notExists(fileUploadDirStorageLocation)) {
                File file = fileUploadDirStorageLocation.toFile();
                file.mkdir();
            }
        } catch (Exception e) {
            throw new ServerException("Could not init uploads directory");
        }
    }

    @Override
    public FileStoreResult storeFile(File file, String subFolder) {
        // Normalize file name
        String fileName = generateFileName();
        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Path targetLocation;
            if (!ObjectUtils.isEmpty(subFolder)) {
                if (!Files.exists(Paths.get(this.fileUploadDirStorageLocation.getFileName().toString()
                        + File.separatorChar
                        + subFolder))) {
                    String fullDirectoryPath = this.fileUploadDirStorageLocation.toAbsolutePath()
                            + this.fileUploadDirStorageLocation.getFileName().toString()
                            + File.separator
                            + subFolder;
                    File directory = new File(fullDirectoryPath);
                    directory.mkdir();
                }
                targetLocation = Paths.get(this.fileUploadDirStorageLocation.getFileName().toString()
                        + File.separatorChar
                        + subFolder
                        + File.separatorChar
                        + fileName);
            } else {
                targetLocation = Paths.get(this.fileUploadDirStorageLocation.getFileName().toString()
                        + File.separatorChar
                        + fileName);
            }
            FileUtils.copyFile(file, targetLocation.toFile(), true);

            // delete file temp
            FileUtils.deleteQuietly(file);
            FileStoreResult fileStoreResult = new FileStoreResult();
            fileStoreResult.setFileName(fileName);
            fileStoreResult.setPath(targetLocation.toString());
            return fileStoreResult;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public Resource loadFile(String location) {
        try {
            if (Files.notExists(Paths.get(location))) {
                throw new NotFoundException("File not found!");
            }
            Path filePath = Paths.get(location);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new NotFoundException("File not Found!");
            }
        } catch (MalformedURLException ex) {
            throw new NotFoundException("File not Found! ", ex);
        }
    }

    private String generateFileName() {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < 5; i++) {
            result += (random.nextInt(26) + 'a');
        }
        result += "_";
        result += (new SimpleDateFormat("dd-MM-yyyy HH-mm-ss z").format(new Date()));
        return result;
    }
}
