package com.colibri.toread.web;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;

import java.io.File;

public class WebApplication extends Application {

	/**
	 * Creates a root Restlet that will receive all incoming calls.
	 */
	@Override
	public synchronized Restlet createInboundRoot() {
		
		// Create a router Restlet that routes each call to a new instance of
		// HelloWorldResource.
		Router router = new Router(getContext());		
		
		// AbsolutePath returns the current working directory
		// In Linux, it's fine
		// In Windows you need to replace the \ slashes with / slashes
		String absolutePath = new File(".").getAbsolutePath().replace("\\", "/");
		
		String imagesPath = "file:///" + absolutePath + "/resources/images/";
		System.out.println("Images path: " + imagesPath);
		String cssPath = "file:///" + absolutePath + "/resources/css/";
		System.out.println("CSS path: " + cssPath);
				
		router.attach("/images", new Directory(getContext(), imagesPath));
		router.attach("/css", new Directory(getContext(), cssPath));
		router.attach("", WebResource.class);
		router.attach("/manage", WebLoginResource.class);
		
		return router;
	}

}
