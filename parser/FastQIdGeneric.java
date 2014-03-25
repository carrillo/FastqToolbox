package parser;

/**
 * Generic fastqId holder. Holding just the id as a string
 * @author carrillo
 *
 */
public class FastQIdGeneric extends FastQId 
{
	private String id; 
	
	public FastQIdGeneric( final String idLine ) {
		setId( idLine );
	}
	
	@Override
	public String toString() {
		return getId();
	}

	public String getId() { return this.id; } 
	private void setId( final String id ) { this.id = id; }  
}
