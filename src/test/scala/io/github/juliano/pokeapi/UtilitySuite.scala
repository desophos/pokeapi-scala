package io.github.juliano.pokeapi

import io.github.juliano.pokeapi.models.utility.*
import io.github.juliano.pokeapi.PokeApiClient.*

class UtilitySuite extends ArmeriaZIOSuite:
  spec("language by id", SimplePokeRequest[Language](1), _.name, "ja-Hrkt")
  spec("language by name", SimplePokeRequest[Language]("ja-Hrkt"), _.id, 1)
  spec("language resource list", fullResourceList[Language], _.count, 13)
