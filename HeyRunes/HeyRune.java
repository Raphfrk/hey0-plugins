/**
* HeyRune.java - Extend this and register it to listen to specific hooks.
* @author chrisinajar
*/

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HeyRune
{
	private static Logger a = Logger.getLogger("Minecraft");
	public static enum Direction { NORTH, SOUTH, EAST, WEST, NONE }
	private ArrayList<Integer> pattern = new ArrayList<Integer>();
	public String name;
	public Direction direction;
	public boolean north = true;
	public boolean south = true;
	public boolean east = true;
	public boolean west = true;
	public int northTier = -1;
	public int southTier = -1;
        public int eastTier  = -1;
        public int westTier  = -1;
	
	public HeyRune(String in_name, int[][] in_pattern) {
		name = in_name;
		windPattern(in_pattern);
	}

	public HeyRune(String in_name, ArrayList<Integer> in_pattern)
	{
		name = in_name;
		pattern = in_pattern;
	}

	public String name()
	{
		return name;
	}

	public ArrayList<Integer> pattern()
	{
		return pattern;
	}
	
	public int size() {
		return pattern.size();
	}

	public int height() {
		return (int)Math.ceil(Math.sqrt(pattern.size()));
	}
	
	public int idAt(int index) {
		try {
			return pattern.get(index);
		} catch (IndexOutOfBoundsException ex) {
			return -1;
		}
	}
	
	private void windPattern(int[][] p) {
		double height = p.length;
		double width = 0;
		double size = height;
		for(int i = 0; i < p.length; ++i) {
			if (p[i].length > width)
				width = p[i].length;
		}
		int offset = -1;

		int curx = 0;
		int cury = 0;
		if (width > size) {
			size = width;
			offset = -2;
			if ((size%2) == 1) {
				offset = -3;
			}
			else if ((size%2) == 0) {
				offset = -4;
			}
		}
		else
		{
			if ((size%2) == 0) {
				offset = -4;
			}
		}
		
		cury += (height)/2;
		curx += (width)/2;
	
		int counter = 1;
		for (int i = 0; i < ((size*2) + offset); ++i) {
			for (int j = 0; j < ((i/2) + 1); ++j) {
				try {
					pattern.add(p[cury][curx]);
				} catch (Throwable t) {
					pattern.add(-1);
				}
				switch((i)%4) {
					case 1:
						cury++;
						break;
					case 0:
						curx++;
						break;
					case 3:
						cury--;
						break;
					case 2:
						curx--;
						break;
				}
			}
		}
	}
	
	public void reset() {
		north = true;
		south = true;
		east = true;
		west = true;

		northTier = -1;
		southTier = -1;
		eastTier = -1;
		westTier = -1;
	}
	
	public static HeyRune match(ArrayList<HeyRune> _r, int x, int y, int z) {
		ArrayList<HeyRune> runes = new ArrayList<HeyRune>(_r);
		HeyRune victor = null;
		Direction victorD = null;
		
		int size = 0;
		for (HeyRune r : runes) {
			size = (size < r.size() ? r.size() : size);
			r.reset();
		}
		
		int index = 0;
		int xoffset = 0;
		int yoffset = 0;
		int zoffset = 0;
		int blockId = 0;
		int blockTier = 0;
		for (int i = 0; index < size; ++i) {
			for (int j = 0; j < ((i/2) + 1); ++j) {
				try {
					Iterator<HeyRune> iter = runes.iterator();
					while (iter.hasNext()) {
						HeyRune rune = iter.next();
						if (!rune.north && !rune.south && !rune.east && !rune.west) {
							iter.remove();
							continue;
						}
						int myId = rune.idAt(index);
						if (myId > 0) {
							blockId =  etc.getServer().getBlockIdAt(xoffset + x, y,  zoffset + z);
	                                                blockTier = HeyRuneTiers.tier[myId];
							if(rune.north && myId != blockId) {
								if(myId != -2 || blockTier != 0) { // Ink not permitted or ink not found
									if(myId == -3 && rune.northTier == -1 && blockTier > 0) {// first tiered material
										rune.northTier = blockId;
									} else if ( myId != -3 || blockId != rune.northTier ) {
										rune.north = false;
									}
								}
							}
							blockId =  etc.getServer().getBlockIdAt(zoffset + x, y,  -xoffset + z);
	                                                blockTier = HeyRuneTiers.tier[myId];
							if(rune.east && myId != blockId) {
								if(myId != -2 || blockTier != 0) { // Ink not permitted or ink not found
									if(myId == -3 && rune.eastTier == -1 && blockTier > 0) {// first tiered material
										rune.eastTier = blockId;
									} else if ( myId != -3 || blockId != rune.eastTier ) {
										rune.east = false;
									}
								}
							}
							blockId =  etc.getServer().getBlockIdAt(-xoffset + x, y,  -zoffset + z);
	                                                blockTier = HeyRuneTiers.tier[myId];
							if(rune.south && myId != blockId) {
								if(myId != -2 || blockTier != 0) { // Ink not permitted or ink not found
									if(myId == -3 && rune.southTier == -1 && blockTier > 0) {// first tiered material
										rune.southTier = blockId;
									} else if ( myId != -3 || blockId != rune.southTier ) {
										rune.south = false;
									}
								}
							}
							blockId =  etc.getServer().getBlockIdAt(-zoffset + x, y,  xoffset + z);
	                                                blockTier = HeyRuneTiers.tier[myId];
							if(rune.west && myId != blockId) {
								if(myId != -2 || blockTier != 0) { // Ink not permitted or ink not found
									if(myId == -3 && rune.westTier == -1 && blockTier > 0) {// first tiered material
										rune.westTier = blockId;
									} else if ( myId != -3 || blockId != rune.westTier ) {
										rune.west = false;
									}
								}
							}
						}
						if (index + 1 == rune.size()) {
							if(rune.north) {
								victorD = Direction.NORTH;
								victor = rune;
							}
							else if(rune.south) {
								victorD = Direction.SOUTH;
								victor = rune;
							}
							else if(rune.east) {
								victorD = Direction.EAST;
								victor = rune;
							}
							else if(rune.west) {
								victorD = Direction.WEST;
								victor = rune;
							}
							iter.remove();
							continue;
						}
					}
				} catch (Throwable t) {
					a.log(Level.SEVERE, "Exception while iterating a rune " + t);
				}
				switch((i)%4) {
					case 1:
						zoffset++;
						break;
					case 0:
						xoffset++;
						break;
					case 3:
						zoffset--;
						break;
					case 2:
						xoffset--;
						break;
				}
				index++;
			}
		}
		return victor;
	}
}
