package com.containerstore.common.thirdparty.itext;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.ListItem;

import java.util.List;

import static com.google.common.collect.Lists.*;
import static com.itextpdf.text.Font.FontFamily.*;

public class UnorderedListBuilder extends FormatableBuilder<UnorderedListBuilder> {

    private static final char BULLET = 108;

    private final List<String> items = newArrayList();

    private UnorderedListBuilder(List<String> items) {
        this.items.addAll(items);
    }

    @Override
    <T extends Element> T  build() {
        com.itextpdf.text.List unorderedList = new com.itextpdf.text.List();
        unorderedList.setListSymbol(bulletChunk());
        for (String item : items) {
            ListItem each = new ListItem(item);
            each.getFont().setSize(fontSize);
            unorderedList.add(each);
        }
        return (T) unorderedList;
    }

    private Chunk bulletChunk() {
        // bullet size is typically smaller than font
        Font font = new Font(ZAPFDINGBATS, fontSize - 5);
        String text = BULLET + "      ";
        return new Chunk(text, font).setTextRise(1.5f);
    }

    public static UnorderedListBuilder unorderedList(String... items) {
        return new UnorderedListBuilder(newArrayList(items));
    }
}
