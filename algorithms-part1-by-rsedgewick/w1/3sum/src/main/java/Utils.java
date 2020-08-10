package main.java;

public class Utils {
    public static int[] getInts(String a) {
        String[] strArr = a.split(",");
        int[] arr = new int[strArr.length];
        for(int i = 0; i < strArr.length; i++) {
            if(strArr[i].isEmpty()) {
                continue;
            }
            arr[i] = Integer.parseInt(strArr[i].strip());
        }
        return arr;
    }
}
