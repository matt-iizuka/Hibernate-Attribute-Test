/*
 * $HeadURL$
 * 
 * (c) 2013 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package test;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 
 * 
 * @author Matt Todd
 * @version $Id$
 */
@Entity
public class ManyToOneType
{
	@Id
	@GeneratedValue
	public Long id;

	private String value;

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}
