package com.company.vika.models;

import java.util.List;

public class Group {
    private String Name;
    private List<Pair> pairs;

    public Group(String name, List<Pair> pairs) {
        Name = name;
        this.pairs = pairs;
    }

    public List<Pair> getPairs() {
        return pairs;
    }

    public String getName() {
        return Name;
    }
}
