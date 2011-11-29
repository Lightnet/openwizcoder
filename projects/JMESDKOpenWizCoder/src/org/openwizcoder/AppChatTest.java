package org.openwizcoder;

import com.jme3.app.SimpleApplication;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openwizcoder.ui.UIChatScreenController;

/**
 *
 * @author Lightnet
 */

public class AppChatTest extends SimpleApplication implements ScreenController {
	public Nifty nifty;

	public static void main(final String[] args) throws Exception {
            Logger.getLogger("").setLevel(Level.SEVERE);
            AppChatTest app = new AppChatTest();
            app.setShowSettings(false);
            app.start();
	}

    public void Init_Nifty(){
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                                                          inputManager,
                                                          audioRenderer,
                                                          guiViewPort);
        nifty = niftyDisplay.getNifty();
        UIChatScreenController chatbox = new UIChatScreenController();
        nifty.fromXml("Interface/UI_Chat.xml" ,"start",chatbox);
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