package com.demo;

/**
 * Created by admin on 2017/4/27.
 * Class Request: controller will use it to receive json object
 */
public class Request {
    /**
     * the member 'input' maps to key of json object like {'input':'abc123}
     */
    private String input;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
