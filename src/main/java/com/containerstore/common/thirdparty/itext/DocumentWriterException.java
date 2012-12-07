package com.containerstore.common.thirdparty.itext;

public class DocumentWriterException extends Exception {
    public DocumentWriterException() {
    }

    public DocumentWriterException(String message) {
        super(message);
    }

    public DocumentWriterException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public DocumentWriterException(Throwable throwable) {
        super(throwable);
    }
}
