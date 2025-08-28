package io.github.juliano.pokeapi

import io.github.juliano.pokeapi.models.contests.*
import io.github.juliano.pokeapi.PokeApiClient.*
import scala.concurrent.ExecutionContext.Implicits.global

class ContestsSuite extends FutureSuite:
  spec("contest type by id", SimplePokeRequest[ContestType](1), _.name, "cool")
  spec("contest type by name", SimplePokeRequest[ContestType]("cool"), _.id, 1)
  spec("contest type resource list", fullResourceList[ContestType], _.count, 5)

  spec("contest effect by id", SimplePokeRequest[ContestEffect](1), _.appeal, 4)
  spec("contest effect resource list", fullResourceList[ContestEffect], _.count, 33)

  spec("super contest effect by id", SimplePokeRequest[SuperContestEffect](1), _.appeal, 2)
  spec("super contest effect resource list", fullResourceList[SuperContestEffect], _.count, 22)
