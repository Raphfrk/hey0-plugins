/**
* HeyRune.java - Extend this and register it to listen to specific hooks.
* @author raphfrk
*/
	
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
	
	
public class HeyRuneTiers {
	
	static public int[] tier = new int[512]; 
	
	public void HeyRuneTiers() {
	
		PropertiesFile pf = new PropertiesFile( "HeyRuneTiers.txt" );
		pf.load();
	
		// Probably should do better checking here
		if( !pf.keyExists("Tier_0") ) {
			pf.setString("Tier_0" , "0,1,2,3");
			pf.setString("Tier_1" , "4,5,12,13,17");
			pf.setString("Tier_2" , "20");
			pf.setString("Tier_3" , "15,45,82,73,74");
			pf.setString("Tier_4" , "7,14,42,48");
			pf.setString("Tier_5" , "41,49");
			pf.setString("Tier_6" , "57");
		}

		pf.save();
	
		int cnt;
	
		for(cnt=0;cnt<512;cnt++) {
			tier[cnt] = -1;
		}
	
		for(cnt=0;cnt<6;cnt++) {
			String key = "Tier_" + cnt;
			if(pf.keyExists(key)) {
				for( String id : pf.getString(key).split(",") ) {
					if( !id.equals("") ) {
						int idInt = Integer.parseInt(id);
						tier[idInt] = cnt;
					}
				}
			}
		}	
	
	}
	
}
	
	
			
	
