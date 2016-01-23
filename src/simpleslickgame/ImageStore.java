package simpleslickgame;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import gameandwatch.*;	

public class ImageStore implements ImageStoreInterface {
	private HashMap<String, Image> images = new HashMap<String, Image>();
	
	public void addImage(String nom, InputStream is){
		try {
			images.put(nom, new Image(is, nom, false));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public Image getImage(Sprite sprite){
		return(images.get(sprite.getNom()));
	}
	
	public void printAll(){
		for(String key : images.keySet()){
			System.out.println(key);
		}
	}
}
