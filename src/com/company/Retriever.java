package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class Retriever extends Thread {
    URL url;
    RetrieverStatusListener listener;
    private List<Tag.Type> allowedTagTypes;

    public Retriever() {
    }

    public Retriever(List<Tag.Type> tags) {
        this.allowedTagTypes = tags;
    }

    public Retriever(URL url) {
        this.url = url;
    }

    public Retriever(String url, List<Tag.Type> tags) throws MalformedURLException {
        this(new URL(url), tags);
    }

    public Retriever(URL url, List<Tag.Type> tags) {
        this(url);
        allowedTagTypes = tags;
    }

    @Override
    public void run() {
        getData();
    }

    public void getData() {
        try {
            getData(Jsoup
                    .connect(url.toString())
                    .userAgent(
                            "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.110 Safari/537.36")
                    .timeout(0).followRedirects(true).execute().parse());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Tag> getData(Document htmlDocument) {
        List<Tag> tagsList;
        Elements links = htmlDocument.select(generateSelector());
        tagsList = getAllowedTagsObjectsList(links);

        if (listener != null) {
            listener.onDataRetrieved(tagsList);
        }
        return tagsList;
    }

    private List<Tag> getAllowedTagsObjectsList(Elements allowedElements) {
        return allowedElements.stream()
                .filter(element -> !element.attr("src").equals("") || !element.attr("href").equals(""))
                .map(this::buildTag).filter(Objects::nonNull).toList();
    }

    public void setListener(RetrieverStatusListener listener) {
        this.listener = listener;
    }

    public void setUrl(String url) {
        try {
            setUrl(new URL(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private Tag buildTag(Element el) {
        Tag tagObject = null;
        if (el.tagName().equals("link")) {
            tagObject = new Tag(el.tagName(), el.attr("abs:href"));
        } else if (el.tagName().equals("script") || el.tagName().equals("img")) {
            tagObject = new Tag(el.tagName(), el.attr("abs:src"));
        }
        return tagObject;
    }

    public String generateSelector() {
        String query = allowedTagTypes.isEmpty() ?
                "*":
                String.join(", ", allowedTagTypes.stream().map(type -> type.tagName).toList());
        return query;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public void updateAllowedTagsList(List<Tag.Type> scanningTagsTypesList) {
        allowedTagTypes = scanningTagsTypesList;
    }
}
