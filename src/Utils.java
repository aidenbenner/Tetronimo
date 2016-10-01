import java.awt.Color;
import java.util.Random;


public class Utils {

	public static Color getRandColor(){
		Random r = new Random();
		int re = r.nextInt(200) + 50; 
		int gr = r.nextInt(200) + 50;
		int bl = r.nextInt(200) + 50; 
		return new Color(re, gr, bl);
	}
}