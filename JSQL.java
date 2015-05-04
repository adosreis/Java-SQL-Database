import java.util.*;

/**
 * Created by andrewdos2 on 4/7/15.
 */
public class JSQL {
    private static TreeMap<String, ArrayList<TreeMap<String, String>>> table = new TreeMap<String, ArrayList<TreeMap<String, String>>>();

    public static SortedMap<String, String> select(String tableName, Integer ID) { //this
        if (table.size() < ID-1) {
            return null;
        } else {
            return table.get(tableName).get(ID - 1);
        }
    }

    public static ArrayList<TreeMap<String, String>> select(String tableName) {
        return table.get(tableName);
    }

    public static ArrayList<TreeMap<String, String>> select(String tableName, String[] fields) {
        ArrayList<TreeMap<String,String>> returnArr = new ArrayList<TreeMap<String, String>>();
        for(int i = 0; i<table.get(tableName).size();i++) {
            TreeMap<String, String> returnMap = new TreeMap<String, String>();
            ArrayList<String> fieldList = new ArrayList<String>(Arrays.asList(fields));
            ArrayList<String> values = new ArrayList<String>();
            if (table.get(tableName).size() < i) {
                return null;
            } else {
                for (String s : fields) {
                    values.add(table.get(tableName).get(i).get(s));
                }
            }
            for (int j = 0; j < fieldList.size(); j++) {
                returnMap.put((String) fieldList.get(j), (String) values.get(j));
            }
            returnArr.add(returnMap);
        }
        return returnArr;

    }
    public static SortedMap<String, String> select(String tableName, Integer ID, String[] fields) {
        TreeMap<String, String> returnMap = new TreeMap<String, String>();
        ArrayList<String> fieldList = new ArrayList<String>(Arrays.asList(fields));
        ArrayList<String> values = new ArrayList<String>();
        if (table.get(tableName).size() <= ID-1) {
            return null;
        } else {
            for (String s : fields) {
                values.add(table.get(tableName).get(ID - 1).get(s));
            }
        }
        for (int i = 0; i < fieldList.size(); i++) {
            returnMap.put((String) fieldList.get(i), (String) values.get(i));
        }
        return returnMap;
    }


    public static void insert(String tableName, Integer ID, TreeMap<String, String> infoTypes) {
        Set<String> columnLabels = gettableColumnLabels(tableName);
        TreeMap<String, String> insertingID = infoTypes;
        for (String s : infoTypes.keySet()) {
            if (!(columnLabels.contains(s))) {
                return;
            }
        }
        for (String s : columnLabels) {
            if (!(infoTypes.keySet().contains(s))) {
                insertingID.put(s, null);
            }
        }
        table.get(tableName).add(insertingID);
    }

    public static void insert(String tableName, TreeMap<String, String> infoTypes) {
        insert(tableName, table.get(tableName).size(), infoTypes);
    }

    public static void delete(String tableName, Integer ID) {
        if (table.get(tableName).size() > ID - 1) {
           deleteValues(tableName,ID);
        }
    }
    public static void delete(String tableName){
        for (int i = 1; i <= table.get(tableName).size(); i++){
            deleteValues(tableName,i);
        }
    }

    public static void update(String tableName, Integer ID, String column, String value) {
        if (table.get(tableName).size() > ID - 1) {
            if (table.get(tableName).get(0).containsKey(column))
                table.get(tableName).get(ID - 1).put(column, value);
        }
    }
    private static void deleteValues(String tableName, Integer ID){
        for (String s : table.get(tableName).get(ID-1).keySet()){
            table.get(tableName).get(ID-1).put(s,null);
        }
    }

    public static Set<String> gettableColumnLabels(String tableName) {return table.get(tableName).get(0).keySet();}

    public static ArrayList<Integer> getIDArray(String tableName){
        ArrayList<TreeMap<String,String>> t = table.get(tableName);
        ArrayList<Integer> returnInts = new ArrayList<Integer>();
        for(int i = 0; i < t.size(); i++){
            if(!(t.get(i).equals(null))){
                returnInts.add(i+1);
            }
        }return returnInts;
    }

    public static Set<String> getTables(){return table.keySet();}

    public static void createTable(String tableName, ArrayList<TreeMap<String, String>> table) {
        JSQL.table.put(tableName, table);
    }
    public static void createTable(String tableName){
        JSQL.table.put(tableName,null);
    }

    public static void printTable(Integer ID, SortedMap<String, String> table) {
        if(table != null) {
            if(table.keySet() !=null) {
                System.out.println();
                System.out.print("| ID ");
                for (String s : table.keySet()) {
                    System.out.print("| " + s);
                }
                System.out.println("|");
                System.out.print("| " + ID + " ");
                for (String s : table.values()) {
                    System.out.print("| " + s + " ");
                }
                System.out.println(" |");
            }else{
                System.out.println("ID not contained in table");
            }
        }else{
            System.out.println("ID not contained in table");
        }
    }

    public static void printTable (ArrayList<TreeMap<String,String>> table) {
        if (table != null) {
            //System.out.println(table);
            System.out.print("| ID ");
            for (String s : table.get(table.size()-1).keySet()) {
                System.out.print("| " + s + " ");
            }
            System.out.println(" |");
            System.out.println("--------------------------------------------------------------");
            for (int i = 1; i <= table.size(); i++) {
                boolean firstVal = true;
                System.out.print("| " + i + " ");
                for (String s : table.get(i - 1).values()) {
                    if(s != null){
                    System.out.print(" | " + s);
                    }else if(firstVal){
                        System.out.print(" |       ");
                        firstVal = false;
                    }else{
                        System.out.print("          ");
                    }

                }
                System.out.println(" |");
                    System.out.println("--------------------------------------------------------------");

            }
        }else{
            System.out.println("table not valid");
        }
    }
}