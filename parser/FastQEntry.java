package parser;

public class FastQEntry 
{
	private FastQId id; 
	private String sequence, comment, quality;

	public FastQEntry( final String[] fastqEntry, final boolean isIlluminaFastq ) {
		setId( fastqEntry[ 0 ], isIlluminaFastq );
		
		setSequence( fastqEntry[ 1 ] );
		setComment( fastqEntry[ 2 ] );
		setQuality( fastqEntry[ 3 ] );
	}
	
	public String toString() {
		return getId() + "\n" + getSequence() + "\n" + getComment() + "\n" + getQuality();  
	}
	
	public FastQId getId() {
		return id;
	}

	public void setId( final String id, final boolean isIlluminaFastq ) {
		if( isIlluminaFastq ) 
		{
			this.id = new FastQIdIllumina( id ); 
		}
		else 
		{
			this.id = new FastQIdGeneric( id );
		}
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	} 
	
	
}
