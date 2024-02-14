package jwoz.sprproj.data;

import jwoz.sprproj.db.Validator;
import jwoz.sprproj.record.Person;

import java.util.List;
import java.util.Map;

public interface DataAccessor {
    public List<Person> getRecords(Map<String, String> parameters);
    public boolean createNewRecord(String name, int age);
    public boolean changeRecord(int id, String name, String age);
    public boolean deleteRecord(int id);
    public boolean createTable();
    public boolean deleteTable();
    public boolean setTableName(String newTableName);
}
