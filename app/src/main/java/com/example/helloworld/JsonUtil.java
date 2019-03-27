package com.example.helloworld;

public class JsonUtil {

    public void string2json() {

    }

    public static String getJSON(Object... args) {
        StringBuilder resultJson = new StringBuilder("{");

        for (int i = 0; i < args.length; i += 2) {
            resultJson.append("\"")
                    .append(args[i])
                    .append("\"")
                    .append(":")
                    .append("\"")
                    .append(args[i + 1])
                    .append("\"")
                    .append(",");
        }
        resultJson.setLength(resultJson.length() - 1);
        resultJson.append("}");
        return resultJson.toString();
    }

}
