package com.containerstore.common.thirdparty.itext;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;

import java.util.List;

import static com.google.common.collect.Lists.*;
import static com.itextpdf.text.Font.FontFamily.*;

public class UnorderedListBuilder extends FormatableBuilder<UnorderedListBuilder> {
    public static final char BULLET = 108;

    private final List<String> items = newArrayList();

    private UnorderedListBuilder(List<String> items) {
        this.items.addAll(items);
    }

    @Override
    <T extends Element> T  build() {
        com.itextpdf.text.List unorderedList = new com.itextpdf.text.List();
        unorderedList.setListSymbol(bulletChunk());

        for (String item : items) {
            unorderedList.add(item);
        }
        return (T) unorderedList;
    }

    private Chunk bulletChunk() {
        Font font = new Font(ZAPFDINGBATS, 7);
        String text = BULLET + "      ";
        return new Chunk(text, font).setTextRise(1.5f);
    }

    public static UnorderedListBuilder unorderedList(String... items) {
        return new UnorderedListBuilder(newArrayList(items));
    }
}
