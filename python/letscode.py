#!/usr/bin/env python
# -*- coding: utf-8 -*-
'''
Created on 03.05.2019

@author: Martin Seidel
'''

import heapq
import json
import os
import sys

#Basisklasse der Datenstruktur: Symbolisiert einen Knoten
#Enthält eine Liste mit den ausgehenden Kanten, die als Tupel (Ziel, Kosten) gespeichert werden.
#Speichert außerdem die Kosten, die der Dijkstra-Algorithmus berechnet.
class Planet():

  def __init__(self, _id, name):
    self._id = _id
    self.name = name
    self.routes = []
    self.cost = float('inf')

  def addRoute(self, target, cost):
    if (target, cost) not in self.routes:
      self.routes.append((target, cost))

  def __lt__(self, other):
    return self.cost < other.cost

  def __str__(self):
    return self.name

  def __repr__(self):
    return f'{self._id}:{self.name}'


#Implementierung des Dijkstra-Algorithmus
def dijkstra(start, goal):
  predecessor = {start:None}              #Map mit den vorgängern um den Pfad am Ende zu rekonstrurieren
  visited = []                            #Liste mit schon abgeschlossenen Knoten
  start.cost = 0.0                        #Initiale Kosten
  q = [start]                             #Priority Queue mit den Knoten
  while q:                                #Hauptschleife
    u = heapq.heappop(q)     
    visited.append(u._id)                 #u ist jetzt abgeschlossen
    if u == goal:
      break                               #Ziel gefunden
    for i in u.routes:
      if i in visited:                    #Nachbar ist schon abgeschlossen, muss nicht nochmal untersucht werden
        continue
      if i[0] in q:                       #Nachbar ist schon in q, muss eventuell aktualisiert werden
        if u.cost + i[1] < i[0].cost:     
          i[0].cost = u.cost + i[1]
          predecessor[i[0]] = u
          heapq.heapify(q)
      elif i[0]._id not in visited:       #Nachbar noch nicht in q, wird hinzugefügt
        i[0].cost = u.cost + i[1]
        predecessor[i[0]] = u
        heapq.heappush(q, i[0])
  
  if not goal in predecessor:                       #Zielknoten ist nicht erreichbar, leerer Pfad
    return []
  
  path = []                               #Pfad rekonstruieren und zurückgeben
  p = goal
  while p != None:
    path.append(p)
    p = predecessor[p]
  
  path.reverse()
  return path


if __name__ == '__main__':
  #Suche nach der JSON Datei
  if os.path.isfile(r'generatedGraph.json'):
    p = r'generatedGraph.json'
  elif os.path.isfile(r'../data/generatedGraph.json'):
    p = r'../data/generatedGraph.json'
  else:
    print('Fehler, generatedGraph.json nicht gefunden.', file=sys.stderr)
    exit()
  
  #JSON Datei einlesen
  with open(p, 'r') as f:
    data = json.load(f)
    
  #Umwandeln der JSON Objekte in die Datenstruktur für den Dijkstra Algorithmus
  nodes = data['nodes']
  edges = data['edges']
  planets = [Planet(x, nodes[x]['label']) for x in range(len(nodes))]
  for edge in edges:
    planets[edge['source']].addRoute(planets[edge['target']], edge['cost'])
    planets[edge['target']].addRoute(planets[edge['source']], edge['cost'])
    
  #Start und Zielknoten finden    
  erde, bertrandt = None, None
  for planet in planets:
    if planet.name == 'Erde':
      erde = planet
    elif planet.name == 'b3-r7-r4nd7':
      bertrandt = planet
    if erde and bertrandt:
      break

  if erde is None or bertrandt is None:
    print('Fehler, Erde oder b3-r7-r4nd7, nicht im graph gefunden.', file=sys.stderr)
    exit()
      
  #Algorithmus ausführen und Ergebnis ausgeben
  path = dijkstra(erde, bertrandt)
  print('Lösung:')
  print(f'Gesamtentfernung: {bertrandt.cost}')
  print(f'Pfad: {" -> ".join([str(x) for x in path])}')
