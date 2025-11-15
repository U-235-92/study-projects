package jp.tam.util;

public class Switcher {

	private int characterIndex;
	private int parameterIndex;
	
	private ParameterStore parameterStore;
	
	private CharacterStore characterStore;
	
	public Switcher(ParameterStore parameterStore, CharacterStore characterStore) {
		this.parameterStore = parameterStore;
		this.characterStore = characterStore;
	}
	
	public void switchCharacterIndex(int path) {
		if(path >= 1) {
			if(characterIndex >= characterStore.getCharacterList().size() - 1) {
				characterIndex = 0;
			} else {
				characterIndex++;
			}
		} else if(path <= -1) {
			if(characterIndex <= 0) {
				characterIndex = characterStore.getCharacterList().size() - 1;
			} else {
				characterIndex--;
			}
		} else {
			characterIndex = 0;
		}
	}
	
	public void switchParameterIndex(int path) {
		if(path >= 1) {
			if(parameterIndex >= parameterStore.getParameterList().size() - 1) {
				parameterIndex = 0;
			} else {
				parameterIndex++;
			}
		} else if(path <= -1) {
			if(parameterIndex <= 0) {
				parameterIndex = parameterStore.getParameterList().size() - 1;
			} else {
				parameterIndex--;
			}
		} else {
			characterIndex = 0;
		}
	}
	
	public int getCharacterIndex() {
		return characterIndex;
	}
	
	public int getParameterIndex() {
		return parameterIndex;
	}
}
