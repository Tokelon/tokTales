package com.tokelon.toktales.core.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.inject.Module;
import com.google.inject.Stage;
import com.google.inject.util.Modules;

public class AdvancedInjectConfig implements IInjectConfig {

    private List<Module> moduleList = new ArrayList<>();
    
    private Stage defaultStage = Stage.PRODUCTION;
    
    
    /** Combines all current modules with the given ones.
     * <p>
     * Note: When passing an array, do not modify it afterwards.
     * 
     * @param modules
     * @return
     */
    public AdvancedInjectConfig extend(Module... modules) {
        return extend(Arrays.asList(modules));
    }
    
    /** Combines all current modules with the given ones.
     * 
     * @param modules
     * @return
     */
    public AdvancedInjectConfig extend(Collection<Module> modules) {
        moduleList.addAll(modules);
        return this;
    }
    
    
    /** Combines all current modules with the given ones, but marks the given ones as overrides.
     * <p>
     * Note: When passing an array, do not modify it afterwards.
     * 
     * @param modules
     * @return
     */
    public AdvancedInjectConfig override(Module... modules) {
        return override(Arrays.asList(modules));
    }
    
    /** Combines all current modules with the given ones, but marks the given ones as overrides.
     * 
     * @param modules
     * @return
     */
    public AdvancedInjectConfig override(Collection<Module> modules) {
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
    

    /** Sets the default stage.
     * 
     * @param stage The stage that will be used if none is provided.
     * @return This object.
     */
    public AdvancedInjectConfig setDefaultStage(Stage stage) {
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
