package com.company.electro_store.temp;

import com.company.electro_store.temp.other.PersonsCount.Total;
import com.company.electro_store.temp.other.ProductCount.Count;
import com.company.electro_store.temp.product.Product;
import com.company.electro_store.temp.worker.person.Person;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.Stream;

public class ReportController extends MenuController{
    private Font font;
    public ReportController() throws IOException, ClassNotFoundException {
    }

    private void addTableHeader(PdfPTable table, String...values) {
        Stream.of(values)
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle,font));
                    table.addCell(header);
                });
    }
    private void addRows(PdfPTable table, String...str) throws DocumentException, IOException {
        PdfPCell header = new PdfPCell();
        for(String stroke:str) {
            header.setPhrase(new Phrase(stroke, font));
            table.addCell(header);
        }
    }
    public void addTables(ObservableList<Product> products, ObservableList<Person> persons, ObservableList<Count> sales, ObservableList<Total> salaries) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("Report.pdf"));

        document.open();
        BaseFont helvetica=BaseFont.createFont(getClass().getResource("/font/arial.ttf").toString(),BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
        font=new Font(helvetica,10,Font.NORMAL);


        PdfPTable table_products = new PdfPTable(7);
        table_products.setSpacingAfter(10);
        addTableHeader(table_products,"Свойство","Цена","Штрихкод","Номер полки","Название","Количество","Номинальная цена");
        for(Product product:products) {
            addRows(table_products, product.getProperty(), product.getPrice().toString(), product.getCode().toString()
                    , product.getShell(), product.getProductName(), product.getNumber().toString(), product.getNominalPrice().toString());
        }

        document.add(table_products);

        PdfPTable table_workers=new PdfPTable(4);
        table_workers.setSpacingAfter(10);
        addTableHeader(table_workers,"Имя","Фамилия","Телефон","Должность");
        for(Person person:persons){
            addRows(table_workers,person.getFirstName(),person.getLastName(),person.getPhone(),person.getPost());
        }

        document.add(table_workers);

        PdfPTable table_sales=new PdfPTable(4);
        addTableHeader(table_sales,"Дата","Затраты","Прибыль","Проданные товары");
        for (Count sale: sales){
            addRows(table_sales,sale.getDate().get().toString(),Double.toString(sale.getExpences().get()),
                    Double.toString(sale.getProfit().get()),Integer.toString(sale.getAmount().get()));
        }
        table_sales.setSpacingAfter(10);
        document.add(table_sales);

        PdfPTable table_salaries=new PdfPTable(2);
        addTableHeader(table_salaries,"Дата","Общий оклад");
        for(Total salary:salaries){
            addRows(table_salaries,salary.getDate().toString(),salary.getDate().get().toString());
        }
        table_salaries.setSpacingAfter(10);
        document.add(table_salaries);
        document.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(getPrimaryStage());
        alert.setTitle("Добавлен отчет!");
        alert.setHeaderText("Отчет добавлен в папку");
        alert.showAndWait();
    }
}
