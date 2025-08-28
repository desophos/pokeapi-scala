package io.github.juliano.pokeapi

import io.github.juliano.pokeapi.models.evolution.*
import io.github.juliano.pokeapi.PokeApiClient.*

class EvolutionSuite extends Fs2Suite:
  spec("evolution chain by id", SimplePokeRequest[EvolutionChain](1), _.babyTriggerItem.isEmpty)
  spec("evolution chain resource list", fullResourceList[EvolutionChain], _.count, 541)

  spec("evolution trigger by id", SimplePokeRequest[EvolutionTrigger](1), _.name, "level-up")
  spec("evolution trigger by name", SimplePokeRequest[EvolutionTrigger]("level-up"), _.id, 1)
  spec("evolution trigger resource list", fullResourceList[EvolutionTrigger], _.count, 13)
