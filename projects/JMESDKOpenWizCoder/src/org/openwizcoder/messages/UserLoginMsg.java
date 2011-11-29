package org.openwizcoder.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Lightnet
 */

@Serializable
public class UserLoginMsg extends AbstractMessage {
    public int userid;
    public String username = "";
    public String userpass = "";
    
    public String msgtype = "";//pass , fail, ban, suspesion
}