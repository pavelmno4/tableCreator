package com.example.tableCreator.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "settings")
public class Settings {
    private Page page;
    private List<Column> column = new ArrayList<>();


    public Settings() {}

    public Settings(Page page, List<Column> column) {
        this.page = page;
        this.column = column;
    }

    public Page getPage() {
        return page;
    }

    @XmlElement(name = "page")
    public void setPage(Page page) {
        this.page = page;
    }


    public List<Column> getColumn() {
        return column;
    }

    @XmlElementWrapper(name = "columns", nillable = true)
    public void setColumn(List<Column> column) {
        this.column = column;
    }

    public void addColumns(Column column) {
        this.column.add(column);
    }
}