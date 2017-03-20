import akka.actor.{Actor, Props, ActorSystem}


class BookMyShow extends Actor {

  var numberOfSeats = 1
  override  def receive ={

    case request:String if(request == "Book Seat") =>
      if(numberOfSeats > 0) {
        numberOfSeats -= 1
        println("Seat Booked! "+numberOfSeats+sender().path)
      }
      else{
        println("HouseFull!")
      }

    case request:String if(request == "Cancel Seat") =>
      if(numberOfSeats < 2 && numberOfSeats >= 0 ) {
      numberOfSeats += 1
      println("Booking cancelled!")
     }
  }
}


  object BookMyShow {

    def prop:Props = Props[BookMyShow]


  }

