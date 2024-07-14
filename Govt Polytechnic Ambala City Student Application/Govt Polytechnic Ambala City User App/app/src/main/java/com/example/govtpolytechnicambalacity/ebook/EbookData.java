package com.example.govtpolytechnicambalacity.ebook;

public class EbookData {
    private String ebookTitle, ebookUrl;

    public EbookData() {
    }

    public EbookData(String ebookTitle, String ebookUrl) {
        this.ebookTitle = ebookTitle;
        this.ebookUrl = ebookUrl;
    }

    public String getEbookTitle() {
        return ebookTitle;
    }

    public void setEbookTitle(String ebookTitle) {
        this.ebookTitle = ebookTitle;
    }

    public String getEbookUrl() {
        return ebookUrl;
    }

    public void setEbookUrl(String ebookUrl) {
        this.ebookUrl = ebookUrl;
    }
}
