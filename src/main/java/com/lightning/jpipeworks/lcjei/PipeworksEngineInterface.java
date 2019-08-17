package com.lightning.jpipeworks.lcjei;

import java.awt.Container;

import com.lightning.jpipeworks.Engine;
import com.lightning.jpipeworks.Game;

import github.lightningcreations.lcjei.IEngineInterface;

/**
 * Interface for manipulating Pipeworks Engines according to LCJEI.
 * @author chorm
 *
 */
public class PipeworksEngineInterface implements IEngineInterface<Game> {
	private Engine e;
	private Game g;
	private Container c;
	/**
	 * Creates a new PipeworksEngineInterface in an uninitialized state bound to g
	 * @param g the game to bind
	 * @throws IllegalStateException if the game is associated with an existing engine.
	 */
	public PipeworksEngineInterface(Game g) {
		this.g = g;
		this.e = new Engine(g);
	}
	
	/**
	 * Creates a new PipeworksEngineInterface wrapping an already executing Engine. The game associated with this interface is the game associated with e.
	 * @param e The engine to bind to
	 */
	public PipeworksEngineInterface(Engine e) {
		this.e = e;
		this.g = e.getRunningGame();
	}
	
	public Engine getDirectEngine() {
		return e;
	}

	@Override
	public boolean initialize(Container c) throws IllegalStateException {
		//this.c = c;
		initialize();
		return false;
	}

	@Override
	public void initialize() throws IllegalStateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() throws IllegalStateException {
		e.isClosing = true;//Pretty Easy to destroy a Pipeworks Engine.
	}

	@Override
	public void run() throws IllegalStateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Container getCurrentDrawContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Game getGameObject() {
		// TODO Auto-generated method stub
		return g;
	}

	@Override
	public void suspend() throws IllegalStateException {
		
	}

	@Override
	public void resume() throws IllegalStateException {
		// TODO Auto-generated method stub
		
	}

}
