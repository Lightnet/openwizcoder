package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.PlaneCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Plane;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication implements ActionListener/*, AnimEventListener*/{
    private BulletAppState bulletAppState;
    
    private CharacterControl character;
    private Node model;
    private ChaseCamera chaseCam;
    boolean left = false, right = false, up = false, down = false;
    Vector3f walkDirection = new Vector3f();
    
    public static void main(String[] args) {
        Main app = new Main();
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Stand Alone");
        settings.setWidth(640);
        settings.setHeight(480);
        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        //bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        stateManager.attach(bulletAppState);
        
        //PhysicsTestHelper.createPhysicsTestWorld(rootNode,
        //assetManager, bulletAppState.getPhysicsSpace());
        
        
        
        CapsuleCollisionShape capsule = new CapsuleCollisionShape(3f, 4f);
        character = new CharacterControl(capsule, 0.05f);
        character.setJumpSpeed(20f);
        model = (Node) assetManager.loadModel("Models/protypemech_basic.j3o");
        model.addControl(character);
        
        
        
        
        bulletAppState.getPhysicsSpace().add(character);
        rootNode.attachChild(model);
        
        //flyCam.setEnabled(false);
        chaseCam = new ChaseCamera(cam, model, inputManager);
        
        
        inputManager.addMapping("CharLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("CharRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("CharForward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("CharBackward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("CharJump", new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addMapping("CharAttack", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, "CharLeft", "CharRight");
        inputManager.addListener(this, "CharForward", "CharBackward");
        inputManager.addListener(this, "CharJump", "CharAttack");

        
        
        Box b = new Box(Vector3f.ZERO, 1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);
        
        rootNode.attachChild(geom);
        
        
        // the floor mesh, does not move (mass=0)
        Node node3 = PhysicsTestHelper.createPhysicsTestNode(assetManager, new PlaneCollisionShape(new Plane(new Vector3f(0, 1, 0), 0)), 0);
        node3.getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(0f, -6, 0f));
        rootNode.attachChild(node3);
        getPhysicsSpace().add(node3);
        
        Spatial gameLevel = assetManager.loadModel("Scenes/TestScene.j3o");
        gameLevel.setLocalTranslation(0.0f, 0.0f, 0.0f);
        //gameLevel.setLocalScale(2);
        rootNode.attachChild(gameLevel);
        setupKeys();
    }
    
    private void setupKeys() {
        inputManager.addMapping("wireframe", new KeyTrigger(KeyInput.KEY_T));
        inputManager.addListener(this, "wireframe");
        inputManager.addMapping("CharLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("CharRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("CharUp", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("CharDown", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("CharSpace", new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addMapping("CharShoot", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, "CharLeft");
        inputManager.addListener(this, "CharRight");
        inputManager.addListener(this, "CharUp");
        inputManager.addListener(this, "CharDown");
        inputManager.addListener(this, "CharSpace");
        inputManager.addListener(this, "CharShoot");
    }
    
    private PhysicsSpace getPhysicsSpace() {
        return bulletAppState.getPhysicsSpace();
    }
    
    @Override
    public void onAction(String binding, boolean value, float tpf) {
        if (binding.equals("CharLeft")) {
            if (value) {
                left = true;
            } else {
                left = false;
            }
        } else if (binding.equals("CharRight")) {
            if (value) {
                right = true;
            } else {
                right = false;
            }
        } else if (binding.equals("CharUp")) {
            if (value) {
                up = true;
            } else {
                up = false;
            }
        } else if (binding.equals("CharDown")) {
            if (value) {
                down = true;
            } else {
                down = false;
            }
        } else if (binding.equals("CharSpace")) {
            character.jump();
        } else if (binding.equals("CharShoot") && !value) {
            //bulletControl();
        }
    }

     @Override
    public void simpleUpdate(float tpf) {
        Vector3f camDir = cam.getDirection().clone().multLocal(0.1f);
        Vector3f camLeft = cam.getLeft().clone().multLocal(0.1f);
        camDir.y = 0;
        camLeft.y = 0;
        walkDirection.set(0, 0, 0);
        if (left) {
            walkDirection.addLocal(camLeft);
        }
        if (right) {
            walkDirection.addLocal(camLeft.negate());
        }
        if (up) {
            walkDirection.addLocal(camDir);
        }
        if (down) {
            walkDirection.addLocal(camDir.negate());
        }
        
        character.setWalkDirection(walkDirection);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
