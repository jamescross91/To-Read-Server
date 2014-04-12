package com.colibri.toread.api;

import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.colibri.toread.auth.Device;
import com.colibri.toread.auth.Token;
import com.colibri.toread.auth.User;
import com.colibri.toread.persistence.MongoResource;
import com.colibri.toread.persistence.MorphiaResource;
import com.colibri.toread.persistence.UserDAO;

public class LoginResource extends ServerResource {

	@Get
	public String represent() {
		return "hello, world";
	}
	
	@Post
	public Representation acceptItem(Representation entity) {		
		System.out.println("User attempting to login");
		Form form = new Form(entity);
		
		String username = form.getFirstValue("username");
		String password = form.getFirstValue("password");
		if(username == null)
			return new JsonRepresentation(
					getErrorRepresentation("Invalid username or password")
				);
		
		UserDAO dao = new UserDAO(MorphiaResource.INSTANCE.getMorphia(), MongoResource.INSTANCE.getMongoClient());
		User thisUser = dao.findByUsername(username);
		
		//User doesnt exist
		if(thisUser == null)
			return new JsonRepresentation(
					getErrorRepresentation("Invalid username or password")
				);
		
		//Validate the password for this user
		if(password != null){
			if(!thisUser.validatePassword(password)){
				return new JsonRepresentation(
						getErrorRepresentation("Invalid username or password"));
			}
		}
		
		//They are logging in so this must be a new device, otherwise the app
		//would have a copy of the device id and auth token and would bypass
		//the login screen
		Device thisDevice = new Device();
		thisDevice.setDevice_os_id(form.getFirstValue("os_id"));
		thisDevice.setDevice_make(form.getFirstValue("make"));
		thisDevice.setDevice_model(form.getFirstValue("model"));
		thisDevice.setCellular_network(form.getFirstValue("network"));
		thisDevice.setDevice_model(form.getFirstValue("model"));
		thisDevice.setOs_version(form.getFirstValue("os_version"));
		thisDevice.setPlatform(form.getFirstValue("platform"));
		
		//Add the device to the user
		thisUser.addDevice(thisDevice);
		
		//Update the database
		dao.save(thisUser);
		
		JSONObject deviceJSON = thisDevice.toJson();
		try {
			deviceJSON.append("login_success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		//Return a JSONified version of the device containing the device id and auth code
		return new JsonRepresentation(deviceJSON);
	}
	
	private JSONObject getErrorRepresentation(String message){
			JSONObject object = new JSONObject();
			try {
				object.put("login_success", false);
				object.put("auth_token", "");
				object.put("device_id", "");
				object.put("message", message);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return object;
	}

}