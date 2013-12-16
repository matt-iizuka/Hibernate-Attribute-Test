package test;

import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@javax.persistence.Entity
public class Entity
{
	@Id
	@GeneratedValue
	private Long id;

	@Embedded
	private EmbeddedType embeddedType;
	
	public Entity()
	{
		// for jpa
	}

	public EmbeddedType getEmbeddedType()
	{
		return embeddedType;
	}
	
	public void setEmbeddedType(EmbeddedType embeddedType)
	{
		this.embeddedType = embeddedType;
	}
}
