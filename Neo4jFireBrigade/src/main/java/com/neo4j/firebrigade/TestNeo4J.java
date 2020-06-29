package com.neo4j.firebrigade;

import java.util.Map;
import java.util.Scanner;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

public class TestNeo4J {

	public static void main(String[] args) {

		Configuration configuration = new Configuration.Builder().uri("bolt://localhost:7687").credentials("neo4j", "neo4jpassword").build();
		SessionFactory sessionFactory = new SessionFactory(configuration, "com.neo4j.firebrigade");

		Session session = sessionFactory.openSession();

		session.purgeDatabase();

		FirefighterService firefighterService = new FirefighterService(session);
		FireBrigadeService fireBrigadeService = new FireBrigadeService(session);
		fireBrigadeService.deleteAll();


		do {
			Scanner scanner = new Scanner(System.in);
			System.out.println("");
			System.out.println("<--------------STRAŻ POŻARNA-------------->");
			System.out.println("-- 1. Dodaj straż");
			System.out.println("-- 2. Wyświetl wszystkie jednostki");
			System.out.println("-- 3. Wyświetl po id");
			System.out.println("-- 4. Wyświetl strażaków z określonym wiekiem");
			System.out.println("-- 5. Usuń strażaka po id");
			System.out.println("-- 6. Zwiększ pensję");
			System.out.println("-- 7. Aktualizuj");
			System.out.println("-- 8. Zakończ");


			System.out.println("Podaj numer operacji do wykonania: ");
			String option = scanner.nextLine();
			switch (option) {
				case "1":
					Firefighter firefighter1 = new Firefighter("Adamos Nowak", 25, 3400);
					Firefighter firefighter2 = new Firefighter("Piotr Kowalski", 40, 6000);
					Firefighter firefighter3 = new Firefighter("Mariusz Tomczyk", 30, 4000);
					Firefighter firefighter4 = new Firefighter("Andrzej Badura", 33, 4200);
					Firefighter firefighter5 = new Firefighter("Jan Kozioł", 29, 1200);
					Firefighter firefighter6 = new Firefighter("Tadeusz Ocimek", 49, 2000);

					FireBrigade fireBrigade1 = new FireBrigade("KRSG", "Warszawa");
					FireBrigade fireBrigade2 = new FireBrigade("OSP", "Kraków");
					fireBrigade1.addFirefighter(firefighter1);
					fireBrigade1.addFirefighter(firefighter2);
					fireBrigade1.addFirefighter(firefighter3);
					fireBrigade1.addFirefighter(firefighter4);
					fireBrigade2.addFirefighter(firefighter5);
					fireBrigade2.addFirefighter(firefighter6);

					fireBrigadeService.createOrUpdate(fireBrigade1);
					fireBrigadeService.createOrUpdate(fireBrigade2);
				break;

				case "2":
				System.out.println("Jednostki straży pożarnej:");
					for(FireBrigade b : fireBrigadeService.readAll())
					System.out.println(b);
	
				for(Map<String, Object> map : fireBrigadeService.getFireBrigadeRelationships())
					System.out.println(map);
				break;

				case "3":
					System.out.println("Podaj id jednostki do wyświetlenia");
					Long ids =scanner.nextLong();
					System.out.println(fireBrigadeService.read(ids));
					break;

				case "4":

					System.out.println("Podaj minimalny wiek strażaka, którego chcesz wyświetlić");
					int age = scanner.nextInt();
					for(Firefighter firefighter : firefighterService.getFirefighters(age))
						System.out.println(firefighter);
					break;

				case "5":
					System.out.println("Podaj id strażaka, którego chcesz usunąć");
					Long idF =scanner.nextLong();
					scanner.nextLine();
					firefighterService.delete(idF);

					break;


				case "6":
					firefighterService.increaseSalary();
					break;


				case "7":
					System.out.println("Podaj id strażaka do aktualizacji");
					Long id =Long.valueOf(scanner.nextLine());
					System.out.println("Podaj nowe imie i nazwisko");
					String newName = scanner.nextLine();
					firefighterService.update(id, newName);
					break;

				case "8":

					sessionFactory.close();

					break;

				default:
					System.out.println("Błędna instrukcja");
			}
		} while (true);
	}
}
