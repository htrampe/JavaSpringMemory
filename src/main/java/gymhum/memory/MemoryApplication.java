package gymhum.memory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MemoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemoryApplication.class, args);
	}
}

/*


	Projektplanung

	Ziele:
		1. Zwei Spieler spielen gegenseitig ein Memory-Spiel mit 16x16 Feldern.
		2. Die Spieler werden vorher ausgewählt und in einer Partie sortiert.
		3. Die Partien werden in einer SQL-Datenbank gespeichert.
		4. Jeder Spieler hat eine Statistik, bei der alle Spiele dargestellt werden und ersichtlich wird,
		   wie viel er gewonnen/verloren hat.

	Meileinsteine
	- Grundprojekt aufsetzen (OK)
	- Framework Material CSS https://materializecss.com/ einbinden inkl. Navigation für Memory und Historie sowie Spieler anlegen/löschen/verändern und Partien löschen ()
	- Spielmodel implementieren ()
	- Partiemodel implementieren ()
	- Memoryansicht implementieren ()
		- Bilder/Zeichen raussuchen und einpflegen ()
		- Zufallsplatzierung ()
	- Spiellogik aufbauen ()
	- Gewinn/Verloren-Logik implementieren ()
	- DB anlegen und Tabellenstruktur aufbauen entsprechend der Models ()
		- Tabelle für Spieler ()
		- Tabelle für Partien ()
		- Fremdschlüssel Partie-Spieler ()
	- Spieler und Partie in DB speichern ()
	- Historienansicht aufbauen ()

	- Test und Bugfixing die ganze Zeit beibehalten

 */