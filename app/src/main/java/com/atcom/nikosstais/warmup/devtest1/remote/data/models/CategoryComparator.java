package com.atcom.nikosstais.warmup.devtest1.remote.data.models;

import java.util.Comparator;

public class CategoryComparator implements Comparator<Category> {

    @Override
    public int compare(Category o1, Category o2) {
        return Integer.compare(o1.getOrder(),o2.getOrder());
    }
}
