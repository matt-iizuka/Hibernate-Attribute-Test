package test;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.PluralAttribute;

import org.hibernate.jpa.criteria.path.PluralAttributePath;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EntityTest
{
	private EntityManager entityManager;
	
	private Metamodel metamodel;
	
	@Before
	public void setup()
	{
		entityManager = Persistence.createEntityManagerFactory("unit1").createEntityManager();
		
		metamodel = entityManager.getMetamodel();
	}
	
	@After
	public void after()
	{
		if (entityManager != null)
		{
			EntityManagerFactory entityManagerFactory = entityManager.getEntityManagerFactory();
			entityManager.close();
			entityManagerFactory.close();
			
			entityManagerFactory = null;
			metamodel = null;
		}
	}
	
	/**
	 * Tests that the children element collection is a plural attribute and contains Entity elements. It uses
	 * the metamodel to get this information.
	 * 
	 */
	@Test
	public void getChildrenElementFromMetamodel()
	{
		PluralAttribute<?, ?, ?> pluralAttribute =
			(PluralAttribute<?, ?, ?>) metamodel.entity(Entity.class).getAttribute("children");
		
		assertEquals(Entity.class, pluralAttribute.getElementType()
			.getJavaType());
	}
	
	/**
	 * Tests that the children element collection is a plural attribute and contains Entity elements. This information
	 * should be able to be derived from the model behind the path. The test throws a null pointer as the model returned
	 * from this method is null.
	 * 
	 * @see PluralAttributePath
	 * 
	 */
	@Test
	public void getChildrenElementFromQueryRoot()
	{
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<?> criteriaQuery = builder.createQuery();
		Root<Entity> root = criteriaQuery.from(Entity.class);
		
		Path<?> pluralAttributePath = root.join("children", JoinType.LEFT);
		
		// this returns null, but it should return the correct plural attribute as in the above test
		PluralAttribute<?, ?, ?> pluralAttribute = (PluralAttribute<?, ?, ?>) pluralAttributePath.getModel();
		
		// getModel is implemented here by org.hibernate.jpa.criteria.path.PluralAttributePath.getModel, which always
		// returns null
		
		// throws a null pointer exception as the plural attribute is null
		assertEquals(Entity.class, pluralAttribute.getElementType().getJavaType());
	}
}
