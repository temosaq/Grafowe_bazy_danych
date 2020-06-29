package com.neo4j.firebrigade;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.ogm.session.Session;

class FireBrigadeService extends GenericService<FireBrigade> {

    public FireBrigadeService(Session session) {
		super(session);
	}
    
	@Override
	Class<FireBrigade> getEntityType() {
		return FireBrigade.class;
	}
	
    Iterable<Map<String, Object>> getFireBrigadeRelationships() {
        String query = 
        		"MATCH (b:FireBrigade)-[r]-() " +
        		"WITH type(r) AS t, COUNT(r) AS c " +
        		"WHERE c >= 1 " +
        		"RETURN t, c";
        System.out.println("Executing " + query);
        HashMap<String, Object> params = new HashMap<String, Object>();
        return session.query(query, params).queryResults();
    }


}
