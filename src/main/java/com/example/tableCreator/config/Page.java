package com.example.tableCreator.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "page")
public class Page {
    private int width;
    private int height;

    public Page() {}

    public Page(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    @XmlElement(name = "width")
    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    @XmlElement(name = "height")
    public void setHeight(int height) {
        this.height = height;
    }
}
