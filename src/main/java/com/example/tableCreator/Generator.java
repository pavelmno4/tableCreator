package com.example.tableCreator;

import com.example.tableCreator.config.Column;
import com.example.tableCreator.config.Page;
import com.example.tableCreator.config.Settings;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Generator {

    public static void main(String[] args) throws JAXBException, FileNotFoundException {

        // Загружаем настройки из XML
        Settings settings = loadSettings(args[0]);

        // Ширина страницы
        int pageWidth = settings.getPage().getWidth();

        // Длина страницы
        int pageHeight = settings.getPage().getHeight();

        // Список со значениями ширины колонок
        List<Integer> columnWidths = new ArrayList<>();

        // Список со значениями заголовков колонок
        List<String> columnsTitle = new ArrayList<>();

        // Добавление значений из настроек в соответствующие списки
        for(Column col : settings.getColumn()) {
            columnWidths.add(col.getWidth());
            columnsTitle.add(col.getTitle());
        }

        // Загрузка данных из tsv-файла
        TsvParserSettings parserSettings = new TsvParserSettings();
        TsvParser parser = new TsvParser(parserSettings);
        List<String[]> dataList = parser.parseAll(new File(args[1]), "UTF-16");

        // Изменение ширины колонок под ширину данных
        for(String[] str : dataList) {
            for(int i = 0; i < str.length; i++) {
                if(str[i].length() > columnWidths.get(i)) {
                    columnWidths.set(i, str[i].length());
                }
            }
        }

        // Длина разделительной линии
        int separateLineReiteration = 4;
        for(int i = 0; i < columnWidths.size(); i++ ) {
            separateLineReiteration += columnWidths.get(i) + 2;
        }

        // Разделительная линия
        String separateLine = "";
        for(int i = 0; i < separateLineReiteration; i++) {
            separateLine += "-";
        }

        // Формат вывода заголовка
        String heading = String.format("| %-" +columnWidths.get(0)+ "s" + "%s" +
                                         "%-" +columnWidths.get(1)+ "s" + "%s" +
                                         "%-" +columnWidths.get(2)+ "s" + "%s",
                columnsTitle.get(0), " | ", columnsTitle.get(1), " | ", columnsTitle.get(2), " | ");



        try(BufferedWriter bufferedWriter = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(args[2]), StandardCharsets.UTF_16))) {
            bufferedWriter.write(heading);

            // Формат вывода данных
            String data = "";
            int count = 1;
            for(int i = 0; i < dataList.size(); i++) {
                count += 2;
                data = String.format("%n%s%n| %-" +columnWidths.get(0)+ "s" + "%s" +
                                "%-" +columnWidths.get(1)+ "s" + "%s" +
                                "%-" +columnWidths.get(2)+ "s" + "%s",
                        separateLine, dataList.get(i)[0], " | ", dataList.get(i)[1], " | ", dataList.get(i)[2], " | ");
                bufferedWriter.write(data);
                if(count >= pageHeight) {
                    bufferedWriter.write("\n~\n");
                    if(i != dataList.size()-1) {
                        bufferedWriter.write(heading);
                    }
                    count = 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    // Метод создания файла настроек
    public static void createSettings() throws JAXBException {
        Page page = new Page(32, 12);
        List<Column> column = new ArrayList<Column>();


        Settings settings = new Settings(page, column);


        column.add(new Column("Номер", 8));
        column.add(new Column("Дата", 7));
        column.add(new Column("ФИО", 7));


        JAXBContext context = JAXBContext.newInstance(Settings.class);
        Marshaller mar = context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        mar.marshal(settings, new File("./settings_2.xml"));
    }

    // Метод загрузки настроек из файла
    public static Settings loadSettings(String fileName) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(Settings.class);
        return (Settings) context.createUnmarshaller()
                .unmarshal(new FileReader(fileName));
    }
}
