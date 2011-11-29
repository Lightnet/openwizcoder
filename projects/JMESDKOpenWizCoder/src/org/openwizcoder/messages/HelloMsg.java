package org.openwizcoder.messages;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public class HelloMsg extends AbstractMessage {
    private String hello;       // message data
    public HelloMsg() {}    // empty constructor
    public HelloMsg(String s) { hello = s; } // custom constructor

    public String getSomething() {
        return hello;
    }  
}