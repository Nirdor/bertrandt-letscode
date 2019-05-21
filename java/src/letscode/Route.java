/**
 * 
 */
package letscode;

/**
 * Klasse, die eine Kante des Graphen repräsentiert.
 * Enthält einen Start und Zielknoten, sowie die Kosten.
 * @author Martin Seidel
 */
public class Route {

  protected int source;
  protected int target;
  protected double cost;
  
  /**
   * Default Constructor
   */
  public Route() {
  }

  @Override
  public String toString() {
    return source + " <-> " + target;
  }

  /**
   * @return the source
   */
  public int getSource() {
    return source;
  }

  /**
   * @param source the source to set
   */
  public void setSource(int source) {
    this.source = source;
  }

  /**
   * @return the target
   */
  public int getTarget() {
    return target;
  }

  /**
   * @param target the target to set
   */
  public void setTarget(int target) {
    this.target = target;
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
  
}
