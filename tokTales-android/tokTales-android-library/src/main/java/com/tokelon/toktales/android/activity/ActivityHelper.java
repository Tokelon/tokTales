package com.tokelon.toktales.android.activity;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.inject.Injector;
import com.tokelon.toktales.android.activity.integration.IActivityIntegration;
import com.tokelon.toktales.android.activity.integration.IActivityIntegrator;
import com.tokelon.toktales.android.activity.integration.IActivityIntegrator.IActivityIntegratorFactory;
import com.tokelon.toktales.android.activity.integration.IDefaultActivityIntegratorBuilder;
import com.tokelon.toktales.android.activity.integration.IInjectActivityIntegratorBuilder;
import com.tokelon.toktales.android.activity.integration.IIntegratedActivity;
import com.tokelon.toktales.core.engine.TokTales;

import android.app.Activity;

public final class ActivityHelper {

	private ActivityHelper() {}
	
	

	/** Injects dependencies into the given activity.
	 * 
	 * @param activity
	 */
	public static void injectActivityDependencies(Activity activity) {
		Injector injector = TokTales.getInjector();
		injector.injectMembers(activity);
	}
	
	
	/** Creates an integrator builder that will utility injection.
	 * 
	 * <b>If there are any declared dependencies in integrations passed to the builder they will be injected.</b>
	 * 
	 * @return A new integrator builder.
	 */
	public static IInjectActivityIntegratorBuilder createActivityIntegratorBuilder() {
		return TokTales.getInjector().getInstance(IInjectActivityIntegratorBuilder.class);
	}

	/** Creates a default integrator builder that will utilize injection.
	 * 
	 * <b>If there are any declared dependencies in integrations passed to the builder they will be injected.</b>
	 * 
	 * @return A new default integrator builder.
	 */
	public static IDefaultActivityIntegratorBuilder createDefaultActivityIntegratorBuilder() {
		return TokTales.getInjector().getInstance(IDefaultActivityIntegratorBuilder.class);
	}
	
	
	/** Creates an integrator for the given activity with the given integrations.<p>
	 * 
	 * <b>If there are any declared dependencies in the given integrations they will be injected.</b>
	 * 
	 * @param activity
	 * @param integrations
	 * @return A new integrator with the given configuration.
	 */
	public static IActivityIntegrator createActivityIntegrator(IIntegratedActivity activity, IActivityIntegration... integrations) {
		return createActivityIntegrator(activity, Arrays.asList(integrations));
	}
	
	/** Creates an integrator for the given activity with the given integrations.<p>
	 * 
	 * <b>If there are any declared dependencies in the given integrations they will be injected.</b>
	 * 
	 * @param activity
	 * @param integrations
	 * @return A new integrator with the given configuration.
	 */
	public static IActivityIntegrator createActivityIntegrator(IIntegratedActivity activity, Iterable<IActivityIntegration> integrations) {
		Injector injector = TokTales.getInjector();
		
		for(IActivityIntegration integration: integrations) {
			injector.injectMembers(integration);
		}

		IActivityIntegratorFactory activityIntegratorFactory = injector.getInstance(IActivityIntegratorFactory.class);
		return activityIntegratorFactory.create(activity, integrations);
	}
	
	
	/** Creates an integrator for the given activity with the given integration types.<p>
	 * 
	 * <b>The integrations will be created via dependency injection.</b>
	 * 
	 * @param activity
	 * @param integrationTypes
	 * @return A new integrator with the given configuration.
	 */
	@SafeVarargs
	public static IActivityIntegrator createActivityIntegratorWithTypes(IIntegratedActivity activity, Class<? extends IActivityIntegration>... integrationTypes) {
		return createActivityIntegratorWithTypes(activity, Arrays.asList(integrationTypes));
	}
	
	/** Creates an integrator for the given activity with the given integration types.<p>
	 * 
	 * <b>The integrations will be created via dependency injection.</b>
	 * 
	 * @param activity
	 * @param integrationTypes
	 * @return A new integrator with the given configuration.
	 */
	public static IActivityIntegrator createActivityIntegratorWithTypes(IIntegratedActivity activity, Iterable<Class<? extends IActivityIntegration>> integrationTypes) {
		Injector injector = TokTales.getInjector();
		
		ArrayList<IActivityIntegration> integrations = new ArrayList<>();
		for(Class<? extends IActivityIntegration> type: integrationTypes) {
			integrations.add(injector.getInstance(type));
		}
		
		IActivityIntegratorFactory activityIntegratorFactory = injector.getInstance(IActivityIntegratorFactory.class);
		return activityIntegratorFactory.create(activity, integrations);
	}
	
}
