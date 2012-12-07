package com.containerstore.common.thirdparty.itext;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;

public abstract class AbstractPdfDocumentBuilder {

    protected final Logger log = Logger.getLogger(getClass());

    protected PdfWriter initializePdfWriter(PdfPageEventHelper decorator, Document document, ByteArrayOutputStream out)
            throws DocumentException {
        PdfWriter pdfWriter = initializePdfWriter(document, out);
        pdfWriter.setPageEvent(decorator);
        return pdfWriter;
    }

    protected PdfWriter initializePdfWriter(Document document, ByteArrayOutputStream out) throws DocumentException {
        PdfWriter pdfWriter = PdfWriter.getInstance(document, out);
        pdfWriter.setViewerPreferences(PdfWriter.PageModeUseOutlines | PdfWriter.DisplayDocTitle);
        return pdfWriter;
    }

    protected abstract void addDocumentMetadata(Document document);
}
