package me.iazure.ex.express.exceptions;

public class OrderOperationException extends RuntimeException {
    public OrderOperationException(String msg){
        super(msg);
    }
}
