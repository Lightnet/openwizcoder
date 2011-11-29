package org.openwizcoder;

import com.jme3.app.SimpleApplication;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openwizcoder.ui.UIPopMenuItemScreenController;

/**
 *
 * @author Lightnet
 */

public class AppMenuPopUp extends SimpleApplication implements ScreenController {
	public Nifty nifty;

	public static void main(final String[] args) throws Exception {
            Logger.getLogger("").setLevel(Level.SEVERE);
            AppMenuPopUp app = new AppMenuPopUp();
            app.setShowSettings(false);
            app.start();
	}

    public void Init_Nifty(){
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                                                          inputManager,
                                                          audioRenderer,
                                                          guiViewPort);
        nifty = niftyDisplay.getNifty();

        UIPopMenuItemScreenController itemconrol = new UIPopMenuItemScreenController();
        nifty.fromXml("Interface/UI_PopMenuItem.xml" ,"start",itemconrol);
        // attach the nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay); 
        
    }    

    @Override
    public void simpleInitApp() {
        Init_Nifty();
        setDisplayStatView(false);
        mouseInput.setCursorVisible(true);
        this.pauseOnFocus = false;
        this.flyCam.setEnabled(false);
    }

   public void bind(Nifty nifty, Screen screen) {
        System.out.println("bind( " + screen.getScreenId() + ")");
    }

    public void onStartScreen() {
        System.out.println("onStartScreen");
    }

    public void onEndScreen() {
        System.out.println("onEndScreen");
    }

    public void quit(){
        nifty.gotoScreen("end");
    }
}