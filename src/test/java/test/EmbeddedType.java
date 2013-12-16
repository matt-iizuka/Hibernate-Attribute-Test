/*
 * $HeadURL$
 * 
 * (c) 2013 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package test;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * 
 * 
 * @author Matt Todd
 * @version $Id$
 */
@Embeddable
public class EmbeddedType
{
	@ManyToOne(cascade = CascadeType.ALL)
	private ManyToOneType manyToOneType;
	
	public ManyToOneType getManyToOneType()
	{
		return manyToOneType;
	}
	
	public void setManyToOneType(ManyToOneType manyToOneType)
	{
		this.manyToOneType = manyToOneType;
	}
}
