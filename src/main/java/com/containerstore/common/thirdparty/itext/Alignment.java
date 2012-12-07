package com.containerstore.common.thirdparty.itext;

import com.itextpdf.text.Element;

public enum Alignment {
    UNDEFINED(Element.ALIGN_UNDEFINED),
    LEFT(Element.ALIGN_LEFT),
    CENTER(Element.ALIGN_CENTER),
    RIGHT(Element.ALIGN_RIGHT),
    JUSTIFIED(Element.ALIGN_JUSTIFIED),
    JUSTIFIED_ALL(Element.ALIGN_JUSTIFIED_ALL),
    MIDDLE(Element.ALIGN_MIDDLE);

    private final int pdfAlignment;

    Alignment(int pdfAlignment) {
        this.pdfAlignment = pdfAlignment;
    }

    int toPdfAlignment() {
        return pdfAlignment;
    }
}
