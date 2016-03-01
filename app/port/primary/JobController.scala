package port.primary

import application.JobApplicationService
import com.google.inject.Inject
import play.api.libs.json.Json
import play.api.mvc._

class JobController @Inject()(jobApplicationService: JobApplicationService) extends Controller {

  def index = Action  {
    Ok(views.html.index("Long running process with REST"))
  }

  def create() = Action { implicit request =>
    val jobId = jobApplicationService.createNewJob()
    //Accepted.withHeaders(LOCATION -> routes.JobController.showProcessing(jobId.value.toString).absoluteURL())
    SeeOther(routes.JobController.showProcessing(jobId.value.toString).absoluteURL())
  }

  def showProcessing(id: String) = Action { implicit request =>
    jobApplicationService.status(id).fold(
      finishedJob => MovedPermanently(routes.JobController.show(finishedJob.id.value.toString).absoluteURL()), {
        case (Some(job)) => Ok(Json.toJson(job))
        case None => NotFound
      }
    )
  }

  def show(id: String) = Action {
    jobApplicationService.showFinishedJob(id) match {
      case Some(job) => Ok(Json.toJson(job))
      case None => NotFound
    }
  }
}
