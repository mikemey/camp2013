package uk.mmi.dw;

import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.common.base.Optional;
import com.yammer.metrics.annotation.Timed;

@Path("/hw")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
	private final String template = "Hello %s!";
	private final String defaultTemplate = "Hello! Who are you again?";
	private final AtomicLong counter;

	public HelloWorldResource() {
		counter = new AtomicLong();
	}

	@GET
	@Timed
	public HelloWorldReply respondToWorld(@QueryParam("name") Optional<String> name) {
		String repl;
		if (name.isPresent()) {
			repl = String.format(template, name.get());
		} else {
			repl = defaultTemplate;
		}
		return new HelloWorldReply(counter.incrementAndGet(), repl);
	}
}
