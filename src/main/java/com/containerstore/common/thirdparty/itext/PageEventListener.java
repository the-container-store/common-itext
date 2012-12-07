package com.containerstore.common.thirdparty.itext;

public interface PageEventListener {

    void onOpenDocument(DocumentBuilder document);

    void onStartPage(DocumentBuilder document);

    void onEndPage(DocumentBuilder document);

    void onCloseDocument(DocumentBuilder document);

}
