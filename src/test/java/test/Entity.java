package test;

import java.util.Set;

import javax.persistence.Id;
import javax.persistence.OneToMany;

@javax.persistence.Entity
public class Entity
{
	@Id
	public Long id;

	@OneToMany
	private Set<Entity> children;
	
	public Entity()
	{
		// for jpa
	}
	
	public Set<Entity> getChildren()
	{
		return children;
	}
	
	public Long getId()
	{
		return id;
	}
}
