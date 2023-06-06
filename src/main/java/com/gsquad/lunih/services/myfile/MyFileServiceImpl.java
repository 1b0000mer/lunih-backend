package com.gsquad.lunih.services.myfile;

import com.gsquad.lunih.dtos.FileStoreResult;
import com.gsquad.lunih.entities.MyFile;
import com.gsquad.lunih.exceptions.InvalidException;
import com.gsquad.lunih.exceptions.NotFoundException;
import com.gsquad.lunih.repos.MyFileRepo;
import com.gsquad.lunih.services.filestore.FileStorageService;
import org.apache.commons.io.FileUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@Transactional
public class MyFileServiceImpl implements MyFileService {
    private final MyFileRepo myFileRepo;
    private final FileStorageService fileStorageService;
    private final MessageSource messageSource;
    private static final List<String> contentImageTypes = Arrays.asList(
            MediaType.IMAGE_GIF_VALUE,
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE
    );

    public MyFileServiceImpl(MyFileRepo myFileRepo, FileStorageService fileStorageService, MessageSource messageSource) {
        this.myFileRepo = myFileRepo;
        this.fileStorageService = fileStorageService;
        this.messageSource = messageSource;
    }

    @Override
    public MyFile uploadFile(MultipartFile file, String subFolder, Principal principal) throws Exception {
        File temp = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileUtils.copyInputStreamToFile(file.getInputStream(), temp);
        FileStoreResult fileStoreResult = fileStorageService.storeFile(temp, subFolder);

        MyFile myFile = new MyFile();
        myFile.setFileName(fileStoreResult.getFileName());
        myFile.setSize(file.getSize());
        myFile.setFileType(file.getContentType());
        myFile.setPath(fileStoreResult.getPath());

        myFileRepo.save(myFile);
        return myFile;
    }

    @Override
    public MyFile getFileInfo(int id) {
        Locale locale = LocaleContextHolder.getLocale();
        return myFileRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(messageSource.getMessage("error.filenotfound", null, locale), id)));
    }

    @Override
    public Resource viewFile(int id, Principal principal) {
        Locale locale = LocaleContextHolder.getLocale();
        MyFile myFile = getFileInfo(id);
        if (!contentImageTypes.contains(myFile.getFileType()) && !myFile.getFileType().equals(MediaType.APPLICATION_PDF_VALUE)) {
            throw new InvalidException(messageSource.getMessage("error.filestypenotsupport", null, locale));
        }
        return fileStorageService.loadFile(myFile.getPath());
    }

    @Override
    public Resource downloadFile(int id, Principal principal) {
        MyFile myFile = getFileInfo(id);
        return fileStorageService.loadFile(myFile.getPath());
    }
}
