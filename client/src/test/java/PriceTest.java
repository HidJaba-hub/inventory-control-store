import java.io.IOException;
import java.lang.ref.Cleaner;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.company.electro_store.dto.accountant.SalariesDto;
import com.company.electro_store.dto.accountant.SalesDto;
import com.company.electro_store.dto.persons.UserDto;
import com.company.electro_store.functions.GeneralFunctions;
import com.company.electro_store.functions.accountant.SalariesFunctions;
import com.company.electro_store.functions.accountant.SalesFunctions;
import com.company.electro_store.temp.MenuController;
import com.company.electro_store.temp.authorization.CheckButton;
import com.company.electro_store.temp.user.UserController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class PriceTest {
    @Test
    protected void CountMoney() {
        GeneralFunctions generalFunctions = new GeneralFunctions();
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        try {
        SalesDto salesDto= SalesFunctions.readByDate(sqlDate);
        String dayLabel="0",mounthLabel="0",yearLabel="0";
        if(salesDto!=null) {
            double c=salesDto.getProfit()-salesDto.getExpences();
            dayLabel=Double.toString(c);
        }
        LocalDate localDate = LocalDate.now();
        List<SalesDto> salesDtoList=SalesFunctions.readAllUsers();
        for(SalesDto s:salesDtoList){
            Calendar cal = Calendar.getInstance();
            cal.setTime(s.getDate());
            int month = cal.get(Calendar.MONTH)+1;
            int year=cal.get(Calendar.YEAR);
            if(month==localDate.getMonthValue()){
                double c=Double.parseDouble(mounthLabel);
                double cm=s.getProfit()-s.getExpences()+c;
                mounthLabel=Double.toString(cm);
            }
            if(year==localDate.getYear()){
                double c=Double.parseDouble(yearLabel);
                double cy=s.getProfit()-s.getExpences()+c;
                yearLabel=Double.toString(cy);
            }
        }
        System.out.println("Product profit: "+dayLabel+" "+mounthLabel+" "+yearLabel+" ");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            fail("Not found.");
        }
        catch (IOException e){
            e.printStackTrace();
            fail("Mistake");
        }
        catch (Exception exception) {
            exception.printStackTrace();
            fail("Login failed.");
        }
    }
    @Test
    protected void CountMoneyForPersons() {
        GeneralFunctions generalFunctions = new GeneralFunctions();
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        try {
            SalariesDto salariesDto = SalariesFunctions.readByDate(sqlDate);
            String dayLabel = "0", mounthLabel = "0", yearLabel = "0";
            if (salariesDto != null) {
                dayLabel = Double.toString(salariesDto.getSalary());
            }
            LocalDate localDate = LocalDate.now();
            List<SalariesDto> salariesDtos = SalariesFunctions.readAllUsers();
            for (SalariesDto s : salariesDtos) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(s.getDate());
                int month = cal.get(Calendar.MONTH) + 1;
                int year = cal.get(Calendar.YEAR);
                if (month == localDate.getMonthValue()) {
                    double c = Double.parseDouble(mounthLabel);
                    double cm = s.getSalary() + c;
                    mounthLabel = Double.toString(cm);
                }
                if (year == localDate.getYear()) {
                    double c = Double.parseDouble(yearLabel);
                    double cy = s.getSalary() + c;
                    yearLabel = Double.toString(cy);
                }
            }
            System.out.println("Salaries: "+dayLabel + " " + mounthLabel + " " + yearLabel + " ");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            fail("Not found.");
        }
        catch (IOException e){
            e.printStackTrace();
            fail("Mistake");
        }
        catch (Exception exception) {
            exception.printStackTrace();
            fail("Login failed.");
        }
    }
    @Test
    protected void onLoginButtonClick() {
        try {
            GeneralFunctions generalFunctions = new GeneralFunctions();
            UserDto userDto = generalFunctions.login("111", "111");
            if (userDto != null) {
                System.out.println(userDto.getUserId());
                System.out.println(userDto.getPost());
                System.out.println(userDto.getPerson());
                System.out.println(userDto.getMinutes());
                System.out.println(userDto.getLogin());
                System.out.println(userDto.getPassword());
            } else {
                System.out.println("User not found");
            }
        }catch (Exception exception) {
            exception.printStackTrace();
            fail("Login failed.");
        }
    }
}
