package edu.tum.ase.restapiexample;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;

// similar to an http servlet, define behavior on request
// a singleton bean
// this object should essentially be instantiated once, and is shared across the service
@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private List<Greeting> greetingList = new ArrayList<Greeting>();
    // annotator, can also use getmapping
    @RequestMapping(method = RequestMethod.GET, path = "/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    @RequestMapping(method = RequestMethod.POST, path="/greeting")
    public Greeting addGreeting(@RequestBody Greeting greeting){
        Greeting g = new Greeting(
                counter.incrementAndGet(),
                String.format(template,greeting.getContent()));
        greetingList.add(g);
                return g;
    }


    @RequestMapping(method = RequestMethod.GET, path = "/greetings")
    public  List<Greeting> listAllGreetings(){
        return greetingList;
    }


}
