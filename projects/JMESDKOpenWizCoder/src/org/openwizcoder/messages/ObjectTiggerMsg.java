package org.openwizcoder.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Lightnet
 */

@Serializable
public class ObjectTiggerMsg extends AbstractMessage {
    
    public String objname = "";
    public String name_tag = "";
    public String name_event = "";
    
}