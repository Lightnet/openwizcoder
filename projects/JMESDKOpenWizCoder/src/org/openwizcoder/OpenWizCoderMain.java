package org.openwizcoder;

import org.openwizcoder.controllers.SMObjectPlayerController;
import org.openwizcoder.messages.ObjectShare;
import org.openwizcoder.messages.HelloMessage;
import org.openwizcoder.listeners.SpiderMonkeyClientListener;
import org.openwizcoder.listeners.SpiderMonkeyObjectClientListener;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.system.AppSettings;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openwizcoder.listeners.BaseServerConnectionListener;
import org.openwizcoder.listeners.SpiderMonkeyObjPlayerServerListener;
import org.openwizcoder.listeners.SpiderMonkeyServerListener;

import org.openwizcoder.ui.UIConsoleScreenController;
import org.openwizcoder.ui.UIModPackagesScreenController;

/*
 * Created By: Lightnet
 * 
 * three type build stand alone, client , and server
 * 
 */

public class OpenWizCoderMain extends SimpleApplication implements ScreenController {
      
    public List<SMObjectPlayerController> players = new ArrayList<SMObjectPlayerController>();
    public static final String NAME = "App Server";
    public static final int VERSION = 1;
    public static final int PORT = 5110;
    public static final int UDP_PORT = 5110;    
    public Client myClient;
    public Server myServer;
    public Nifty nifty;
    
    public static String NETWORKTYPE = "NONE";
    
    public String CurrentUserName = "Guest";
    public int ClientID = -1;
    
    public boolean btoggleconsole = false;
    
