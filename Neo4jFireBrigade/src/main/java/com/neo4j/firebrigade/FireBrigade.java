package com.neo4j.firebrigade;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity(label = "FireBrigade")
public class FireBrigade {

	@Id
	@GeneratedValue
	private Long id;

	@Property(name = "name")
	private String name;
	private String city;

	public FireBrigade() {
	}

	public FireBrigade(String name, String city) {
		this.name = name;
		this.city = city;

	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCity() { return city;}


	@Relationship(type = "FireBrigades_Firefighter")
	private Set<Firefighter> firefighters = new HashSet<>();

	public void addFirefighter(Firefighter firefighter) {
		firefighters.add(firefighter);
	}

	@Override
	public String toString() {
		return "FireBrigade [id=" + id + ", name=" + name + ", city=" + city + ", firefighters=" +firefighters+"]";
	}
}
