/**
 * 
 */
package letscode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

/**
 * Hauptklasse mit der Main Methode.
 * @author Martin Seidel
 */
public class LetsCode {

  /**
   * Hauptfunktion des Programms.
   * Parst die Json Datei.
   * @param args
   */
  public static void main(String[] args) {
    
    //Default Werte für Start und Ziel aus der Challenge können mit Argumenten überschrieben werden.
    String start_label = "Erde";
    String ziel_label = "b3-r7-r4nd7";
    if (args.length == 2) {
       start_label = args[0];
       ziel_label = args[1];
    }
    
    
    //Einlesen der JSon Datei und initialisierung der Graph Datenstruktur.
    Graph graph = null;
    Gson g = new Gson();
    try {
      graph = g.fromJson(new FileReader("generatedGraph.json"), Graph.class);
    } catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
      System.err.println("Fehler beim Parsen der JSON Daten!");
      e.printStackTrace();
      return;
    }
    graph.initializeNeighbours();
    
    //Ausgangs und Zielknoten finden
    Planet start = null;
    Planet ziel = null;
    for (Planet p : graph.getPlanets()) {
      if (p.getLabel().equals(start_label)) {
        start = p;
      } else if (p.getLabel().equals(ziel_label)) {
        ziel = p;
      }
      if (start != null && ziel != null) {
        break;
      }
    }
    if (start == null || ziel == null) {
      System.err.println("Planet " + start_label + " oder " + ziel_label + " nicht in den Json Daten gefunden!");
      return;
    }
    
    //Dijkstra Algorithmus ausführen und Ergebnis ausgeben.
    List<Planet> path = dijkstra(start, ziel);
    System.out.println("Lösung:");
    System.out.println("Gesamtentfernung: " + ziel.cost);
    System.out.print("Pfad: " + path.get(0));
    for (int i = 1; i < path.size(); ++i) {
      System.out.print(" -> ");
      System.out.print(path.get(i));
    }
    System.out.println();
  }

  /**
   * Implementierung des Dijkstra-algorithmus
   * @param start Ausgangsknoten
   * @param goal Zielknoten, zu dem der Pfad gefunden werden soll
   * @return Liste, die die Abfolge der Knoten des kürzesten Pfades enthält oder leere Liste, falls Zielknoten nicht erreichbar
   */
  private static List<Planet> dijkstra(Planet start, Planet goal) {
    PriorityQueue<Planet> pq = new PriorityQueue<>();                 //PriorityQueue
    Map<Planet, Planet> predecessor = new HashMap<Planet, Planet>();  //Map mit Vorgängen um am Ende den Pfad zu rekonstruieren
    List<Planet> visited = new ArrayList<>();                         //Liste mit schon abgeschlossenen Knoten
    
    start.setCost(0.0);                                               //Inititalisierung
    pq.add(start);
    
    while (!pq.isEmpty()) {                                           //Hauptschleife
      Planet cur = pq.poll();
      visited.add(cur);                                               //Knoten ist jetzt abgeschlossen
      
      if (cur.equals(goal)) {
        break;                                                        //Ziel gefunden
      }
      
      for (Planet next : cur.getNeighbours()) {
        if (visited.contains(next)) {                                 //Nachbar ist schon abgeschlossen, muss nicht nochmal untersucht werden
          continue;
        }
        double next_cost = cur.getCost() + cur.getTravelCost(next);   //Kosten zum Nachbarn
        if (pq.contains(next)) {                                      //Nachbar schon in der PQ, muss aktualisiert werden, falls neue Kosten geringer
          if (next_cost < next.getCost()) {
            next.setCost(next_cost);
            predecessor.put(next, cur);
            pq.remove(next);
            pq.add(next);
          }
        } else {                                                      //Nachbar noch nicht in der PQ, hinzufügen 
          next.setCost(next_cost);
          predecessor.put(next, cur);
          pq.add(next);
        }
      }
      
    }
    
    if (!predecessor.containsKey(goal)) {                             //Zielknoten ist nicht erreichbar, leeren Pfad zurück geben
      return new ArrayList<Planet>();
    }
    
    ArrayList<Planet> ret = new ArrayList<>();                        //Pfad aus Vorgänger-Map rekonstruieren
    Planet target = goal;
    ret.add(target);
    while (predecessor.containsKey(target)) {
      target = predecessor.get(target);
      ret.add(target);
    }
    Collections.reverse(ret);
    
    return ret;
  }

}
