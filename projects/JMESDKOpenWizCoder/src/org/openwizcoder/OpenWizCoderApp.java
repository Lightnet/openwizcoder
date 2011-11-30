package org.openwizcoder;

import org.openwizcoder.controllers.ObjectPlayerController;
import org.openwizcoder.messages.ObjectShareMsg;
import org.openwizcoder.messages.HelloMsg;
import org.openwizcoder.listeners.ObjectShareClientListener;
import org.openwizcoder.listeners.HelloMsgClientListener;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.ArrayList;
import java.util.List;
import org.openwizcoder.controllers.BaseController;
import org.openwizcoder.listeners.BaseClientListener;
import org.openwizcoder.listeners.BaseServerConnectionListener;
import org.openwizcoder.listeners.BaseServerListener;
import org.openwizcoder.listeners.CharInputServerListener;
import org.openwizcoder.listeners.ObjectShareServerListener;
import org.openwizcoder.listeners.HelloMsgServerListener;
import org.openwizcoder.listeners.UserRequesttServerListener;
import org.openwizcoder.messages.CharInputMsg;
import org.openwizcoder.messages.RequestSpawnMsg;
import org.openwizcoder.ui.UIBasicScreenController;
import org.openwizcoder.ui.UIConsoleScreenController;

/*
 * Created By: Lightnet
 * 
 * three type build stand alone, client , and server
 * 
 */

public class OpenWizCoderApp extends SimpleApplication implements ScreenController,ActionListener {
      
    public List<ObjectPlayerController> players = new ArrayList<ObjectPlayerController>();
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
    
    private BulletAppState bulletAppState;
    private Node gameLevel;
    private CharacterControl character;
    private Node model;
    
    public boolean btoggleconsole = false;
    private ChaseCamera chaseCam;
    private boolean left = false, right = false, up = false, down = false;
    public CharInputMsg charinput = new CharInputMsg();
    
    Material floor_mat;
    private Box    floor;
    private RigidBodyControl    floor_phy;
    public float timekey = 0;
    public float timekeynext = 10;
        
    @Override
    public void simpleInitApp() {
        Init_Serializer();
        UIBasicScreenController.SetApp(this);
        BaseClientListener.setApp(this);
        BaseServerListener.setApp(this);
        BaseServerConnectionListener.setApp(this);
        BaseController.setApp(this);
        
        Init_nifty();
        // activate windowed input behaviour
        flyCam.setDragToRotate(false);
        mouseInput.setCursorVisible(true);
        this.pauseOnFocus = false;
        //this.flyCam.setDragToRotate(false);
        this.flyCam.setEnabled(false);
        //viewPort.setBackgroundColor(ColorRGBA.Blue);
        
        setDisplayStatView(false);
        
        Init_KeysSetups();
        Init_Bullet();
        initFloor();
        //Init_Map();
        Init_chars();
        
    }
    
