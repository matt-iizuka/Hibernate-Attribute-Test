package test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EntityTest
{
	private EntityManager entityManager;
	
	private EntityTransaction entityTransaction;
	
	private CriteriaBuilder builder;
	
	private CriteriaQuery<Tuple> criteriaQuery;
	
	private Root<Entity> root;
	
	private Join<Entity, EmbeddedType> join;
	
	@Before
	public void setup()
	{
		entityManager = Persistence.createEntityManagerFactory("unit1").createEntityManager();
		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		ManyToOneType manyToOneType = new ManyToOneType();
		manyToOneType.setValue("thevalue");
		
		EmbeddedType embeddedType = new EmbeddedType();
		embeddedType.setManyToOneType(manyToOneType);
		
		Entity entity = new Entity();
		entity.setEmbeddedType(embeddedType);
		
		entityManager.persist(entity);
		entityManager.flush();
		
		builder = entityManager.getCriteriaBuilder();
		
		criteriaQuery = builder.createTupleQuery();
		root = criteriaQuery.from(Entity.class);
		
		join = root.join("embeddedType", JoinType.LEFT);
	}
	
	@After
	public void after()
	{
		if (entityManager != null)
		{
			EntityManagerFactory entityManagerFactory = entityManager.getEntityManagerFactory();
			entityTransaction.rollback();
			entityManager.close();
			entityManagerFactory.close();
			
			entityManagerFactory = null;
		}
	}
	
	@Test
	public void getResultWithStringPropertyDerivedPath()
	{
		// left join to the manyToOne on the embeddable with a string property
		Path<?> path = join.join("manyToOneType", JoinType.LEFT).get("value");

		// select the path in the tuple
		criteriaQuery.select(builder.tuple(path));
		
		assertEquals("thevalue", getResult());
	}
	
	@Test
	public void getResultWithMetamodelDerivedPath()
	{
		// get the attribute from the embedded type metamodel
		SingularAttribute<EmbeddedType, ?> attr =
			entityManager.getMetamodel().managedType(EmbeddedType.class)
				.getDeclaredSingularAttribute("manyToOneType");
		
		// join using the metamodel attribute instance
		Path<?> path = join.join(attr, JoinType.LEFT).get("value");
		
		// select the value property
		criteriaQuery.select(builder.tuple(path));
		
		assertEquals("thevalue", getResult());
	}
	
	private String getResult()
	{
		List<Tuple> results = entityManager.createQuery(criteriaQuery).getResultList();
		
		return (String) results.iterator().next().get(0);
	}
}
