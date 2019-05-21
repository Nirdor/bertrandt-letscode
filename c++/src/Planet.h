/*
 * Planet.h
 *
 * Klasse, die einen Knoten der Datenstruktur Repräsentiert.
 * Kanten werden impizit, durch Referenzen der Knoten untereinander, repräsentiert.
 *
 *  Created on: 09.05.2019
 *      Author: Martin Seidel
 */

#ifndef PLANET_H_
#define PLANET_H_

#include <string>
#include <vector>

using namespace std;

namespace letscode {

class Planet {

  string label;
  vector<pair<Planet*, double>> routes;

public:
  Planet(string label);
  virtual ~Planet();

  void addRoute(Planet* target, const double cost);

  friend bool operator==(const Planet& p, const string& s);
  friend ostream& operator<<(ostream &o, const Planet& p);

  const string& getLabel() const {
    return label;
  }

  const vector<pair<Planet*, double> >& getRoutes() const {
    return routes;
  }
};

/**
 * Hilfsfunktion um mit einem String innerhalb des Graph Vectors, der die Knoten enthält, suchen zu können.
 */
inline bool operator==(const Planet& p, const string& s) {
    return p.label == s;
}

/**
 * Hilfsfunktion für die einfache Ausgabe mit Streams
 */
inline ostream& operator<<(ostream &o, const Planet& p) {
  return o << p.label;
}


} /* namespace letscode */

#endif /* PLANET_H_ */
