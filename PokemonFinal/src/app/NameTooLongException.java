package app;

import java.io.Serializable;
//This class creates teh custom exception
public class NameTooLongException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;

    public NameTooLongException(String message) {
        super(message);
    }
}


