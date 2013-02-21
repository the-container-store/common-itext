package com.containerstore.common.thirdparty.itext;

import com.containerstore.common.base.exception.SystemException;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;

import java.io.IOException;

import static com.containerstore.common.thirdparty.itext.Alignment.*;
import static com.containerstore.common.thirdparty.itext.BorderDirective.*;
import static com.containerstore.common.thirdparty.itext.TableBuilder.*;
import static com.containerstore.common.thirdparty.itext.TableCellBuilder.*;

public class PageHeaderEventListener extends PageEventListenerAdapter {

    private final String id;
    private final String displayName;

    public PageHeaderEventListener(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    @Override
    public void onBeginPage(DocumentBuilder document, int pageNumber) {
        TableBuilder table = buildHeaderTable(document);
        writeTableToDocument(document, table);
    }

    private TableBuilder buildHeaderTable(DocumentBuilder document) {
        // top right of header with customer name, project name and project barcode
        TableBuilder projectTable = table(1)
                .addCell(buildProjectInfoCell())
                .addCell(buildBarcodeCell(document));

        // entire header, including the TCS log on the top left
        return table(2)
                .addCell(buildLogoCell())
                .addCell(nestedTableCell(projectTable));
    }

    private TableCellBuilder buildProjectInfoCell() {
        return textCell(RIGHT, displayName).padded(10, ON_BOTTOM).withNoBorder();
    }

    private TableCellBuilder buildLogoCell() {
        Image logo = createImage("/media/images/tcs_logo_blue.jpg");
        logo.scalePercent(20);
        return imageCell(logo);
    }

    private Image createImage(String location) {
        try {
            return Image.getInstance(getClass().getResource(location));
        } catch (BadElementException e) {
            throw new SystemException("Error creating image.");
        } catch (IOException e) {
            throw new SystemException("Error creating image.");
        }
    }


    private TableCellBuilder buildBarcodeCell(DocumentBuilder writer) {
        return imageCell(buildBarcode(writer)).aligned(RIGHT);
    }

    private void writeTableToDocument(DocumentBuilder document, TableBuilder headerTable) {
        document.writeTable(headerTable, 30);
    }

    private Image buildBarcode(DocumentBuilder document) {
        String barcodeText = "PROJ" + id;
        return document.createBarcodeImage(barcodeText);
    }

}
