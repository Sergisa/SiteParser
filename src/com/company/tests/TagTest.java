package com.company.tests;

import com.company.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class TagTest {
    @Test
    void mergeTypes() {
        List<Tag.Type> allAvailableTypes = new ArrayList<>(List.of(Tag.Type.values()));
        List<Tag.Type> scanningTypeList = new ArrayList<>(List.of(new String[]{"meta", "script"})
                .stream()
                .map(s -> Tag.Type.valueOf(s.toUpperCase()))
                .toList());
        System.out.println(allAvailableTypes.stream().filter(type -> !scanningTypeList.contains(type)).toList());
    }

}