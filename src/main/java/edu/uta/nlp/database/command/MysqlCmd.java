package edu.uta.nlp.database.command;

/**
 * @author hxy
 */
public interface MysqlCmd {

    /**
     * execute sql command
     * @return Object
     * @throws Exception
     */
    Object execute() throws Exception;
}
