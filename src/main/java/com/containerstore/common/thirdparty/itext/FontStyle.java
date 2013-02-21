package com.containerstore.common.thirdparty.itext;

import com.itextpdf.text.Font;

public enum FontStyle {
    BOLD(Font.BOLD), NORMAL(Font.NORMAL), ITALIC(Font.ITALIC), BOLDITALIC(Font.BOLDITALIC);

    private final int pdfStyle;

    FontStyle(int pdfStyle) {
        this.pdfStyle = pdfStyle;
    }

    public int toPdfStyle() {
        return pdfStyle;
    }
}
