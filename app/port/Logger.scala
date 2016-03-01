package port

import com.google.inject.Singleton


@Singleton
class Logger {

  def debug(s: String) = println(s)

}
