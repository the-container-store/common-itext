package com.containerstore.common.thirdparty.itext;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.ListItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.itextpdf.text.Font.FontFamily.*;

public class UnorderedListBuilder extends FormatableBuilder<UnorderedListBuilder> {
    private static final char BULLET = 108;

    private final List<String> items = new ArrayList<>();

    protected UnorderedListBuilder(List<String> items) {
        this.items.addAll(items);
    }

    @Override
    <T extends Element> T build() {
        com.itextpdf.text.List unorderedList = new com.itextpdf.text.List();

        unorderedList.setListSymbol(bulletChunk());
        for (String item : items) {
            ListItem each = new ListItem(item);
            each.getFont().setSize(fontSize);
            each.getFont().setStyle(fontStyle.toPdfStyle());
            unorderedList.add(each);
        }

        return (T) unorderedList;
    }

    private Chunk bulletChunk() {
        return new Chunk(bulletText(), bulletFont()).setTextRise(1.5f);
    }

    protected Font bulletFont() {
        // bullet size is typically smaller than font
        return new Font(ZAPFDINGBATS, fontSize - 6);
    }

    protected String bulletText() {
        return BULLET + "      ";
    }

    public static UnorderedListBuilder unorderedList(String... items) {
        return new UnorderedListBuilder(Arrays.asList(items));
    }
}
