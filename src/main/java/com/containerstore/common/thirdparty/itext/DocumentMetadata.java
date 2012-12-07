package com.containerstore.common.thirdparty.itext;

public class DocumentMetadata {
    private final String title;
    private final String author;
    private final String subject;
    private final String creator;

    public DocumentMetadata(String title, String author, String subject, String creator) {
        this.title = title;
        this.author = author;
        this.subject = subject;
        this.creator = creator;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getSubject() {
        return subject;
    }

    public String getCreator() {
        return creator;
    }
}
