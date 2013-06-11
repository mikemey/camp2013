package uk.mmi.dw.health;

import com.yammer.metrics.core.HealthCheck;

public abstract class AssertHealthCheck extends HealthCheck {

	public AssertHealthCheck(String name) {
		super(name);
	}

	@Override
	protected final Result check() throws Exception {
		try {
			assertHealth();
			return Result.healthy();
		} catch (AssertionError e) {
			return Result.unhealthy(e.getMessage());
		}
	}

	protected abstract void assertHealth() throws Exception;
}
