package io.github.juliano.pokeapi

import io.github.juliano.pokeapi.models.encounters.*
import io.github.juliano.pokeapi.PokeApiClient.*

class EncountersSuite extends ArmeriaCatsSuite:
  spec("enconter method by id", SimplePokeRequest[EncounterMethod](1), _.name, "walk")
  spec("enconter method by name", SimplePokeRequest[EncounterMethod]("walk"), _.id, 1)
  spec("enconter method resource list", fullResourceList[EncounterMethod], _.count, 37)

  spec("enconter condition by id", SimplePokeRequest[EncounterCondition](1), _.name, "swarm")
  spec("enconter condition by name", SimplePokeRequest[EncounterCondition]("swarm"), _.id, 1)
  spec("enconter condition resource list", fullResourceList[EncounterCondition], _.count, 13)

  spec(
    "enconter condition value by id",
    SimplePokeRequest[EncounterConditionValue](1),
    _.name,
    "swarm-yes"
  )
  spec(
    "enconter condition value by name",
    SimplePokeRequest[EncounterConditionValue]("swarm-yes"),
    _.id,
    1
  )
  spec(
    "enconter condition value resource list",
    fullResourceList[EncounterConditionValue],
    _.count,
    71
  )
