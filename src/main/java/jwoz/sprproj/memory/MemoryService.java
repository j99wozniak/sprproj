package jwoz.sprproj.memory;

import jwoz.sprproj.data.DataAccessor;
import jwoz.sprproj.record.Person;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MemoryService implements DataAccessor {
    private HashMap<String, List<Person>> data = new HashMap<>();
    private String tableName;
    public List<Person> getRecords(Map<String, String> parameters) {
        try{
            List<Person> outList = new ArrayList<>(data.get(tableName));
            if(parameters.containsKey("id")){
                outList.removeIf(p -> p.getId() != Long.parseLong(parameters.get("id")));
            }
            if(parameters.containsKey("name")){
                outList.removeIf(p -> !p.getName().equals(parameters.get("name")));
            }
            if(parameters.containsKey("age")){
                outList.removeIf(p -> p.getAge() != Integer.parseInt(parameters.get("age")));
            }
            return outList;
        }
        catch(Exception e){
            System.out.println("Failed to retrieve records: " + e.getMessage());
            return null;
        }
    }

    public boolean createNewRecord(String name, int age) {
        try{
            ListIterator<Person> it = data.get(tableName).listIterator();
            long id = 0;
            while(it.hasNext()){
                if(it.next().getId()>id+1){
                    System.out.println("breaking out of loop with id " + id);
                    break;
                }
                id+=1;
            }
            data.get(tableName).add((int)id,new Person(id+1, name, age));
            return true;
        }
        catch(Exception e){
            System.out.println("Record not inserted: " + e.getMessage());
            return false;
        }
    }

    public boolean changeRecord(int id, String name, String age) {
        try {
            for(Person p : data.get(tableName)){
                if(p.getId() == id){
                    if(name!=null){
                        p.setName(name);
                    }
                    if(age!=null){
                        p.setAge(Integer.parseInt(age));
                    }
                }
            }
            return true;
        }
        catch(Exception e){
            System.out.println("Record not changed: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteRecord(int id) {
        try {
            data.get(tableName).removeIf(p -> p.getId() == id);
            return true;
        }
        catch(Exception e){
            System.out.println("Record not deleted: " + e.getMessage());
            return false;
        }
    }

    public boolean createTable() {
        data.put(tableName, new ArrayList<Person>());
        return true;
    }

    public boolean deleteTable() {
        data.remove(tableName);
        return true;
    }

    public boolean setTableName(String newTableName) {
        tableName = newTableName;
        return true;
    }
}
