package org.betehess.sudoku.client;

import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SampleRemoteServiceAsync {
	public void mytest(Set<String> constraints, AsyncCallback<Set<String>> callback);
}
