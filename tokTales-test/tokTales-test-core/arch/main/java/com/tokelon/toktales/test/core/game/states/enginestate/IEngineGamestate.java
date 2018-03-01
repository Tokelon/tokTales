package com.tokelon.toktales.test.core.game.states.enginestate;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

import com.tokelon.toktales.core.game.states.IGameState;

public interface IEngineGamestate extends IGameState {

	
	
	@Qualifier // @BindingAnnotation
	@Target({FIELD, PARAMETER, METHOD})
	@Retention(RUNTIME)
	public @interface IEngineGamestateType {

	}

}
