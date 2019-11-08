package edu.uta.nlp.util;

import java.util.regex.Pattern;

/**
 * @author hxy
 */
public class StrUtil {

    public static String getLastWord(String word) {
        String newWord = word.substring(word.lastIndexOf(" ") == -1 ? 0 : word.lastIndexOf(" ")).trim();
        return newWord;
    }

    public static String getFirstWord(String word) {
        String newWord = word.substring(0, word.indexOf(" ") == -1 ? word.length() : word.indexOf(" ")).trim();
        return newWord;
    }

    public static String getIndexOfWord(String word, Integer index) {
        String[] tmp = word.split(" ");
        if(index > tmp.length) {
            return "";
        }
        return tmp[index-1];
    }

    public static String getLastIndexOfWord(String word, Integer index) {
        String[] tmp = word.split(" ");
        if(index > tmp.length) {
            return "";
        }
        return tmp[tmp.length-index];
    }

    public static String chRegex(String s) {
        if(s.contains("'")) {
            s = s.replaceAll("\\'","\\\\\\'");
        }
        if(s.contains("&")) {
            s = s.replaceAll("&", "_");
        }

        return s;
    }

    public static String filterTableName(String s) {
        s = s.replaceAll("[^_a-zA-Z0-9]", "");

        if('0' <= s.charAt(0) && s.charAt(0) <= '9') {
            s = "r" + s;
        }

        return s;
    }
}
