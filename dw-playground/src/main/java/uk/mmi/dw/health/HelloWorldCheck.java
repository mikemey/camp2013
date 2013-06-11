package uk.mmi.dw.health;

import junit.framework.Assert;
import uk.mmi.dw.HelloWorldReply;
import uk.mmi.dw.HelloWorldResource;

import com.google.common.base.Optional;

public class HelloWorldCheck extends AssertHealthCheck {

	private final HelloWorldResource helloWorldResource;

	public HelloWorldCheck(HelloWorldResource helloWorldResource) {
		super("hello-world health-check");
		this.helloWorldResource = helloWorldResource;
	}

	@Override
	protected void assertHealth() throws Exception {
		Assert.assertNotNull(helloWorldResource);
		HelloWorldReply reply = helloWorldResource.respondToWorld(Optional.<String> absent());

		Assert.assertEquals("Hello! Who are you again?", reply.getContent());

		reply = helloWorldResource.respondToWorld(Optional.<String> of("me"));
		Assert.assertEquals("Hello me!", reply.getContent());
	}
}
