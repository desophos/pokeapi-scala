package io.github.juliano.pokeapi

import io.github.juliano.pokeapi.requests.*

class BerriesSuite extends SyncSuite:
  spec("berry by id", BerryRequest(1), _.name == "cheri")
  spec("berry by name", BerryRequest("cheri"), _.id == 1)
  spec("berry resource list", BerryRequest.resourceList(), _.count == 64)

  spec("berry firmness by id", BerryFirmnessRequest(1), _.name == "very-soft")
  spec("berry firmness by name", BerryFirmnessRequest("very-soft"), _.id == 1)
  spec("berry firmness resource list", BerryFirmnessRequest.resourceList(), _.count == 5)

  spec("berry flavor by id", BerryFlavorRequest(1), _.name == "spicy")
  spec("berry flavor by name", BerryFlavorRequest("spicy"), _.id == 1)
  spec("berry flavor resource list", BerryFlavorRequest.resourceList(), _.count == 5)
