package com.example.tableCreator.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "column")
public class Column {
    private String title;
    private int width;

    public Column() {}

    public Column(String title, int width) {
        this.title = title;
        this.width = width;
    }

    public String getTitle() {
        return title;
    }

    @XmlElement(name = "title")
    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    @XmlElement(name = "width")
    public void setWidth(int width) {
        this.width = width;
    }
}
