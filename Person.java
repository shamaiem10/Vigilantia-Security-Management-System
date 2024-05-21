

import java.io.Serializable;

public class Person implements Serializable {
    //Attributes
    public String name;
    public String contact;
    //Constructor
    protected Person(String name, String contact){
        this.name = name;
        this.contact = contact;
    }
}
