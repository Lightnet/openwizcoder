/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openwizcoder.controllers;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.SizeValue;
import de.lessvoid.xml.xpp3.Attributes;
import java.util.Properties;

public class ProgressbarControl implements Controller {

    private Nifty nifty;
    private Screen screen;
    private Element element;
    private Properties parameter;
    private Attributes controlDefinitionAttributes;
        
    private Element progressBarElement;
    private Element progressTextElement;
        
    @Override
    public void init(Properties parameter, Attributes controlDefinitionAttributes) {
        this.parameter = parameter;
        this.controlDefinitionAttributes = controlDefinitionAttributes;
    }
        
    @Override
    public void bind(Nifty nifty,
                        Screen screen,
                        Element element,
                        Properties parameter,
                        Attributes controlDefinitionAttributes) {
        this.nifty = nifty;
        this.screen = screen;
        this.element = element;
        this.parameter = parameter;
        
        progressBarElement = element.findElementByName("progress");
        progressTextElement = element.findElementByName("progress-text");
    }

    public void onStartScreen() {
    }

    public void onFocus(final boolean getFocus) {
    }

    public boolean inputEvent(final NiftyInputEvent inputEvent) {
        return false;
    }
  
  
    public void setProgress(final float progressValue) {
        float progress = progressValue;
        if (progress < 0.0f) {
            progress = 0.0f;
        } else if (progress > 1.0f) {
            progress = 1.0f;
        }
        final int MIN_WIDTH = 32; 
        int pixelWidth = (int)(MIN_WIDTH + (progressBarElement.getParent().getWidth() - MIN_WIDTH) * progress);
        progressBarElement.setConstraintWidth(new SizeValue(pixelWidth + "px"));
        progressBarElement.getParent().layoutElements();

        String progressText = String.format("%3.0f%%", progress * 100);
        progressTextElement.getRenderer(TextRenderer.class).setText(progressText);
    }
}