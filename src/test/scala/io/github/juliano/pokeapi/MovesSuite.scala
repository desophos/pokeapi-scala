package io.github.juliano.pokeapi

import io.github.juliano.pokeapi.models.moves.*
import io.github.juliano.pokeapi.PokeApiClient.*

class MovesSuite extends TrySuite:
  spec("move by id", SimplePokeRequest[Move](1), _.name, "pound")
  spec("move by name", SimplePokeRequest[Move]("pound"), _.id, 1)
  spec("move by resource list", fullResourceList[Move], _.count, 937)

  spec("move ailment by id", SimplePokeRequest[MoveAilment](1), _.name, "paralysis")
  spec("move ailment by name", SimplePokeRequest[MoveAilment]("paralysis"), _.id, 1)
  spec("move ailment by resource list", fullResourceList[MoveAilment], _.count, 22)

  spec("move battle style by id", SimplePokeRequest[MoveBattleStyle](1), _.name, "attack")
  spec("move battle style by name", SimplePokeRequest[MoveBattleStyle]("attack"), _.id, 1)
  spec("move battle style by resource list", fullResourceList[MoveBattleStyle], _.count, 3)

  spec("move category by id", SimplePokeRequest[MoveCategory](1), _.name, "ailment")
  spec("move category by name", SimplePokeRequest[MoveCategory]("ailment"), _.id, 1)
  spec("move category by resource list", fullResourceList[MoveCategory], _.count, 14)

  spec("move damage by id", SimplePokeRequest[MoveDamageClass](1), _.name, "status")
  spec("move damage by name", SimplePokeRequest[MoveDamageClass]("status"), _.id, 1)
  spec("move damage by resource list", fullResourceList[MoveDamageClass], _.count, 3)

  spec("move learn method by id", SimplePokeRequest[MoveLearnMethod](1), _.name, "level-up")
  spec("move learn method by name", SimplePokeRequest[MoveLearnMethod]("level-up"), _.id, 1)
  spec("move learn method by resource list", fullResourceList[MoveLearnMethod], _.count, 11)

  spec("move target by id", SimplePokeRequest[MoveTarget](1), _.name, "specific-move")
  spec("move target by name", SimplePokeRequest[MoveTarget]("specific-move"), _.id, 1)
  spec("move target by resource list", fullResourceList[MoveTarget], _.count, 16)
