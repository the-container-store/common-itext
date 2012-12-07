package com.containerstore.common.thirdparty.itext;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import static com.google.common.base.Preconditions.*;
import static com.google.common.collect.Maps.*;

public class DocumentContextBuilder {
    private Map<String, Object> itemLookup = newHashMap();
    private DocumentBuilder documentBuilder;

    public DocumentContextBuilder(DocumentBuilder documentBuilder) {
        this.documentBuilder = documentBuilder;
    }

    public DocumentContextBuilder add(String key, Object contextItem) {
        itemLookup.put(key, contextItem);
        return this;
    }

    public DocumentContext build() {
        checkState(documentBuilder != null);
        DocumentContext context = new DocumentContext(documentBuilder);
        for(Map.Entry<String, Object> entry : itemLookup.entrySet()) {
            context.add(entry.getKey(), entry.getValue());
        }
        return context;
    }

    public static DocumentContext defaultContext(String modelKey, Object documentModel,
                                         ByteArrayOutputStream os) throws DocumentException {
        Document document = new Document(PageSize.LETTER, 20, 20, 100, 48);
        PdfWriter writer = PdfWriter.getInstance(document, os);
        DocumentBuilder documentBuilder = new DocumentBuilder(document, writer);
        return new DocumentContextBuilder(documentBuilder).add(modelKey, documentModel).build();
    }
}
