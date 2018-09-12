package com.containerstore.common.thirdparty.itext;

import com.containerstore.common.base.exception.SystemException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class TableBuilder extends ElementBuilder {

    final PdfPTable pdfTable;
    int borderWidthRight = 0;
    int borderWidthLeft = 0;
    int borderWidthTop = 0;
    int borderWidthBottom = 0;
    int borderType = Rectangle.NO_BORDER;

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
            setBorder((PdfPCell) element);
            pdfTable.addCell((PdfPCell) element);
        } else {
            PdfPCell cell = new PdfPCell();
            setBorder(cell);
            cell.addElement(element);
            pdfTable.addCell(cell);
        }

        return this;
    }

    public TableBuilder withWidthPercentage(float percentage, Alignment alignment) {
        pdfTable.setWidthPercentage(percentage);
        pdfTable.setHorizontalAlignment(alignment.toPdfAlignment());
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
        setUniformWidth((int)width);
        return this;
    }

    private void setUniformWidth(int width) {
        borderWidthRight = width;
        borderWidthLeft = width;
        borderWidthTop = width;
        borderWidthBottom = width;
    }

    public void setBorder(PdfPCell cell) {
        //TODO: support bit wise combinations
        cell.setBorder(borderType);
        switch (borderType) {
            case Rectangle.BOX:
                cell.setBorderWidth(borderWidthLeft);
                break;
            case Rectangle.TOP:
                cell.setBorderWidthTop(borderWidthTop);
                break;
            case Rectangle.BOTTOM:
                cell.setBorderWidthBottom(borderWidthBottom);
                break;
            case Rectangle.RIGHT:
                cell.setBorderWidthRight(borderWidthRight);
                break;
            case Rectangle.LEFT:
                cell.setBorderWidthLeft(borderWidthLeft);
                break;
            default:
                cell.setBorderWidth(0);
        }
    }

    public TableBuilder withBorder(float width, BorderDirective directive) {
        //TODO: support bit wise combinations
        switch (directive) {
            case ON_ALL_SIDES:
                borderType = Rectangle.BOX;
                setUniformWidth((int)width);
                break;
            case ON_TOP:
                borderType = Rectangle.TOP;
                borderWidthTop = (int) width;
                break;
            case ON_BOTTOM:
                borderType = Rectangle.BOTTOM;
                borderWidthBottom = (int) width;
                break;
            case ON_RIGHT:
                borderType = Rectangle.RIGHT;
                borderWidthRight = (int) width;
                break;
            case ON_LEFT:
                borderType = Rectangle.LEFT;
                borderWidthLeft = (int) width;
                break;
            default:
                borderType = Rectangle.NO_BORDER;
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
