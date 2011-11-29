package org.openwizcoder.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Lightnet
 */

@Serializable
public class ObjectShareMsg extends AbstractMessage {
    public String userid = "";
    public float x = 0;
    public float y = 0;
    public float z = 0;
    
    private String hello;       // message data
    public ObjectShareMsg() {}    // empty constructor
    public ObjectShareMsg(String s) { hello = s; } // custom constructor

    public String getSomething() {
        return hello;
    }  
}