package io.github.juliano

import io.github.juliano.pokeapi.PokeApiClient.{ PokeRequest, ResourceListRequest }
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try
import munit.{ AfterEach, AnyFixture, BeforeEach, Compare, FunSuite }
import sttp.monad.MonadError
import sttp.monad.syntax.*
import sttp.client3.{ Identity, SttpBackend }
import zio.{ Task, ZIO }
import zio.json.JsonDecoder
import cats.effect.IO

package object pokeapi:
  given MonadError[Identity] = sttp.client3.monad.IdMonad
  given MonadError[Try] = sttp.monad.TryMonad
  given MonadError[Future] = sttp.monad.FutureMonad()
  given MonadError[IO] = sttp.client3.impl.cats.CatsMonadError[IO]
  given MonadError[Task] = sttp.client3.impl.zio.RIOMonadAsyncError[Any]

  private abstract class EffectFixture[F[_], T](name: String)(using mf: MonadError[F])
      extends AnyFixture[T](name) {
    override def beforeAll(): F[Unit]                     = mf.unit(())
    override def beforeEach(context: BeforeEach): F[Unit] = mf.unit(())
    override def afterEach(context: AfterEach): F[Unit]   = mf.unit(())
    override def afterAll(): F[Unit]                      = mf.unit(())
  }

  trait EffectSuite[F[_]](using mf: MonadError[F]) extends FunSuite:
    protected val backend: F[SttpBackend[F, Any]]

    protected val finalizer: F[Unit] = mf.unit(())

    protected val client = new EffectFixture[F, PokeApiClient[F, Any]]("client") {
      private var client: PokeApiClient[F, Any] = null

      def apply() = client

      override def beforeAll() = backend.map(implicit b => client = PokeApiClient())
      override def afterAll()  = finalizer
    }

    override def munitFixtures = List(client)

    override def munitValueTransforms = super.munitValueTransforms ++ List(
      new ValueTransform(
        "Try",
        { case t: Try[Any] => Future.fromTry(t) }
      ),
      new ValueTransform(
        "ZIO",
        { case z: ZIO[?, ?, ?] =>
          zio.Unsafe.unsafe { implicit u =>
            zio.Runtime.default.unsafe.runToFuture(z.asInstanceOf[Task[Any]])
          }
        }
      ),
      new ValueTransform(
        "Cats",
        { case io: IO[Any] =>
          import cats.effect.unsafe.implicits.global
          io.unsafeToFuture()
        }
      )
    )

    def fullResourceList[R: ApiPath] = ResourceListRequest[R](limit = 100000)

    def spec[A: JsonDecoder, R: ApiPath](
        label: String,
        request: PokeRequest[A, R],
        predicate: A => Boolean
    ): Unit = test(label) {
      client().send(request).map(result => assert(predicate(result), result))
    }

    def spec[A: JsonDecoder, R: ApiPath, B](
        label: String,
        request: PokeRequest[A, R],
        keyFn: A => B,
        expected: B
    ): Unit = test(label) {
      client().send(request).map(result => assertEquals(keyFn(result), expected, result))
    }

  trait FutureSuite extends EffectSuite[Future]:
    import scala.concurrent.ExecutionContext.Implicits.global
    override val backend = Future(sttp.client3.HttpClientFutureBackend())

  trait TrySuite extends EffectSuite[Try]:
    override val backend = scala.util.Success(sttp.client3.TryHttpURLConnectionBackend())

  trait SyncSuite extends EffectSuite[Identity]:
    override val backend = sttp.client3.HttpClientSyncBackend()

  trait OkSyncSuite extends SyncSuite:
    override val backend = sttp.client3.okhttp.OkHttpSyncBackend()

  trait ZIOSuite extends EffectSuite[[A] =>> ZIO[Any, Throwable, A]]:
    override val backend = sttp.client3.asynchttpclient.zio.AsyncHttpClientZioBackend()

  trait ArmeriaZIOSuite extends ZIOSuite:
    override val backend = sttp.client3.armeria.zio.ArmeriaZioBackend()

  trait CatsSuite extends EffectSuite[IO]:
    override val backend = sttp.client3.asynchttpclient.cats.AsyncHttpClientCatsBackend()

  trait ArmeriaCatsSuite extends CatsSuite:
    override val backend = IO(sttp.client3.armeria.cats.ArmeriaCatsBackend[IO]())

  trait Fs2Suite extends EffectSuite[IO]:
    private val backendAndFinalizer =
      sttp.client3.asynchttpclient.fs2.AsyncHttpClientFs2Backend.resource[IO]().allocated

    override val backend   = backendAndFinalizer.map(_._1)
    override val finalizer = backendAndFinalizer.flatMap(_._2)
