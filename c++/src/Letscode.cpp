//============================================================================
// Name        : Letscode.cpp
// Author      : Martin Seidel
// Version     :
// Copyright   : 
// Description : Liest die JSON Definition eines Graphen ein, und findet darin den kürzesten Pfad vom Knoten Erde zum Knoten b3-r7-r4nd7
//============================================================================

#include <iostream>
#include <fstream>
#include <vector>
#include <queue>

#include "json.hpp"
using json = nlohmann::json;

#include "Planet.h"

using namespace std;

//Vergleichsfunktion für die PriorityQueue
class priority {
public:
  bool operator()(pair<letscode::Planet*, double>& p1, pair<letscode::Planet*, double>& p2) {
    return p1.second > p2.second;
  }
};

/**
 * Implementierung des Dijkstra-Algorithmus
 */
pair<double, vector<letscode::Planet*>> dijkstra(letscode::Planet* start, letscode::Planet* target) {
  priority_queue<pair<letscode::Planet*, double>, vector<pair<letscode::Planet*, double>>, priority> pq;
  vector<string> visited; //Speichert die schon abgeschlossenen Knoten
  map<string, double> costs; //Speichert die Kosten, zu denen jeder Knoten aktuell erreichbar ist.
  map<string, letscode::Planet*> predecessor; //Speichert zu jedem Knoten den Vorgänger, um nacher den Pfad rekonstruieren zu können.

  //init
  predecessor.emplace(start->getLabel(), static_cast<letscode::Planet*>(NULL));
  pq.push(make_pair(start, 0.0));
  double result = -1.0;

  //Hauptschleife
  while (!pq.empty()) {
    pair<letscode::Planet*, double> cur = pq.top();
    pq.pop();

    if (cur.first->getLabel() == target->getLabel()) {
      //Ziel gefunden!
      result = cur.second;
      break;
    }

    if (find(visited.begin(), visited.end(), cur.first->getLabel()) != visited.end()) {
      //Schon besuchte Knoten müssen nicht nochmal untersucht werden.
      continue;
    }
    visited.push_back(cur.first->getLabel());

    for (pair<letscode::Planet* ,double> next : cur.first->getRoutes()) {
      if (find(visited.begin(), visited.end(), next.first->getLabel()) != visited.end()) {
        //Schon besuchte Knoten müssen nicht nochmal untersucht werden.
        continue;
      }

      auto cost = costs.find(next.first->getLabel());
      double next_cost = cur.second + next.second;
      if (cost == costs.end()) {
        costs.emplace(next.first->getLabel(), next_cost);
        predecessor.emplace(next.first->getLabel(), cur.first);
        pq.push(make_pair(next.first, next_cost));
      } else if(next_cost < cost->second) {
        costs.at(next.first->getLabel()) = next_cost;
        predecessor.at(next.first->getLabel()) = cur.first;
        pq.push(make_pair(next.first, next_cost));
      }

    }
  }

  vector<letscode::Planet*> ret;

  if (result != -1.0) {
    while (target != NULL) {
      ret.push_back(target);
      target = predecessor.at(target->getLabel());
    }
    reverse(ret.begin(), ret.end());
  }

  return make_pair(result, ret);
}


int main() {

  //Einlesen des Json Files
	ifstream is("generatedGraph.json");
	json j;
	is >> j;

	//Aufbau einer Datenstruktur für den Inhalt
	vector<letscode::Planet> graph;

	for (auto p : j["nodes"]) {
	  graph.push_back(letscode::Planet(p["label"]));
	}
	for (auto r : j["edges"]) {
	  graph.at(r["source"]).addRoute(&graph.at(r["target"]), r["cost"]);
	  graph.at(r["target"]).addRoute(&graph.at(r["source"]), r["cost"]);
	}

	//Start und Zielknoten finden
	auto e = find(graph.begin(), graph.end(), "Erde");
	auto b = find(graph.begin(), graph.end(), "b3-r7-r4nd7");

	if (e == graph.end() || b == graph.end()) {
	  cerr << "Fehler: Planet Erde oder b3-r7-r4nd7 nicht gefunden!" << endl;
	  return -1;
	}

	letscode::Planet* erde = &(*e);
	letscode::Planet* bertrandt = &(*b);

	//Berechung des kürzesten Pfades mit dem Dijkstra Algorithmus
	auto result = dijkstra(erde, bertrandt);
	cout << "Lösung:" << endl;
	cout << "Gesamtentfernung: " << result.first << endl;
	cout << "Pfad: " << *result.second.at(0);
	for (size_t i = 1; i < result.second.size(); ++i) {
	  cout << " -> " << *result.second.at(i);
	}
	cout << endl;

	return 0;
}
