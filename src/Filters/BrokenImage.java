package Filters;

public class BrokenImage extends Exception{
    private String message;
    BrokenImage(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }
}
