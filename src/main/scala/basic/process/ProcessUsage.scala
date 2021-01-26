package basic.process

import scala.sys.process._

object ProcessUsage extends App {
  val sampleCmd = "python -u any_python_program.py"
  val cmdToRun = "ls"
  println("Starting App")
  // Process receives cmd:String or cmd:Seq[String]
  // for example. Seq("sleep","500")
  // if you run cmd on Windows, add cmd,/C prefix.
  val p = Process(Seq("cmd","/C","echo","helloFromProcess"))
  val running = p.run{
    // capture the stdout from python program.
    new ProcessLogger {
      override def out(s: => String): Unit = println(s)

      override def err(s: => String): Unit = println(s)

      override def buffer[T](f: => T): T = ???
    }
  }
  sys.addShutdownHook{
    println("Shutting down application...")
    println(s"Process finished in shutdown hook with code ${running.exitValue()}")
  }
}
// NOTE: you can learn more about scala.sys.process.
// at https://alvinalexander.com/scala/how-to-build-pipeline-external-commands-in-scala/