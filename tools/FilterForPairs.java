package tools;

import inputOutput.TextFileAccess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;

import parser.FastQIdIllumina;
import parser.FastQParser;
import parser.FastQEntry;

/**
 * Takes two fastq files and returns only those reads present in both files. 
 * 
 * To maximize memory efficiency it has to read both files twice. 
 * 1. It hashes all read ids present in both samples
 * 2. Filters both list based on present ids. 
 * @author carrillo
 *
 */

public class FilterForPairs 
{
	private File fastq1, fastq2; 
	private HashSet<String> nameIntersect; 
	
	
	public FilterForPairs( final File fastq1, final File fastq2 )
	{
		setFastq1( fastq1 );
		setFastq2( fastq2 ); 
	}
	
	/*
	 * 1. Hash the names of both input fastq files
	 * 2. Intersect both sets 
	 */
	public void read( final boolean isIlluminaFastq ) throws FileNotFoundException
	{
		HashSet<String> names1 = getNames(getFastq1(), isIlluminaFastq ); 
		HashSet<String> names2 = getNames(getFastq2(), isIlluminaFastq ); 
		
		setNameIntersect( getIntersect( names1, names2 ) ); 
	}
	
	/*
	 * Write files with matching ids. 
	 */
	public void write( final boolean isIlluminaFastq ) throws IOException 
	{
		writeMatchedEntries( getFastq1(), isIlluminaFastq );
		writeMatchedEntries( getFastq2(), isIlluminaFastq );
	}
	
	private void writeMatchedEntries( final File fastqIn, final boolean isIlluminaFastq ) throws IOException
	{
		PrintWriter out = TextFileAccess.openFileWrite( getOutputFile( fastqIn ) ); 
	
		FastQParser parser = new FastQParser( fastqIn, isIlluminaFastq );
		FastQEntry currentEntry = null; 
		FastQIdIllumina currentId = null; 
		while( parser.hasNext() )
		{
			currentEntry = parser.next(); 
			currentId = (FastQIdIllumina) currentEntry.getId(); 
			
			if( getNameIntersect().contains( currentId.getName() ) )
			{
				out.println( currentEntry ); 
			}
		}
		
		out.close();
	}
	
	
	private File getOutputFile( final File inFile ) throws IOException 
	{
		
		final String path = inFile.getAbsolutePath();
		final String stem = path.substring( 0,  path.lastIndexOf( "." ) );  
		
		File file = new File( stem + "_matched.fastq" ) ;
		file.createNewFile();
		
		return file; 
	}
	
	
	/*
	 * Get the intersect of two Hashsets.
	 * Loop through smaller set and lookup in larger one. 
	 * Return the intersect. 
	 */
	public HashSet<String> getIntersect( final HashSet<String> set1, final HashSet<String> set2 ) 
	{
		HashSet<String> a = null;
		HashSet<String> b = null; 
		
		if( set1.size() >= set2.size() ) 
		{
			a = set2;
			b = set1; 
		}
		else 
		{
			a = set1; 
			b = set2; 
		}
		
		HashSet<String> intersect = new HashSet<String>(); 
		for( String name : a ) 
		{
			if( b.contains( name ) )
			{
				intersect.add( name ); 
			}
		}
		
		return intersect; 
	}
	
	/**
	 * Get read name hash from fastq file 
	 * @param fastq input fastq 
	 * @return
	 * @throws FileNotFoundException 
	 */
	private HashSet<String> getNames( final File fastq, final boolean isIlluminaFastq ) throws FileNotFoundException  
	{
		HashSet<String> names = new HashSet<String>(); 
		
		FastQParser parser = new FastQParser( fastq, isIlluminaFastq );
		
		FastQEntry currentEntry = null;
		FastQIdIllumina id = null; 
		while( parser.hasNext() ) 
		{
			currentEntry = parser.next(); 
			id = (FastQIdIllumina) currentEntry.getId();
			names.add( id.getName() ); 
		}
		
		return names; 
	}
	
	private void setFastq1( final File fastq1 ) { this.fastq1 = fastq1; } 
	public File getFastq1() { return fastq1; }
	
	private void setFastq2( final File fastq2 ) { this.fastq2 = fastq2; } 
	public File getFastq2() { return fastq2; }
	
	private void setNameIntersect( final HashSet<String> intersect ) { this.nameIntersect = intersect; }
	public HashSet<String> getNameIntersect() { return this.nameIntersect; }
	
	public static void main(String[] args) throws IOException 
	{
		if( args.length != 2 )
		{
			final String info = "\n#######################\n" + 
					"java -jar filterForPairs /path/to/fastq1 /path/to/fastq2\n" +
					"#######################\n" + 
					"Filters both fastq files for reads with matching names in both files..\n" +
					"Specify the uncompressed input fastq files 1 and 2.\n" +
					"Outputs the filterd reads to filenames appended by matched.\n" +
					"\n\n"; 
			System.err.println( info ); 
		}
		else 
		{	
			final File fastq1 = new File( args[ 0 ] );
			final File fastq2 = new File( args[ 1 ] );
			

			final boolean isIlluminaFastq = true; 
			
			FilterForPairs filter = new FilterForPairs( fastq1, fastq2 ); 
			System.out.println( "Intersecting read names." ); 
			filter.read( isIlluminaFastq );
			System.out.println( "Found " + filter.getNameIntersect().size() + " matching read names." ); 
			System.out.println( "Write filtered fastq files." ); 
			filter.write( isIlluminaFastq ); 
		}
		
	}
		

}
