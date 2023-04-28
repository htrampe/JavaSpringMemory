package gymhum.memory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MemoryApplication {

	public static void main(String[] args)  {
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
	- Framework Material CSS https://materializecss.com/ einbinden inkl. Navigation (OK) 
	- Unterseiten erstellen Spieler anlegen/löschen (OK) und Partien löschen ()
	- Spielmodel implementieren ()
	- Partiemodel implementieren ()
	- Memoryansicht implementieren ()
		- Rasteransicht aufbauen ()
		- Klick-Events implementieren ()
		- Bilder/Zeichen raussuchen und einpflegen ()
		- Zufallsplatzierung ()
		- Allgemeinen Spielablauf vorbereiten ()
		- Spiellogik aufbauen ()
		- Gewinn/Verloren-Logik implementieren ()
	- DB anlegen und Tabellenstruktur aufbauen entsprechend der Models (OK)
		- Tabelle für Spieler (OK)
		- Tabelle für Partien (OK)
		- Fremdschlüssel Partie-Spieler ()
	- Spieler und Partie in DB speichern (Spieler OK, Partie OK)
	- Historienansicht aufbauen ()

	- Test und Bugfixing die ganze Zeit beibehalten

	API-KEY Bilddatenbank: O4wyLlJNjXgpFxcJPkqyGrToonSTkQFFvjRw4Nj4J3jgUhhoUxNhnJsu
 */