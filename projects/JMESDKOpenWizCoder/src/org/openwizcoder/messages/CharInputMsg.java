package org.openwizcoder.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Lightnet
 */

@Serializable
public class CharInputMsg extends AbstractMessage {
    public int userid = 0;
    
    public boolean right = false;
    public boolean left = false;
    public boolean up = false;
    public boolean down = false;
    public boolean jump = false;
    
    private String hello;       // message data
    public CharInputMsg() {}    // empty constructor
    public CharInputMsg(String s) { hello = s; } // custom constructor

    public String getSomething() {
        return hello;
    }  
}