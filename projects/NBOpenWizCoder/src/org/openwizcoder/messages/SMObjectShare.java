package org.openwizcoder.messages;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public class SMObjectShare extends AbstractMessage {
    public String userid = "";
    public float x = 0;
    public float y = 0;
    public float z = 0;
    
    private String hello;       // message data
    public SMObjectShare() {}    // empty constructor
    public SMObjectShare(String s) { hello = s; } // custom constructor

    public String getSomething() {
        return hello;
    }  
}