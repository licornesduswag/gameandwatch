package simpleslickgame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class Keyboard {
	Input input; 
	
	Keyboard(GameContainer container){
		input = container.getInput();
	}
	
	int keyDown(){
		if (input.isKeyDown(Input.KEY_UP))
		{
		    return 1;
		}
		else if (input.isKeyDown(Input.KEY_DOWN))
		{
		    return 2;
		}
		else if (input.isKeyDown(Input.KEY_LEFT))
		{
		    return 2;
		}
		else if (input.isKeyDown(Input.KEY_RIGHT))
		{
		    return 1;
		}
		else
		{
			return 0;
		}
	}
	
	boolean keyUp(){
		if (!input.isKeyDown(Input.KEY_UP) && !input.isKeyDown(Input.KEY_DOWN) && !input.isKeyDown(Input.KEY_LEFT) && !input.isKeyDown(Input.KEY_RIGHT))
		{
		    return true;
		}
		else 
			return false;
	}
}

