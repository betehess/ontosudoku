package org.betehess.sudoku.client;

import java.util.Set;

import com.google.gwt.user.client.rpc.RemoteService;

public interface SampleRemoteService extends RemoteService {
	public Set<String> mytest(Set<String> constraints);
}
