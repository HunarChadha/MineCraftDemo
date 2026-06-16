package Terrain;
import java.util.*;

public class HelpClass {
    static Timer timer = new Timer();
    public static void main(String[] args) {
        InnerHelpClass hel = new InnerHelpClass();
        hel.print();
    }
}
/**
 * InnerHelpClass
 */
class InnerHelpClass {
    String Name;

    public InnerHelpClass(String Name){
        this.Name = Name;
    }
    public InnerHelpClass(){
        this("HelloWorld");
    }
    public void print(){
        System.out.println(this.Name);
    }
}