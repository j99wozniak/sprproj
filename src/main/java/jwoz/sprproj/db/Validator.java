package jwoz.sprproj.db;

public class Validator {
    public static boolean ValidateIfSingleWord(String query){
        if(query==null) return true;
        return query.matches("^\\S*$");
    }
}
