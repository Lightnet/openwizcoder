package org.openwizcoder.messages;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public class UserLoginMessage extends AbstractMessage {
    public int userid;
    public String username = "";
    public String userpass = "";
    
    public String msgtype = "";//pass , fail, ban, suspesion
}