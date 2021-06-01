package com.example.recyclerviewdemo;

public class Card {

    private final String textExample, titleExample;

    public Card( String titleExample, String textExample) {
        this.titleExample = titleExample;
        this.textExample = textExample;
    }

    public String getTextExample() {
        return textExample;
    }

    public String getTitleExample() {
        return titleExample;
    }
}
