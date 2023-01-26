package com.company.electro_store.temp.worker.person;

import com.company.electro_store.dto.persons.PostDto;
import com.company.electro_store.dto.persons.UserDto;
import com.company.electro_store.dto.persons.WorkDto;
import com.company.electro_store.functions.Action;
import com.company.electro_store.functions.persons.PersonFunctions;
import com.company.electro_store.functions.persons.PostFunctions;
import com.company.electro_store.temp.MenuController;
import com.company.electro_store.temp.authorization.CheckButton;
import com.company.electro_store.temp.worker.post.Post;
import com.company.electro_store.util.managers.HashPassword;
import com.company.electro_store.util.managers.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//TODO окна предпреждения
public class PersonController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label postLabel;
    // Ссылка на главное приложение
    @FXML
    private TableView<Post> postTable;
    @FXML
    private TableColumn<Post, String> postNameColumn;
    @FXML
    private TableColumn<Post, String> costColumn;
    @FXML
    private GridPane postGrid;
    @FXML
    private ComboBox<String> chooseList;
    @FXML
    private Button buttonOk;
    @FXML
    private ScrollPane scrollFunctions;
    @FXML private Button addWorkerButton;
    @FXML private Button addPostButton;
    @FXML private Button editWorkerButton;
    @FXML private Button editPostButton;
    @FXML private Button deletePostButton;
    @FXML private Button deleteWorkerButton;
    @FXML private Button addFunctionButton;
    @FXML private Button deleteFunctionButton;
    @FXML private Button addPostToWorkerButton;
    private MenuController menu;
    boolean okClicked;
    String active;
    public void checkButton(){
        CheckButton.check(Action.ADDWORKER,addWorkerButton);
        CheckButton.check(Action.EDITWORKER,editWorkerButton);
        CheckButton.check(Action.DELETEWORKER,deleteWorkerButton);
        CheckButton.check(Action.ADDPOSTTOWORKER,addPostToWorkerButton);
        CheckButton.check(null,addPostButton);
        CheckButton.check(null,deletePostButton);
        CheckButton.check(null,editPostButton);
        CheckButton.check(null,addFunctionButton);
        CheckButton.check(null,deleteFunctionButton);
    }
    private void showPersonDetails(Person person) {
        if (person != null) {
            firstNameLabel.setText(person.getFirstName().toString());
            lastNameLabel.setText(person.getLastName().toString());
            phoneLabel.setText(person.getPhone().toString());
            postLabel.setText(person.getPost().toString());

        } else {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            postLabel.setText("");
            phoneLabel.setText("");
        }
    }
    private void showPostDetails(Post post) throws IOException {
        VBox vBox=new VBox();
        scrollFunctions.setStyle("-fx-background-color: rgba(206,206,206,0.35);");
        if(post!=null){
            if(post.getPostName().equals("Администратор")){
                Action []actions=Action.values();
                for(int i=0;i<actions.length;i++) {
                    Label label = new Label();
                    label.setStyle("-fx-text-fill: #fff; -fx-font-family: 'Century Gothic';-fx-font-size: 15px;-fx-padding: 5px;");
                    label.setText(actions[i].getStr());

                    vBox.getChildren().add(label);
                }
            }
            for(String works:post.getFunctions()){

                Label label=new Label();
                label.setStyle("-fx-text-fill: #fff; -fx-font-family: 'Century Gothic';-fx-font-size: 15px;-fx-padding: 5px;");
                label.setText(works);

                vBox.getChildren().add(label);
            }
            scrollFunctions.setContent(vBox);
        }
        else {

        }
    }

    public PersonController() {
    }
    @FXML
    private void initialize() {
        checkButton();
        //метод setCellValueFactory определяет, как необходимо заполнить данные каждой ячейки
        // Инициализация столбца с данными об именах
        this.firstNameColumn.setCellValueFactory(cellData ->
                cellData.getValue().firstNameProperty());
        // Инициализация столбца с данными об фамилиях
        this.lastNameColumn.setCellValueFactory(cellData ->
                cellData.getValue().lastNameProperty());
        personTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));

        this.postNameColumn.setCellValueFactory(cellData->
                cellData.getValue().postNameProperty());
        this.costColumn.setCellValueFactory(cellData->
                cellData.getValue().costProperty().asString());
        postTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                showPostDetails(newValue);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    /**
     * Вызывается главным приложением, которое даёт на себя ссылку
     *
     * @param
     */
    @FXML
    private void handleDeletePerson() throws IOException, ClassNotFoundException {
       // int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson!=null) {
            UserDto userDto=PersonFunctions.readUserById(selectedPerson.getUserId());
            PersonFunctions.deletePerson(userDto.getPerson());
            menu.setPersonData();
        }
        else{
            Validator.fieldBlank(menu,Alert.AlertType.INFORMATION);
        }
    }
    @FXML
    private void handleNewPerson() throws IOException, ClassNotFoundException {
       Person person = new Person();
       boolean okClicked = menu.showPersonEditDialog(person);
        if (okClicked) {
            PersonFunctions.addPerson(person.getFirstName(),person.getLastName(),person.getPhone(),person.getLogin(),person.getPassword());
            menu.setPersonData();
        }
    }
    @FXML
    private void handleNewPost() throws IOException, ClassNotFoundException {
        Post post = new Post();
        boolean okClicked = menu.showPostEditDialog(post);
        if (okClicked) {
            PostFunctions.addPost(post.getPostName(),post.getCost().toString());
            menu.setPostData();
        }
    }
    @FXML
    private void handleDeletePost() throws IOException, ClassNotFoundException {
        Post selectedPost = postTable.getSelectionModel().getSelectedItem();
        if (selectedPost!=null&& !selectedPost.getPostName().equals("Администратор")) {
            PostDto postDto=PostFunctions.readById(selectedPost.getPost_id());
            PostFunctions.deletePost(postDto);
            menu.setPostData();
            menu.setPersonData();
        }
        else{
            Validator.fieldBlank(menu,Alert.AlertType.INFORMATION);
        }
    }
    @FXML
    private void handleEditPost() throws IOException, ClassNotFoundException {
        Post selectedPost = postTable.getSelectionModel().getSelectedItem();
        if (selectedPost !=null && !selectedPost.getPostName().equals("Администратор")) {
            boolean okClicked = menu.showPostEditDialog(selectedPost);
            if (okClicked) {
                PostDto postDto=PostFunctions.readById(selectedPost.getPost_id());
                postDto.setName(selectedPost.getPostName());
                postDto.setCost(selectedPost.getCost());
                PostFunctions.updatePost(postDto);
                menu.setPostData();
                menu.setPersonData();
            }
        } else {
            Validator.fieldBlank(menu,Alert.AlertType.WARNING);
        }
    }
    @FXML
    public void handleOk() throws IOException, ClassNotFoundException {
        Post selectedPost = postTable.getSelectionModel().getSelectedItem();
        PostDto postDto=PostFunctions.readById(selectedPost.getPost_id());
        String selectedValue=chooseList.getSelectionModel().getSelectedItem();
            Action[] actions = Action.values();
            int chk = 0;
            for (Action action : actions) {
                if (action.getStr().equals(selectedValue)) {
                    List<WorkDto> workDtos = postDto.getActions();
                    for (WorkDto w : workDtos) {
                        if (w.getAction().equals(action)) {
                            if(active.equals("delete")){
                                PostFunctions.deleteWork(w);
                            }
                            chk = 1;
                        }
                    }
                    if (chk != 1) {
                        WorkDto work = new WorkDto(action, postDto);
                        PostFunctions.addWork(work);
                    }
                }
            }
        menu.setPostData();
            for(Post post:menu.getPostData()){
                showPostDetails(post);
            }
        chooseList.setVisible(false);
        buttonOk.setVisible(false);
    }
    @FXML
    private void handleNewFunction() throws IOException, ClassNotFoundException{
        chooseList.setVisible(true);
        buttonOk.setVisible(true);
        buttonOk.setDisable(false);
        Post selectedPost = postTable.getSelectionModel().getSelectedItem();
       if(selectedPost==null) {
            // Ничего не выбрано.
            Validator.fieldBlank(menu,Alert.AlertType.WARNING);
            chooseList.setVisible(false);
            buttonOk.setVisible(false);
            initialize();
            return;
        }
        chooseList.getItems().clear();
        Action[] actions=Action.values();
        boolean add=true;
        if(selectedPost.getPostName().equals("Администратор")){
            return;
        }
        for(int i=0;i<actions.length;i++){
            for(String str:selectedPost.getFunctions()){
                if(actions[i].getStr().equals(str)){
                    add=false;
                    break;
                }
            }
            if(add)chooseList.getItems().add(actions[i].getStr());
            add=true;
        }
        active="add";
    }
    @FXML
    private void handleAddPost()throws IOException, ClassNotFoundException {
        Post selectedPost = postTable.getSelectionModel().getSelectedItem();
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if(selectedPost==null||selectedPerson==null) {
            // Ничего не выбрано.
            Validator.fieldBlank(menu,Alert.AlertType.WARNING);
            chooseList.setVisible(false);
            buttonOk.setVisible(false);
            initialize();
            return;
        }
        PostDto postDto=PostFunctions.readById(selectedPost.getPost_id());
        UserDto personDto=PersonFunctions.readUserById(selectedPerson.getUserId());
        personDto.setPost(postDto);
        PersonFunctions.updatePerson(personDto);
        menu.setPersonData();
    }
    @FXML
    private void handleDeleteFunction()throws IOException, ClassNotFoundException{
        chooseList.setVisible(true);
        buttonOk.setVisible(true);
        chooseList.getItems().clear();
        Post selectedPost = postTable.getSelectionModel().getSelectedItem();
        if(selectedPost==null) {
            // Ничего не выбрано.
            Validator.fieldBlank(menu,Alert.AlertType.WARNING);
            chooseList.setVisible(false);
            buttonOk.setVisible(false);
            initialize();
            return;
        }
        for(String str:selectedPost.getFunctions()){
            chooseList.getItems().add(str);
        }
        active="delete";
    }
    /**
     * Вызывается, когда пользователь кликает по кнопка
     "Редактировать"
     * Открывает диалоговое окно для изменения выбранного адресата.
     */
    @FXML
    private void handleEditPerson() throws IOException, ClassNotFoundException {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = menu.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                //showPersonDetails(selectedPerson);
                UserDto user=PersonFunctions.readUserById(selectedPerson.getUserId());
                user.getPerson().setName(selectedPerson.getFirstName());
                user.getPerson().setSurname(selectedPerson.getLastName());
                user.getPerson().setPhone(selectedPerson.getPhone());
                user.setPassword(HashPassword.getHash(selectedPerson.getPassword()));
                user.setLogin(selectedPerson.getLogin());
                PersonFunctions.updatePerson(user);
                menu.setPersonData();
            }
        } else {
            // Ничего не выбрано.
            Validator.fieldBlank(menu,Alert.AlertType.WARNING);
        }
    }
    public void setMainApp(MenuController menu) throws IOException, ClassNotFoundException {
        this.menu = menu;
        personTable.setItems(menu.getPersonData());
        postTable.setItems(menu.getPostData());
    }
}