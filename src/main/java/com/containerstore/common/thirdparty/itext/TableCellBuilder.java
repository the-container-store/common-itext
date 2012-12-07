package com.containerstore.common.thirdparty.itext;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;

import static com.containerstore.common.thirdparty.itext.BorderDirective.ON_ALL_SIDES;
import static com.itextpdf.text.Rectangle.*;

public abstract class TableCellBuilder extends FormatableBuilder<TableCellBuilder> {

    protected final PdfPCell cell;

    public static TableCellBuilder nestedTableCell(final TableBuilder table) {
        TableCellBuilder cell = new TableCellBuilder() {
            @Override
            <T extends Element> T  build() {
                cell.addElement(table.build());
                return (T) cell;
            }
        };
        return cell.withNoBorder();
    }

    public static ImageTableCellBuilder imageCell(Image image) {
        return new ImageTableCellBuilder(image);
    }

    public static TableCellBuilder textCell(Alignment alignment, FontStyle style, String template, Object... params) {
        return new TextTableCellBuilder(template, params).aligned(alignment).fontStyle(style);
    }

    public static TableCellBuilder textCell(Alignment alignment, String template, Object... params) {
        return new TextTableCellBuilder(template, params).aligned(alignment);
    }

    public static TextTableCellBuilder textCell(String template, Object... params) {
        return new TextTableCellBuilder(template, params);
    }

    public static QRCodeTableCellBuilder qrCodeCell(String toQrCode) {
        return new QRCodeTableCellBuilder(toQrCode);
    }

    public static TableCellBuilder children(ElementBuilder... childBuilders) {
        return new ChildElementsCellBuilder(childBuilders);
    }

    private TableCellBuilder() {
        this(new PdfPCell());
    }

    private TableCellBuilder(PdfPCell cell) {
        this.cell = cell;
    }

    public TableCellBuilder aligned(Alignment alignment) {
        cell.setHorizontalAlignment(alignment.toPdfAlignment());
        return this;
    }

    @Override
    <T extends Element> T  build() {
        cell.setBackgroundColor(new BaseColor(backgroundColor.getRGB()));
        return (T) cell;
    }

    public TableCellBuilder withBorderWidth(int width) {
        cell.setBorderWidth(width);
        return this;
    }
    public TableCellBuilder withNoBorder() {
        cell.setBorder(NO_BORDER);
        return this;
    }

    public TableCellBuilder withBorderWidth(int width, BorderDirective directive) {
        //TODO: support bit wise combinations
        switch (directive) {
            case ON_ALL_SIDES:
                cell.setBorder(Rectangle.BOX);
                cell.setBorderWidth(width);
                break;
            case ON_TOP:
                cell.setBorder(Rectangle.TOP);
                cell.setBorderWidthTop(width);
                break;
            case ON_BOTTOM:
                cell.setBorder(Rectangle.BOTTOM);
                cell.setBorderWidthBottom(width);
                break;
            case ON_RIGHT:
                cell.setBorder(Rectangle.RIGHT);
                cell.setBorderWidthRight(width);
                break;
            case ON_LEFT:
                cell.setBorder(Rectangle.LEFT);
                cell.setBorderWidthLeft(width);
                break;
            default:
                withNoBorder();
        }
        return this;
    }

    public TableCellBuilder spanning(int count, SpanDirective directive) {
        switch (directive) {
            case COLUMNS:
                cell.setColspan(count);
                break;
            case ROWS:
                cell.setRowspan(count);
                break;
            default:
                throw new IllegalArgumentException("Unsupported directive: " + directive);
        }
        return this;
    }

    public TableCellBuilder padded(int count, BorderDirective directive) {
        switch (directive) {
            case ON_LEFT:
                cell.setPaddingLeft(count);
                break;
            case ON_BOTTOM:
                cell.setPaddingBottom(count);
                break;
            case ON_TOP:
                cell.setPaddingTop(count);
                break;
            case ON_ALL_SIDES:
                cell.setPadding(count);
                break;
            default:
                throw new IllegalArgumentException("Unsupported directive: " + directive);
        }
        return this;
    }


    public static enum SpanDirective {
        ROWS, COLUMNS
    }

    public static class TextTableCellBuilder extends TableCellBuilder {

        private final Phrase phrase;

        private TextTableCellBuilder(String template, Object[] params) {
            phrase = new Phrase(String.format(template, params));
            // give a little padding on the bottom of the cell by default
            padded(5, ON_ALL_SIDES);
        }

        @Override
        <T extends Element> T  build() {
            super.build();
            phrase.getFont().setStyle(fontStyle.toPdfStyle());
            phrase.getFont().setColor(new BaseColor(fontColor.getRGB()));
            phrase.getFont().setSize(fontSize);
            cell.setPhrase(phrase);
            return (T) cell;
        }
    }

    public static class ImageTableCellBuilder extends TableCellBuilder {

        private ImageTableCellBuilder(Image image) {
            super(new PdfPCell(image, false));
            withBorderWidth(0);
        }

        @Override
        <T extends Element> T build() {
            super.build();
            return (T) cell;
        }
    }

    public static class ChildElementsCellBuilder extends TableCellBuilder {
        private final ElementBuilder[] elementBuilders;

        public ChildElementsCellBuilder(ElementBuilder... elementBuilders) {
            super(new PdfPCell());
            this.elementBuilders = elementBuilders;
            withBorderWidth(0);
        }

        @Override
        <T extends Element> T  build() {
            super.build();
            for (ElementBuilder each : elementBuilders) {
                cell.addElement(each.build());
            }
            return (T) cell;
        }
    }

    public static class QRCodeTableCellBuilder extends TableCellBuilder {

        private final String toQrCode;
        protected int height;
        protected int width;

        private QRCodeTableCellBuilder(String toQrCode) {
            super(new PdfPCell());
            this.toQrCode = toQrCode;
            withBorderWidth(0);
        }

        public QRCodeTableCellBuilder width(int width) {
            this.width = width;
            return this;
        }

        public QRCodeTableCellBuilder height(int height) {
            this.height = height;
            return this;
        }

        @Override
        <T extends Element> T  build() {
            super.build();
            BarcodeQRCode qrCode = new BarcodeQRCode(toQrCode, width, height, null);
            try {
                cell.setImage(qrCode.getImage());
            } catch (BadElementException e) {
                super.cell.setPhrase(new Phrase("Error: " + toQrCode));
            }
            return (T) cell;
        }
    }
}
