package io.github.juliano.pokeapi

import io.github.juliano.pokeapi.models.utility.*
import io.github.juliano.pokeapi.PokeApiClient.*

class UtilitySuite extends ArmeriaZIOSuite:
  spec[Language]("language by id", 1, _.name, "ja-Hrkt")
  spec[Language]("language by name", "ja-Hrkt", _.id, 1)
  spec[Language]("language resource list", _.count, 13)
