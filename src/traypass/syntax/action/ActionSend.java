package traypass.syntax.action;

import java.awt.event.KeyEvent;
import java.util.List;

import traypass.TrayPassObject;
import traypass.syntax.Action;
import traypass.tools.Pair;

public class ActionSend extends Action {

	public static String keyCodeString = "{code:";

	public String doAction(List<String> parameters) {
		if (parameters.size() == 1) {
			String characters = parameters.get(0);
			for (int i = 0; i < characters.length() && !interpreter.isStop(); i++) {
				Pair<Integer, int[]> result = getCode(characters, i);
				i = result.getA();
				doType(result.getB());
			}
		} else {
			int[] toType = new int[0];
			for (String characters : parameters) {
				for (int i = 0; i < characters.length() && !interpreter.isStop(); i++) {
					Pair<Integer, int[]> result = getCode(characters, i);
					i = result.getA();
					toType = merge(toType, result.getB());
				}
			}
			doType(toType);
		}
		return "";
	}

	private Pair<Integer, int[]> getCode(String characters, int i) {
		Pair<Integer, int[]> result = null;
		int[] array = null;
		if (characters.substring(i, i + 1).equals("{") && characters.indexOf("}", i) != -1) {
			int[] specials = getCodeSpecial(characters.substring(i, characters.indexOf("}", i) + 1));
			if (specials != null) {
				array = specials;
				i = characters.indexOf("}", i);
			} else {
				array = getCodeClassic(characters.charAt(i));
			}
		} else {
			array = getCodeClassic(characters.charAt(i));
		}
		result = new Pair<Integer, int[]>(i, array);
		return result;
	}

