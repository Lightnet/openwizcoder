/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openwizcoder.ui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.ScrollPanel;
import de.lessvoid.nifty.controls.ScrollPanel.AutoScroll;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;

/**
 *
 * @author Lightnet
 */
public class UIUserAgreementScreenController extends UIBasicScreenController{
    private ScrollPanel scrollPanel;
    private Element textArea;
    public String bagree ="false";
    
    private Element textInput;
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
        //nifty.getScreen(this.screen).findElementByName("text").getRenderer(TextRenderer.class).setText(text);
        
       //scrollPanel = this.screen.findControl("scroll_panel", ScrollPanel.class); 
       textArea = this.screen.findElementByName("textareat"); //.getRenderer(TextRenderer.class).setText("Hello world");
       
       textArea.getRenderer(TextRenderer.class).setText("User Agreement(s)!\n 1). Do not abuse coding!");
       //setText("hello world");
    }
    
    
    @Override
    public void onStartScreen() {
        
    }
    
    @Override
    public void onEndScreen() {
        
    }
    
    
    public void setAutoScroll(AutoScroll auto) {
        scrollPanel.setAutoScroll(auto);
    }
	
    public AutoScroll getAutoScroll() {
        return scrollPanel.getAutoScroll();
    }
	
    public void append(String text) {
        setText(getText()+text);
    }
	
    public void setText(String text) {
        textArea.getRenderer(TextRenderer.class).setText(text);
        screen.layoutLayers();
        //chatArea = screen.findControl("chat_area", ChatAreaController.class);
        
    }
	
    public String getText() {
        return textArea.getRenderer(TextRenderer.class).getOriginalText();
    }
    

    public void BtAgree(){        
        System.out.print("\nBtAgree");
    }
    
    public void BtDisagree(){
        System.out.print("\nBtDisagree");
        nifty.gotoScreen("end");        
    }
    
    
}
