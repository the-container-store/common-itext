package com.containerstore.common.thirdparty.itext;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;

public class PhraseBuilder extends FormatableBuilder<PhraseBuilder> {
    private String sentence;

    public PhraseBuilder(String sentence) {
        this.sentence = sentence;
    }

    @Override
    <T extends Element> T build() {
        Phrase phrase = new Phrase(new Chunk(sentence));
        phrase.getFont().setStyle(fontStyle.toPdfStyle());
        phrase.getFont().setColor(new BaseColor(fontColor.getRGB()));
        phrase.getFont().setSize(fontSize);
        return (T) phrase;
    }

    public static PhraseBuilder phrase(String template, Object...args) {
        return new PhraseBuilder(String.format(template, args));
    }
}
