package io.github.juliano.pokeapi

import io.github.juliano.pokeapi.requests.MachineRequest

class MachinesSuite extends OkSyncSuite:
  spec("machine by id", MachineRequest(1), _.move.name.contains("mega-punch"))
  spec("machine resource list", MachineRequest.resourceList(), _.count, 1688)
