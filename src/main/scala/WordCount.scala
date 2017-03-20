import java.io.{FileNotFoundException, File}

import akka.actor.{Actor, Props, ActorSystem}

import scala.io.Source

class WordCount extends Actor{
  var wcount = 0
  val sendLine = context.actorOf(Props[childActor])
  override def receive ={
    case "EOF" => println("no. of words in file : " +wcount)
    case msg:String =>
      try {
        for (line <- Source.fromFile(msg).getLines()) {
          sendLine ! line
          //                println("for loop")
        }
        sendLine ! "EOF"
      }
      catch{
        case ex: FileNotFoundException => {
          println("File not found!!!")
        }
      }
    case count: Int => wcount += count
  }
}

class childActor extends WordCount{
  override def receive ={
    case "EOF" => sender ! "EOF"
    case msg:String => sender ! msg.split("\\W+").size
    //         println("in child")
  }
}

object WordCount extends App{

  val system = ActorSystem("WordCount")
  val props = Props[WordCount]
  val ref = system.actorOf(props)
  val file = ("myfile.txt")
  ref ! file


}