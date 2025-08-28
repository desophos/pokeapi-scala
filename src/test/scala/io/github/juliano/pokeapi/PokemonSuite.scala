package io.github.juliano.pokeapi

import io.github.juliano.pokeapi.models.pokemon.*
import io.github.juliano.pokeapi.PokeApiClient.*

class PokemonSuite extends ZIOSuite:
  spec("ability by id", SimplePokeRequest[Ability](1), _.name, "stench")
  spec("ability by name", SimplePokeRequest[Ability]("stench"), _.id, 1)
  spec("ability resource list", fullResourceList[Ability], _.count, 367)

  spec(
    "characteristic by id",
    SimplePokeRequest[Characteristic](1),
    _.highestStat.name.contains("hp")
  )
  spec("characteristic resource list", fullResourceList[Characteristic], _.count, 30)

  spec("egg group by id", SimplePokeRequest[EggGroup](1), _.name, "monster")
  spec("egg group by name", SimplePokeRequest[EggGroup]("monster"), _.id, 1)
  spec("egg group resource list", fullResourceList[EggGroup], _.count, 15)

  spec("gender by id", SimplePokeRequest[Gender](1), _.name, "female")
  spec("gender by name", SimplePokeRequest[Gender]("female"), _.id, 1)
  spec("gender resource list", fullResourceList[Gender], _.count, 3)

  spec("growth rate by id", SimplePokeRequest[GrowthRate](1), _.name, "slow")
  spec("growth rate by name", SimplePokeRequest[GrowthRate]("slow"), _.id, 1)
  spec("growth rate resource list", fullResourceList[GrowthRate], _.count, 6)

  spec("nature by id", SimplePokeRequest[Nature](1), _.name, "hardy")
  spec("nature by name", SimplePokeRequest[Nature]("hardy"), _.id, 1)
  spec("nature resource list", fullResourceList[Nature], _.count, 25)

  spec("pokeathlon stat by id", SimplePokeRequest[PokeathlonStat](1), _.name, "speed")
  spec("pokeathlon stat by name", SimplePokeRequest[PokeathlonStat]("speed"), _.id, 1)
  spec("pokeathlon stat resource list", fullResourceList[PokeathlonStat], _.count, 5)

  spec("pokemon by id", SimplePokeRequest[Pokemon](1), _.name, "bulbasaur")
  spec("pokemon by name", SimplePokeRequest[Pokemon]("bulbasaur"), _.id, 1)
  spec(
    "pokemon resource list",
    fullResourceList[Pokemon],
    _.count,
    1302
  )

  spec(
    "location area encounter by id",
    SimplePokeRequest[LocationAreaEncounters](1),
    _.head.locationArea.name.contains("cerulean-city-area")
  )
  spec(
    "location area encounter by name",
    SimplePokeRequest[LocationAreaEncounters]("bulbasaur"),
    _.head.locationArea.name.contains("cerulean-city-area")
  )

  spec("pokemon color by id", SimplePokeRequest[PokemonColor](1), _.name, "black")
  spec("pokemon color by name", SimplePokeRequest[PokemonColor]("black"), _.id, 1)
  spec("pokemon color resource list", fullResourceList[PokemonColor], _.count, 10)

  spec("pokemon form by id", SimplePokeRequest[PokemonForm](1), _.name, "bulbasaur")
  spec("pokemon form by name", SimplePokeRequest[PokemonForm]("bulbasaur"), _.id, 1)
  spec("pokemon form resource list", fullResourceList[PokemonForm], _.count, 1473)

  spec("pokemon habitat by id", SimplePokeRequest[PokemonHabitat](1), _.name, "cave")
  spec("pokemon habitat by name", SimplePokeRequest[PokemonHabitat]("cave"), _.id, 1)
  spec("pokemon habitat resource list", fullResourceList[PokemonHabitat], _.count, 9)

  spec("pokemon shape by id", SimplePokeRequest[PokemonShape](1), _.name, "ball")
  spec("pokemon shape by name", SimplePokeRequest[PokemonShape]("ball"), _.id, 1)
  spec("pokemon shape resource list", fullResourceList[PokemonShape], _.count, 14)

  spec("pokemon species by id", SimplePokeRequest[PokemonSpecies](1), _.name, "bulbasaur")
  spec("pokemon species by name", SimplePokeRequest[PokemonSpecies]("bulbasaur"), _.id, 1)
  spec("pokemon species resource list", fullResourceList[PokemonSpecies], _.count, 1025)

  spec[Stat, Stat, String]("stat by id", SimplePokeRequest[Stat](1), _.name, "hp")
  spec("stat by name", SimplePokeRequest[Stat]("hp"), _.id, 1)
  spec("stat resource list", fullResourceList[Stat], _.count, 8)

  spec[Type, Type, String]("type by id", SimplePokeRequest[Type](1), _.name, "normal")
  spec("type by name", SimplePokeRequest[Type]("normal"), _.id, 1)
  spec("type resource list", fullResourceList[Type], _.count, 20)
