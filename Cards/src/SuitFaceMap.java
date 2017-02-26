import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import javax.imageio.ImageIO;

enum Suit {Hearts, Spades, Diamonds, Clubs}
enum Face { Ace , Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King}


class SuitFace {
	public Suit suit;
	public Face face;
	
	public SuitFace(Suit suit, Face face) {
		this.suit = suit;
		this.face = face;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof SuitFace))
			return false;
        SuitFace sf = (SuitFace) obj;
        return Objects.equals(suit, sf.suit) && Objects.equals(face, sf.face);
		 
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(suit, face);
    }
	

}

public class SuitFaceMap {
	private HashMap<SuitFace, BufferedImage> map;
	private String carddirname = "C:\\Users\\giang_000\\eclipse_workspace\\PlayingCardManager\\src\\Images";
	private double imagescalefactor = 0.3;
	
	public SuitFaceMap() {
		map = new HashMap<SuitFace, BufferedImage>();
		loadCardImages();
		for(SuitFace sf : map.keySet()) {
			System.out.println(sf.suit +" " +  sf.face);
		}
		
		
	}
	
	private void loadCardImages() {
		File dir = new File(carddirname);
		File[] files = dir.listFiles();
		for (File file : files) {
			try {
				BufferedImage img = ImageIO.read(file);
				putImage(parseSuit(file.getName()),img);
			}
			catch(IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void putImage(SuitFace sf, BufferedImage img) {
		map.put(sf, img);
	}
	
	public BufferedImage getImage(SuitFace sf) {
		return map.get(sf);
	}
	
	private SuitFace parseSuit(String s) {
		//Face 10, Hearts Suit
		String faceString;
		String suit;
		if (Character.isDigit(s.charAt(0))) {
			faceString = s.replaceAll("\\D+","");
			suit = s.replaceAll("[^a-zA-Z]+", "")
					.replaceAll("png", "");				
		} 
		
		else {
			String[] tokens;
			tokens = s.split("Of");
			tokens[1] = tokens[1].substring(0, tokens[1].indexOf('.'));
			//face 
			faceString = tokens[0];
			//suit
			suit = tokens[1];		
		}
		
		Face faceFace = 
				(faceString.equals("10")) ? Face.Ten :
				(faceString.equals("9")) ? Face.Nine :
				(faceString.equals("8")) ? Face.Eight :
				(faceString.equals("7")) ? Face.Seven :
				(faceString.equals("6")) ? Face.Six :
				(faceString.equals("5")) ? Face.Five :
				(faceString.equals("4")) ? Face.Four :
				(faceString.equals("3")) ? Face.Three :
				(faceString.equals("2")) ? Face.Two :
				(faceString.equals("King")) ? Face.King :
				(faceString.equals("Queen")) ? Face.Queen :
				(faceString.equals("Jack")) ? Face.Jack :							
					 Face.Ace;
		return new SuitFace(Suit.valueOf(suit), faceFace);
				
			
		
	}
	
	public BufferedImage scale(BufferedImage img, double factor){
		int width = (int) (img.getWidth() * factor);
		int height = (int) (img.getHeight() * factor);
		
		int type = 0;
		
		type = (img.getType() == 0) ? BufferedImage.TYPE_INT_ARGB : img.getType();
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(img, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}
}
