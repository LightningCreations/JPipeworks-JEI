package com.lightning.jpipeworks.lcjei;

import java.awt.Container;
import java.util.Optional;

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

	private Optional<PipeworksResourceController> res;
	/**
	 * Creates a new PipeworksEngineInterface in an uninitialized state bound to g
	 * @param g the game to bind
	 * @throws IllegalStateException if the game is associated with an existing engine.
	 */
	public PipeworksEngineInterface(Game g) {
		this.g = g;
		this.e = new Engine(g);
	}
	
	public PipeworksResourceController getResourceController() {
		if(res.isPresent())
			return res.get();
		else {
			res = Optional.of(new PipeworksResourceController(e));
			return res.get();
		}
			
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
		if(c == null){
			initialize();
			return false;
		}
		getDirectEngine().init(c);
		return true;
	}

	@Override
	public void initialize() throws IllegalStateException {
		getDirectEngine().init();
	}

	@Override
	public void destroy() throws IllegalStateException {
		try {
			getDirectEngine().closeJoin();
		} catch (InterruptedException ex) {

		}
	}

	@Override
	public void run() throws IllegalStateException {
		getDirectEngine().start();
	}

	@Override
	public Container getCurrentDrawContainer() {
		return getDirectEngine().getContainer();
	}

	@Override
	public Game getGameObject() {
		return g;
	}

	@Override
	public void suspend() throws IllegalStateException {
		getDirectEngine().suspend();
	}

	@Override
	public void resume() throws IllegalStateException {
		getDirectEngine().resume();
	}

}
