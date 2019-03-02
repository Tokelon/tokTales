package com.tokelon.toktales.core.content.text;

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
