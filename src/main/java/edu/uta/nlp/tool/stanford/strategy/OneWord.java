package edu.uta.nlp.tool.stanford.strategy;

/**
 * @author hxy
 */
public class OneWord implements VerbSelectStrategy{
    @Override
    public String process(String verb) {
        return verb;
    }
}
