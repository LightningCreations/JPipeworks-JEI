package com.lightning.jpipeworks.lcjei;

import java.util.Optional;
import java.util.stream.Stream;

import com.lightning.jpipeworks.Engine;
import com.lightning.jpipeworks.Game;

import github.lightningcreations.lcjei.IEngineInterface;
import github.lightningcreations.lcjei.service.JEIServiceProvider;

public class PipeworksJEIServiceProvider implements JEIServiceProvider<Game> {

	public PipeworksJEIServiceProvider() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<Game> getGameClass() {
		// TODO Auto-generated method stub
		return Game.class;
	}

	@Override
	public String getEngineName() {
		// TODO Auto-generated method stub
		return "pipeworks";
	}

	@Override
	public IEngineInterface<Game> newEngine(Game game) {
		// TODO Auto-generated method stub
		return new PipeworksEngineInterface(game);
	}

	@Override
	public Optional<IEngineInterface<Game>> getActiveEngineInterface() {
		// TODO Auto-generated method stub
		return getActiveEngines().findAny();
	}

	
	public Stream<IEngineInterface<Game>> getActiveEngines() {
		return Engine.getRunningPipeworksEngines().map(PipeworksEngineInterface::new);
	}

}
