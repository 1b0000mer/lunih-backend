package com.gsquad.lunih.exceptions;

public class FileStorageException extends RuntimeException {

    public FileStorageException(String msg) {
        super(msg);
    }

    public FileStorageException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
