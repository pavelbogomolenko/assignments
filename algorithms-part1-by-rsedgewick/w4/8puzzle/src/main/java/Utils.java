package main.java;

import java.util.stream.Stream;

public final class Utils {
    public static int[] csvStrToInt(String s) {
        Stream<String> rowOneStream = Stream.of(s.split(" "));
        return rowOneStream.mapToInt(Integer::parseInt).toArray();
    }
}
