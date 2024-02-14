package jwoz.sprproj.db;

public class Validator {
    public static boolean ValidateIfSingleWord(String query){
        return query.matches("^\\S*$");
    }
}
