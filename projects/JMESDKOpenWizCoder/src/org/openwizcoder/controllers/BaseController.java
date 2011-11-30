package org.openwizcoder.controllers;

import org.openwizcoder.messages.ObjectShareMsg;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.io.IOException;
import org.openwizcoder.OpenWizCoderApp;
import org.openwizcoder.messages.CharInputMsg;

/**
 *
 * @author Lightnet
 */

public class BaseController implements Control {
    public static OpenWizCoderApp app;
    
    protected Spatial spatial;
    protected boolean enabled = true;
    
    public ObjectShareMsg smobjshare;
    public CharInputMsg charinput;
    
    public BaseController() { 
        System.out.print("\nINIT CONTORLER:"+enabled);
    }
    
    public BaseController(Spatial sptl) {
        spatial = sptl;
        spatial.addControl(this);
        System.out.print("\nINIT CONTORLER:"+enabled);
    }    
    @Override
    public void update(float tpf) {
        if (enabled && spatial != null) {            
            /*
            if(smobjshare !=null){
                Vector3f pos = spatial.getLocalTranslation();
                //System.out.print("\n"+pos);
                spatial.setLocalTranslation( smobjshare.x,  smobjshare.y,  smobjshare.z);
            }
            */
            
            if(charinput !=null){
                Vector3f pos = spatial.getLocalTranslation();
                Vector3f posmove = Vector3f.ZERO;
                 if(charinput.up){
                     posmove.z = 0.1f;
                 }else if(charinput.down){
                     posmove.z = -0.1f;
                 }else{
                     posmove.z = 0;
                 }
                 
                 if(charinput.right){
                     posmove.x = 0.1f;
                 }else if(charinput.left){
                     posmove.x = -0.1f;
                 }else{
                     posmove.x = 0;
                 }
                
                 spatial.setLocalTranslation( pos.x + posmove.x,  pos.y,  pos.z + posmove.z);
                 if(((pos.x + posmove.x) != pos.x )
                     ||((pos.y + posmove.y) != pos.y )
                    ||((pos.z + posmove.z) != pos.z ) ){
                     System.out.print("\n"+spatial.getLocalTranslation()+"update!");
                     
                     
                 }
                 
                
                //System.out.print("\n"+spatial.getLocalTranslation());
            }
        }
    }

    @Override
    public void render(RenderManager rm, ViewPort vp) {
        // optional, e.g. to display a debug shape
    }
    @Override
    public Control cloneForSpatial(Spatial _spatial) {
        BaseController control = new BaseController(_spatial);
        // ... // set custom properties
        _spatial.addControl(control);
        return control;
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        // ...
    }
    @Override
    public boolean isEnabled() {
        return enabled;
    }
   
    @Override
    public void write(JmeExporter ex) throws IOException {
        //super.write(ex);
        OutputCapsule oc = ex.getCapsule(this);
        oc.write(enabled, "enabled", true);
        oc.write(spatial, "spatial", null);
        // write custom variables ....
    }
    @Override
    public void read(JmeImporter im) throws IOException {
        //super.read(im);
        InputCapsule ic = im.getCapsule(this);
        enabled = ic.readBoolean("enabled", true);
        spatial = (Spatial) ic.readSavable("spatial", null);
        // read custom variables ....
    }

    @Override
    public void setSpatial(Spatial sptl) {
        spatial = sptl;
        //spatial.addControl(this);
    }
    
    public Spatial getSpatial() {
        return spatial;
    }
    
    public static OpenWizCoderApp getApp(){
        return app;
    }
    
    public static void setApp(OpenWizCoderApp _app){
        app = _app;
    }
}
