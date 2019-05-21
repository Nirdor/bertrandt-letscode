/*
 * Planet.cpp
 *
 *  Created on: 09.05.2019
 *      Author: Martin Seidel
 */

#include "Planet.h"

namespace letscode {

Planet::Planet(string label) :
  label(label) {
}

Planet::~Planet() {
}

void Planet::addRoute(Planet* target, const double cost) {
  routes.push_back(make_pair(target, cost));
}

} /* namespace letscode */
