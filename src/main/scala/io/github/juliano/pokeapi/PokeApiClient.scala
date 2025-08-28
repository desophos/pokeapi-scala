package io.github.juliano.pokeapi

import com.github.blemale.scaffeine.{ Cache, Scaffeine }
import io.github.juliano.pokeapi.models.ResourceList
import io.github.juliano.pokeapi.PokeApiClient.*
import sttp.client3.*
import sttp.client3.ziojson.asJson
import sttp.model.{ MediaType, Uri }
import sttp.monad.MonadError
import sttp.monad.syntax.MonadErrorOps
import zio.json.JsonDecoder

import scala.concurrent.duration.DurationInt

case class PokeApiClient[F[_], +P](host: ApiHost = ApiHost.default)(using
    backend: SttpBackend[F, P]
):
  given monadError: MonadError[F] = backend.responseMonad
  private def cache[A, R]: Cache[PokeRequest[A, R], A] =
    Scaffeine().build[PokeRequest[A, R], A]()

  def send[A: JsonDecoder, R: ApiPath](request: PokeRequest[A, R]): F[A] =
    cache.getIfPresent(request) match
      case Some(value) =>
        monadError.unit(value)
      case None =>
        request
          .sttpRequest(host)
          .send(backend)
          .map(_.body)
          .flatMap {
            case Right(value) =>
              cache.put(request, value)
              monadError.unit(value)
            case Left(error) => monadError.error(error)
          }

  def send[A: JsonDecoder: ApiPath](request: SimplePokeRequest[A]): F[A] = send(request)

  def send[R: ApiPath](request: ResourceListRequest[R]): F[ResourceList] = send(request)

object PokeApiClient:
  type FailureResponse = ResponseException[String, String]
  type SttpRequest[A]  = Request[Either[FailureResponse, A], Any]

  sealed trait PokeRequest[A: JsonDecoder, R: ApiPath](
      id: String | Long,
      params: Map[String, String] = Map.empty
  ):
    def sttpRequest(host: ApiHost): SttpRequest[A] =
      basicRequest
        .get(host.uri.addResourcePath[R](id).addParams(params))
        .readTimeout(10.seconds)
        .contentType(MediaType.ApplicationJson)
        .response(asJson[A])

  case class SimplePokeRequest[A: JsonDecoder: ApiPath](id: String | Long)
      extends PokeRequest[A, A](id)

  case class ResourceListRequest[R: ApiPath](offset: Int = 0, limit: Int = 20)
      extends PokeRequest[ResourceList, R](
        "",
        Map("offset" -> offset.toString, "limit" -> limit.toString)
      )
