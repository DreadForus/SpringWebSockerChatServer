package com.example.websocketdemo.security.websocket;

import java.util.List;

public interface WebSocketTraceRepository {

	/**
	 * Find all objects contained in the repository.
	 * @return the results
	 */
	List<WebSocketTrace> findAll();

	/**
	 * Adds a trace to the repository.
	 * @param trace the trace to add
	 */
	void add(WebSocketTrace trace);
}