import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.OEdge;
import com.orientechnologies.orient.core.record.OVertex;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultSet;

import java.util.Scanner;


public class FireBrigadeOrientDB {

    public static void main(String[] args) {

        OrientDB orient = new OrientDB("remote:localhost", OrientDBConfig.defaultConfig());
        ODatabaseSession db = orient.open("fire", "root", "admin");

        createSchema(db);

		do {
        Scanner scanner = new Scanner(System.in);
        System.out.println("");
        System.out.println("<--------------STRAŻ POŻARNA-------------->");
        System.out.println("-- 1. Dodaj straż");
        System.out.println("-- 2. Wyświetl wszystkich strażaków");
        System.out.println("-- 3. Wyświetl strażaków po id");
        System.out.println("-- 4. Wyświetl strażaków w przedziale wiekowym");
        System.out.println("-- 5. Usuń strażaka po id");
        System.out.println("-- 6. Zwiększ pensję");
        System.out.println("-- 7. Aktualizuj");
        System.out.println("-- 8. Zakończ");


        System.out.println("Podaj numer operacji do wykonania: ");
        String option = scanner.nextLine();
        switch (option) {
            case "1":
                createFireBrigades(db);
                break;

            case "2":
                readAll(db);
                break;

            case "3":
                System.out.println("Podaj id strażaka, którego chcesz wyświetlić");
                String rid = scanner.nextLine();
                readId(db, rid);
                break;

            case "4":
                System.out.println("Podaj minimalny wiek:");
                String ageF = scanner.nextLine();
                System.out.println("Podaj maksymalny wiek");
                String ageT = scanner.nextLine();
                readAge(db, ageF, ageT);
                break;

            case "5":
                System.out.println("Podaj id strażaka, którego chcesz usunąć:");
                String  id = scanner.nextLine();
                delete(db, id);
                break;

            case "6":
                System.out.println("Podaj wysokość podwyżki");
                int rise = scanner.nextInt();
                increaseSalary(db, rise);
                break;

            case "7":
                System.out.println("Podaj id strażaka do aktualizacji");
                String idN = scanner.nextLine();
                System.out.println("Podaj nowe imię i nazwisko");
                String newName = scanner.nextLine();
                update(db, newName, idN);

                break;

            case "8":
                db.close();
                orient.close();
                break;

            default:
                System.out.println("Błędna instrukcja");
        }
		} while (true);
    }


    private static void createSchema(ODatabaseSession db) {
        OClass fireBrigade = db.getClass("Firefighter");

        if (fireBrigade == null) {
            fireBrigade = db.createVertexClass("Firefighter");
        }

        if (fireBrigade.getProperty("name") == null) {
            fireBrigade.createProperty("name", OType.STRING);
            fireBrigade.createIndex("Firefighter_name_index", OClass.INDEX_TYPE.NOTUNIQUE, "name");
        }

        if (db.getClass("command") == null) {
            db.createEdgeClass("command");
        }

    }

    private static void createFireBrigades(ODatabaseSession db) {

        OVertex firefighter1 = createFirefighter(db, "Adam Nowak", "senior foreman", 60, 8000);
        OVertex firefighter2 = createFirefighter(db, "Piotr Kowalski", "junior foreman",55, 6800);
        OVertex firefighter3 = createFirefighter(db, "Mariusz Tomczyk", "junior foreman", 54, 6400);
        OVertex firefighter4 = createFirefighter(db, "Andrzej Badura", "aspirant",42, 4800);

        OEdge edge1 = firefighter1.addEdge(firefighter2, "command");
        edge1.save();
        OEdge edge2 = firefighter1.addEdge(firefighter3, "command");
        edge2.save();
        OEdge edge3 = firefighter2.addEdge(firefighter4, "command");
        edge3.save();
        OEdge edge4 = firefighter3.addEdge(firefighter4, "command");
        edge4.save();
    }

    private static OVertex createFirefighter(ODatabaseSession db, String name, String rank, int age, int salary) {
        OVertex result = db.newVertex("Firefighter");
        result.setProperty("name", name);
        result.setProperty("rank", rank);
        result.setProperty("age", age);
        result.setProperty("salary", salary);
        result.save();
        return result;
    }

    private static void readAll(ODatabaseSession db) {

        String query = "SELECT *from Firefighter";
        OResultSet rs = db.query(query);

        while (rs.hasNext()) {
            OResult item = rs.next();
            System.out.println(item);
        }
        rs.close();
    }
    private static void readId(ODatabaseSession db, String rid) {

        String query = "SELECT *from Firefighter where @rid = ?";
        OResultSet rs = db.query(query, rid);

        while (rs.hasNext()) {
            OResult item = rs.next();
            System.out.println(item);
        }
        rs.close();
    }

    private static void readAge(ODatabaseSession db, String ageF, String ageT) {

        String query = "SELECT *from Firefighter where age >= ? AND age<=?";
        OResultSet rs = db.query(query, ageF, ageT);

        while (rs.hasNext()) {
            OResult item = rs.next();
            System.out.println("firefighter: " + item);
        }
        rs.close();
    }

    private static void delete(ODatabaseSession db, String id) {

        db.command("DELETE VERTEX Firefighter WHERE @rid=?", id);
    }

    private static void increaseSalary(ODatabaseSession db, int rise) {

        db.command("UPDATE Firefighter SET salary = salary + ?", rise);

    }

    private static void update(ODatabaseSession db, String newName, String id) {

        db.command("UPDATE Firefighter SET name=? WHERE @rid=?", newName, id);

    }

}
