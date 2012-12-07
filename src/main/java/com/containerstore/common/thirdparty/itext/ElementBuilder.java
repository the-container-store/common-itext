package com.containerstore.common.thirdparty.itext;

import com.itextpdf.text.Element;

public abstract class ElementBuilder {
    abstract <T extends Element> T build();
}
