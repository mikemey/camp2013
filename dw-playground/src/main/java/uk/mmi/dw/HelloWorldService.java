package uk.mmi.dw;

import org.apache.commons.lang.ArrayUtils;

import uk.mmi.dw.health.HelloWorldCheck;
import uk.mmi.movies.MovieResource;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.config.Environment;

public class HelloWorldService extends Service<Configuration> {

	public static void main(String[] args) throws Exception {
		String[] serverArgs = (String[]) ArrayUtils.add(args, "server");
		serverArgs = (String[]) ArrayUtils.add(serverArgs, "src/main/resources/service.yaml");
		new HelloWorldService().run(serverArgs);
	}

	@Override
	public void initialize(Bootstrap<Configuration> bootstrap) {
		bootstrap.setName("hello-world");
		bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html"));
	}

	@Override
	public void run(Configuration configuration, Environment environment) throws Exception {
		HelloWorldResource helloWorld = new HelloWorldResource();
		environment.addResource(helloWorld);
		environment.addResource(new MovieResource());
		environment.addHealthCheck(new HelloWorldCheck(helloWorld));
	}

}
