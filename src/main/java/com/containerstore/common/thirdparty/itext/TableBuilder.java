package com.containerstore.common.thirdparty.itext;

import com.containerstore.common.base.exception.SystemException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class TableBuilder extends ElementBuilder {

    final PdfPTable pdfTable;

    public static TableBuilder table(int numberOfColumns) {
        return new TableBuilder(numberOfColumns);
    }

    private TableBuilder(int numberOfColumns) {
        pdfTable = new PdfPTable(numberOfColumns);
        pdfTable.setWidthPercentage(100);
    }

    @Override
    <T extends Element> T  build() {
        return (T) pdfTable;
    }

    public TableBuilder addCell(ElementBuilder cellBuilder) {
        Element element = cellBuilder.build();
        if (element instanceof PdfPCell) {
            pdfTable.addCell((PdfPCell) element);
        } else {
            PdfPCell cell = new PdfPCell();
            cell.addElement(element);
            pdfTable.addCell(cell);
        }

        return this;
    }

    public TableBuilder withRelativeWidths(int... relativeWidths) {
        try {
            pdfTable.setWidths(relativeWidths);
        } catch (DocumentException e) {
            throw new SystemException("Error setting relative widths.", e);
        }
        return this;
    }

    public TableBuilder withBorder(float width) {
        pdfTable.getDefaultCell().setBorderWidth(width);
        return this;
    }

    public TableBuilder withBorder(float width, BorderDirective directive) {
        //TODO: support bit wise combinations
        switch (directive) {
            case ON_ALL_SIDES:
                pdfTable.getDefaultCell().setBorder(Rectangle.BOX);
                pdfTable.getDefaultCell().setBorderWidth(width);
                break;
            case ON_TOP:
                pdfTable.getDefaultCell().setBorder(Rectangle.TOP);
                pdfTable.getDefaultCell().setBorderWidthTop(width);
                break;
            case ON_BOTTOM:
                pdfTable.getDefaultCell().setBorder(Rectangle.BOTTOM);
                pdfTable.getDefaultCell().setBorderWidthBottom(width);
                break;
            case ON_RIGHT:
                pdfTable.getDefaultCell().setBorder(Rectangle.RIGHT);
                pdfTable.getDefaultCell().setBorderWidthRight(width);
                break;
            case ON_LEFT:
                pdfTable.getDefaultCell().setBorder(Rectangle.LEFT);
                pdfTable.getDefaultCell().setBorderWidthLeft(width);
                break;
            default:
                pdfTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        }
        return this;
    }

    public TableBuilder padded(int count, PaddingDirective directive) {
        switch (directive) {
            case ON_BOTTOM:
                pdfTable.setSpacingAfter(count);
                break;
            default:
                throw new IllegalArgumentException("Unsupported directive: " + directive);
        }
        return this;
    }
}
