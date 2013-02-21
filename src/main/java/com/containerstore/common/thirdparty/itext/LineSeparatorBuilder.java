package com.containerstore.common.thirdparty.itext;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.awt.Color;

public class LineSeparatorBuilder extends FormatableBuilder<LineSeparatorBuilder> {
    private final LineSeparator lineSeparator;

    public LineSeparatorBuilder(float width, float percentage, Color color, Alignment alignment, float offset) {
        BaseColor lineColor = null;
        if (color != null) {
            lineColor = new BaseColor(color.getRGB());
        }
        lineSeparator = new LineSeparator(width, percentage, lineColor, alignment.toPdfAlignment(), offset);
    }

    public LineSeparatorBuilder() {
        lineSeparator = new LineSeparator();
    }

    @Override
    <T extends Element> T  build() {
        return (T) lineSeparator;
    }
}
