package org.openwizcoder;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import org.openwizcoder.controllers.ProgressbarControl;

/**
 *
 * @author Lightnet
 */

public class AppProgressBarTest extends SimpleApplication implements ScreenController {
    public Nifty nifty;
    public float percent;

    public static void main(final String[] args) throws Exception {
        AppProgressBarTest app = new AppProgressBarTest();
        app.setShowSettings(false);
        app.start();
    }

    public void Init_Nifty(){
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                                                          inputManager,
                                                          audioRenderer,
                                                          guiViewPort);
        nifty = niftyDisplay.getNifty();
        
        
        
        nifty.fromXml("Interface/UI_ProgressBar.xml", "start");
        // attach the nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay);        
    }    

    @Override
    public void simpleInitApp() {
        Init_Nifty();
        //set port backcolor to blue
        viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
        //to see the progress bar
    }
    
    
    @Override
    public void simpleUpdate(float tpf) {
        percent += 1.0f;
        if(percent > 100.0f){
            percent = 0.0f;
        }
        if(nifty !=null){
            if(nifty.getCurrentScreen() !=null ){
            //nifty.getCurrentScreen().findControl("my-progress", ProgressbarControl.class).setProgress(percent / 100.0f);
                
            //nifty.getScreen("start").findControl("my-progress", ProgressbarControl.class).setProgress(percent / 100.0f);
                
                ProgressbarControl probar = nifty.getScreen("start").findControl("my-progress", ProgressbarControl.class);
                probar.setProgress(percent / 100.0f);
            }
        }
        
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