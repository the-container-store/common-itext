package com.containerstore.common.thirdparty.itext;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;

import java.io.IOException;

import static com.itextpdf.text.pdf.BaseFont.*;

public final class Fonts {

    public static final String ARIAL = "Arial";
    public static final String ARIAL_BOLD = "Arial-Bold";
    public static final String GOTHIC = "Gothic";
    public static final String GOTHIC_BOLD = "Gothic-Bold";

    static {
        FontFactory.register("/media/fonts/GOTHIC.TTF", GOTHIC);
        FontFactory.register("/media/fonts/GOTHICB.TTF", GOTHIC_BOLD);
        FontFactory.register("/media/fonts/arial.ttf", ARIAL);
        FontFactory.register("/media/fonts/arialbd.ttf", ARIAL_BOLD);
    }

    public static Font topLevelHeaderFont() {
        return gothicBold(11f);
    }
    
    public static Font defaultFont() {
        return gothic(9f);
    }

    public static Font boldFont() {
        return gothicBold(9f);
    }

    public static Font secondLevelHeaderFont() {
        return gothicBold(10f);
    }

    public static Font thirdLevelHeaderFont() {
        return gothicBold(9f);
    }

    public static Font gothicBold(float size) {
        return FontFactory.getFont(GOTHIC_BOLD, WINANSI, true, size);
    }

    public static Font gothic(float size) {
        return FontFactory.getFont(GOTHIC, WINANSI, true, size);
    }

    public static BaseFont gothic() throws IOException, DocumentException {
        return createFont("/media/fonts/GOTHIC.TTF", WINANSI, EMBEDDED);
    }

    public static Font createCourierNew(float size, int style) throws IOException, DocumentException {
        BaseFont base = createFont("/media/fonts/courier-new.ttf", IDENTITY_H, EMBEDDED);
        return new Font(base, size, style);
    }

    public static BaseFont arial() throws IOException, DocumentException {
        return createFont("/media/fonts/arial.ttf", WINANSI, EMBEDDED);
    }

    public static Font arial(float size) {
        return FontFactory.getFont(ARIAL, WINANSI, true, size);
    }

    public static Font arialBold(float size) {
        return FontFactory.getFont(ARIAL_BOLD, WINANSI, true, size);
    }
    
}
