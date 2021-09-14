package com.company.tests;

import com.company.Retriever;
import com.company.Tag;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class RetrieverTest {
    static Retriever retriever;
    static Tag.Type[] types = new Tag.Type[]{Tag.Type.LINK, Tag.Type.IMG, Tag.Type.SCRIPT, Tag.Type.META};

    @BeforeAll
    static void beforeAll() {
        retriever = new Retriever(List.of(types));
    }

    @Test
    void generateSelector() {
        Document doc = Jsoup.parse("");
        retriever.getData(doc);
        Assertions.assertEquals("link, img, script", retriever.generateSelector());
    }

    @Test
    void getData() {
        Document doc = null;
        try {
            doc = Jsoup
                    .connect("http://jsonprettify.com/")
                    .userAgent(
                            "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.110 Safari/537.36")
                    .timeout(0).followRedirects(true).execute().parse();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Tag tg : retriever.getData(doc)) {
            System.out.println(tg);
        }
    }
}