/*******************************************************************************
 * The MIT License (MIT)
 * 
 * Copyright (c) 2011 - 2015 OpenWorm.
 * http://openworm.org
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the MIT License
 * which accompanies this distribution, and is available at
 * http://opensource.org/licenses/MIT
 *
 * Contributors:
 *     	OpenWorm - http://openworm.org/people.html
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights 
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
 * copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR 
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE 
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package org.geppetto.frontend.messages;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import org.geppetto.frontend.Resources;

import com.google.gson.JsonObject;

/**
 * Utility class to create JSON objects to be send to the clients. 
 * NOTE: this utility class has knowledge of what the messages looks like to communicate with the client
 * 
 * @author  Jesus R. Martinez (jesus@metacell.us)
 * @author  Giovanni Idili (giovanni@openworm.org)
 *
 */
public class TransportMessageFactory {
	
	private static final String EMPTY_STRING = "";
	
	/**
	 * Create JSON object with appropriate message for its type
	 * @param id 
	 * 
	 * @param type - Type of message of requested
	 * @return
	 */
	public static GeppettoTransportMessage getTransportMessage(String requestID, OutboundMessages type, String update){

		String messageType = type.toString();
		
		List<SimpleEntry<String, String>> params = new ArrayList<SimpleEntry<String, String>>();

		switch(type){
			case RELOAD_CANVAS:
				break;	
			case ERROR:
				params.add(new SimpleEntry<String, String>("message", update));
				break;
			case INFO_MESSAGE:
				params.add(new SimpleEntry<String, String>("message", update));
				break;
			case ERROR_LOADING_PROJECT:
				params.add(new SimpleEntry<String, String>("message", Resources.ERROR_LOADING_PROJECT_MESSAGE.toString()));
				break;
			case ERROR_DOWNLOADING_MODEL:	
				params.add(new SimpleEntry<String, String>("message", Resources.ERROR_DOWNLOADING_MODEL.toString()));
				break;
			case ERROR_DOWNLOADING_RESULTS:	
				params.add(new SimpleEntry<String, String>("message", Resources.ERROR_DOWNLOADING_MODEL.toString()));
				break;	
			case READ_URL_PARAMETERS:
				break;
			case FIRE_SIM_SCRIPTS:
				params.add(new SimpleEntry<String, String>(OutboundMessages.GET_SCRIPTS.toString(), (update!=null) ? update : EMPTY_STRING));
				break;
			case EXPERIMENT_RUNNING:
				params.add(new SimpleEntry<String, String>("update", (update!=null) ? update : EMPTY_STRING));
				break;
			case EXPERIMENT_STATUS:
				params.add(new SimpleEntry<String, String>("update", (update!=null) ? update : EMPTY_STRING));
				break;
			case DOWNLOAD_MODEL:
				params.add(new SimpleEntry<String, String>("update", (update!=null) ? update : EMPTY_STRING));
				break;
			case PLAY_EXPERIMENT:
				params.add(new SimpleEntry<String, String>("update", (update!=null) ? update : EMPTY_STRING));
				break;
			case DELETE_EXPERIMENT:
				params.add(new SimpleEntry<String, String>("update", (update!=null) ? update : EMPTY_STRING));
				break;
			case SIMULATION_CONFIGURATION:
				params.add(new SimpleEntry<String, String>("configuration", (update!=null) ? update : EMPTY_STRING));
				break;
		
			case CLIENT_ID:
				params.add(new SimpleEntry<String, String>("clientID", (update!=null) ? update : EMPTY_STRING));
				break;
			case PROJECT_PERSISTED:
				params.add(new SimpleEntry<String, String>("update", (update!=null) ? update : EMPTY_STRING));
				break;
			case PROJECT_PROPS_SAVED:
				params.add(new SimpleEntry<String, String>("update", (update!=null) ? update : EMPTY_STRING));
				break;
			default:
				params.add(new SimpleEntry<String, String>(type.toString(),  (update!=null) ? update : EMPTY_STRING));
				break;
		}
		
		return createTransportMessage(requestID,messageType, params);
	}
	
	/**
	 * Create JSON object with type and parameters
	 * 
	 * @param type - Type of message 
	 * @param params - list of name-value pairs representing parameter names and values
	 * @return
	 */
	private static GeppettoTransportMessage createTransportMessage(String requestID,String type, List<SimpleEntry<String, String>> params){
		GeppettoTransportMessage msg = new GeppettoTransportMessage();
		
		// JSON nested object stored in the data field of the transport message
		JsonObject json = new JsonObject();
		for(SimpleEntry<String, String> param : params)
		{
			json.addProperty(param.getKey(), param.getValue());
		}

		msg.requestID = requestID;
		msg.type = type;
		// data stored as a string (could be anything) - will be interpreted by the client as a json object
		msg.data = json.toString();
		
		return msg;
	}
}
