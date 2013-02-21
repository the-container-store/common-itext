package com.containerstore.common.thirdparty.itext;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;

import java.util.List;

import static com.google.common.collect.Lists.*;

public class UnorderedListBuilder extends FormatableBuilder<UnorderedListBuilder> {
    private List<String> items = newArrayList();
//    private char symbol = 149;
    private char symbol = '\u2022';
    private float size = 10;

    public UnorderedListBuilder(List<String> items) {
        this.items = items;
    }

    public UnorderedListBuilder items(List<String> items) {
        items.addAll(items);
        return this;
    }

    public UnorderedListBuilder bulletSymbol(char symbol) {
        this.symbol = symbol;
        return this;
    }

    public UnorderedListBuilder size(float size) {
        this.size = size;
        return this;
    }

    @Override
    <T extends Element> T  build() {
        com.itextpdf.text.List unorderedList = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
        for (String item : items) {
            unorderedList.add(item);
        }
        Font font = new Font(Font.FontFamily.SYMBOL, size, Font.NORMAL);
        Chunk bullet = new Chunk(symbol, font);
        unorderedList.setListSymbol(bullet);
        return (T) unorderedList;
    }

    public static UnorderedListBuilder unorderedList(String... items) {
        return new UnorderedListBuilder(newArrayList(items));
    }
}
