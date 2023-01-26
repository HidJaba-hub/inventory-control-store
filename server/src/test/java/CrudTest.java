import com.company.electro_store.entity.persons.Person;
import com.company.electro_store.entity.persons.Post;
import com.company.electro_store.entity.persons.User;
import com.company.electro_store.entity.persons.Work;
import com.company.electro_store.entity.store.Product;
import com.company.electro_store.entity.store.Property;
import com.company.electro_store.entity.store.Rack;
import com.company.electro_store.entity.store.Shell;
import com.company.electro_store.service.persons.PersonService;
import com.company.electro_store.service.persons.PostService;
import com.company.electro_store.service.persons.UserService;
import com.company.electro_store.service.persons.WorkService;
import com.company.electro_store.service.store.ProductService;
import com.company.electro_store.service.store.PropertyService;
import com.company.electro_store.service.store.RackService;
import com.company.electro_store.service.store.ShellService;
import com.company.electro_store.util.managers.HashPassword;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.fail;

public class CrudTest {
    UserService userService = new UserService();
    PropertyService propertyService=new PropertyService();
    ProductService productService=new ProductService();
    RackService rackService=new RackService();
    ShellService shellService=new ShellService();
    PersonService personService=new PersonService();
    PostService postService=new PostService();
    WorkService workService=new WorkService();
    String getRandomNumberString() {
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            randomString.append(random.nextInt(9));
        }
        return randomString.toString();
    }
    @Test
    void readUser() {
        System.out.println("------------ReadUserByLogin------------");
        try {
            String login = "testUser" + getRandomNumberString();
            User user = new User(login, HashPassword.getHash(login));
            if (!userService.saveOrUpdate(user)) {
                throw new Exception("Cannot create user " + login + " !");
            }
            System.out.println(user);

            user = userService.read(login);
            System.out.println(user);

            if (!userService.delete(user)) {
                throw new Exception("Cannot delete user " + login + " !");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            fail("Failed to make CRUD employee operations.");
        }
    }
    @Test
    public void addProperty(){
        String name,value;
        String result = null;
        name = getRandomNumberString();
        value = getRandomNumberString();

        Property property=new Property(name,value);
        if (propertyService.saveOrUpdate(property)) {
            System.out.println("---added!---");
        }
        else {
            fail("Failed to make CRUD employee operations.");
        }
        propertyService.delete(property);
    }
    @Test
    public void updateProperty(){
        String name;
        String result = null;
        showProperties();
        name= getRandomNumberString();
        Property property=getPropertyName(name);
        if (property!=null) {
            String value;
            value = getRandomNumberString();
            //TODO возможность изменять свойство
            /*System.out.print("Выберите измененное название свойства: ");
            name= in.nextLine();*/
            property.setValue(value);
            //property.setName(name);
            if (propertyService.saveOrUpdate(property)) {
                System.out.println("---updated!---");
            }
            else {
                fail("Failed to make CRUD employee operations.");
            }
            propertyService.delete(property);
        }
        else{
            System.out.println("not found");
        }
    }
    @Test
    public Property getPropertyName(String name) {
        Property property=null;
        for (Property c : getProperty()) {
            if (c.getName().equals(name)) {
                property=c;
            }
        }
        return property;
    }
    @Test
    public void deleteProperty(){
        String name;
        String result = null;
        showProperties();
        name= getRandomNumberString();
        Property property=getPropertyName(name);
        if (property!=null) {
            if (propertyService.delete(property)) {
                System.out.println("---Удаление выполнено!---");
            }
            else {
                fail("Failed to make CRUD employee operations.");
            }
        }
        else{
            System.out.println("not found");
        }
    }

    @Test
    public void showProperties() {
        List<Property> properties = getProperty();
        if (properties.size() != 0) {
            for (Property c: properties) {
                theHeaderForProperty();
                theTableForProperty(c);
            }
            System.out.println(" ");
        }
        else {
            System.out.println("nothing to show");
        }
    }
    @Test
    private List<Property> getProperty() {
        List<Property> properties = propertyService.readAll();
        return properties;
    }
    public void theTableForProperty(Property c) {
        System.out.println(" ");
        String str="";
        int i=0;
        System.out.format("%5s%30s\n", c.getName() + " |", c.getValue()+" |");
    }
    public void theHeaderForProperty() {
        System.out.format("%5s%30s"," name |", "value |");
    }
    //---Product---
    @Test
    public void addProduct(){
        String name,choice, product_name, cost, code;
        name=getRandomNumberString();
        Property property=propertyService.read("Звук");
        product_name=getRandomNumberString();
        cost=getRandomNumberString();
        code=getRandomNumberString();
         Product product=new Product(Integer.parseInt(code),Double.parseDouble(cost),property,name,Double.parseDouble(cost));
        if (productService.saveOrUpdate(product)) {
            System.out.println("---added!---");
        }
        else {
            fail("Failed to make CRUD employee operations.");
        }
    }
    @Test
    public void updateProduct(){
        String code, name, cost;
        String result = null;
        showProducts();
        code= getRandomNumberString();
        Product product=productService.read(Integer.parseInt(code));
        if (product!=null) {
            name=getRandomNumberString();
            cost=getRandomNumberString();
            product.setName(name);
            product.setCost(Double.parseDouble(cost));
            if (productService.saveOrUpdate(product)) {
                System.out.println("---updated!---");
            }
            else {
                fail("Failed to make CRUD employee operations.");
            }
        }
        else{
            System.out.println("nou found");
        }
    }
    @Test
    public Product getProductName(String name) {
        Product Product=null;
        for (Product c : getProduct()) {
            if (c.getName().equals(name)) {
                Product=c;
            }
        }
        return Product;
    }
    @Test
    public void deleteProduct(){
        String code;
        String result = null;
        code= getRandomNumberString();
        Product Product=productService.read(Integer.parseInt(code));
        if (Product!=null) {
            if (productService.delete(Product)) {
                System.out.println("---delted!---");
            }
            else {
                fail("Failed to make CRUD employee operations.");
            }
        }
        else{
            System.out.println("not found");
        }
    }
    @Test
    public void showProducts() {
        List<Product> products = getProduct();
        if (products.size() != 0) {
            for (Product c: products) {
                theHeaderForProduct();
                theTableForProduct(c);
            }
            System.out.println(" ");
        }
        else {
            System.out.println("not found!");
        }
    }
    public List<Product> getProduct() {
        List<Product> products = productService.readAll();
        return products;
    }
    public void theTableForProduct(Product c) {
        System.out.println(" ");
        String str="";
        int i=0;
        if(c.getShell()!=null&&c.getProperty()!=null) System.out.format("%5s%30s%30s%30s%30s\n", c.getCode() + " |", c.getName()+" |", c.getCost()+" |", c.getProperty().getName()+" |", c.getShell().getShell_id()+" |");
        else System.out.format("%5s%30s%30s\n", c.getCode() + " |", c.getName()+" |", c.getCost()+" |");
    }
    public void theHeaderForProduct() {
        System.out.format("%5s%30s%30s%30s%30s"," code |", "name |", "cost |", "property |", " shell_id |");
    }

    //Racks
    @Test
    public void addRack(){
        String name;
        name = getRandomNumberString();
        Rack rack=new Rack(name, Rack.Place.STORAGE);
        if (rackService.saveOrUpdate(rack)) {
            System.out.println("---added!---");
        }
        else {
            System.out.println( "Что-то пошло не так...");
        }
    }
    @Test
    public void updateRack(){
        String name;
        name= getRandomNumberString();
        Rack rack=getRackName(name);
        if (rack!=null) {
            String value;
            rack.getRackId().setPlace(Rack.Place.HALL);
            //Rack.setName(name);
            for(Shell shell:rack.getShells()){
                shell.setPlace(Rack.Place.HALL);
                setCoordinate(rack,shell.getIndex());
                shellService.saveOrUpdate(shell);
            }
            if (rackService.saveOrUpdate(rack)) {
                System.out.println("---updated!---");
            }
            else {
                System.out.println("Что-то пошло не так...");
            }
        }
        else{
            System.out.println("not found");
        }
    }
    @Test
    public Rack getRackName(String name) {
        Rack Rack=null;
        for (Rack c : getRack()) {
            if (c.getRackId().getName().equals(name)) {
                Rack=c;
            }
        }
        return Rack;
    }

    public void showShells(Rack rack){
        List<Shell> shells = rack.getShells();
        theHeaderForShell();
        for (Shell shell : shells) {
            theTableForShell(shell);
        }
    }

    public void showRacks(Rack.Place place){
        List<Rack> racks = getRack();
        if (racks.size() != 0) {
            theHeaderForRack();
            for (Rack c: racks) {
                if(c.getRackId().getPlace().equals(place)) {
                    theTableForRack(c);
                }
            }
            System.out.println(" ");
        }
        else {
            System.out.println("not found!");
        }

    }
    @Test
    public int setShellId(Rack rack, Rack.Place place){
        List<Shell> shells=rack.getShells();
        List<Shell> shells_return=new ArrayList<>();
        int i=1;
        for(Shell shell: shells){
            if(shell.getIndex()!=i){return i;}
            i++;
        }
        return i;
    }
    public void addShell(Shell shell, Rack rack){
        rack.addShell(shell);
        rackService.saveOrUpdate(rack);
    }
    @Test
    public void deleteRack(){
        String name;
        String result = null;
        name= getRandomNumberString();
        Rack Rack=getRackName(name);
        if (Rack!=null) {
            if (rackService.delete(Rack)) {
                System.out.println("---deleted!---");
            }
            else {
                System.out.println("Что-то пошло не так...");
            }
        }
        else{
            System.out.println("not found");
        }
    }
    @Test
    public List<Rack> getRack() {
        List<Rack> racks = rackService.readAll();
        return racks;
    }
    public void theTableForRack(Rack c) {
        System.out.println(" ");
        String str="";
        int i=0;
        System.out.format("%5s%30s\n", c.getRackId().getName() + " |", c.getRackId().getPlace()+" |");
        showShells(c);
    }
    public void theHeaderForRack() {
        System.out.format("%5s%30s"," name |", "place |", "shells |");
    }
@Test
    public void addShell(){
        String name,choice, capacity;
        name = getRandomNumberString();
        Rack rack=rackService.read(name, Rack.Place.HALL);
        if(rack!=null) {
            capacity = getRandomNumberString();
            Shell shell = new Shell(
                    setShellId(rack, rack.getRackId().getPlace()),
                    rack.getRackId().getPlace(),
                    rack,
                    Double.parseDouble(capacity),
                    setCoordinate(rack, setShellId(rack, rack.getRackId().getPlace())));
            addShell(shell, rack);
            if (shellService.saveOrUpdate(shell)) {
                System.out.println("---added!---");
            } else {
                System.out.println("Что-то пошло не так...");
            }
        }
    }
    @Test
    public String setCoordinate(Rack rack, Integer index){
        String result="";
        result+=rack.getRackId().getName();
        switch (rack.getRackId().getPlace()){
            case HALL -> result+="H";
            case STORAGE -> result+="S";
        }
        result+=index.toString();
        return result;
    }
    @Test
    public void updateShell(){
        String id, capacity;
        id= getRandomNumberString();
        Shell shell=shellService.read(id);
        if (shell!=null) {
            capacity=getRandomNumberString();
            shell.setCapacity(Double.parseDouble(capacity));
            if (shellService.saveOrUpdate(shell)) {
                System.out.println("---updated!---");
            }
            else {
                System.out.println("Что-то пошло не так...");
            }
        }
        else{
            System.out.println("Не найдено");
        }
    }
    @Test
    private Shell getShellName(String name) {
        Shell Shell=null;
        for (Shell c : getShell()) {
            if (c.getShell_id().equals(name)) {
                Shell=c;
            }
        }
        return Shell;
    }
    @Test
    public void deleteShell(){
        String id;
        id= getRandomNumberString();
        Shell shell=shellService.read(id);
        if (shell!=null) {
            if (shellService.delete(shell)) {
                System.out.println("---deleted!---");
            }
            else {
                System.out.println("Что-то пошло не так...");
            }
        }
        else{
            System.out.println("not found");
        }
    }
    public void addProductToShell(Product product, String name){
        Shell shell=shellService.read(name);
        shell.addProduct(product);
        shellService.saveOrUpdate(shell);
    }
    @Test
    public void showShells() {
        List<Shell> shells = getShell();
        if (shells.size() != 0) {
            theHeaderForShell();
            for (Shell c: shells) {

                theTableForShell(c);
            }
            System.out.println(" ");
        }
        else {
            System.out.println("Нет стеллажей!");
        }
    }
    private List<Shell> getShell() {
        List<Shell> shells = shellService.readAll();
        return shells;
    }
    public void theTableForShell(Shell c) {
        System.out.println(" ");
        String str="";
        int i=0;
        System.out.format("%5s%30s%30s%30s\n", c.getShell_id()+ " |",c.getRack().getRackId().getName()+ " |", c.getPlace()+" |", c.getCapacity()+ " |");
    }
    public void theHeaderForShell() {
        System.out.format("%5s%30s%30s%30s"," shell id |"," rack |", "place |", "capacity |");
    }
    public void addPerson() {
        Person person = getPersonInfo();
        if (person != null) {
            if (personService.saveOrUpdate(person)) {
                System.out.println("---Добавление выполнено!---");
            }
        }
    }

    @Test
    private Person getPersonInfo() {
        String name,surname,phone,mail;
        Person person = null;
        name = getRandomNumberString();
        surname = getRandomNumberString();
        phone = getRandomNumberString();
        User user = getUserInfo();
        if (user != null) {
            person = new Person(name, surname, phone);
            user.setPerson(person);
            person.setUser(user);
        }
        else {

        }
        return person;
    }
@Test
    private User getUserInfo() {
        String login,password;
        User user = null;
        login= getRandomNumberString();
        password= getRandomNumberString();
        if(checkUniqueLogin(login)) {
            user = new User(login, HashPassword.getHash(password));
        }
        else {
        }
        return user;
    }
    @Test
    public void addPost(){
        String id, choice;
        id= getRandomNumberString();
        Person person=personService.read(Integer.parseInt(id));
        if(person!=null) {
            showProperties();
            choice = getRandomNumberString();
            Post post = postService.read(choice);
            person.getUser().setPost(post);
            post.setUser(person.getUser());
            postService.saveOrUpdate(post);
            personService.saveOrUpdate(person);
        }
    }
    private boolean checkUniqueLogin(String login) {
        boolean isUnique = true;
        for (Person p : getPeople()) {
            if (p.getUser().getLogin().equals(login)) {
                isUnique = false;
            }
        }
        return isUnique;
    }
@Test
    public void updatePerson() {
        String id;
        id= getRandomNumberString();
        if (getPersonId(id)) {
            Person person = findPersonById(Integer.parseInt(id));
            if (person != null) {
                changeDataFromPerson(person);
                changeDataFromUser(person);
                if (personService.saveOrUpdate(person)) {
                    System.out.println("---updated!---");
                }
            }
        }
    }

    private Person changeDataFromPerson(Person person) {
        String name,surname,phone,mail;
        name = getRandomNumberString();
        surname = getRandomNumberString();
        phone = getRandomNumberString();
        person.setName(name);
        person.setSurname(surname);
        person.setPhone(phone);
        return person;
    }
    private Person changeDataFromUser(Person person) {

        String login,password;
        login= getRandomNumberString();
        password= getRandomNumberString();
        person.getUser().setLogin(login);
        person.getUser().setPassword(HashPassword.getHash(password));

        return person;
    }

    public void updateLoginAndPassword(int id) {
        Person person = findPersonById(id);
        changeDataFromUser(person);
        if (personService.saveOrUpdate(person)) {
            System.out.println("---updated!---");
        }

    }
    public void deletePerson(Person person) {
        String id;
        showPeople();
        System.out.print("Выберите ID для удаления: ");
        id=getRandomNumberString();
        if(person.getPersonId()== Integer.parseInt(id)) {System.out.println("Нельзя удалить самого себя");return;}
        Person p=personService.read(Integer.parseInt(id));
        if (p!=null) {
            if (personService.delete(p)) {
                System.out.println("---deleted!---");
            }
        }
    }
@Test
    private boolean getPersonId(String id) {
        boolean isAppropriateNumber = false;
        int i=(getPeople().get(getPeople().size()-1)).getPersonId();
        if (!(Integer.parseInt(id) < 0) && !(Integer.parseInt(id) > i)) {
            isAppropriateNumber = true;
        }
        else {
            System.out.println("Такого ID нет!");
        }
        return isAppropriateNumber;
    }
@Test
    public void showPeople() {
        List<Person> people = getPeople();
        if (people.size() != 0) {
            System.out.format("%10s%20s%20s%20s%20s", "ID |", "Имя |", "Фамилия |", "Телефон |", "Логин |");
            for (Person p: people) {
                System.out.println(" ");
                System.out.format("%10s%20s%20s%20s", p.getPersonId() + " |", p.getName() + " |",
                        p.getSurname() + " |", p.getPhone() + " |");
            }
            System.out.println(" ");
        }
        else {
            System.out.println("Нет пользователей!");
        }
    }

    private List<Person> getPeople() {
        List<Person> people = personService.readAll();
        return people;
    }

    private Person findPersonById(int id) {
        Person person = (Person) personService.read(id);
        return person;
    }

    //Post
    @Test
    public void addPosttoBD(){
        String name,cost;
        name = getRandomNumberString();
        cost =getRandomNumberString();
        Post property=new Post(name,Double.parseDouble(cost));
        if (postService.saveOrUpdate(property)) {
            System.out.println("---added---");
        }
        else {
        }
    }
    @Test
    public void addAdmin(){
        if(postService.read("Администратор")==null) {
            String name = "Администратор";
            Post property = new Post(name, 1.0);
            //propertyService.saveOrUpdate(property);
            postService.saveOrUpdate(property);
        }
    }
    @Test
    public void deleteFunction(){
        String id;
        id= getRandomNumberString();
        Post property=postService.read(Integer.parseInt(id));
        if (property!=null) {
            List<Work> works=showFunctions(property);
            id= getRandomNumberString();
            workService.delete(works.get(Integer.parseInt(id)));
        }
    }
    @Test
    public void updatePost(){
        String id,name,cost;
        showProperties();
        id= getRandomNumberString();
        Post property=postService.read(Integer.parseInt(id));
        if (property!=null) {
            name = getRandomNumberString();
            cost = getRandomNumberString();
            property.setCost(Double.parseDouble(cost));
            property.setName(name);
            if (postService.saveOrUpdate(property)) {
                System.out.println("---updated!---");
            }
            else {
                System.out.println("Что-то пошло не так...");
            }
        }
        else{
            System.out.println("not found");
        }
    }
    public Post getPostName(String name) {
        Post property=null;
        for (Post c : getPost()) {
            if (c.getName().equals(name)) {
                property=c;
            }
        }
        return property;
    }
    @Test
    public void deletePost(){
        String id;
        showProperties();
        id= getRandomNumberString();
        Post property=postService.read(Integer.parseInt(id));
        if (property!=null) {
            if (postService.delete(property)) {
                System.out.println("---deleted!---");
            }
            else {
                System.out.println("Что-то пошло не так...");
            }
        }
        else{
            System.out.println("Не нгайдено");
        }
    }
    public List<Work> showFunctions(Post post){
        List<Work> works=post.getActions();
        int i=1;
        for(Work w: works){
            System.out.println(i+" "+w.getAction().getStr());
            i++;
        }
        return works;
    }
    private List<Post> getPost() {
        List<Post> properties = postService.readAll();
        return properties;
    }
    public void theTableForPost(Post c) {
        System.out.println(" ");
        String str="";
        int i=0;
        System.out.format("%5s%30s%30s\n", c.getPost_id() + " |", c.getName() + " |", c.getCost()+" |");
        showFunctions(c);
    }
    public void theHeaderForPost() {
        System.out.format("%5s%30s%30s"," id |"," name |", " cost |");
    }
}
