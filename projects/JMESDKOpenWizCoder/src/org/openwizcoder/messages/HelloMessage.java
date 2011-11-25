package org.openwizcoder.messages;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public class HelloMessage extends AbstractMessage {
    private String hello;       // message data
    public HelloMessage() {}    // empty constructor
    public HelloMessage(String s) { hello = s; } // custom constructor

    public String getSomething() {
        return hello;
    }  
}