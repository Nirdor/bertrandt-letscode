/**
 * 
 */
package letscode;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Klasse die einen Knoten des Graphen repräsentiert.
 * Implementiert das Comparable Interface um die Kosten zweier Knoten vergleichen zu können.
 * Dies wird für die PriorityQueue benötigt. 
 * @author Martin Seidel
 */
public class Planet implements Comparable<Planet> {
  
  protected String label;
  protected Map<Planet, Double> neighbours;
  
  protected double cost;
  
  /**
   * Default Constructor.
   * Erzeugt die Map für die Nachbarknoten und initialisiert die Kosten, um den Knoten zu erreichen mit unendlich..
   */
  public Planet() {
    this.neighbours = new HashMap<>();
    this.cost = Double.POSITIVE_INFINITY;
  }

  /**
   * Fügt einen Nachbarn hinzu. Die Kosten der Kante werden ebenfals gespeichert.
   * @param p Nachbarknoten
   * @param cost Kosten um den Knoten zu erreichen
   */
  public void addNeighbour(Planet p, double cost) {
    this.neighbours.put(p, cost);
  }
  
  /**
   * Liefert die Kosten der Kante zu dem entsprechenden Nachbarn zurück.
   * Sollte der Knoten, kein Nachbar sein. wird eine IllegalArgumentException geworfen.
   * @param p der Nachbarknoten, dessen Kosten abgefragt werden.
   * @return Die Kosten der Kante zu dem entsprechenden Nachbarn.
   */
  public double getTravelCost(Planet p) {
    Double ret = neighbours.get(p);
    if (ret == null) {
      throw new IllegalArgumentException("Planet ist kein Nachbar!");
    }
    return ret;
  }

  @Override
  public String toString() {
    return label;
  }

  /**
   * @return the label
   */
  public String getLabel() {
    return label;
  }

  /**
   * @param label the label to set
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * @return the neighbours
   */
  public Set<Planet> getNeighbours() {
    return neighbours.keySet();
  }

  /**
   * @return the cost
   */
  public double getCost() {
    return cost;
  }

  /**
   * @param cost the cost to set
   */
  public void setCost(double cost) {
    this.cost = cost;
  }

  @Override
  public int compareTo(Planet other) {
    if (cost > other.cost) {
      return 1;
    } else if (cost < other.cost) {
      return -1;
    }
    return 0;
  }

  @Override
  public int hashCode() {
    return label.hashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof Planet) {
      return label.equals(((Planet) other).label);
    }
    return super.equals(other);
  }
  
}
