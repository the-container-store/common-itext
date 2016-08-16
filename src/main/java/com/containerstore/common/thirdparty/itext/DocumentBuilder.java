package com.containerstore.common.thirdparty.itext;

import com.containerstore.common.base.exception.SystemException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.List;

import static com.google.common.collect.Lists.*;

public class DocumentBuilder {

    private Document document;
    private final PdfWriter pdfWriter;
    private final PdfPageEventAdapter event;

    public DocumentBuilder(Document document, PdfWriter pdfWriter) {
        this.document = document;
        this.pdfWriter = pdfWriter;
        event = new PdfPageEventAdapter();
        pdfWriter.setPageEvent(event);
    }

    public void openDocument() {
        if (!document.isOpen()) {
            document.open();
        }
    }

    public void closeDocument() {
        if (document.isOpen()) {
            document.close();
        }
    }

    public void newPage() {
        document.newPage();
    }

    public DocumentBuilder addEventListener(PdfPageEvent event) {
        pdfWriter.setPageEvent(event);
        return this;
    }

    public DocumentBuilder addEventListener(PageEventListener listener) {
        event.addListener(listener);
        return this;
    }

    public DocumentBuilder addTable(TableBuilder table) {
        try {
            document.add(table.build());
            return this;
        } catch (DocumentException e) {
            throw new SystemException("Error adding table to document.", e);
        }
    }

    public void addParagraph(ParagraphBuilder paraBuilder) throws DocumentException {
        document.add(paraBuilder.build());
    }

    public void addUnorderedList(UnorderedListBuilder listBuilder) throws DocumentException {
        document.add(listBuilder.build());
    }

    public void addLineSeparator(LineSeparatorBuilder lineSeparatorBuilder) throws DocumentException {
        document.add(lineSeparatorBuilder.build());
    }

    public void addDocumentMetadata(DocumentMetadata metadata) {
        document.addTitle(metadata.getTitle());
        document.addAuthor(metadata.getAuthor());
        document.addSubject(metadata.getSubject());
        document.addCreator(metadata.getCreator());
    }

    public void writeTable(TableBuilder table, int y) {
        PdfPTable pdfTable = table.build();
        Rectangle page = document.getPageSize();
        pdfTable.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
        pdfTable.writeSelectedRows(0, -1, document.leftMargin(), page.getHeight() - y, pdfWriter.getDirectContent());
    }

    public Image createBarcodeImage(String barcodeText) {
        PdfContentByte cb = pdfWriter.getDirectContent();
        cb.saveState();

        Barcode128 code128 = new Barcode128();
        code128.setCode(barcodeText);
        code128.setBarHeight(15f);

        code128.setAltText(barcodeText);
        Image image = code128.createImageWithBarcode(cb, null, null);

        cb.restoreState();

        return image;
    }

    private class PdfPageEventAdapter extends PdfPageEventHelper {

        private List<PageEventListener> listeners = newArrayList();
        int pageNumber = 1;

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            for (PageEventListener each : listeners) {
                each.onBeginPage(DocumentBuilder.this, pageNumber++);
            }
        }

        public void addListener(PageEventListener listener) {
            listeners.add(listener);
        }
    }


}
