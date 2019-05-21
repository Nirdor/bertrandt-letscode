/**
 * 
 */
package letscode;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Basisklasse für die Repräsentation des Graphen.
 * @author Martin Seidel
 */
public class Graph {
  
  @SerializedName("nodes")
  protected List<Planet> planets;
  @SerializedName("edges")
  protected List<Route> routes;
  
  /**
   * Default Constructor.
   * Erzeugt die Listen für Knoten und Kanten.
   */
  public Graph() {
    planets = new ArrayList<>();
    routes = new ArrayList<>();
  }
  
  
  /**
   * Funktion, die basierend auf den Kanten, jedem Knoten seine Nachbarn zuweist, so dass diese in konstanter Zeit gefunden werden können.
   */
  public void initializeNeighbours() {
    for (Route e : routes) {
      planets.get(e.source).addNeighbour(planets.get(e.target), e.cost);
      planets.get(e.target).addNeighbour(planets.get(e.source), e.cost);
    }
  }

  /**
   * @return the planets
   */
  public List<Planet> getPlanets() {
    return planets;
  }

  /**
   * @param planets the planets to set
   */
  public void setPlanets(List<Planet> nodes) {
    this.planets = nodes;
  }

  /**
   * @return the routes
   */
  public List<Route> getRoutes() {
    return routes;
  }

  /**
   * @param routes the routes to set
   */
  public void setRoutes(List<Route> edges) {
    this.routes = edges;
  }
  
}
