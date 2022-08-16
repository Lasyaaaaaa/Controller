package stepdefination;

import Models.SuperHero;
import Utils.EndPoints;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;

import static Utils.TestngListener.userData;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class MyStepdefs {

    ObjectMapper objectMapper = new ObjectMapper();
    JSONObject jsonObject;
    JsonPath jsonpath;
    SuperHero superHero;
    Response response;
    SuperHero responseHero;
    SuperHero responseHero1;

    @Given("User Details")
    public void userDetails() {
        jsonObject = (JSONObject) userData.get("createUsers");


    }

    @When("creating a User")
    public void creatingAUser()  throws JsonProcessingException {
        superHero=new SuperHero((String) jsonObject.get("name"), (String) jsonObject.get("superName"),
                (String) jsonObject.get("profession"), (Long) jsonObject.get("age"),(Boolean)jsonObject.get("canFly"));
        response=given().body(superHero).post(EndPoints.endpoint)
                .then().body(matchesJsonSchemaInClasspath("User_Schema.json"))
                .statusCode(201).extract().response();
        responseHero=objectMapper.readValue(response.asString(), SuperHero.class);
    }



    @Then("User must  be Created")
    public void userMustBeCreated() {
        Assert.assertEquals(superHero.getName(),responseHero.getName());
        Assert.assertEquals(superHero.getSuperName(),responseHero.getSuperName());
        Assert.assertEquals(superHero.getProfession(),responseHero.getProfession());
        Assert.assertEquals(superHero.getAge(),responseHero.getAge());
        Assert.assertEquals(superHero.getCanFly(),responseHero.getCanFly());

    }


    @When("creating a user with null name")
    public void creatingAUserWithNullName() {
        superHero=new SuperHero(null, (String) jsonObject.get("superName"), (String) jsonObject.get("profession"),
                (Long) jsonObject.get("age"),(Boolean) jsonObject.get("canFly"));
        response=given().body(superHero).post(EndPoints.endpoint)
                .then()
                .statusCode(400).extract().response();

    }

    @Then("Name is required error appears")
    public void nameIsRequiredErrorAppears() {
        jsonpath = new JsonPath(response.asString());
        Assert.assertEquals(jsonpath.getString("message"),"Name is required");

    }

    @When("Creating a user with null super name")
    public void creatingAUserWithNullSuperName() {
        superHero=new SuperHero((String) jsonObject.get("name"),  null, (String) jsonObject.get("profession"),
                (Long)jsonObject.get("age"),(Boolean) jsonObject.get("canFly"));
        response=given().body(superHero).post(EndPoints.endpoint).then()
                .statusCode(400).extract().response();
    }

    @Then("Super name is required error appears")
    public void superNameIsRequiredErrorAppears() {
        jsonpath=new JsonPath(response.asString());
        Assert.assertEquals(jsonpath.getString("message"),"Super Name is required");
    }

    @When("Creating user with null profession")
    public void creatingUserWithNullProfession() {
        superHero=new SuperHero((String) jsonObject.get("name"), (String) jsonObject.get("superName"),null,
                (Long)jsonObject.get("age"),(Boolean) jsonObject.get("canFly"));
        response=given().body(superHero).post(EndPoints.endpoint).then().
                statusCode(400).extract().response();

    }

    @Then("profession is required error appears")
    public void professionIsRequiredErrorAppears() {
        jsonpath=new JsonPath(response.asString());
        Assert.assertEquals(jsonpath.getString("message"),"Profession is required");
    }

    @When("Creating user with null age")
    public void creatingUserWithNullAge() {
        superHero=new SuperHero((String) jsonObject.get("name"), (String) jsonObject.get("superName"),
                (String) jsonObject.get("profession"),0,(Boolean) jsonObject.get("canFly"));
        response=given().body(superHero).post(EndPoints.endpoint).then().
                statusCode(400).extract().response();

    }

    @Then("Age is required error appears")
    public void ageIsRequiredErrorAppears() {
        jsonpath=new JsonPath(response.asString());
        Assert.assertEquals(jsonpath.getString("message"),"Age is required");
    }

    @When("Creating user with blank canFly")
    public void creatingUserWithBlankCanFly() throws JsonProcessingException {
        superHero=new SuperHero((String) jsonObject.get("name"), (String) jsonObject.get("superName"),
                (String) jsonObject.get("profession"), (Long) jsonObject.get("age"),
                (Boolean)jsonObject.get("canFly"));
        if(superHero.getCanFly()==true)
        {
            superHero.setCanFly(false);
        }
        response=given().body(superHero).post(EndPoints.endpoint).then()
                .statusCode(201).extract().response();
        responseHero=objectMapper.readValue(response.asString(),SuperHero.class);

    }



    @Then("User must Created")
    public void userMustCreated() {

    }



    @And("updating a user name")
    public void updatingAUserName() throws JsonProcessingException {
        jsonObject=(JSONObject)userData.get("updateUsers");
        superHero.setName((String) jsonObject.get("name"));
        response=given().body(superHero).when().put(EndPoints.endpoint+"/"+responseHero.getId())
                .then().statusCode(200).extract().response();
        responseHero1=objectMapper.readValue(response.asString(),SuperHero.class);
    }

    @Then("User name must be updated")
    public void userNameMustBeUpdated() {
        Assert.assertEquals(responseHero.getId(),responseHero1.getId());
        Assert.assertEquals(superHero.getName(),responseHero1.getName());
    }
    @And("updating super name")
    public void updatingSuperName() throws JsonProcessingException {
            jsonObject = (JSONObject) userData.get("updateUsers");
            superHero.setName((String) jsonObject.get("superName"));
            response = given().body(superHero).when().put(EndPoints.endpoint + "/" + responseHero.getId())
                    .then().statusCode(200).extract().response();
            responseHero1 = objectMapper.readValue(response.asString(), SuperHero.class);

        }


        @Then("User super name must be updated")
        public void userSuperNameMustBeUpdated () {
            Assert.assertEquals(responseHero.getId(),responseHero1.getId());
            Assert.assertEquals(superHero.getName(),responseHero1.getName());

        }

    @And("updating profession")
    public void updatingProfession() throws JsonProcessingException {
        jsonObject = (JSONObject) userData.get("updateUsers");
        superHero.setName((String) jsonObject.get("profession"));
        response = given().body(superHero).when().put(EndPoints.endpoint + "/" + responseHero.getId())
                .then().statusCode(200).extract().response();
        responseHero1 = objectMapper.readValue(response.asString(), SuperHero.class);


    }

    @Then("User profession must be updated")
    public void userProfessionMustBeUpdated() {
        Assert.assertEquals(responseHero.getId(),responseHero1.getId());
        Assert.assertEquals(superHero.getName(),responseHero1.getName());



    }

    @And("updating age")
    public void updatingAge() throws JsonProcessingException {
        jsonObject=(JSONObject)userData.get("updateUsers");
        superHero.setAge((Long) jsonObject.get("age"));
    response=given().body(superHero).when().put(EndPoints.endpoint+"/"+responseHero.getId())
            .then().statusCode(200).extract().response();
    responseHero1=objectMapper.readValue(response.asString(),SuperHero.class);
}
    @Then("User age must be updated")
    public void userAgeMustBeUpdated() {
        Assert.assertEquals(responseHero.getId(),responseHero1.getId());
        Assert.assertEquals(superHero.getName(),responseHero1.getName());


    }

    @And("updating canFly  status of user")
    public void updatingCanFlyStatusOfUser() throws JsonProcessingException {
        jsonObject=(JSONObject)userData.get("updateUsers");
        superHero.setCanFly((Boolean) jsonObject.get("canFly"));
        response=given().body(superHero).when().put(EndPoints.endpoint+"/"+responseHero.getId())
                .then().statusCode(200).extract().response();
        responseHero1=objectMapper.readValue(response.asString(),SuperHero.class);
    }


    @Then("User canFly status must be updated")
    public void userCanFlyStatusMustBeUpdated() {
        Assert.assertEquals(responseHero.getId(),responseHero1.getId());
        Assert.assertEquals(superHero.getName(),responseHero1.getName());

    }

    @Then("List of user must be displayed")
    public void listOfUserMustBeDisplayed() {
        given().when().get(EndPoints.endpoint).then().statusCode(200).extract().response();

    }

    @Then("user with particular id must be displayed")
    public void userWithParticularIdMustBeDisplayed() {
        given().when().get(EndPoints.endpoint+"/"+responseHero.getId()).then()
                .statusCode(200).extract().response();
    }

    @Then("Deleting a user")
    public void deletingAUser() {
        given().when().delete(EndPoints.endpoint+"/"+responseHero.getId()).
                then().statusCode(200).extract().response();
    }

    @Then("Deleting a invalid user")
    public void deletingAInvalidUser() {
        given().when().delete(EndPoints.endpoint+"/"+200)
                .then().statusCode(200).extract().response();
    }
}











