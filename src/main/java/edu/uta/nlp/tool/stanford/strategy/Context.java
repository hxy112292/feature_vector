package edu.uta.nlp.tool.stanford.strategy;

/**
 * @author hxy
 */
public class Context {

    private VerbSelectStrategy verbSelectStrategy;

    public Context(VerbSelectStrategy verbSelectStrategy) {
        this.verbSelectStrategy = verbSelectStrategy;
    }

    public String executeStrategy(String verb) throws Exception{
        return verbSelectStrategy.process(verb);
    }
}
