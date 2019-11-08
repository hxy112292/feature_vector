package edu.uta.nlp.util;

/**
 * @author hxy
 */
public class CamelUtil {

    public static String camel2under(String c) {
        String separator = "_";
        c = c.replaceAll("([a-z])([A-Z])", "$1"+separator+"$2").toLowerCase();
        return c;
    }

    public static String under2camel(String s) {
        String separator = "_";
        String under="";
        s = s.toLowerCase().replace(separator, " ");
        String sarr[]=s.split(" ");
        for(int i=0;i<sarr.length;i++) {
            String w=sarr[i].substring(0,1).toUpperCase()+sarr[i].substring(1);
            under +=w;
        }
        return under;
    }
}
