package com.containerstore.common.thirdparty.itext;

import java.util.Map;

import static com.google.common.collect.Maps.*;

public class DocumentContext {
    private Map<String, Object> itemLookup = newHashMap();
    private DocumentBuilder documentBuilder;

    DocumentContext(DocumentBuilder documentBuilder) {
        this.documentBuilder = documentBuilder;
    }

    public void add(String key, Object contextItem) {
        itemLookup.put(key, contextItem);
    }

    public <T> T get(String key) {
        return (T) itemLookup.get(key);
    }

    public DocumentBuilder builder() {
        return this.documentBuilder;
    }

    public DocumentContext createCopy() {
        DocumentContext documentContext = new DocumentContext(builder());
        for(Map.Entry<String, Object> each : itemLookup.entrySet()) {
            documentContext.add(each.getKey(), each.getValue());
        }
        return documentContext;
    }
}
