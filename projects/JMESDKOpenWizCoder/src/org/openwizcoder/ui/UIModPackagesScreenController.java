/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openwizcoder.ui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.ListBoxSelectionChangedEvent;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import java.util.List;

/**
 *
 * @author Lightnet
 */

public class UIModPackagesScreenController extends UIBasicScreenController{

    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen; 
        fillMyListBox();
    }
    
    @Override
    public void onStartScreen() {
        
    }
    
    @Override
    public void onEndScreen() {
        
    }
    
     /**
    * Fill the listbox with items. In this case with Strings.
    */
    public void fillMyListBox() {
        ListBox listBox = screen.findNiftyControl("myListBox", ListBox.class);
        listBox.addItem("a");
        listBox.addItem("b");
        listBox.addItem("c");
        listBox.addItem("c");
        listBox.addItem("c");
        listBox.addItem("c");
        listBox.addItem("c");
        listBox.addItem("c");
        listBox.addItem("c");
    }

    /**
    * When the selection of the ListBox changes this method is called.
    */
    @NiftyEventSubscriber(id="myListBox")
    public void onMyListBoxSelectionChanged(final String id, final ListBoxSelectionChangedEvent<String> event) {
        List<String> selection = event.getSelection();
        for (String selectedItem : selection) {
            System.out.println("listbox selection [" + selectedItem + "]");
        }
    }
    
}
