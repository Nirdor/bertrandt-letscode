# bertrandt-letscode

## Java Implementierung

Die Java Implementierung besteht aus 4 Klassen. Dem Objektorietntierten Java Ansatz folgend, dass alles als Klasse implementiert wird.
Die Hauptklasse LetsCode enthält dabei die Main Funktion und die Implementierung des Dijkstra-Algorithmus.
Zusätzlich gibt es die Klassen Graph, Planet und Route, welche die Datenstruktur definieren. Diese sind so anottiert, dass mit Hilfe von Gson eine direkte Deserialisierung aus der JSON Datei möglich ist.

### Abhängigkeiten

Die Java Implmentierung nutzt [Googles Gson](https://github.com/google/gson) für die JSON Deserialisierung.

