package jwoz.sprproj.net;

import jwoz.sprproj.data.DataAccessor;
import jwoz.sprproj.db.DBService;
import jwoz.sprproj.memory.MemoryService;
import jwoz.sprproj.record.NewPerson;
import jwoz.sprproj.record.Person;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ApiController {

    final DataAccessor dataAccessor;
    private final Environment environment;
    public ApiController(DBService dbService, MemoryService memoryService, Environment environment) {
        String dataService = environment.getProperty("dataaccessor");
        if(dataService.equals("DBService")){
            System.out.println("Using db service");
            this.dataAccessor = dbService;
        }
        else{
            System.out.println("Using memory service");
            this.dataAccessor = memoryService;
        }
        dbService.setTableName("persons");
        this.environment = environment;
    }

    @GetMapping("/api/user")
    public String getUser(Authentication authentication) {
        return "Hello, your name is " + authentication.getName() + " :)" + authentication.getAuthorities();
    }

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello, this should be a public endpoint!";
    }

    @GetMapping("/api/property")
    public String getProperty(@RequestParam String name) {
        String springPropertyValue = environment.getProperty(name);
        return "Requested property with name " + name + ": " + springPropertyValue;
    }

    @GetMapping("/api/show")
    public List<Person> showRecord(@RequestParam(required = false) String id, @RequestParam(required = false) String name, @RequestParam(required = false) String age){
        Map<String, String> parameters = new HashMap<>();
        if(id!=null){ parameters.put("id", id); }
        if(name!=null){ parameters.put("name", name); }
        if(age!=null){ parameters.put("age", age); }
        return dataAccessor.getRecords(parameters);
    }

    @PostMapping("/api/create")
    public String createNewRecord(@RequestBody NewPerson person){
        if(person.name==null || person.name.isBlank() || person.age<0){
            return "Neither name nor age can be blank, and age can't be lower than 0";
        }
        boolean success = dataAccessor.createNewRecord(person.name, person.age);
        return success ? "Create Done" : "Create Failed";
    }

    @PatchMapping("/api/change")
    public String changeRecord(@RequestParam String id, @RequestParam(required = false) String name, @RequestParam(required = false) String age){
        if(name==null && age==null){ return "Need at least one property to be changed";}
        if(age!=null && (age.isBlank() || Integer.parseInt(age)<0)){ return "Age can't be lower than 0 or blank";}
        if(name!=null && name.isBlank()){ return "Name can't be blank";}
        boolean success = dataAccessor.changeRecord(Integer.parseInt(id), name, age);
        return success ? "Change Done" : "Change Failed";
    }

    @DeleteMapping("/api/delete")
    public String deleteRecord(@RequestParam String id){
        boolean success = dataAccessor.deleteRecord(Integer.parseInt(id));
        return success ? "Delete Done" : "Delete Failed";
    }

    @PostMapping("/api/createtable")
    public String createTable(){
        boolean success = dataAccessor.createTable();
        return success ? "Create table Done" : "Create table Failed";
    }

    @DeleteMapping("/api/deletetable")
    public String deleteTable(){
        boolean success = dataAccessor.deleteTable();
        return success ? "Delete table Done" : "Delete table Failed";
    }

    @PostMapping("/api/settablename")
    public String createNewRecord(@RequestParam String name){
        if(name==null || name.isBlank()){
            return "name of the table can't be set to nothing";
        }
        boolean success = dataAccessor.setTableName(name);
        return success ? "Setname Done" : "Setname Failed";
    }

    //docker run -d --name mysql-container -e MYSQL_ROOT_PASSWORD=aaa -e MYSQL_DATABASE=your_database_name -e MYSQL_USER=user -e MYSQL_PASSWORD=bbb -p 3306:3306 mysql:latest
}