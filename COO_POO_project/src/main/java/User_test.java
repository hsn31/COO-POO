
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author peraire
 */
public class User_test {
    
    private int id;
    
    private LinkedList<String> memoryMessages;
    
    User_test(int new_id)
    {
        id = new_id;
        memoryMessages = new LinkedList<String>();
    }
    
    public void registerMessage(String message)
    {
        memoryMessages.add(message);
    }
    
    public int getId()
    {
        return id;
    }
}
