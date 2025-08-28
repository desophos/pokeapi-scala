package io.github.juliano.pokeapi

import io.github.juliano.pokeapi.models.games.*
import io.github.juliano.pokeapi.PokeApiClient.*

class GamesSuite extends Fs2Suite:
  spec("generation by id", SimplePokeRequest[Generation](1), _.name, "generation-i")
  spec("generation by name", SimplePokeRequest[Generation]("generation-i"), _.id, 1)
  spec("generation resource list", fullResourceList[Generation], _.count, 9)

  spec("pokedex by id", SimplePokeRequest[Pokedex](1), _.name, "national")
  spec("pokedex by name", SimplePokeRequest[Pokedex]("national"), _.id, 1)
  spec("pokedex resource list", fullResourceList[Pokedex], _.count, 32)

  spec("version by id", SimplePokeRequest[Version](1), _.name, "red")
  spec("version by name", SimplePokeRequest[Version]("red"), _.id, 1)
  spec("version resource list", fullResourceList[Version], _.count, 43)

  spec("version group by id", SimplePokeRequest[VersionGroup](1), _.name, "red-blue")
  spec("version group by name", SimplePokeRequest[VersionGroup]("red-blue"), _.id, 1)
  spec("version group resource list", fullResourceList[VersionGroup], _.count, 27)
