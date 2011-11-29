/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openwizcoder.ui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.Console;
import de.lessvoid.nifty.controls.ConsoleCommands;
import de.lessvoid.nifty.controls.ConsoleCommands.ConsoleCommand;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.TextFieldChangedEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author Lightnet
 */

public class UIConsoleScreenController implements ScreenController{
    private Nifty nifty;
    private Screen screen;
    private String ScreenNameStart = "startconsole";
    private String ScreenNameEnd = "end";
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
        
        if(this.screen !=null){
            // get the console control (this assumes that there is a console in the current screen with the id="console"
            Console console = this.screen.findNiftyControl("console", Console.class);

            // output hello to the console
            console.output("Hello :)");

            // create the console commands class and attach it to the console
            ConsoleCommands consoleCommands = new ConsoleCommands(nifty, console);

            // create a simple command (see below for implementation) this class will be called when the command is detected
            // and register the command as a command with the console
            ConsoleCommand simpleCommand = new SimpleCommand();
            consoleCommands.registerCommand("simple", simpleCommand);

            // create another command (this time we can even register arguments with nifty so that the command completion will work with arguments too)
            ConsoleCommand showCommand = new ShowCommand();
            consoleCommands.registerCommand("show a", showCommand);
            consoleCommands.registerCommand("show b", showCommand);
            consoleCommands.registerCommand("show c", showCommand);
            consoleCommands.registerCommand("quit", showCommand);
            consoleCommands.registerCommand("hide", showCommand);
            consoleCommands.registerCommand("console", showCommand);
            consoleCommands.registerCommand("h c", showCommand);

            // finally enable command completion
            consoleCommands.enableCommandCompletion(true);
        }
    }
    
    @Override
    public void onStartScreen() {
        
    }
    
    @Override
    public void onEndScreen() {
        
    }
    
    //@Override
    //public void 
            
            
    
    @NiftyEventSubscriber(id = "console")
    public void onconsoleChanged(final String id, final TextFieldChangedEvent event) {
        TextField score = event.getTextFieldControl();
        String text = event.getText();  
        System.out.print(text);
    }
    
    private class SimpleCommand implements ConsoleCommand {
        @Override
        public void execute(final String[] args) {
            System.out.println(args[0]); // this is always the command (in this case 'simple')
            if (args.length > 1) {
                for (String a : args) {
                    System.out.println(a);
                }
            }
        }
    }

    private class ShowCommand implements ConsoleCommand {
        @Override
        public void execute(final String[] args) {
            System.out.println(args[0] + " " + args[1]);
            
            if((args[0].equalsIgnoreCase("h"))&&(args[1].equalsIgnoreCase("c"))){
                onEndScreen();
                System.out.print("HIDE ME!");
                for (String name: nifty.getAllScreensName()){
                    System.out.print(name);
                }
                
                nifty.gotoScreen("end");
            }
        }
    }
    
    public String getScreenNameStart(){
        return ScreenNameStart;        
    }
    
    public String getScreenNameEnd(){
        return ScreenNameEnd; 
    }
    
}
