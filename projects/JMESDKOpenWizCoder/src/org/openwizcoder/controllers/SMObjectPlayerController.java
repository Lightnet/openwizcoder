package org.openwizcoder.controllers;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import org.openwizcoder.messages.ObjectShare;
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

/**
 *
 * @author HP_Administrator
 */
public class SMObjectPlayerController implements Control {
    protected Spatial spatial;
    protected boolean enabled = true;
    
    public ObjectShare smobjshare;
    
    public SMObjectPlayerController() { 
        System.out.print("\nINIT CONTORLER:"+enabled);
    }
    
    public SMObjectPlayerController(Spatial sptl) {
        spatial = sptl;
        spatial.addControl(this);
        System.out.print("\nINIT CONTORLER:"+enabled);
    }    
    @Override
    public void update(float tpf) {
        if (enabled && spatial != null) {            
            if(smobjshare !=null){
                Vector3f pos = spatial.getLocalTranslation();
                System.out.print(pos);
                spatial.setLocalTranslation( smobjshare.x,  smobjshare.y,  smobjshare.z);
            }
        }
    }

    @Override
    public void render(RenderManager rm, ViewPort vp) {
        // optional, e.g. to display a debug shape
    }
    @Override
    public Control cloneForSpatial(Spatial _spatial) {
        SMObjectPlayerController control = new SMObjectPlayerController(_spatial);
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
}
