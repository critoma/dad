package eu.ase.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

// http://127.0.0.1:8080/s12_jweb_rest/rest/TrackJsonService/metallica/get

@Path ("TrackJsonService/metallica")
public class JsonService {

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Track getTrackInJSON() {
		Track track = new Track();
		track.setTitle("Nothing Else Matters");
		track.setSinger("Metallica");
		
		return track;
	}
	
}
