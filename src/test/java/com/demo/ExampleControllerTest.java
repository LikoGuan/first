package com.demo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by admin on 2017/4/28.
 */
public class ExampleControllerTest {
    @Test
    public void statGet() throws Exception {
        assertEquals("the input string can not be empty",
                new ExampleController().statGet(""));
        assertEquals("no alphbet found in the string: 123****!!!!",
                new ExampleController().statGet("123****!!!!"));
        assertEquals("The letters are: ‘AbacgcCZAtttyzaaabBBBBb’. The most frequent letter is ‘b’, and the frequency is 7",
                new ExampleController().statGet("5Ab3*acg48cCZAtttyzaaabBBBBb"));
    }

    @Test
    public void statPost() throws Exception {
        Request req = new Request();
        req.setInput("");
        assertEquals("the input string can not be empty",
                new ExampleController().statPost(req));

        req.setInput("123()()!!!!");
        assertEquals("no alphbet found in the string: 123()()!!!!",
                new ExampleController().statPost(req));

        req.setInput("5Ab3*acg48cCZAtttttttyzaaabBBBBb");
        assertEquals("The letters are: ‘AbacgcCZAtttttttyzaaabBBBBb’. The most frequent letter is ‘t’‘b’, and the frequency is 7",
                new ExampleController().statPost(req));
    }

}