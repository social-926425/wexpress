package me.iazure.ex.express.exceptions;

import me.iazure.ex.express.enums.PresonInfoEnum;

public class PresonInfoOperationException extends RuntimeException {
    public PresonInfoOperationException(PresonInfoEnum msg){
        super(String.valueOf(msg));
    }
}
