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

/**
 *
 * @author Lightnet
 */

public class UIPopMenuItemScreenController extends UIBasicScreenController{
    Element popup;
    Element popupExit;
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
        System.out.print("Pop Up Exit");
        
        createExitPopupMenu();
        createMyPopupMenu();
    }
    
    @Override
    public void onStartScreen() {
        
    }
    
    @Override
    public void onEndScreen() {
        
    }
    
    @NiftyEventSubscriber(id = "BtMenuItem")
    public void BtMenuItemClicked(final String id, final ButtonClickedEvent event) {
        System.out.print("\nButton press menu item");
        showMenu();
    }
    
    @NiftyEventSubscriber(id = "BtClose")
    public void BtCloseClicked(final String id, final ButtonClickedEvent event) {
        System.out.print("\nButton press exit");
        showpopupExit();
    }
    
    @NiftyEventSubscriber(id = "BtYes")
    public void BtYesClicked(final String id, final ButtonClickedEvent event) {
        System.out.print("\nButton press BtYes");
        System.exit(0);
    }
    
    @NiftyEventSubscriber(id = "BtNo")
    public void BtNoClicked(final String id, final ButtonClickedEvent event) {
        System.out.print("\nButton press BtNo");
        this.nifty.closePopup( popupExit.getId()); 
    }
    
    @NiftyEventSubscriber(id = "menuItemid")
    public void MenuItemClicked(final String id, final MenuItemActivatedEvent event) {
        System.out.print("\nItem menu[image]:"+event.getItem().toString());
        if(event.getItem().toString().equalsIgnoreCase("close")){
            this.nifty.closePopup( popup.getId()); 
        }
    }
    
    //Menu Items
    public void createMyPopupMenu(){
        if(popup == null){
            popup = this.nifty.createPopup("niftyPopupMenu");
            Menu myMenu = popup.findNiftyControl("#menu", Menu.class);
            myMenu.setWidth(new SizeValue("100px")); // must be set
            myMenu.addMenuItem("Click me!", "clickme");
            myMenu.addMenuItem("Close Me!", "close");
            myMenu.setId("menuItemid");//remeber to assign NiftyEventSubscriber for this
        }
    }
    
    /*
    public void createMyPopupMenu(){
        if(popup == null){
            popup = this.nifty.createPopup("niftyPopupMenu");
            Menu myMenu = popup.findNiftyControl("#menu", Menu.class);
            myMenu.setWidth(new SizeValue("100px")); // must be set
            myMenu.addMenuItem("Click me!", "Textures/member_icon.png",new menuItem("menuItemid", "blah blah"));
            myMenu.addMenuItem("Hide Me!", "Textures/member_icon.png",new menuItem("menuItemid", "hide"));
            myMenu.setId("menuItemid");
            
            
            
            this.nifty.subscribe(
                this.nifty.getCurrentScreen(), 
                myMenu.getId(), 
                MenuItemActivatedEvent.class, 
                new MenuItemActivatedEventSubscriber()
            );
        }else{
            //resign subscribe bug on > this.nifty.closePopup( popup.getId());?
            //to detect selected
            Menu myMenu = popup.findNiftyControl("#menu", Menu.class);
            this.nifty.subscribe(
                this.nifty.getCurrentScreen(), 
                myMenu.getId(), 
                MenuItemActivatedEvent.class, 
                new MenuItemActivatedEventSubscriber()
            );
        }
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
                if(item.name.equalsIgnoreCase("hide")){
                    hideMenu();
                }
            }
        }
    };
    */
    
    public void showMenu() { // the method to trigger the menu
        // call the popup to screen of your choice:
        this.nifty.showPopup(this.nifty.getCurrentScreen(), popup.getId(), null); 
    }
    
    public void hideMenu() { // the method to trigger the menu
        // call the popup to screen of your choice:
        this.nifty.closePopup( popup.getId());
    }
    
    //Popup Exit Menu
    public void createExitPopupMenu(){
        popupExit = this.nifty.createPopup("PopupExitID");
    }
      
    public void showpopupExit() { // the method to trigger the menu
        this.nifty.showPopup(this.nifty.getCurrentScreen(), popupExit.getId(), null); 
    }
}
