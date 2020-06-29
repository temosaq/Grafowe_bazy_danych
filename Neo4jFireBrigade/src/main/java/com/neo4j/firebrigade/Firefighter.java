package com.neo4j.firebrigade;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "Firefighter")
public class Firefighter {

	@Id
	@GeneratedValue
	private Long id;

	@Property(name = "name")
	private String name;
	private int age;
	private int salary;
	
	public Firefighter() {
	}

	public Firefighter(String name, int age, int salary) {
		this.name = name;
		this.age = age;
		this.salary = salary;
	}

	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public int getAge() { return age; }

	public int getSalary() { return salary; }

	@Override
	public String toString() {
		return "Firefighter [id=" + id + ", name=" + name + ", age" +age + ", salary:" + salary +"]";
	}
}
