/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openwizcoder.ui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.Menu;
import de.lessvoid.nifty.controls.MenuItemActivatedEvent;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.SizeValue;
import org.bushe.swing.event.EventTopicSubscriber;

/**
 *
 * @author HP_Administrator
 */
public class UIMainMenuScreenController extends UIBasicScreenController{
    Element popup;
    Element popupExit;
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
        createMyPopupMenu();
        createExitPopupMenu();
    }
    
    @Override
    public void onStartScreen() {
        
    }
    
    @Override
    public void onEndScreen() {
        
    }
    
    @NiftyEventSubscriber(id = "BtSinglePlayer")
    public void BtSinglePlayernClicked(final String id, final ButtonClickedEvent event) {
        System.out.print("\nButton press");
    }
    
    
    @NiftyEventSubscriber(id = "BtMultiplePlayer")
    public void BtMultiplePlayerClicked(final String id, final ButtonClickedEvent event) {
        System.out.print("\nButton press");
    }
    
    @NiftyEventSubscriber(id = "BtOptions")
    public void BtOptionsClicked(final String id, final ButtonClickedEvent event) {
        System.out.print("\nButton press");
    }
    
    @NiftyEventSubscriber(id = "BtModPackages")
    public void BtModPackagesClicked(final String id, final ButtonClickedEvent event) {
        System.out.print("\nButton press");
    }
    
    @NiftyEventSubscriber(id = "BtCredits")
    public void BtCreditsClicked(final String id, final ButtonClickedEvent event) {
        System.out.print("\nButton press");
    }
    
    @NiftyEventSubscriber(id = "BtExit")
    public void BtExitClicked(final String id, final ButtonClickedEvent event) {
        System.out.print("\nButton press exit");
        showMenu();
        //showpopupExit();
    }
    
    public void createExitPopupMenu(){
        popupExit = nifty.createPopup("PopupExitID");
    }
    
    
    public void createMyPopupMenu(){
        popup = nifty.createPopup("niftyPopupMenu");
        Menu myMenu = popup.findNiftyControl("#menu", Menu.class);
        myMenu.setWidth(new SizeValue("100px")); // must be set
        myMenu.addMenuItem("Click me!", "Textures/member_icon.png",new menuItem("menuItemid", "blah blah"));
        myMenu.addMenuItem("Click me2!", "Textures/member_icon.png",new menuItem("menuItemid", "blah blah2"));
        
        nifty.subscribe(
            nifty.getCurrentScreen(), 
            myMenu.getId(), 
            MenuItemActivatedEvent.class, 
            new MenuItemActivatedEventSubscriber());
    }
    
    private class menuItem {
        public String id;
        public String name;
        public menuItem(String id, String name){
            this.id= id;
            this.name = name;
        }
    }
    
    private class MenuItemActivatedEventSubscriber 
        implements EventTopicSubscriber<MenuItemActivatedEvent> {
 
        @Override
        public void onEvent(final String id, final MenuItemActivatedEvent event) {
            menuItem item = (menuItem) event.getItem();
            if ("menuItemid".equals(item.id)) {
		//do something !!!
                System.out.print("\nitem click > " + item.id+ " > " + item.name);
            }
        }
    };
    

    
    public void showpopupExit() { // the method to trigger the menu
        nifty.showPopup(nifty.getCurrentScreen(), popupExit.getId(), null); 
    }
    
    public void showMenu() { // the method to trigger the menu
        // If this is a menu that is going to be used many times, then
        // call this in your constructor rather than here   
        //createMyPopupMenu(); 
        // call the popup to screen of your choice:
        nifty.showPopup(nifty.getCurrentScreen(), popup.getId(), null); 
    }
}
