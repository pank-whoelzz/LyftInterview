package test.lyft;

import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.*;

public class KV {
    Map<String, Integer> kvMap;
    Map<Integer, Integer> vCntMap;

    KV () {
        kvMap = new HashMap<>();
        vCntMap = new HashMap<>();
    }

    void set(String key, int value) {
        if (kvMap.containsKey(key)) {
            int oldValue = kvMap.get(key);
            vCntMap.put(oldValue, vCntMap.getOrDefault(oldValue,0) - 1);
        }
        kvMap.put(key, value);
        vCntMap.put(value, vCntMap.getOrDefault(value,0) + 1);
    }

    String get(String key) {
        if (kvMap.containsKey(key)) {
            return String.valueOf(kvMap.get(key));
        } else {
            return "NULL";
        }
    }

    void unset(String key) {
        if (kvMap.containsKey(key)) {
            int value = kvMap.get(key);
            kvMap.remove(key);
            if (vCntMap.containsKey(value)) {
                if (vCntMap.get(value) == 1) {
                    vCntMap.remove(value);
                } else {
                    vCntMap.put(value, vCntMap.get(value) - 1);
                }
            }
        }
    }

    int numWithValue(int value) {
        if (vCntMap.containsKey(value)) {
            return vCntMap.get(value);
        } else {
            return 0;
        }
    }


    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        try {
            KV kv = new KV();

            int lineNumber = 0;
            File kvInput = new File(
                    "/Users/pagupta/Repos/TestI/LyftInterview/src/main/resources/input");
            Scanner kvInputSc = new Scanner(kvInput);

            File myObj = new File("/Users/pagupta/Repos/TestI/LyftInterview/src/main/resources/output");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/pagupta/Repos/TestI/LyftInterview/src/main/resources/output.txt", false));
            while (kvInputSc.hasNextLine()) {
                String currentlineContent = kvInputSc.nextLine();
                String[] strArr = currentlineContent.split("\\s+");
                if (strArr[0].equals("SET")) {
                    kv.set(strArr[1], Integer.valueOf(strArr[2]));
                } else if (strArr[0].equals("GET")) {
                    String res = kv.get(strArr[1]);
                    writer.write(res);
                    writer.newLine();
                } else if (strArr[0].equals("UNSET")) {
                    kv.unset(strArr[1]);
                } else if (strArr[0].equals("NUMWITHVALUE")) {
                    int res = kv.numWithValue(Integer.valueOf(strArr[1]));
                    writer.write(String.valueOf(res));
                    writer.newLine();
                } else if (strArr[0].equals("END")) {
                    writer.close();
                }
                lineNumber++;
            }

            //ToDo : Successful code fir input
//            int lineNumber = 0;
//            File kvInput = new File(
//                    "/Users/pagupta/Repos/TestI/LyftInterview/src/main/resources/input");
//            Scanner kvInputSc = new Scanner(kvInput);
//
//            File myObj = new File("/Users/pagupta/Repos/TestI/LyftInterview/src/main/resources/output");
//            if (myObj.createNewFile()) {
//                System.out.println("File created: " + myObj.getName());
//            } else {
//                System.out.println("File already exists.");
//            }
//
//            BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/pagupta/Repos/TestI/LyftInterview/src/main/resources/Output", false));
//            while (kvInputSc.hasNextLine()) {
//                String currentlineContent = kvInputSc.nextLine();
//                String[] strArr = currentlineContent.split("\\s+");
//                if (strArr[0].equals("SET")) {
//                    kv.set(strArr[1], Integer.valueOf(strArr[2]));
//                } else if (strArr[0].equals("GET")) {
//                    String res = kv.get(strArr[1]);
//                    writer.write(res);
//                    writer.newLine();
//                } else if (strArr[0].equals("UNSET")) {
//                    kv.unset(strArr[1]);
//                } else if (strArr[0].equals("END")) {
//                    writer.close();
//                }
//                lineNumber++;
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}