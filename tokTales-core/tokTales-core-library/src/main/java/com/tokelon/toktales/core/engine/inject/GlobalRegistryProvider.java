package com.tokelon.toktales.core.engine.inject;

import java.util.Map;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.inject.annotation.GlobalRegistryAliases;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalRegistryEntries;

public class GlobalRegistryProvider extends RegistryProvider {


	@Inject
	public GlobalRegistryProvider(@GlobalRegistryEntries Map<Object, Object> entries, @GlobalRegistryAliases Map<Object, Object> aliases) {
		super(entries, aliases);
	}

}
