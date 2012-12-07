package com.containerstore.common.thirdparty.itext;

import java.awt.Color;

abstract class FormatableBuilder<T> extends ElementBuilder {
    protected int fontSize = 12;
    protected Color fontColor = Color.BLACK;
    protected Color backgroundColor = Color.WHITE;
    protected FontStyle fontStyle = FontStyle.NORMAL;
    protected Alignment alignment = Alignment.JUSTIFIED;
    protected float indentationLeft = 0;
    protected float indentationRight = 0;
    protected float spacingBefore = 10;
    protected float spacingAfter = 10;


    public T fontStyle(FontStyle style) {
        this.fontStyle = style;
        return castToSubclass();
    }

    public T fontColor(Color fontColor) {
        this.fontColor = fontColor;
        return castToSubclass();
    }

    public T backgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        return castToSubclass();
    }

    public T fontSize(int size) {
        this.fontSize = size;
        return castToSubclass();
    }

    protected T castToSubclass() {
        return (T) this;
    }

    public T alignment(Alignment alignment) {
        this.alignment = alignment;
        return castToSubclass();
    }

    public T indentationLeft(float indentationLeft) {
        this.indentationLeft = indentationLeft;
        return castToSubclass();
    }

    public T indentationRight(float indentationRight) {
        this.indentationRight = indentationRight;
        return castToSubclass();
    }

    public T spacingBefore(float spacingBeforet) {
        this.spacingBefore = spacingBefore;
        return castToSubclass();
    }

    public T spacingAfter(float spacingAftert) {
        this.spacingAfter = spacingAfter;
        return castToSubclass();
    }
}
