package controllers

import models.{DB, Person}
import play.api._
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import play.api.libs.json._

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your app in scala and play!!"))
  }

  val personForm: Form[Person] = Form {
    mapping(
      "name" -> text 
    )(Person.apply)(Person.unapply)
  }

  def addPerson = Action { implicit request => 
    val person = personForm.bindFromRequest.get
    DB.save(person)
    Redirect(routes.Application.index())
  }

  def getPersons = Action {
    val persons = DB.query[Person].fetch
    Ok(Json.toJson(persons))
  }

}
