package parser;

import java.io.File;
import java.io.FileNotFoundException;

public class FastQIdIllumina extends FastQId 
{
	private String name, instrument; 
	private int lane, tile, xPos, yPos, pair; 
	
	public FastQIdIllumina( final String idLine ) {
		parseIdLine( idLine );
	}
	
	public void parseIdLine( final String idLine ) 
	{
		String[] split = idLine.split(" ");
		
		parseName( split[ 0 ] ); 
		parseInfo( split[ 1 ] ); 
	}
	
	public void parseName( final String nameLine ) 
	{
		setName( nameLine.replace("@", "") ); 
	}
	
	public void parseInfo( final String infoLine )
	{
		final String[] split = infoLine.split(":"); 
		setInstrument( split[ 0 ] ); 
		setLane( Integer.parseInt( split[ 1 ] ) );
		setTile( Integer.parseInt( split[ 2 ] ) );
		setXPos( Integer.parseInt( split[ 3 ] ) );
		
		final String[] split2 = split[ 4 ].split("/");
		setYPos( Integer.parseInt( split2[ 0 ] ) );
		setPair( Integer.parseInt( split2[ 1 ] ) );
	}
	
	public String getName() { return this.name; }
	private void setName( final String name ) { this.name = name; }
	
	public String getInstrument() { return this.instrument; }
	private void setInstrument( final String instrument ) { this.instrument = instrument; }
	
	public int getLane() { return this.lane; }
	public void setLane( final int lane ) { this.lane = lane; }  
	
	public int getTile() { return this.tile; }
	public void setTile( final int tile ) { this.tile = tile; }  
	
	public int getXPos() { return this.xPos; }
	public void setXPos( final int xPos ) { this.xPos = xPos; }  
	
	public int getYPos() { return this.yPos; }
	public void setYPos( final int yPos ) { this.yPos = yPos; }  
	
	public int getPair() { return this.pair; }
	public void setPair( final int pair ) { this.pair = pair; }  
	
	
	@Override
	public String toString() {
		String out = "@" + getName() + " " + getInstrument() 
				+ ":" + getLane() + ":" + getTile() + ":" + getXPos() 
				+ ":" + getYPos() + "/" + getPair(); 
		// TODO Auto-generated method stub
		return out;
	}
	
	public static void main(String[] args) throws FileNotFoundException
	{
		final String input = "@SRR453566.5 HWI-ST167:4:1101:4770:1966/1";
		FastQId entry = new FastQIdIllumina( input );
		System.out.println( input ); 
		System.out.println( entry ); 
	}
}
