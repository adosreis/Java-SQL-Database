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
        JSQL.printTable(preTable2);
        System.out.println("Preparing to receive input, exit Token is \"*\"");
        while(true){
            Query inputs = Query.readQuery("*", true);
            if (inputs == null) {
                System.out.println("");
                break;
            }
            if (inputs.isSelect()) {
                select(inputs, "");
            } else if (inputs.isInsert()) {
                insert(inputs, "");
            } else {
                System.out.println("not yet implemented or incorrect input!");
            }

        }
    }

}
