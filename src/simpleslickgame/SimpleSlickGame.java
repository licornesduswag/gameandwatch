package simpleslickgame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import gameandwatch.*;

public class SimpleSlickGame extends BasicGame
{
	int time;
	Boolean test = false;
	Image game;
	Keyboard key;
	GameAndWatch gaw = new GameAndWatch();
	ImageStore is = new ImageStore();
	public SimpleSlickGame(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		gc.setShowFPS(false);
		game = new Image("resources/octopus.gif");
		key = new Keyboard(gc);
		try {
			gaw = GameAndWatch.fromZip("resources/game.zip", is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Test
		/*File file = new File("resources/sprite.png");
		try {
			FileInputStream fis = new FileInputStream(file);
			is.addImage("sprite", fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<SpritePoint> arr = new ArrayList<SpritePoint>();
		arr.add(new SpritePoint(new Sprite("sprite"), new Point(20, 350)));
		arr.add(new SpritePoint(new Sprite("sprite"), new Point(70, 350)));
		arr.add(new SpritePoint(new Sprite("sprite"), new Point(150, 350)));
		arr.add(new SpritePoint(new Sprite("sprite"), new Point(220, 350)));
		Trajectoire traj = new Trajectoire(arr);
		gaw.setPlayer(new Player());
		gaw.getPlayer().setTrajectoire(traj);*/
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		time += i;
		if(key.keyDown() == 1 && !test){
			gaw.getPlayer().nextPosition();
		}
		else if(key.keyDown() == 2 && !test){
			gaw.getPlayer().prevPosition();
		}
		test = !key.keyUp();
		if(time >= gaw.getGenerateur().getPeriode()){
			gaw.getGenerateur().incrementer();
			time = 0;
			for(Item item : gaw.getItems()){
				item.nextPosition();
			}
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		game.draw(0,0,2);
		SpritePoint sp = gaw.getPlayer().getTrajectoire().getPoints().get(gaw.getPlayer().getPosition());
		Image img = is.getImage(sp.getSprite());
		img.draw(sp.getPoint().getX(), sp.getPoint().getY());
		for(Item item : gaw.getItems()){
			SpritePoint sp2 = item.getTrajectoire().getPoints().get(item.getPosition());
			img = is.getImage(sp2.getSprite());
			img.draw(sp2.getPoint().getX(), sp2.getPoint().getY());			
		}
	}

	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new SimpleSlickGame("Game and Watch Emu"));
			appgc.setDisplayMode(600, 400, false);			
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(SimpleSlickGame.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}