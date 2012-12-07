package com.containerstore.common.thirdparty.itext;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.awt.Color;

public class LineSeparatorBuilder extends FormatableBuilder<LineSeparatorBuilder> {
    private final LineSeparator lineSeperator;

    public LineSeparatorBuilder(float width, float percentage, Color color, Alignment alignment, float offset) {
        lineSeperator = new LineSeparator(width, percentage, new BaseColor(color.getRGB()), alignment.toPdfAlignment(),
                                                 offset);
    }

    @Override
    <T extends Element> T  build() {
        return (T) lineSeperator;
    }
}
