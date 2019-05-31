package com.tokelon.toktales.core.engine.inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.inject.Module;
import com.google.inject.Stage;
import com.google.inject.util.Modules;

import java9.util.stream.Collectors;
import java9.util.stream.StreamSupport;

public class HierarchicalInjectConfig implements IHierarchicalInjectConfig {

	
    private Stage defaultStage = Stage.PRODUCTION;
    
    private List<Module> configModules = new ArrayList<>();
    
    private List<Class<? extends Module>> configFilterModules = new ArrayList<>();
    
    
    public HierarchicalInjectConfig() { }
    
    @SafeVarargs
	public HierarchicalInjectConfig(Class<? extends Module>... filterModules) {
    	configFilterModules.addAll(Arrays.asList(filterModules));
	}
    
    public HierarchicalInjectConfig(Collection<Class<? extends Module>> filterModules) {
    	configFilterModules.addAll(filterModules);
	}
    
    
    @Override
	public HierarchicalInjectConfig extend(Module... modules) {
        return extend(Arrays.asList(modules));
    }
    
    @Override
	public HierarchicalInjectConfig extend(Collection<Module> modules) {
    	Collection<Module> filteredModules = filterModules(modules);
        configModules.addAll(filteredModules);
        return this;
    }
    
    
    @Override
	public HierarchicalInjectConfig override(Module... modules) {
        return override(Arrays.asList(modules));
    }
    
    @Override
	public HierarchicalInjectConfig override(Collection<Module> modules) {
    	Collection<Module> filteredModules = filterModules(modules);
        if(configModules.isEmpty()) {
            configModules.addAll(filteredModules);
        }
        else {
            Module overrideModule = Modules.override(configModules).with(filteredModules);
            
            configModules = new ArrayList<>();
            configModules.add(overrideModule);    
        }
        
        return this;
    }
    
    
    @Override
    public IHierarchicalInjectConfig filter(@SuppressWarnings("unchecked") Class<? extends Module>... modules) {
    	return filter(Arrays.asList(modules));
    }
    
    @Override
    public IHierarchicalInjectConfig filter(Collection<Class<? extends Module>> modules) {
    	configFilterModules.addAll(modules);
    	return this;
    }

    
    protected Collection<Module> filterModules(Collection<Module> modules) {
    	if(modules.isEmpty()) {
    		return modules;
    	}
    	
    	List<Module> result = StreamSupport.stream(modules)
    	.filter(m -> StreamSupport.stream(configFilterModules).noneMatch(c -> c.isInstance(m)))
    	.collect(Collectors.toList());

    	return result;
    }


    @Override
	public HierarchicalInjectConfig setDefaultStage(Stage stage) {
        this.defaultStage = stage;
        return this;
    }
    
    @Override
    public Stage getDefaultStage() {
        return defaultStage;
    }
    
    @Override
    public Collection<Module> getModules() {
        return configModules;
    }
    
}
