package jwoz.sprproj.db;

import jwoz.sprproj.data.DataAccessor;
import jwoz.sprproj.record.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DBService implements DataAccessor {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String tableName;

    public List<Person> getRecords(Map<String, String> parameters) {
        try {
            String sql = "SELECT * FROM "+tableName;
            if(!parameters.isEmpty()){
                sql += " WHERE ";
                boolean first = true;
                for(Map.Entry<String, String> entry : parameters.entrySet()) {
                    if(!Validator.ValidateIfSingleWord(entry.getKey()) ||
                            !Validator.ValidateIfSingleWord(entry.getValue())){
                        System.out.println("getRecords failed validation: "+entry.getKey()+"/"+entry.getValue());
                        return null;
                    }
                    if(!first){ sql += " AND ";}
                    // name is a text field so we need ''
                    if(entry.getKey() == "name"){ sql += entry.getKey()+"='"+entry.getValue()+"'";}
                    else{ sql += entry.getKey()+"="+entry.getValue();}
                    first = false;
                }
            }
            System.out.println("Prepared SQL: " + sql);
            return jdbcTemplate.query(sql, (resultSet, rowNum) ->
                    new Person(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("age")
                    )
            );
        }
        catch(Exception e){
            System.out.println("Failed to retrieve records: " + e.getMessage());
            return null;
        }
    }

    public boolean createNewRecord(String name, int age) {
        try {
            String sql = "INSERT INTO "+tableName+" (name, age) VALUES (?, ?)";
            jdbcTemplate.update(sql, name, age);
            System.out.println("Record inserted successfully!");
            return true;
        }
        catch(Exception e){
            System.out.println("Record not inserted: " + e.getMessage());
            return false;
        }
    }

    private String getSqlForChange(String name, String age){
        String sql = "UPDATE "+tableName+" SET ";
        if(!Validator.ValidateIfSingleWord(name) ||
                !Validator.ValidateIfSingleWord(age)){
            System.out.println("getSqlForChange failed validation: "+name+"/"+age);
            return null;
        }
        if(name!=null && age!=null){sql += "name='"+name+"', age="+age;}
        else if(name!=null){sql += "name='"+name+"'";}
        else if(age!=null){sql += "age="+age;}
        sql += " WHERE id = ?";
        return sql;
    }

    public boolean changeRecord(int id, String name, String age) {
        try {
            String sql = getSqlForChange(name, age);
            System.out.println("Prepared SQL:" + sql + "| for id: " + id);
            jdbcTemplate.update(sql, id);
            return true;
        }
        catch(Exception e){
            System.out.println("Record not changed: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteRecord(int id){
        try {
            String sql = "DELETE FROM "+tableName+" WHERE id = ?";
            return jdbcTemplate.update(sql, id) == 1;
        }
        catch(Exception e){
            System.out.println("Record not deleted: " + e.getMessage());
            return false;
        }
    }

    public boolean createTable(){
        try {
            String sql = "CREATE TABLE IF NOT EXISTS "+tableName+" (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), age INT)";
            jdbcTemplate.update(sql);
            return true;
        }
        catch(Exception e){
            System.out.println("Table not created: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteTable() {
        try {
            String sql = "DROP TABLE IF EXISTS " + tableName;
            jdbcTemplate.update(sql);
            return true;
        }
        catch(Exception e){
            System.out.println("Table not deleted: " + e.getMessage());
            return false;
        }
    }

    public boolean setTableName(String newTableName){
        if(!Validator.ValidateIfSingleWord(newTableName)){
            System.out.println("setTableName failed validation: "+newTableName);
            return false;
        }
        else{
            tableName = newTableName;
            return true;
        }
    }

}
