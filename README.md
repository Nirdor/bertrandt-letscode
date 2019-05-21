# bertrandt-letscode

## Challenge

Let's Code ist eine [Coding Competition](https://www.get-in-it.de/coding-challenge) von *get in {IT}* und *Bertrandt*.
Die Aufgabe besteht darin, in einem Graphen mit ungerichteten, gewichteten Kanten, den kürzesten Pfad von einem Start zu einem Zielknoten zu finden.
Das Problem soll dabei mit einer der Programmiersprachen, *C++*, *Java*, *JavaScript* oder *Python* gelöst werden.

### Input
Als Eingabe wird eine JSON Datei zur Verfügung gestellt. Die ein Array mit 1000 Knoten und ein Array mit 1500 Kanten enthält.
Außerdem erfährt man, dass der Name des Startknoten "Erde" und der Name des Zielknoten "b3-r7-r4nd7" ist. Sucht man diese Namen in der JSON Datei, erfährt man, dass damit die Knoten 18 und 246 gemeint sind. Dies ist wichtig, da die Kanten auf die Indizes und nicht die Namen der Knoten verweisen.

### Output
Als Lösung sollen die Abfolge der Knoten und die Gesamtkosten des kürzesten Pfades übergeben werden.

## Vorgehensweise
Um das Problem zu Lösen, muss zuerst die JSON Datei in eine Datenstruktur eingelesen werden. Diese sollte es ermöglichen von einem Knoten effizient(O(1)) alle Nachbarn, sowie die Kantengewichte zu diesen zu bekommen.
in dieser Datenstruktur, kann dann mit dem [Dijkstra-Algorithmus](https://de.wikipedia.org/wiki/Dijkstra-Algorithmus) effizient der kürzeste Pfad vom Start zum Zielknoten berechnet werden.

## Implementierung
Ich habe das Problem in den Sprachen [*C++*](./c++), [*Java*](./java) und [*Python*](./python) beispielhaft implementiert.
Details dazu im jeweiligen Ordner.

## Lösung

Wird das fertige Programm ausgeführt, ergibt sich folgende Lösung:

Gesamtentfernung: 2.995687895999458
Pfad: Erde -> node_810 -> node_595 -> node_132 -> node_519 -> node_71 -> node_432 -> b3-r7-r4nd7

