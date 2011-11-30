/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openwizcoder.ui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.TextFieldChangedEvent;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;

/**
 *
 * @author Lightnet
 */

public class UILoginScreenController extends UIBasicScreenController{
    
    public String username ="guest";
    public String userpass ="pass";
    
    private Element textInput;
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;        
    }
    
    @Override
    public void onStartScreen() {
        
    }
    
    @Override
    public void onEndScreen() {
        
    }
    
    @NiftyEventSubscriber(id = "userid")
    public void onUserNameChanged(final String id, final TextFieldChangedEvent event) {
        TextField score = event.getTextFieldControl();
        username = event.getText();
        //System.out.print("text:"+text);
    }
    
    @NiftyEventSubscriber(id = "userpass")
    public void onUserPassChanged(final String id, final TextFieldChangedEvent event) {
        TextField score = event.getTextFieldControl();
        userpass = event.getText();
        //System.out.print("text:"+text);
    }
    
    
    @NiftyEventSubscriber(id = "BtLogin")
    public void BtLoginClick(final String id, final ButtonClickedEvent eventt) {
        System.out.print("\nclose console");
       
          
        //textInput = screen.findElementByName("userid");
        //textInput.getControl(TextFieldControl.class).getText()
        textInput = this.nifty.getCurrentScreen().findElementByName("userid");
        /*
        textInput = nifty.getCurrentScreen().findElementByName("userid");
        
        if(textInput !=null){
            //System.out.print(textInput.getRenderer(TextRenderer.class).getWrappedText());
            
            System.out.print(textInput.get);
            System.out.print("text found");
        }else{
            System.out.print("text error");
        }
        */
        System.out.print("\nUSER:"+username);
        System.out.print("\nPASS:"+userpass);
        
        //System.out.print("\nTEXT: "+nifty.getCurrentScreen().findElementByName("userid").getRenderer(TextRenderer.class).getOriginalText());
        System.out.print("\nenter login");
    }
    
    @NiftyEventSubscriber(id = "BtCancel")
    public void BtCancelClick(final String id, final ButtonClickedEvent eventt) {
        System.out.print("\ncancel login");
    }
    
}
