package io.github.juliano.pokeapi

import io.github.juliano.pokeapi.requests.LanguageRequest

class UtilitySuite extends ArmeriaZIOSuite:
  spec("language by id", LanguageRequest(1), _.name == "ja-Hrkt")
  spec("language by name", LanguageRequest("ja-Hrkt"), _.id == 1)
  spec("language resource list", LanguageRequest.resourceList(), _.count == 13)
