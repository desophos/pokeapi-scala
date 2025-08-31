package io.github.juliano.pokeapi;

import io.github.juliano.pokeapi.models.items.*
import io.github.juliano.pokeapi.PokeApiClient.*

class ItemsSuite extends ZIOSuite:
  spec("item by id", SimplePokeRequest[Item](1), _.name, "master-ball")
  spec("item by name", SimplePokeRequest[Item]("master-ball"), _.id, 1)
  spec("item resource list", fullResourceList[Item], _.count, 2180)

  spec("item attribute by id", SimplePokeRequest[ItemAttribute](1), _.name, "countable")
  spec("item attribute by name", SimplePokeRequest[ItemAttribute]("countable"), _.id, 1)
  spec("item attribute resource list", fullResourceList[ItemAttribute], _.count, 8)

  spec("item category by id", SimplePokeRequest[ItemCategory](1), _.name, "stat-boosts")
  spec("item category by name", SimplePokeRequest[ItemCategory]("stat-boosts"), _.id, 1)
  spec("item category resource list", fullResourceList[ItemCategory], _.count, 54)

  spec("item fling effect by id", SimplePokeRequest[ItemFlingEffect](1), _.name, "badly-poison")
  spec("item fling effect by name", SimplePokeRequest[ItemFlingEffect]("badly-poison"), _.id, 1)
  spec("item fling effect resource list", fullResourceList[ItemFlingEffect], _.count, 7)

  spec("item pocket by id", SimplePokeRequest[ItemPocket](1), _.name, "misc")
  spec("item pocket by name", SimplePokeRequest[ItemPocket]("misc"), _.id, 1)
  spec("item pocket resource list", fullResourceList[ItemPocket], _.count, 8)
