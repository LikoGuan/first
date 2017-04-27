package com.demo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/4/27.
 * ExampleController handle string-statistic requests from clients
 */
@RestController
public class ExampleController {

    /**
     * handle HTTP GET requests
     * @param input: the input string
     * @return statistic information after computing
     */
    @RequestMapping(value = "/stat/{input}", method = RequestMethod.GET)
    public String statGet(@PathVariable("input") String input) {
        if (StringUtils.isEmpty(input)) {
            return "the input string can not be empty";
        }

        try {
            return computeString(input);
        } catch (Exception ex) {
            throw new RuntimeException("Server Exception!");
        }
    }

    /**
     * handle HTTP POST requests
     * @param request: object maps to input json string like {'input':'123abc'}
     * @return statistic information after computing
     */
    @RequestMapping(value = "/stat/", method = RequestMethod.POST)
    public String statPost(@RequestBody Request request) {
        if (request == null || StringUtils.isEmpty(request.getInput())) {
            return "the input string can not be empty";
        }

        try {
            return computeString(request.getInput());
        } catch (Exception ex) {
            throw new RuntimeException("Server Exception!");
        }
    }

    /**
     * the computing process will end after scanning the input string one time.
     * during the process, put each pair of <character,frequency> into a map(when one
     * character has already in the map, just increase its frequency).meanwhile, use
     * a variable 'mostFrequency' to store temporary max frequency and a list to store
     * temporary most frequent letters.if encountering one more frequent letter, just
     * modify the 'mostFrequency' and add the letter into the list after clearing it first.
     *
     * @param input: the input string from users
     * @return information about computing results, for example:
     *  “The letters are: ‘asdAearAs’. The most frequent letter is ‘a’, and the frequency is 4”
     */
    private String computeString(String input) {
        int mostFrequency = 0;
        List<Character> list = new ArrayList<Character>();
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        StringBuilder stringBuilder = new StringBuilder();

        String lowerCaseInput = input.toLowerCase();
        for (int i=0; i<lowerCaseInput.length(); i++) {
            char ch = lowerCaseInput.charAt(i);
            if (ch >= 'a' && ch <= 'z') {//only alphabets would be processed
                Integer count = map.get(ch);
                if (count != null) {
                    ++count;
                } else {
                    count = 1;//init the count to 1 when encountering a new letter
                }
                map.put(ch, count);//put <character,frequency> into map

                //add only alphabet with original relative sequence
                stringBuilder.append(input.charAt(i));

                if (count > mostFrequency) {//the current letter is more frequent
                    list.clear();//so, letters in the list are not the most frequent
                    mostFrequency = count;
                    list.add(ch);
                } else if (count == mostFrequency){//more than one letter have the mostFrequency
                    list.add(ch);
                }
            }
        }

        //assemble the return information
        if (map.isEmpty()) {
            return "no alphbet found in the string: " + input;
        } else {
            return "The letters are: ‘" + stringBuilder.toString()
                    + "’. The most frequent letter is " + getMostFrequecyLetters(list)
                    + ", and the frequency is " + mostFrequency;
        }
    }

    /**
     * produce a string which contains all the most frequency letters,
     * like "‘a’" if the list only has only one elem or "‘x’‘y’‘z’" if it has three.
     * @param list: most frequency letters are stored in the list
     * @return a string contains all the most frequency letters
     */
    private String getMostFrequecyLetters(List<Character> list) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<list.size(); i++) {
            sb.append("‘").append(list.get(i)).append("’");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        ExampleController controller = new ExampleController();
        System.out.println(controller.statGet("hello12HELLO34world"));
        Request req = new Request();
        req.setInput("1234567---$$$");
        System.out.println(controller.statPost(req));
    }
}
