package com.tokelon.toktales.core.engine.setup;

import java.util.Map;

public interface ISetupSteps {


	public void insertStep(String name, ISetupStep step);
	public void insertStep(String name, ISetupStep step, double position);

	
	public void removeStep(String name);
	
	public void moveStep(String name, double newPosition);
	

	public boolean hasStep(String name);
	
	public boolean hasPosition(double position);
	
	
	public ISetupStep getStep(String name);
	
	public double getStepPosition(String name);
	
	
	public Map<String, Double> createStepNamesToPositions();
	
	public Map<String, ISetupStep> createNamesToSteps();
	
}
