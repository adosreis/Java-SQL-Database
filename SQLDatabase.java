import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

/**
 * Created by andrewdos2 on 4/9/15.
 */
//taking a guess so   ut is not how im going to be displaying my tables
    //prbs gonna have to make a method for displaying JSQL maps the proper way!
public class SQLDatabase {
    public static boolean select (Query selectQuery,String waste) {
        if(selectQuery.isSelect()){
            ArrayList<String> fields = new ArrayList<String>(Arrays.asList(selectQuery.getSelectFields()));
            if(selectQuery.getWhereID() != -1){
                if (fields.contains("*")){
                    JSQL.printTable(selectQuery.getWhereID(),JSQL.select(selectQuery.getRelationName(), selectQuery.getWhereID()));
                    return true;
                }

                else if(selectQuery.getSelectFields() != null && !fields.contains("*")){
                    JSQL.printTable(selectQuery.getWhereID(), JSQL.select(selectQuery.getRelationName(), selectQuery.getWhereID(), selectQuery.getSelectFields()));
                    return true;
                }
                else{
                    return false;
                }
            }else{
                if(fields.contains("*")){
                    JSQL.printTable(JSQL.select(selectQuery.getRelationName()));
                    return true;
                }
                else if(selectQuery.getSelectFields() != null && !fields.contains("*")) {
                    JSQL.printTable(JSQL.select(selectQuery.getRelationName(), selectQuery.getSelectFields()));
                    return true;
                }
                else{
                    return false;
                }
            }
        }else{
            return false;
        }
    }
    //this seems pretty tasty, hoping the TreeMaps sort the same each time
    public static boolean insert(Query insertQuery, String waste) { //this assumes that the person imputing values knows the order in which to order the inputed information
        if(insertQuery.isInsert()){
            if(JSQL.getTables().contains(insertQuery.getRelationName())){
            if(insertQuery.getInsertValues().length ==JSQL.gettableColumnLabels(insertQuery.getRelationName()).size()) {
                TreeMap<String, String> insertMap = new TreeMap<String, String>();
                ArrayList<String> columnTypes = new ArrayList<String>();
                for (Object s : JSQL.gettableColumnLabels(insertQuery.getRelationName()).toArray()) {
                    columnTypes.add((String) s);
                }
                String[] tableVals = insertQuery.getInsertValues();
                for (int i = 0; i < columnTypes.size(); i++) {
                    insertMap.put(columnTypes.get(i), tableVals[i]);
                }
                JSQL.insert(insertQuery.getRelationName(), insertMap);
                return true;
            }else {
                System.out.println("User did not input enough fields");
                return false;
            }
            }else {
                System.out.println("table " + insertQuery.getRelationName() + " does not exist!");
                return false;
            }
            }else{
                return false;
            }
        }
    public static boolean delete(Query deleteQuery, String waste){
        if(deleteQuery.isDelete()){
            if(JSQL.getTables().contains(deleteQuery.getRelationName())){
                if(deleteQuery.getWhereID() != -1){
                    JSQL.delete(deleteQuery.getRelationName(), deleteQuery.getWhereID());
                    return true;
                } else {
                    JSQL.delete(deleteQuery.getRelationName());
                    return true;
                }
                }else{
                return false;
            }
            }else{
                return false;
            }
    }
    public static boolean update(Query updateQuery, String waste){
        if(updateQuery.isUpdate()){
            if(JSQL.getTables().contains(updateQuery.getRelationName())){
                    if (JSQL.gettableColumnLabels(updateQuery.getRelationName()).contains(updateQuery.getUpdateField())){
                        if (updateQuery.getWhereID() == -1){
                            for(Integer i :JSQL.getIDArray(updateQuery.getRelationName())){
                                JSQL.update(updateQuery.getRelationName(), i, updateQuery.getUpdateField(), updateQuery.getUpdateValue());
                            }
                            return true;
                        }else {
                            JSQL.update(updateQuery.getRelationName(), updateQuery.getWhereID(), updateQuery.getUpdateField(), updateQuery.getUpdateValue());
                            return true;
                        }
                    }else {
                        return false;
                    }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public static void main(String[] args) {
        TreeMap<String,String> preTable1 = new TreeMap<String, String>();
        preTable1.put("SchoolName","Rutger");
        preTable1.put("CityName","New_Brunswick");
        preTable1.put("Type","Public");
        preTable1.put("Year","1766");
        ArrayList<TreeMap<String,String>> preTable2 = new ArrayList<TreeMap<String, String>>();
        preTable2.add(preTable1);
        JSQL.createTable("Schools", preTable2);
        System.out.println("Schools");
        JSQL.printTable(preTable2);
        System.out.println("Preparing to receive input...");
        System.out.println("Exit Token is \"exit\"");
        System.out.println("Spaces in values should be written as \'_\'");
        while(true){
            Query inputs = Query.readQuery("exit", true);
            if (inputs == null) {
                System.out.println("Exiting SQL Terminal...");
                break;
            }
            if (inputs.isSelect()) {
                select(inputs, "");
            } else if (inputs.isInsert()) {
                insert(inputs, "");
            } else if (inputs.isDelete()){
                delete(inputs,"");
            } else if (inputs.isUpdate()){
                update(inputs,"");
            }
            else {
                System.out.println("not yet implemented or incorrect input!");
            }

        }
    }

}