    @Override
    public void simpleInitApp() {        
        //java.awt.EventQueue.invokeLater(new Runnable() {
            //public void run() {
                //try {
                    //Init_network();
                //} catch (Exception e) {
                //}
            //}
        //});
        Init_nifty();
        // activate windowed input behaviour
        flyCam.setDragToRotate(false);
        mouseInput.setCursorVisible(true);
        this.pauseOnFocus = false;
        //this.flyCam.setDragToRotate(false);
        this.flyCam.setEnabled(false);
        //viewPort.setBackgroundColor(ColorRGBA.Blue);        
        
        inputManager.addMapping("Pause",  new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Left",   new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("Right",  new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("toggleconsole",  new KeyTrigger(KeyInput.KEY_T));
        
        inputManager.addListener(analogListener, new String[]{"Left",
                                                                "Right",
                                                                "Rotate",
                                                                "toggleconsole"}); 
    }
    
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float value, float tpf) {
            if (name.equals("toggleconsole")) {
                ToggleConsoleGUI();
            }

            if (name.equals("Rotate")) {
                System.out.print("\nrotate");
                //player.rotate(0, value*speed, 0);
            }
            if (name.equals("Right")) {
                System.out.print("\nright");
                //MoveObject(0.01f,0.0f,0.0f);
            }
            if (name.equals("Left")) {
                //MoveObject(-0.01f,0.0f,0.0f);
                System.out.print("\nleft");
            }
        }
    };
    
    //show console
    public void ToggleConsoleGUI(){
        if(nifty !=null){
                    for (String sname: nifty.getAllScreensName()){
                        System.out.print("\n Screen Name:"+sname);
                    }                
                }else{
            return;
        }
                
                System.out.print("\n...Console");
                if(btoggleconsole){
                   btoggleconsole = false;
                   System.out.print("\nHide Console");
                   UIConsoleScreenController screenControl = (UIConsoleScreenController) nifty.getScreen("startconsole").getScreenController();
                   if(screenControl == null){
                        System.out.print("\nERROR Console!");
                   }else{
                       System.out.print("\nH Console!");
                       nifty.gotoScreen(screenControl.getScreenNameEnd());
                   }
                }else{
                    System.out.print("Show Console");
                    UIConsoleScreenController screenControl = (UIConsoleScreenController) nifty.getScreen("startconsole").getScreenController();
                   if(screenControl == null){
                        System.out.print("\nERROR Console!");                          
                   }else{
                       System.out.print("\nS Console!");
                       nifty.gotoScreen(screenControl.getScreenNameStart());
                   }
                    btoggleconsole = true;                    
                }
        
    }
        
    public void Init_nifty(){
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                                                          inputManager,
                                                          audioRenderer,
                                                          guiViewPort);
        nifty = niftyDisplay.getNifty();
        
        nifty.fromXml("Interface/UI_MainMenu.xml", "start");

        //UIModPackagesScreenController modpack = new UIModPackagesScreenController();
        //nifty.fromXml("Interface/UI_ModPackages.xml", "start", modpack);
        
                
        //UIUserAgreementScreenController useragree = new UIUserAgreementScreenController();
        //nifty.fromXml("Interface/UI_UserAgreement.xml", "start", useragree);
        
        //UIServerNetworkScreenController servernetwork = new UIServerNetworkScreenController();
        //nifty.fromXml("Interface/UI_ServerNetwork.xml", "start", servernetwork);
                
        //UIClientNetworkScreenController clientnetwork = new UIClientNetworkScreenController();
        //nifty.fromXml("Interface/UI_ClientNetwork.xml", "start", clientnetwork);
                
        //UILoginScreenController clientlogin = new UILoginScreenController();
        //nifty.fromXml("Interface/UI_ClientLogin.xml", "start", clientlogin);
        
        //UIConsoleScreenController screenControl = new UIConsoleScreenController();
        //nifty.fromXml("Interface/UI_console.xml", "startconsole", screenControl);
        
        //nifty.fromXml("ui/UI_Client.xml", "start", this);                
        //UIClientScreenController screenControl = (UIClientScreenController) nifty.getScreen("start").getScreenController();
        
        
        // attach the nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay);        
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
    
    public void Init_Serializer(){
        Serializer.registerClass(HelloMessage.class);
        Serializer.registerClass(ObjectShare.class);        
    }
    
    public void Init_Client(){
        try {
            NETWORKTYPE = "CLIENT";
            myClient = Network.connectToServer(OpenWizCoderMain.NAME, OpenWizCoderMain.VERSION,
                "127.0.0.1", OpenWizCoderMain.PORT, OpenWizCoderMain.UDP_PORT);            
            myClient.start();
            //new ClientListener();
            SpiderMonkeyClientListener handler = new SpiderMonkeyClientListener();
            SpiderMonkeyObjectClientListener objplayerhandler = new SpiderMonkeyObjectClientListener();
            //objplayerhandler.SetApp(this);
            myClient.addMessageListener(handler, HelloMessage.class);
            myClient.addMessageListener(objplayerhandler, ObjectShare.class);
        } catch (Exception e) {
            
        }
    }
    
    public void ShutDown_Client(){
        if(myClient !=null){
            myClient.close();
        }
    }
    
    public void Init_Server(){
        Serializer.registerClass(HelloMessage.class);
        Serializer.registerClass(ObjectShare.class);
        try {
            NETWORKTYPE = "SERVER";
            Server myServer = Network.createServer(NAME, VERSION, PORT, UDP_PORT);                                    
            SpiderMonkeyObjPlayerServerListener objlistener = new SpiderMonkeyObjPlayerServerListener();
                        
            myServer.addMessageListener(new SpiderMonkeyServerListener(), HelloMessage.class);            
            myServer.addMessageListener(objlistener, ObjectShare.class);
            
            //this code deal with dis/connect from client
            BaseServerConnectionListener serverlistenconnection = new BaseServerConnectionListener();
            myServer.addConnectionListener(serverlistenconnection);
            
            myServer.start();                        
        } catch (Exception e) {
            
        }
    }
    
    public void ShutDown_Server(){
        if(myServer !=null){
            myServer.close();
        }
    }
    
    
    /*
    private class ListenHandler implements MessageListener<Client> {
        public void messageReceived(Client source, Message message) {
            if (message instanceof HelloMessage) {
                // do something with the message
                HelloMessage helloMessage = (HelloMessage) message;
                System.out.println("Client #"+source.getId()+" received: '"+helloMessage.getSomething()+"'");
            }
        }
    }
    */
    /*
    public void UserJoin(Client source,ObjectShare smobj){
        boolean bfound = false;
        for (SMObjectPlayerController player : players ){   
            if(source.getId() == Integer.parseInt( player.smobjshare.userid)){
                ObjectShare shareobject = (ObjectShare)smobj;
                player.smobjshare = shareobject;
                //player.getSpatial().setLocalTranslation(shareobject.x, shareobject.y, shareobject.z);                
                bfound = true;
            }    
        }
        
        if(!bfound){
            Box objplayer = new Box(Vector3f.ZERO, 1, 1, 1);
            Geometry geomplayer = new Geometry("Box", objplayer);
            Material mat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("m_Color", ColorRGBA.Brown);
            geomplayer.setMaterial(mat);        
            Spatial spl = (Spatial) geomplayer;
            
            SMObjectPlayerController newplayer = new SMObjectPlayerController(spl);
            //spl.addControl(newplayer);//need to be add in the object to able to update function
            //newplayer.userid = Integer.toString(source.getId());
            newplayer.smobjshare = smobj;
            newplayer.smobjshare.userid = Integer.toString(source.getId());
            
            source.send(newplayer.smobjshare);
            rootNode.attachChild(newplayer.getSpatial());
            players.add(newplayer);
        }        
    }
    */
    
    /*
    public void MoveObject(float x,float y,float z){
        if(user == null){
            //System.out.print("nulll...");
            return;
        }
        //System.out.print("\nposition");
        SMObjectPlayerController currentplayer = null;
        System.out.print("PLAYERS:" + players.size());
        for (SMObjectPlayerController player : players){
            //System.out.print("\n[player ID:"+player.shareobject.userid + " CURRENT ID:" +user.userid + "]\n");
            if(player.smobjshare.userid.equalsIgnoreCase(user.userid) == true){
                //System.out.print("FOUND PLAYER!");
                currentplayer = player;
                break;
            }           
        }
        
       if(currentplayer !=null){
            //System.out.print("\nmove position");
            currentplayer.smobjshare.x =  currentplayer.getSpatial().getLocalTranslation().x + x * speed;
            currentplayer.smobjshare.y =  currentplayer.getSpatial().getLocalTranslation().y + y * speed;
            currentplayer.smobjshare.z =  currentplayer.getSpatial().getLocalTranslation().z + z * speed;
            if(myClient !=null){
                myClient.send(currentplayer.smobjshare);
                System.out.print("sending...");
            }
        }
    }
    */

    public static void main(String[] args) {   
        Logger.getLogger("").setLevel(Level.SEVERE);
        //Logger.getLogger("de.lessvoid").setLevel(Level.SEVERE);
        //Logger.getLogger("de.lessvoid.*").setLevel(Level.SEVERE);
        //Logger.getLogger("com.jme3").setLevel(Level.SEVERE);
        OpenWizCoderMain app = new OpenWizCoderMain();      
        // create new JME appsettings
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Open Wiz Coder");
        settings.setWidth(640);
        settings.setHeight(480);
        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();  
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                System.out.print("Testing...");
            }
        });
    }
}