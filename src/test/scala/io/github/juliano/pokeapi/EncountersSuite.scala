package io.github.juliano.pokeapi

import io.github.juliano.pokeapi.requests.*

class EncountersSuite extends ArmeriaCatsSuite:
  spec("enconter method by id", EncounterMethodRequest(1), _.name, "walk")
  spec("enconter method by name", EncounterMethodRequest("walk"), _.id, 1)
  spec("enconter method resource list", EncounterMethodRequest.resourceList(), _.count, 37)

  spec("enconter condition by id", EncounterConditionRequest(1), _.name, "swarm")
  spec("enconter condition by name", EncounterConditionRequest("swarm"), _.id, 1)
  spec("enconter condition resource list", EncounterConditionRequest.resourceList(), _.count, 13)

  spec("enconter condition value by id", EncounterConditionValueRequest(1), _.name, "swarm-yes")
  spec("enconter condition value by name", EncounterConditionValueRequest("swarm-yes"), _.id, 1)
  spec(
    "enconter condition value resource list",
    EncounterConditionValueRequest.resourceList(),
    _.count,
    71
  )
