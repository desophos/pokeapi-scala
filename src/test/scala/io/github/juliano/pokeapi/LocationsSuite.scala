package io.github.juliano.pokeapi

import io.github.juliano.pokeapi.models.locations.*
import io.github.juliano.pokeapi.PokeApiClient.*

class LocationsSuite extends CatsSuite:
  spec("location by id", SimplePokeRequest[Location](1), _.name, "canalave-city")
  spec("location by name", SimplePokeRequest[Location]("canalave-city"), _.id, 1)
  spec("location resource list", fullResourceList[Location], _.count, 1070)

  spec("location area by id", SimplePokeRequest[LocationArea](1), _.name, "canalave-city-area")
  spec("location area by name", SimplePokeRequest[LocationArea]("canalave-city-area"), _.id, 1)
  spec("location area resource list", fullResourceList[LocationArea], _.count, 1089)

  spec("pal park area by id", SimplePokeRequest[PalParkArea](1), _.name, "forest")
  spec("pal park area by name", SimplePokeRequest[PalParkArea]("forest"), _.id, 1)
  spec("pal park area resource list", fullResourceList[PalParkArea], _.count, 5)

  spec("region by id", SimplePokeRequest[Region](1), _.name, "kanto")
  spec("region by name", SimplePokeRequest[Region]("kanto"), _.id, 1)
  spec("region resource list", fullResourceList[Region], _.count, 10)
