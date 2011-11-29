/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openwizcoder.ui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.Chat;
import de.lessvoid.nifty.controls.ChatTextSendEvent;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;

/**
 *
 * @author Lightnet
 */

public class UIChatScreenController extends UIBasicScreenController{

    NiftyImage newImage;// = nifty.getRenderEngine().createImage("Textures/member_icon.png", false);

    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
        try {
            this.newImage = this.nifty.getRenderEngine().createImage("Textures/member_icon.png", false);
            AddPlayer("guest", newImage);
            System.out.println("image dectected");
        } catch (Exception e) {
             System.out.println("image error dectected");
        }
    }
    
    @Override
    public void onStartScreen() {
        
    }
    
    @Override
    public void onEndScreen() {
        
    }

    @NiftyEventSubscriber(id="chatcontrol")
    public final void onSendText(final String id, final ChatTextSendEvent event) {
        if (!event.getText().isEmpty()) {
            String text = event.getText();
            System.out.println("chat event received: " + text);
            Message("guest",text,this.newImage);
        }
        System.out.println("event dectected");
    }
    
    public void AddPlayer(String playerName, NiftyImage avatarImage){
        final Element chatPanel = nifty.getCurrentScreen().findElementByName("chatpanel");
        final Chat chat = chatPanel.findNiftyControl("chatcontrol", Chat.class);
        
        if(avatarImage !=null){
            chat.addPlayer(playerName, avatarImage);    
        }else{
            chat.addPlayer(playerName, newImage);
        }
    }
    
    public void RemovePlayer(String playerName){
        final Element chatPanel = nifty.getCurrentScreen().findElementByName("chatpanel");
        final Chat chatController = chatPanel.findNiftyControl("chatcontrol", Chat.class);
        chatController.removePlayer(playerName);        
    }
    
    public void Message(String playerName,String chatLine,NiftyImage avatarImage){
        System.out.println("image default dectected");
        final Element chatPanel = nifty.getCurrentScreen().findElementByName("chatpanel");
        /*
        if(chatPanel !=null){
            System.out.println("found element");
        }else{
            System.out.println("not found element");
        }
        */
        final Chat chatController = chatPanel.findNiftyControl("chatcontrol", Chat.class);
        /*
        if(chatController !=null){
            System.out.println("found Controller");
        }else{
            System.out.println("not found Controller");
        }
        */
        if(avatarImage !=null){
            chatController.receivedChatLine(playerName +">" + chatLine, avatarImage);
            System.out.println("image mod dectected");
        }else{
            //newImage = this.nifty.getRenderEngine().createImage("Textures/member_icon.png", false);
            chatController.receivedChatLine(playerName +">" + chatLine, newImage);
            System.out.println("image default dectected:"+playerName +">" + chatLine);
        }
    }
    
}
