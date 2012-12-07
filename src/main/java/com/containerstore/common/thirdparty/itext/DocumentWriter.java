package com.containerstore.common.thirdparty.itext;

public interface DocumentWriter {
    void write(DocumentContext documentBuilder) throws DocumentWriterException;
    String modelKey();
    String name();
}