    public void Init_Bullet(){
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);        
    }
    
    @Override
    public void onAction(String binding, boolean value, float tpf) {
        //System.out.print("\naction");
        if (binding.equals("CharLeft")) {
            if (value) left = true;
            else left = false;
        } else if (binding.equals("CharRight")) {
            if (value) right = true;
            else right = false;
        } else if (binding.equals("CharForward")) {
            if (value) up = true;
            else up = false;
        } else if (binding.equals("CharBackward")) {
            if (value) down = true;
            else down = false;
        } else if (binding.equals("CharJump")){
            if(character !=null){
                character.jump();
                //System.out.append("JUMP");
            }
            if (binding.equals("CharAttack")){
            //attack();
            }
        }
        
        if((charinput.up != up)|| (charinput.down != down) || (charinput.right != right)|| (charinput.left != left)  ){
            charinput.up = up;
            charinput.down = down;
            charinput.right = right;
            charinput.left = left;
            //System.out.print("\nupdate move");
            
            if(myClient != null){
                if(myClient.isConnected()){
                    myClient.send(charinput);
                }
            }            
        }
    }

    private Vector3f walkDirection = new Vector3f(0,0,0);
    private float airTime = 0;
    
    public void simpleUpdate(float tpf) {
    
        Vector3f camDir = cam.getDirection().clone().multLocal(0.25f);
        Vector3f camLeft = cam.getLeft().clone().multLocal(0.25f);
        camDir.y = 0;
        camLeft.y = 0;
        walkDirection.set(0, 0, 0);
        if (left)  walkDirection.addLocal(camLeft);
        if (right) walkDirection.addLocal(camLeft.negate());
        if (up) walkDirection.addLocal(camDir);
        if (down) walkDirection.addLocal(camDir.negate());
        if(character !=null){
            if (!character.onGround()) {
                airTime = airTime + tpf;
            } else {
                airTime = 0;
            }
        }
        if(character !=null){            
            character.setWalkDirection(walkDirection);
            //System.out.print("\nwalking...");
        }
    }
    
    public void Init_Map(){        
        Spatial scene = assetManager.loadModel("Scenes/TestScene.j3o");
        rootNode.attachChild(scene);
         
    } 
    
    /** Make a solid floor and add it to the scene. */
    public void initFloor() {
        floor_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");  
      
        /** Initialize the floor geometry */
        floor = new Box(Vector3f.ZERO, 20f, 0.1f, 20f);
        floor.scaleTextureCoordinates(new Vector2f(3, 6));  
      
        Geometry floor_geo = new Geometry("Floor", floor);
        floor_geo.setMaterial(floor_mat);
        floor_geo.setLocalTranslation(0, -3.1f, 0);
        rootNode.attachChild(floor_geo);
        /* Make the floor physical with mass 0.0f! */
        floor_phy = new RigidBodyControl(0.0f);
        floor_geo.addControl(floor_phy);
        bulletAppState.getPhysicsSpace().add(floor_phy);
    }
        
    public void Init_chars(){
        
        Box b = new Box(Vector3f.ZERO, 0.1f, 0.1f, 0.0f); // create cube shape at the origin
        Geometry geom = new Geometry("Box", b);  // create cube geometry from the shape
        Material mat = new Material(assetManager,
          "Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
        mat.setColor("Color", ColorRGBA.Blue);   // set color of material to blue
        geom.setMaterial(mat);                   // set the cube's material
        rootNode.attachChild(geom);              // make the cube appear in the scene
        

        //blocks = assetManager.loadModel("Models/block.j3o");
        //blocks.setLocalTranslation(0, 1.0f, 3.0f);
        //rootNode.attachChild(blocks);
        
        /*
        //CapsuleCollisionShape capsule = new CapsuleCollisionShape(3f, 4f);
        CapsuleCollisionShape capsule = new CapsuleCollisionShape(1.0f,2.0f);
        character = new CharacterControl(capsule, 0.05f);
        
        model = (Node) assetManager.loadModel("Models/protypemech_basic.j3o");        
        model.addControl(character);
        rootNode.attachChild(model);
        CharacterControl charcontrol =  model.getControl(CharacterControl.class);
        bulletAppState.getPhysicsSpace().add(character);
        //bulletAppState.getPhysicsSpace().add(charcontrol);
        
        
        // Enable a chase cam for this target (typically the player).
        flyCam.setEnabled(false);
        chaseCam = new ChaseCamera(cam, model, inputManager);
        */
        chaseCam = new ChaseCamera(cam, geom, inputManager);
        
        Spatial blocks = assetManager.loadModel("Models/block.j3o");
        
        
        CollisionShape sceneShape = CollisionShapeFactory.createMeshShape(blocks);
        RigidBodyControl cuberig = new RigidBodyControl(sceneShape, 0);        
        //blocks.addControl(cuberig);
        //bulletAppState.getPhysicsSpace().add(cuberig);
        
        //RigidBodyControl cuberig = new RigidBodyControl(0.0f);
        //blocks.addControl(cuberig);
        rootNode.attachChild(blocks);
        blocks.setLocalTranslation(0, 0, 4.0f);
        bulletAppState.getPhysicsSpace().add(cuberig);
        cuberig.setPhysicsLocation(blocks.getWorldTranslation());
        
        
        /*
         Box objplayer = new Box(Vector3f.ZERO, 1, 1, 1);
                Geometry geomplayer = new Geometry("Box", objplayer);
                Material mat2 = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
                mat2.setColor("m_Color", ColorRGBA.Brown);
                geomplayer.setMaterial(mat2);        
                Spatial spl = (Spatial) geomplayer;

                SMObjectPlayerController newplayer = new SMObjectPlayerController(spl);
                //spl.addControl(newplayer);//need to be add in the object to able to update function
                //newplayer.userid = Integer.toString(source.getId());
               // newplayer.smobjshare = (ObjectShareMsg)message;
                //newplayer.smobjshare.userid = Integer.toString(source.getId());

                //source.send(newplayer.smobjshare);
                rootNode.attachChild(newplayer.getSpatial());
                players.add(newplayer);
                */
  
    }

    public void Init_KeysSetups(){
        inputManager.addMapping("Pause",  new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Left",   new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("Right",  new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("toggleconsole",  new KeyTrigger(KeyInput.KEY_GRAVE));
        inputManager.addMapping("togglemenu",  new KeyTrigger(KeyInput.KEY_Y));
        
        inputManager.addListener(analogListener, new String[]{"Left",
                                                                "Right",
                                                                "Rotate",
                                                                "toggleconsole",
                                                                "togglemenu"}); 
                
        inputManager.addMapping("CharLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("CharRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("CharForward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("CharBackward", new KeyTrigger(KeyInput.KEY_S));
        //inputManager.addMapping("CharJump", new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addMapping("CharJump", new KeyTrigger(KeyInput.KEY_SPACE));
        //inputManager.addMapping("CharAttack", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, "CharLeft", "CharRight");
        inputManager.addListener(this, "CharForward", "CharBackward");
        inputManager.addListener(this, "CharJump", "CharAttack");
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
                MoveObject(0.01f,0.0f,0.0f);
            }
            if (name.equals("Left")) {
                MoveObject(-0.01f,0.0f,0.0f);
                System.out.print("\nleft");
            }
            
            if (name.equals("togglemenu")) {
                //MoveObject(-0.01f,0.0f,0.0f);
                //System.out.print("\nleft");
                nifty.gotoScreen("end");
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
        
        nifty.fromXml("Interface/UI_GameMenus.xml", "start");
        //nifty.fromXml("Interface/UI_GameMenus.xml", "NetworkBuildMenu");
        //nifty.fromXml("Interface/UI_GameMenus.xml", "HUDPlayer");
        //nifty.fromXml("Interface/UI_GameMenus.xml", "ClientMenuBuild");
        
        //nifty.gotoScreen("end");

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
        Serializer.registerClass(HelloMsg.class);
        Serializer.registerClass(ObjectShareMsg.class);
        Serializer.registerClass(CharInputMsg.class);
        Serializer.registerClass(RequestSpawnMsg.class);
        
    }
    
    public void Init_Client(){
        try {
            NETWORKTYPE = "CLIENT";
            myClient = Network.connectToServer(OpenWizCoderApp.NAME, OpenWizCoderApp.VERSION,
                "127.0.0.1" ,OpenWizCoderApp.PORT, OpenWizCoderApp.UDP_PORT);            
            myClient.start();
            //new ClientListener();
            ObjectShareClientListener handler = new ObjectShareClientListener();
            HelloMsgClientListener objplayerhandler = new HelloMsgClientListener();
            //objplayerhandler.SetApp(this);
            myClient.addMessageListener(handler, HelloMsg.class);
            myClient.addMessageListener(objplayerhandler, ObjectShareMsg.class);
        } catch (Exception e) {
            
        }
    }
        
    public void Init_ClientConfig(String hostname, String hostip,int tcpport, int udpport){
        try {
            NETWORKTYPE = "CLIENT";
            myClient = Network.connectToServer(hostname, OpenWizCoderApp.VERSION,
                hostip ,tcpport, udpport);           
            myClient.start();

            myClient.addMessageListener(new HelloMsgClientListener(), HelloMsg.class);
            myClient.addMessageListener(new ObjectShareClientListener(), ObjectShareMsg.class);
            System.out.print("client pass");
            //nifty.gotoScreen("end");
            nifty.gotoScreen("ClientMenuBuild");
        } catch (Exception e) {
            System.out.print("client fail");
        }
    }
    
    public void ShutDown_Client(){
        if(myClient !=null){
            myClient.close();
        }
    }
    
    public void Init_Server(){
        Serializer.registerClass(HelloMsg.class);
        Serializer.registerClass(ObjectShareMsg.class);
        try {
            NETWORKTYPE = "SERVER";
            myServer = Network.createServer(NAME, VERSION, PORT, UDP_PORT);                                    
                        
            myServer.addMessageListener(new HelloMsgServerListener(), HelloMsg.class);            
            myServer.addMessageListener(new ObjectShareServerListener(), ObjectShareMsg.class);
            
            //dis/connect from client 
            myServer.addConnectionListener(new BaseServerConnectionListener());
            
            myServer.start();                        
        } catch (Exception e) {
            
        }
    }
    
    public void Init_ServerConfig(String hostname,String hostip,int tcpport, int udpport){
        Serializer.registerClass(HelloMsg.class);
        Serializer.registerClass(ObjectShareMsg.class);
        try {
            NETWORKTYPE = "SERVER";
            myServer = Network.createServer(hostname, VERSION, tcpport, udpport);
                        
            myServer.addMessageListener(new HelloMsgServerListener(), HelloMsg.class);            
            myServer.addMessageListener(new ObjectShareServerListener(), ObjectShareMsg.class);
            myServer.addMessageListener(new CharInputServerListener(), CharInputMsg.class);
            myServer.addMessageListener(new UserRequesttServerListener(), RequestSpawnMsg.class);
            
            //this code deal with dis/connect from client
            BaseServerConnectionListener serverlistenconnection = new BaseServerConnectionListener();
            myServer.addConnectionListener(serverlistenconnection);            
            myServer.start();
            System.out.print("\nhosting pass");
            //nifty.gotoScreen("end");
            nifty.gotoScreen("serveronlinemenu");
            
        } catch (Exception e) {
            System.out.print("\nhosting fail");
        }
    }

    public void ShutDown_Server(){
        if(myServer !=null){
            myServer.close();
        }
    }
    
    public void ServerJoinSO(HostedConnection conn,ObjectShareMsg smobj)
    {
        boolean bfound = false;
        for (ObjectPlayerController player : players ){   
            if(smobj.userid.equalsIgnoreCase(player.smobjshare.userid)){
                ObjectShareMsg shareobject = (ObjectShareMsg)smobj;
                player.smobjshare = shareobject;
                //player.getSpatial().setLocalTranslation(shareobject.x, shareobject.y, shareobject.z);                
                bfound = true;
                break;
            }    
        }
        
        if(!bfound){
            Box objplayer = new Box(Vector3f.ZERO, 1, 1, 1);
            Geometry geomplayer = new Geometry("Box", objplayer);
            Material mat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("m_Color", ColorRGBA.Brown);
            geomplayer.setMaterial(mat);        
            Spatial spl = (Spatial) geomplayer;
            
            ObjectPlayerController newplayer = new ObjectPlayerController(spl);
            //spl.addControl(newplayer);//need to be add in the object to able to update function
            //newplayer.userid = Integer.toString(source.getId());
            newplayer.smobjshare = smobj;
            rootNode.updateGeometricState();
            rootNode.attachChild(newplayer.getSpatial());
            rootNode.updateGeometricState();
            players.add(newplayer);
            //newplayer.smobjshare.userid = Integer.toString(source.getId());

            //source.send(newplayer.smobjshare);
            //conn.send(smobj);
            /*
            if(myServer !=null){
                System.out.print("\nCHECK var myserver shareobject");
                //myServer.broadcast(smobj);
                //conn.send(smobj);
            }else{
                System.out.print("\nerror var myserver shareobject");
            }
            */
        }
       // System.out.print("\nserver shareobject?");
    }
    
    public void UpdatePlayerObjectClient(Client source,Message message){
        System.out.print("client update shareobject");
        if (message instanceof ObjectShareMsg) {
            boolean bfound = false;

            for (ObjectPlayerController player : players ){   
                if(source.getId() == Integer.parseInt( player.smobjshare.userid)){
                    ObjectShareMsg shareobject = (ObjectShareMsg)message;
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

                ObjectPlayerController newplayer = new ObjectPlayerController(spl);
                //spl.addControl(newplayer);//need to be add in the object to able to update function
                //newplayer.userid = Integer.toString(source.getId());
                newplayer.smobjshare = (ObjectShareMsg)message;
                newplayer.smobjshare.userid = Integer.toString(source.getId());
                players.add(newplayer);
                rootNode.updateGeometricState();//make sure it get update else it cause an erro
                rootNode.attachChild(newplayer.getSpatial());
                rootNode.updateGeometricState();
                
            }        
        }
        //System.out.print("\nclient shareobject");
    }
    
     public void UpdatePlayerInputServer(HostedConnection source,Message message){
        
        boolean bfound = false;        
        CharInputMsg smobj = (CharInputMsg) message;
                
        if(smobj !=null){
            for (ObjectPlayerController player : players ){   
                if(source.getId() == Integer.parseInt( player.smobjshare.userid)){
                    CharInputMsg shareobject = (CharInputMsg)smobj;
                    player.charinput = shareobject;
                    player.charinput.userid = source.getId();
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

                ObjectPlayerController newplayer = new ObjectPlayerController(spl);
                //spl.addControl(newplayer);//need to be add in the object to able to update function
                //newplayer.userid = Integer.toString(source.getId());
                newplayer.charinput = smobj;
                newplayer.charinput.userid = source.getId();

                //source.send(newplayer.smobjshare);
                rootNode.updateGeometricState();
                rootNode.attachChild(newplayer.getSpatial());
                rootNode.updateGeometricState();
                players.add(newplayer);
            }
        }
    }
    
    public void UpdatePlayerObjectServer(HostedConnection source,Message message){
        
        boolean bfound = false;        
        ObjectShareMsg smobj = (ObjectShareMsg) message;
                
        if(smobj !=null){
            for (ObjectPlayerController player : players ){   
                if(source.getId() == Integer.parseInt( player.smobjshare.userid)){
                    ObjectShareMsg shareobject = (ObjectShareMsg)smobj;
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

                ObjectPlayerController newplayer = new ObjectPlayerController(spl);
                //spl.addControl(newplayer);//need to be add in the object to able to update function
                //newplayer.userid = Integer.toString(source.getId());
                newplayer.smobjshare = smobj;
                newplayer.smobjshare.userid = Integer.toString(source.getId());

                source.send(newplayer.smobjshare);
                rootNode.updateGeometricState();
                rootNode.attachChild(newplayer.getSpatial());
                rootNode.updateGeometricState();
                players.add(newplayer);
            }
        }
    }

    public void MoveObject(float x,float y,float z){
        if(myClient !=null){
            if(ClientID != myClient.getId()){
                return;
            }

            if( myClient.isConnected() == false){
                return;
            }

            //System.out.print("\nposition");
            ObjectPlayerController currentplayer = null;
            System.out.print("PLAYERS:" + players.size());
            for (ObjectPlayerController player : players){
                //System.out.print("\n[player ID:"+player.shareobject.userid + " CURRENT ID:" +user.userid + "]\n");
                if(player.smobjshare.userid.equalsIgnoreCase(Integer.toString(ClientID)) == true){
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
    }
    
}