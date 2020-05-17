package com.tokelon.toktales.core.engine.setup;

import java.util.Map;

/** A collection of {@linkplain ISetupStep} that are sorted in execution order by their position.
 */
public interface ISetupSteps {


	/** Inserts the given step with the given name at the next insert position.
	 *
	 * @param name
	 * @param step
	 * @return The position the step was inserted at.
	 */
	public double insertStep(String name, ISetupStep step);

	/** Inserts the given step with the given name at the given position.
	 *
	 * @param name
	 * @param step
	 * @param position
	 */
	public void insertStep(String name, ISetupStep step, double position);


	/** Removes the step for the given name.
	 *
	 * @param name
	 */
	public void removeStep(String name);

	/** Moves the step for the given name to the given position.
	 *
	 * @param name
	 * @param newPosition
	 */
	public void moveStep(String name, double newPosition);


	/** Returns whether there is a step for the given name.
	 *
	 * @param name
	 * @return True if there is a step, false if not.
	 */
	public boolean hasStep(String name);

	/** Returns whether there is a step at the given position.
	 *
	 * @param position
	 * @return True if there is a step, false if not.
	 */
	public boolean hasPosition(double position);


	/**
	 * @param name
	 * @return The step for the given name, or null if there is none.
	 */
	public ISetupStep getStep(String name);

	/** Returns the position for the given step name, if there is one.
	 *
	 * @param name
	 * @return A position, or null if there was no step for the given name.
	 */
	public Double getStepPosition(String name);


	/** Returns the next insert position without increasing the position counter.
	 *
	 * @return The position at which the next step will be inserted.
	 * @see #claimNextInsertPosition()
	 */
	public double peekNextInsertPosition();

	/** Increases the position counter and returns the resulting position.
	 *
	 * @return The position at which the next step will be inserted.
	 * @see #peekNextInsertPosition()
	 */
	public double claimNextInsertPosition();


	/** Creates a new map with all step names as keys and their positions as values.
	 *
	 * @return A new map from step names to positions.
	 */
	public Map<String, Double> createStepNamesToPositions();

	/** Creates a new map with all step names as keys and their steps as values.
	 *
	 * @return A new map from step names to steps.
	 */
	public Map<String, ISetupStep> createNamesToSteps();

}
