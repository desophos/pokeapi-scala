package io.github.juliano.pokeapi

import io.github.juliano.pokeapi.models.berries.*
import io.github.juliano.pokeapi.PokeApiClient.*

class BerriesSuite extends SyncSuite:
  spec("berry by id", SimplePokeRequest[Berry](1), _.name, "cheri")
  spec("berry by name", SimplePokeRequest[Berry]("cheri"), _.id, 1)
  spec("berry resource list", fullResourceList[Berry], _.count, 64)

  spec("berry firmness by id", SimplePokeRequest[BerryFirmness](1), _.name, "very-soft")
  spec("berry firmness by name", SimplePokeRequest[BerryFirmness]("very-soft"), _.id, 1)
  spec("berry firmness resource list", fullResourceList[BerryFirmness], _.count, 5)

  spec("berry flavor by id", SimplePokeRequest[BerryFlavor](1), _.name, "spicy")
  spec("berry flavor by name", SimplePokeRequest[BerryFlavor]("spicy"), _.id, 1)
  spec("berry flavor resource list", fullResourceList[BerryFlavor], _.count, 5)
