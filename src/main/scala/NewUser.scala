import akka.actor.{ActorRef, Props, ActorSystem, Actor}
import akka.routing.FromConfig
import com.typesafe.config.ConfigFactory


class NewUser(bookMyShow: ActorRef) extends Actor {
  override def receive ={
    case "Book Seat" => bookMyShow ! "Book Seat"
    case "Cancel Seat"   => bookMyShow ! "Cancel Seat"

  }

}

object NewUser{
  def prop(ref: ActorRef):Props = Props(classOf[NewUser],ref)

}

object ActualUser extends App{

  val config = ConfigFactory.parseString(
    """
      |akka.actor.deployment {
      | /poolRouter {
      |   router = round-robin-pool
      |   nr-of-instances = 5
      | }
      |}
    """.stripMargin
  )

  val system = ActorSystem("NewUser",config)
  val bookMyShowActor  = system.actorOf(BookMyShow.prop)
  val userActor = system.actorOf(FromConfig.props(NewUser.prop(bookMyShowActor)),"poolRouter")

 userActor ! "Book Seat"
  userActor ! "Book Seat"
  userActor ! "Book Seat"
  userActor ! "Cancel Seat"
  userActor ! "Book Seat"



}
