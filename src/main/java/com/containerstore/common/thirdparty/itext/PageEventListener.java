package com.containerstore.common.thirdparty.itext;

public interface PageEventListener {

    void onOpenDocument(DocumentBuilder document);

    void onBeginPage(DocumentBuilder document, int pageNumber);

    void onEndPage(DocumentBuilder document);

    void onCloseDocument(DocumentBuilder document);

}
