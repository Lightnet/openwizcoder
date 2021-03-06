/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openwizcoder.ui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.screen.Screen;

/**
 *
 * @author Lightnet
 */

public class UIPopUpExitScreenController extends UIBasicScreenController{

    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
        System.out.print("Pop Up Exit");
    }
    
    @Override
    public void onStartScreen() {
        
    }
    
    @Override
    public void onEndScreen() {
        
    }
    
    @NiftyEventSubscriber(id = "BtYesExit")
    public void BtYesExitClicked(final String id, final ButtonClickedEvent event) {
        System.out.print("\nButton press exit");
        //showMenu();
        System.exit(0);
    }
    
    @NiftyEventSubscriber(id = "BtNoExit")
    public void BtNoExitClicked(final String id, final ButtonClickedEvent event) {
        System.out.print("\nButton press exit");
        //showMenu();
        nifty.closePopup(nifty.getCurrentScreen().getScreenId());
    }

    
}