	private int[] getCodeSpecial(String value) {
		int[] result = null;
		String v = value.toLowerCase().trim();
		if (v.equals("{enter}")) {
			result = new int[] { KeyEvent.VK_ENTER };
		} else if (v.equals("{tab}")) {
			result = new int[] { KeyEvent.VK_TAB };
		} else if (v.equals("{escape}")) {
			result = new int[] { KeyEvent.VK_ESCAPE };
		} else if (v.equals("{delete}")) {
			result = new int[] { KeyEvent.VK_DELETE };
		} else if (v.equals("{del}")) {
			result = new int[] { KeyEvent.VK_DELETE };
		} else if (v.equals("{insert}")) {
			result = new int[] { KeyEvent.VK_INSERT };
		} else if (v.equals("{ctrl}")) {
			result = new int[] { KeyEvent.VK_CONTROL };
		} else if (v.equals("{control}")) {
			result = new int[] { KeyEvent.VK_CONTROL };
		} else if (v.equals("{alt}")) {
			result = new int[] { KeyEvent.VK_ALT };
		} else if (v.equals("{shift}")) {
			result = new int[] { KeyEvent.VK_SHIFT };
		} else if (v.equals("{backspace}")) {
			result = new int[] { KeyEvent.VK_BACK_SPACE };
		} else if (v.equals("{end}")) {
			result = new int[] { KeyEvent.VK_END };
		} else if (v.equals("{home}")) {
			result = new int[] { KeyEvent.VK_HOME };
		} else if (v.equals("{pageup}")) {
			result = new int[] { KeyEvent.VK_PAGE_UP };
		} else if (v.equals("{pagedown}")) {
			result = new int[] { KeyEvent.VK_PAGE_DOWN };
		} else if (v.equals("{capslock}")) {
			result = new int[] { KeyEvent.VK_CAPS_LOCK };
		} else if (v.equals("{f1}")) {
			result = new int[] { KeyEvent.VK_F1 };
		} else if (v.equals("{f2}")) {
			result = new int[] { KeyEvent.VK_F2 };
		} else if (v.equals("{f3}")) {
			result = new int[] { KeyEvent.VK_F3 };
		} else if (v.equals("{f4}")) {
			result = new int[] { KeyEvent.VK_F4 };
		} else if (v.equals("{f5}")) {
			result = new int[] { KeyEvent.VK_F5 };
		} else if (v.equals("{f6}")) {
			result = new int[] { KeyEvent.VK_F6 };
		} else if (v.equals("{f7}")) {
			result = new int[] { KeyEvent.VK_F7 };
		} else if (v.equals("{f8}")) {
			result = new int[] { KeyEvent.VK_F8 };
		} else if (v.equals("{f9}")) {
			result = new int[] { KeyEvent.VK_F9 };
		} else if (v.equals("{f10}")) {
			result = new int[] { KeyEvent.VK_F10 };
		} else if (v.equals("{f11}")) {
			result = new int[] { KeyEvent.VK_F11 };
		} else if (v.equals("{f12}")) {
			result = new int[] { KeyEvent.VK_F12 };
		} else if (v.equals("{up}")) {
			result = new int[] { KeyEvent.VK_UP };
		} else if (v.equals("{down}")) {
			result = new int[] { KeyEvent.VK_DOWN };
		} else if (v.equals("{right}")) {
			result = new int[] { KeyEvent.VK_RIGHT };
		} else if (v.equals("{left}")) {
			result = new int[] { KeyEvent.VK_LEFT };
		} else if (v.startsWith(keyCodeString)) {
			try {
				String code = v.substring(keyCodeString.length(), v.indexOf('}'));
				result = new int[] { Integer.valueOf(code) };
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private int[] getCodeClassic(char character) {
		int[] result = new int[0];
		switch (character) {
		case 'a':
			result = new int[] { KeyEvent.VK_A };
			break;
		case 'b':
			result = new int[] { KeyEvent.VK_B };
			break;
		case 'c':
			result = new int[] { KeyEvent.VK_C };
			break;
		case 'd':
			result = new int[] { KeyEvent.VK_D };
			break;
		case 'e':
			result = new int[] { KeyEvent.VK_E };
			break;
		case 'f':
			result = new int[] { KeyEvent.VK_F };
			break;
		case 'g':
			result = new int[] { KeyEvent.VK_G };
			break;
		case 'h':
			result = new int[] { KeyEvent.VK_H };
			break;
		case 'i':
			result = new int[] { KeyEvent.VK_I };
			break;
		case 'j':
			result = new int[] { KeyEvent.VK_J };
			break;
		case 'k':
			result = new int[] { KeyEvent.VK_K };
			break;
		case 'l':
			result = new int[] { KeyEvent.VK_L };
			break;
		case 'm':
			result = new int[] { KeyEvent.VK_M };
			break;
		case 'n':
			result = new int[] { KeyEvent.VK_N };
			break;
		case 'o':
			result = new int[] { KeyEvent.VK_O };
			break;
		case 'p':
			result = new int[] { KeyEvent.VK_P };
			break;
		case 'q':
			result = new int[] { KeyEvent.VK_Q };
			break;
		case 'r':
			result = new int[] { KeyEvent.VK_R };
			break;
		case 's':
			result = new int[] { KeyEvent.VK_S };
			break;
		case 't':
			result = new int[] { KeyEvent.VK_T };
			break;
		case 'u':
			result = new int[] { KeyEvent.VK_U };
			break;
		case 'v':
			result = new int[] { KeyEvent.VK_V };
			break;
		case 'w':
			result = new int[] { KeyEvent.VK_W };
			break;
		case 'x':
			result = new int[] { KeyEvent.VK_X };
			break;
		case 'y':
			result = new int[] { KeyEvent.VK_Y };
			break;
		case 'z':
			result = new int[] { KeyEvent.VK_Z };
			break;
		case 'é':
			result = new int[] { KeyEvent.VK_2 };
			break;
		case 'è':
			result = new int[] { KeyEvent.VK_7 };
			break;
		case 'à':
			result = new int[] { KeyEvent.VK_0 };
			break;
		case 'A':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_A };
			break;
		case 'B':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_B };
			break;
		case 'C':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_C };
			break;
		case 'D':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_D };
			break;
		case 'E':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_E };
			break;
		case 'F':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_F };
			break;
		case 'G':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_G };
			break;
		case 'H':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_H };
			break;
		case 'I':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_I };
			break;
		case 'J':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_J };
			break;
		case 'K':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_K };
			break;
		case 'L':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_L };
			break;
		case 'M':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_M };
			break;
		case 'N':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_N };
			break;
		case 'O':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_O };
			break;
		case 'P':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_P };
			break;
		case 'Q':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_Q };
			break;
		case 'R':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_R };
			break;
		case 'S':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_S };
			break;
		case 'T':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_T };
			break;
		case 'U':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_U };
			break;
		case 'V':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_V };
			break;
		case 'W':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_W };
			break;
		case 'X':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_X };
			break;
		case 'Y':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_Y };
			break;
		case 'Z':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_Z };
			break;
		case '`':
			result = new int[] { KeyEvent.VK_ALT, KeyEvent.VK_CONTROL, KeyEvent.VK_7 };
			break;
		case '0':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_0 };
			break;
		case '1':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_1 };
			break;
		case '2':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_2 };
			break;
		case '3':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_3 };
			break;
		case '4':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_4 };
			break;
		case '5':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_5 };
			break;
		case '6':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_6 };
			break;
		case '7':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_7 };
			break;
		case '8':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_8 };
			break;
		case '9':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_9 };
			break;
		case '-':
			result = new int[] { KeyEvent.VK_6 };
			break;
		case '=':
			result = new int[] { KeyEvent.VK_EQUALS };
			break;
		case '~':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_BACK_QUOTE };
			break;
		case '!':
			result = new int[] { KeyEvent.VK_EXCLAMATION_MARK };
			break;
		case '@':
			result = new int[] { KeyEvent.VK_ALT, KeyEvent.VK_CONTROL, KeyEvent.VK_0 };
			break;
		case '#':
			result = new int[] { KeyEvent.VK_ALT, KeyEvent.VK_CONTROL, KeyEvent.VK_3 };
			break;
		case '$':
			result = new int[] { KeyEvent.VK_DOLLAR };
			break;
		case '%':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_5 };
			break;
		case '^':
			result = new int[] { KeyEvent.VK_CIRCUMFLEX };
			break;
		case '&':
			result = new int[] { KeyEvent.VK_1 };
			break;
		case '*':
			result = new int[] { KeyEvent.VK_ASTERISK };
			break;
		case '(':
			result = new int[] { KeyEvent.VK_5 };
			break;
		case ')':
			result = new int[] { KeyEvent.VK_RIGHT_PARENTHESIS };
			break;
		case '_':
			result = new int[] { KeyEvent.VK_8 };
			break;
		case '+':
			result = new int[] { KeyEvent.VK_PLUS };
			break;
		case '\t':
			result = new int[] { KeyEvent.VK_TAB };
			break;
		case '\n':
			result = new int[] { KeyEvent.VK_ENTER };
			break;
		case '[':
			result = new int[] { KeyEvent.VK_ALT, KeyEvent.VK_CONTROL, KeyEvent.VK_5 };
			break;
		case ']':
			result = new int[] { KeyEvent.VK_CLOSE_BRACKET };
			break;
		case '\\':
			result = new int[] { KeyEvent.VK_ALT, KeyEvent.VK_CONTROL, KeyEvent.VK_8 };
			break;
		case '{':
			result = new int[] { KeyEvent.VK_ALT, KeyEvent.VK_CONTROL, KeyEvent.VK_4 };
			break;
		case '}':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_CLOSE_BRACKET };
			break;
		case '|':
			result = new int[] { KeyEvent.VK_ALT, KeyEvent.VK_CONTROL, KeyEvent.VK_6 };
			break;
		case ';':
			result = new int[] { KeyEvent.VK_SEMICOLON };
			break;
		case ':':
			result = new int[] { KeyEvent.VK_COLON };
			break;
		case '\'':
			result = new int[] { KeyEvent.VK_4 };
			break;
		case '"':
			result = new int[] { KeyEvent.VK_3 };
			break;
		case ',':
			result = new int[] { KeyEvent.VK_COMMA };
			break;
		case '<':
			result = new int[] { KeyEvent.VK_LESS };
			break;
		case '.':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_SEMICOLON };
			break;
		case '>':
			result = new int[] { KeyEvent.VK_GREATER };
			break;
		case '/':
			result = new int[] { KeyEvent.VK_SLASH };
			break;
		case '?':
			result = new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_SLASH };
			break;
		case ' ':
			result = new int[] { KeyEvent.VK_SPACE };
			break;
		}
		return result;
	}

	private void doType(int... keyCodes) {
		doType(keyCodes, 0, keyCodes.length);
	}

	private void doType(int[] keyCodes, int offset, int length) {
		if (length > 0) {
			ActionWait.waitMS(5);
			try {
				TrayPassObject.getRobot().keyPress(keyCodes[offset]);
				doType(keyCodes, offset + 1, length - 1);
				TrayPassObject.getRobot().keyRelease(keyCodes[offset]);
			} catch (Exception e) {
			}
		}
	}

	public static int[] merge(int[] a, int[] b) {
		int[] result = new int[a.length + b.length];
		int i = 0;
		for (; i < a.length; i++) {
			result[i] = a[i];
		}
		for (int j = 0; j < b.length; j++) {
			result[i + j] = b[j];
		}
		return result;
	}

}