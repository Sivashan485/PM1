package com.NotFalse.app;

import java.util.ArrayList;

public class Formatter {
    private FormatType formatType;
    private String formattedText;

    public Formatter(FormatType formatType) {
        // initialization
    }

    public String formatTextRaw(ArrayList<String> text) {
        // implementation
        return formattedText;
    }

    public String formatTextFixed(ArrayList<String> text, int maxWidth) {
        // implementation
        return formattedText;
    }

    public String getFormattedText() {
        return formattedText;
    }

    public FormatType getFormatType() {
        return formatType;
    }

    public void setFormatType(FormatType formatType) {
        this.formatType = formatType;
    }

}
