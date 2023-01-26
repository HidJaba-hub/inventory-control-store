package com.company.electro_store.util.managers;

import com.company.electro_store.temp.MenuController;
import javafx.scene.control.Alert;

import java.util.regex.Pattern;

public class Validator {

    //private static final Pattern STRING_PATTERN = Pattern.compile("^[(\\p{L})+(\\p{Blank})*(\\d)*]+$");//строка с пробелами
    private static final Pattern STRING_PATTERN = Pattern.compile("^[\\p{L}\\p{Blank}]+$");//строка с пробелами
    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+.\\d+");//числа
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^([a-z0-9_\\.-]+)@([a-z0-9_\\.-]+)\\.([a-z\\.]{2,6})$", Pattern.CASE_INSENSITIVE);//почта
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(375\\([0-9]{2}\\)[0-9]{3}-[0-9]{2}-[0-9]{2})$", Pattern.CASE_INSENSITIVE);//"375(29)343-48-02"
    public static boolean correctBigLetter(String str){
        boolean isCorrect = true;
        if (str.isBlank() || !STRING_PATTERN.matcher(str).matches()) {
            System.out.println("Пустая строка или вы ввели число");
            isCorrect = false;
        }
        return  isCorrect;
    }
    public static boolean isBlank(String str){
        boolean isCorrect = true;
        if (str.isBlank()) {
            System.out.println("Пустая строка");
            isCorrect = false;
        }
        return  isCorrect;
    }
    public static boolean correctNumber(String str){
        boolean isCorrect = true;
        if (str.isBlank() || !NUMBER_PATTERN.matcher(str).matches()) {
            System.out.println("Пустая строка или вы ввели не число");
            isCorrect = false;
        }
        return isCorrect;
    }
    public static boolean correctNumberInterval(String str, int a, int b){
        boolean isCorrect = true;
        if (str.isBlank() || !NUMBER_PATTERN.matcher(str).matches() || Integer.parseInt(str)<a||Integer.parseInt(str)>b) {
            System.out.println("Пустая строка или вы ввели не число");
            isCorrect = false;
        }
        return isCorrect;
    }
    public static boolean correctMail(String str){
        boolean isCorrect = true;
        if (str.isBlank() || !EMAIL_PATTERN.matcher(str).matches()) {
            System.out.println("Пустая строка или неподходящий email");
            isCorrect = false;
        }
        return isCorrect;
    }
    public static boolean correctPhone(String str){
        boolean isCorrect = true;
        if (str.isBlank() || !PHONE_PATTERN.matcher(str).matches()) {
            System.out.println("Пустая строка или неподходящий номер телефона");
            isCorrect = false;
        }
        return isCorrect;
    }
    public static boolean correctCompany(String name, String country) {
        boolean isCorrect = true;
        if (name.isBlank() || country.isBlank() || !STRING_PATTERN.matcher(country).matches()) {
            isCorrect = false;
        }
        return  isCorrect;
    }

    public static boolean correctPerson(String name, String surname, String age, String phone, String mail) {
        boolean isCorrect = true;
        if (name.isBlank() || surname.isBlank() || age.isBlank() || !NUMBER_PATTERN.matcher(age).matches() ||
                phone.isBlank() || mail.isBlank()) {
            isCorrect = false;
        }
        return isCorrect;
    }

    public static boolean correctUser(String login, String password) {
        boolean isCorrect = true;
        if (login.isBlank() || password.isBlank()) {
            isCorrect = false;
        }
        return isCorrect;
    }
    public static void fieldBlank(MenuController menu, Alert.AlertType alertType){
        // Ничего не выбрано.
        Alert alert = new Alert(alertType);
        alert.initOwner(menu.getPrimaryStage());
        alert.setTitle("Нет выбранной записи");
        alert.setHeaderText("Не выбрана запись");
        alert.setContentText("Выберите запись в таблице для редактирования");
        alert.showAndWait();
    }
    public static boolean correctRole(String role) {
        boolean isCorrect = true;
        if (!role.equals("User") || !role.equals("Admin")) {
            isCorrect = false;
        }
        return isCorrect;
    }

    public static boolean correctId(String id) {
        boolean isCorrect = true;
        if (id.isBlank() || !NUMBER_PATTERN.matcher(id).matches()) {
            isCorrect = false;
        }
        return isCorrect;
    }
}
