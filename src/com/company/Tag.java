package com.company;

import java.net.MalformedURLException;
import java.net.URL;

public class Tag {
    private String name;
    private URL url;

    public Tag(String name, URL url) {
        this.name = name;
        this.url = url;
    }

    public Tag(String name, String url, String codeLine) {

    }

    public Tag(String name, String url) {
        this.name = name;
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                ", url=" + url +
                '}';
    }

    public enum Type {
        SCRIPT("script"),
        META("meta"),
        IMG("img"),
        LINK("link"),
        EMBED("embed"),
        IFRAME("iframe");

        public final String tagName;

        Type(String tagName) {
            this.tagName = tagName;
        }


    }
}
