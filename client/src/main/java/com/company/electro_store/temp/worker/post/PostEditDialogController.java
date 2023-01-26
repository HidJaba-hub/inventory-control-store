package com.company.electro_store.temp.worker.post;

import com.company.electro_store.temp.MenuController;
import com.company.electro_store.temp.worker.person.Person;
import com.company.electro_store.temp.worker.post.Post;
import com.company.electro_store.util.managers.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PostEditDialogController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField costField;
    private Stage dialogStage;
    private Post post;
    private boolean okClicked = false;
    private MenuController menu;
    /**
     * Инициализирует класс-контроллер. Этот метод вызывается
     автоматически после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
    }
    /**
     * Устанавливает сцену для этого окна.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage, MenuController menu) {
        this.menu=menu;
        this.dialogStage = dialogStage;
    }
    /**
     * Задаёт данные об адресате, информацию о котором будем
     менять.
     *
     */
    public void setPost(Post post) {
        this.post=post;
        nameField.setText(post.getPostName());
        costField.setText(post.getCost().toString());
    }
    /**
     * Returns true, если пользователь кликнул OK, в другом случае
     false.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }
    /**
     * Вызывается, когда пользователь кликнул по кнопке OK.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            post.setPostName(nameField.getText());
            post.setCost(costField.getText());
            okClicked = true;
            dialogStage.close();
        }
    }
    /**
     * Вызывается, когда пользователь кликнул по кнопке Cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    /**
     * Метод проверяет пользовательский ввод в текстовых полях.
     *
     * @return true, если пользовательский ввод корректен
     */
    private boolean isInputValid() {
        String errorMessage = "";
        if (nameField.getText() == null ||
                nameField.getText().length() == 0 || !Validator.correctBigLetter(nameField.getText())) {
            errorMessage += "Некорректно введена должность!\n";
        }
        for(Post p:menu.getPostData()){
            if(p.getPostName().equals(nameField.getText()))
                errorMessage += "Такая должность уже есть!\n";
        }
        if (costField.getText() == null ||
                costField.getText().length() == 0 || !Validator.correctNumber(costField.getText())||
                Double.parseDouble(costField.getText())<0) {
            errorMessage += "Некорректно введен оклад, он должен быть больше 0!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Неверно заполнены поля");
            alert.setHeaderText("Введите корректные значения полей");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }}
