package jp.tam.util;

import jp.tam.character.Character;
import java.util.ArrayList;
import java.util.List;

public class CharacterStore {

	private List<Character> characterList;
	
	public CharacterStore() {
		characterList = new ArrayList<>();
	}
	
	public List<Character> getCharacterList() {
		return characterList;
	}
}
