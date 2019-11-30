package edu.uta.nlp.tool.stanford.strategy;

/**
 * @author housirvip
 */

public class VerbLemmaStrategy {

    public static String getVerb(String verb) throws Exception{

        Context context;

        Integer wordTotal = verb.split(" ").length;

        switch (wordTotal) {
            case 1:
                context = new Context(new OneWord());
                return context.executeStrategy(verb);
            case 2:
                context = new Context(new TwoWord());
                return context.executeStrategy(verb);
            case 3:
                context = new Context(new ThreeWord());
                return context.executeStrategy(verb);
            default:
                context = new Context(new Other());
                return context.executeStrategy(verb);
        }
    }
}
