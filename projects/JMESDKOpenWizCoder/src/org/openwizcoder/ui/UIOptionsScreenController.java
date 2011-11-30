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

public class UIOptionsScreenController extends UIBasicScreenController{

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

    @NiftyEventSubscriber(id = "BtBackOptions")
    public void BtBackOptionsClick(final String id, final ButtonClickedEvent eventt) {
        System.out.print("\nclose console");
        this.nifty.gotoScreen("start");
    }
    
}
