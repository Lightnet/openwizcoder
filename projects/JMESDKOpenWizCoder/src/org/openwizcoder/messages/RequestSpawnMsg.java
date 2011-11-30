package org.openwizcoder.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Lightnet
 */

@Serializable
public class RequestSpawnMsg extends AbstractMessage {
    public int userid;
    public String username = "";
    public String msgtype = "";//local, world, broadcast, whisper
    public String msg = "";
}