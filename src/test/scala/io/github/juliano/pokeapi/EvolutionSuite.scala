package io.github.juliano.pokeapi

import io.github.juliano.pokeapi.requests.{ EvolutionChainRequest, EvolutionTriggerRequest }

class EvolutionSuite extends Fs2Suite:
  spec("evolution chain by id", EvolutionChainRequest(1), _.babyTriggerItem.isEmpty)
  spec("evolution chain resource list", EvolutionChainRequest.resourceList(), _.count, 541)

  spec("evolution trigger by id", EvolutionTriggerRequest(1), _.name, "level-up")
  spec("evolution trigger by name", EvolutionTriggerRequest("level-up"), _.id, 1)
  spec("evolution trigger resource list", EvolutionTriggerRequest.resourceList(), _.count, 13)
