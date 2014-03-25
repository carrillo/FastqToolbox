package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.text.StyledEditorKit.BoldAction;
/**
  * This simple parser reads fastq files and returs FastqEntry Objects. 
  * @author carrillo
  *
  */
public class FastQParser implements Iterator<FastQEntry>
{
	private Scanner scanner; 
	private boolean isIlluminaFastq; 
	
	private FastQEntry next = null;  
	private boolean hasNext = false; 
	
	public FastQParser( final File input, final boolean isIlluminaFastq ) throws FileNotFoundException 
	{
		setScanner( input );
		setIsIlluminaFastq( isIlluminaFastq );
		
		parseNextEntry(); 
	}
	
	/*
	 * Puts the next FastqEntry in the next slot. 
	 * 1. Tries to get the next 4 lines. 
	 * 2. If succeeds, generate FastQEntry and put it into next slot
	 * 3. If not, make next slot null
	 */
	public void parseNextEntry() {
		 
		FastQEntry entry = null; 
		
		try 
		{
			final String[] fastqLine = new String[ 4 ];
			for( int i = 0; i < 4; i++ ) {
				fastqLine[ i ] = getScanner().next(); 
			}
			entry = new FastQEntry( fastqLine, isIlluminaRead() );
			setHasNext( true );
			
		} catch (NoSuchElementException e) {
			setHasNext( false );
		}
		setNext( entry );
	}
	
	@Override
	public void remove() { // TODO Auto-generated method stub
	}
	
	public Scanner getScanner() { return scanner; }
	private FastQEntry getNext() { return this.next; }
	private boolean isIlluminaRead() { return this.isIlluminaFastq; }
	@Override
	public boolean hasNext() { return this.hasNext; }
	@Override
	public FastQEntry next() {
		final FastQEntry out = getNext(); 
		parseNextEntry(); 
		
		return out;
	}
	
	private void setScanner( final File input ) throws FileNotFoundException 
	{
		Scanner scanner = new Scanner( input ); 
		scanner.useDelimiter("\n"); 
		this.scanner = scanner; 
	}
	private void setIsIlluminaFastq( final boolean isIlluminaFastq ) { this.isIlluminaFastq = isIlluminaFastq; } 
	private void setNext( final FastQEntry next ) { this.next = next; }
	private void setHasNext( final boolean hasNext ) { this.hasNext = hasNext; }

	public static void main(String[] args) throws FileNotFoundException
	{
		final File input = new File( "/Users/carrillo/Desktop/temp/SRR453566_1.fastq" );
		final boolean isIlluminaFastq = true; 
		
		final FastQParser parser = new FastQParser( input, isIlluminaFastq ); 
		while( parser.hasNext() ) {
			FastQEntry entry = parser.next(); 
			FastQIdIllumina id = (FastQIdIllumina) entry.getId(); 
			System.out.println( id.getName() ); 
		}

	}

}
