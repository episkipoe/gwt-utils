package com.episkipoe.common;

import java.util.Collection;

public interface Criterion {
	public boolean valid();
	public Collection<String> getFailureMessage();
}
