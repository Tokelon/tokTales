package com.tokelon.toktales.core.content.manage.codepoint;

import com.tokelon.toktales.core.content.text.ICodepoint;

public class CodepointAsset implements ICodepointAsset {

	
	private final ICodepoint codepoint;

	public CodepointAsset(ICodepoint codepoint) {
		this.codepoint = codepoint;
	}
	
	
	@Override
	public ICodepoint getCodepoint() {
		return codepoint;
	}

	
	@Override
	public void dispose() {
		codepoint.dispose();
	}
	
}
