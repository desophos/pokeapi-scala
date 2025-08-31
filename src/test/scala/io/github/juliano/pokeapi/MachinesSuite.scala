package io.github.juliano.pokeapi

import io.github.juliano.pokeapi.models.machines.*
import io.github.juliano.pokeapi.PokeApiClient.*

class MachinesSuite extends OkSyncSuite:
  spec("machine by id", SimplePokeRequest[Machine](1), _.move.name.contains("mega-punch"))
  spec("machine resource list", fullResourceList[Machine], _.count, 2102)
