package com.tokelon.toktales.core.engine.setup;

import java.util.HashMap;
import java.util.Map;

public class SetupSteps implements ISetupSteps {


	private double nextInsertPosition = 1d;

	private final Map<String, Double> namesToPositions;
	private final Map<String, ISetupStep> namesToSteps;

	public SetupSteps() {
		this(new HashMap<>(), new HashMap<>());
	}

	public SetupSteps(Map<String, Double> namesToPositions, Map<String, ISetupStep> namesToSteps) {
		this.namesToPositions = namesToPositions;
		this.namesToSteps = namesToSteps;
	}


	@Override
	public void insertStep(String name, ISetupStep step) {
		insertStep(name, step, nextInsertPosition++);
	}

	@Override
	public void insertStep(String name, ISetupStep step, double position) {
		namesToPositions.put(name, position);
		namesToSteps.put(name, step);
	}

	@Override
	public void removeStep(String name) {
		namesToPositions.remove(name);
		namesToSteps.remove(name);
	}

	@Override
	public void moveStep(String name, double newPosition) {
		namesToPositions.put(name, newPosition);
	}

	@Override
	public boolean hasStep(String name) {
		return namesToSteps.containsKey(name);
	}

	@Override
	public boolean hasPosition(double position) {
		return namesToPositions.containsValue(position);
	}

	@Override
	public ISetupStep getStep(String name) {
		return namesToSteps.get(name);
	}

	@Override
	public Double getStepPosition(String name) {
		return namesToPositions.get(name);
	}

	@Override
	public Map<String, Double> createStepNamesToPositions() {
		return new HashMap<>(namesToPositions);
	}

	@Override
	public Map<String, ISetupStep> createNamesToSteps() {
		return new HashMap<>(namesToSteps);
	}

}
