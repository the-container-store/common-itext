package com.containerstore.common.thirdparty.itext;

import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;

import java.util.List;

import static com.google.common.collect.Lists.*;

public class ParagraphBuilder extends FormatableBuilder<ParagraphBuilder> {
    private List<PhraseBuilder> phrases = newArrayList();
    private float lineSpacing = 16.0f;

    public ParagraphBuilder() {
    }

    public ParagraphBuilder(String content) {
        phrases.add(new PhraseBuilder(content));
    }

    public ParagraphBuilder(PhraseBuilder[] phrases) {
        this.phrases.addAll(newArrayList(phrases));
    }

    public ParagraphBuilder addPhrase(String sentence) {
        phrases.add(new PhraseBuilder(sentence));
        return this;
    }

    public ParagraphBuilder addPhrase(PhraseBuilder phrase) {
        phrases.add(phrase);
        return this;
    }

    public ParagraphBuilder lineSpacing(float lineSpacing) {
        this.lineSpacing = lineSpacing;
        return this;
    }

    @Override
    <T extends Element> T build() {
        Paragraph paragraph = new Paragraph();
        for (PhraseBuilder each : phrases) {
            T build = each.build();
            paragraph.add(build);
        }
        paragraph.setAlignment(alignment.toPdfAlignment());
        paragraph.setIndentationLeft(indentationLeft);
        paragraph.setIndentationRight(indentationRight);
        paragraph.setSpacingBefore(spacingBefore);
        paragraph.setSpacingAfter(spacingAfter);
        paragraph.setLeading(lineSpacing);
        return (T) paragraph;
    }

    public static ParagraphBuilder paragraph(String template, Object... params) {
        return new ParagraphBuilder(String.format(template, params));
    }

    public static ParagraphBuilder paragraph(PhraseBuilder... phrases) {
        return new ParagraphBuilder(phrases);
    }
}
