package Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuperHero {


    private int id;

    private String name;

    private String superName;
    private String profession;
    private long age;
    private Boolean canFly;

    public SuperHero(){

    }
    public SuperHero( String name, String superName, String profession, long age, Boolean canFly){

        this.name=name;
        this.superName=superName;
        this.profession=profession;
        this.age=age;
        this.canFly=canFly;



    }

}
