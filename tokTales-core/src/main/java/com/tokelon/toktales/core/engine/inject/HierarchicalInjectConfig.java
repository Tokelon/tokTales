package com.tokelon.toktales.core.engine.inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.inject.Module;
import com.google.inject.Stage;
import com.google.inject.util.Modules;

public class HierarchicalInjectConfig implements IHierarchicalInjectConfig {

    private List<Module> moduleList = new ArrayList<>();
    
    private Stage defaultStage = Stage.PRODUCTION;
    
    
    @Override
	public HierarchicalInjectConfig extend(Module... modules) {
        return extend(Arrays.asList(modules));
    }
    
    @Override
	public HierarchicalInjectConfig extend(Collection<Module> modules) {
        moduleList.addAll(modules);
        return this;
    }
    
    
    @Override
	public HierarchicalInjectConfig override(Module... modules) {
        return override(Arrays.asList(modules));
    }
    
    @Override
	public HierarchicalInjectConfig override(Collection<Module> modules) {
        if(moduleList.isEmpty()) {
            moduleList.addAll(modules);    
        }
        else {
            Module overrideModule = Modules.override(moduleList).with(modules);
            
            moduleList = new ArrayList<>();
            moduleList.add(overrideModule);    
        }
        
        return this;
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
        return moduleList;
    }
    
}
