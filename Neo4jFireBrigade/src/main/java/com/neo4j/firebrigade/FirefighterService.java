package com.neo4j.firebrigade;

import org.neo4j.ogm.session.Session;

import java.util.HashMap;
import java.util.Map;

class FirefighterService extends GenericService<Firefighter> {

    public FirefighterService(Session session) {
		super(session);
	}
    
	@Override
	Class<Firefighter> getEntityType() {
		return Firefighter.class;
	}

	public void update(Long id, String newName) {
		String query = "MATCH (firefighter:Firefighter) " +
				"WHERE ID(firefighter) = $firefighterId " +
				"SET firefighter.name = $name";
		Map<String, Object> map = new HashMap<>();
		map.put("firefighterId", id);
		map.put("name", newName);
		session.queryForObject(Firefighter.class, query, map);
	}

	public void increaseSalary() {
		String query = "MATCH (firefighter:Firefighter) " +
				"SET firefighter.salary = (firefighter.salary + 500) ";
		session.query(query, new HashMap<>());
	}

	public Iterable<Firefighter> getFirefighters(int age) {
		String query = "MATCH (firefighter:Firefighter) " +
				"WHERE firefighter.age >= $firefighterAge " +
				"RETURN firefighter";
		Map<String, Object> map = new HashMap<>();
		map.put("firefighterAge", age);
		return session.query(Firefighter.class, query, map);
	}

}
