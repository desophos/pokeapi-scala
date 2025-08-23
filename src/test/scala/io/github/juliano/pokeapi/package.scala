package io.github.juliano

import io.github.juliano.pokeapi.PokeApiClient.PokeRequest
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try
import munit.{ AfterEach, AnyFixture, BeforeEach, FunSuite }
import sttp.client3.{ Identity, SttpBackend }
import magnolia1.Monadic
import magnolia1.Monadic.*
import zio.ZIO
import zio.json.JsonDecoder
import cats.effect.IO

package object pokeapi:
  extension [F[_]](m: Monadic[F]) def unit: F[Unit] = m.point(())

  given Monadic[Identity] with
    def point[A](value: A): Identity[A]                                     = value
    def map[A, B](from: Identity[A])(fn: A => B): Identity[B]               = fn(from)
    def flatMap[A, B](from: Identity[A])(fn: A => Identity[B]): Identity[B] = fn(from)

  given [R, E]: Monadic[[X] =>> ZIO[R, E, X]] with
    def point[A](value: A): ZIO[R, E, A]                                       = ZIO.succeed(value)
    def map[A, B](from: ZIO[R, E, A])(fn: A => B): ZIO[R, E, B]                = from.map(fn)
    def flatMap[A, B](from: ZIO[R, E, A])(fn: A => ZIO[R, E, B]): ZIO[R, E, B] = from.flatMap(fn)

  given Monadic[IO] with
    def point[A](value: A): IO[A]                         = IO.pure(value)
    def map[A, B](from: IO[A])(fn: A => B): IO[B]         = from.map(fn)
    def flatMap[A, B](from: IO[A])(fn: A => IO[B]): IO[B] = from.flatMap(fn)

  private abstract class EffectFixture[F[_], T](name: String)(using mf: Monadic[F])
      extends AnyFixture[T](name) {
    override def beforeAll(): F[Unit]                     = mf.unit
    override def beforeEach(context: BeforeEach): F[Unit] = mf.unit
    override def afterEach(context: AfterEach): F[Unit]   = mf.unit
    override def afterAll(): F[Unit]                      = mf.unit
  }

  trait EffectSuite[F[_]](using mf: Monadic[F]) extends FunSuite:
    protected val backend: F[SttpBackend[F, Any]]

    protected val finalizer: F[Unit] = mf.unit

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
            zio.Runtime.default.unsafe.runToFuture(z.asInstanceOf[ZIO[Any, Throwable, Any]])
          }
        }
      ),
      new ValueTransform(
        "IO",
        { case io: IO[Any] =>
          import cats.effect.unsafe.implicits.global
          io.unsafeToFuture()
        }
      )
    )

    def spec[T](label: String, request: PokeRequest[T], predicate: T => Boolean)(using
        JsonDecoder[T]
    ): Unit = test(label) {
      client().send(request).map(result => assert(predicate(result), result))
    }

    def spec[T, A](
        label: String,
        request: PokeRequest[T],
        keyFn: T => A = identity,
        expected: A
    )(using
        JsonDecoder[T]
    ): Unit = test(label) {
      client().send(request).map(result => assertEquals(keyFn(result), expected))
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
