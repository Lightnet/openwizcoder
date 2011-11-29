package org.openwizcoder.messages;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public class ObjectTiggerMsg extends AbstractMessage {
    
    public String objname = "";
    public String name_tag = "";
    public String name_event = "";
    
}