/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openwizcoder.ui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.textfield.TextFieldControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.KeyInputHandler;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import org.openwizcoder.controllers.ChatAreaController;

public class ChatController implements ScreenController {
	private Element textInput;
	private ChatAreaController chatArea;

	@Override
	public void bind(Nifty nifty, Screen screen) {
		textInput = screen.findElementByName("text_input");
		chatArea = screen.findControl("chat_area", ChatAreaController.class);
	}

	@Override
	public void onStartScreen() {
		textInput.addInputHandler(new KeyInputHandler() {
			@Override
			public boolean keyEvent(NiftyInputEvent inputEvent) {
				if(inputEvent == null) return false;
				switch(inputEvent) {
				case SubmitText:
					sendMessage();
					return true;
				}
				return false;
			}
		});
		textInput.setFocus();
	}

	@Override
	public void onEndScreen() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Sends a message to the chat area
	 */
	public void sendMessage() {
		if(chatArea.getText().isEmpty()) {
			chatArea.append(textInput.getControl(TextFieldControl.class).getText());
		}
		else {
			chatArea.append("\n"+textInput.getControl(TextFieldControl.class).getText());
		}
		textInput.getControl(TextFieldControl.class).setText("");
		textInput.setFocus();
	}
}