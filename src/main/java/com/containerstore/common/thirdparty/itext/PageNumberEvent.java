package com.containerstore.common.thirdparty.itext;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;

public class PageNumberEvent extends PdfPageEventHelper {

    private PdfTemplate pageNumberTemplate;

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        pageNumberTemplate = writer.getDirectContent().createTemplate(200, 200);
        pageNumberTemplate.setBoundingBox(new Rectangle(-15, -15, 200, 200));
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        try {
            addPageNumberPlaceholder(writer, document);
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    // adds "Page n of " to the bottom of each page. The number of pages will be appended to this template in the
    // onCloseDocument method
    private void addPageNumberPlaceholder(PdfWriter writer, Document document) throws IOException, DocumentException {
        PdfContentByte cb = writer.getDirectContent();
        cb.saveState();
        String text = "Page " + writer.getPageNumber() + " of ";
        float textSize = Fonts.arial().getWidthPoint(text, 9);
        float textBase = document.bottom() - 30;

        float adjust = Fonts.arial().getWidthPoint("0", 9) + 0f;
        // Page Number
        cb.beginText();
        cb.setFontAndSize(Fonts.arial(), 9);
        cb.setTextMatrix(document.right() - textSize - adjust, textBase);
        cb.showText(text);
        cb.endText();
        cb.addTemplate(pageNumberTemplate, document.right() - adjust, textBase);
        cb.restoreState();
    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        pageNumberTemplate.beginText();
        try {
            pageNumberTemplate.setFontAndSize(Fonts.arial(), 9);
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
        pageNumberTemplate.setTextMatrix(0, 0);
        pageNumberTemplate.showText(Integer.toString(writer.getPageNumber() - 1));
        pageNumberTemplate.endText();
    }

}
