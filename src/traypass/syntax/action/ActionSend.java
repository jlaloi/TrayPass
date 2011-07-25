package traypass.syntax.action;

import java.awt.event.KeyEvent;
import java.util.List;

import traypass.TrayPassObject;
import traypass.syntax.Action;

public class ActionSend extends Action {

	public String execute(List<String> parameters) {
		String characters = parameters.get(0);
		System.out.println("Typing: " + characters);
		for (int i = 0; i < characters.length(); i++) {
			ActionWait.waitMS(5);
			if (characters.substring(i, i + 1).equals("{") && characters.indexOf("}", i) != -1) {
				if (typeSpecial(characters.substring(i, characters.indexOf("}", i) + 1))) {
					i = characters.indexOf("}", i);
				} else {
					type(characters.charAt(i));
				}
			} else {
				type(characters.charAt(i));
			}
		}
		return "";
	}

	private boolean typeSpecial(String value) {
		boolean result = true;
		String v = value.toLowerCase().trim();
		if (v.equals("{enter}")) {
			doType(KeyEvent.VK_ENTER);
		} else if (v.equals("{tab}")) {
			doType(KeyEvent.VK_TAB);
		} else if (v.equals("{escape}")) {
			doType(KeyEvent.VK_ESCAPE);
		} else if (v.equals("{insert}")) {
			doType(KeyEvent.VK_INSERT);
		} else if (v.equals("{control}")) {
			doType(KeyEvent.VK_CONTROL);
		} else if (v.equals("{alt}")) {
			doType(KeyEvent.VK_ALT);
		} else if (v.equals("{shift}")) {
			doType(KeyEvent.VK_SHIFT);
		} else if (v.equals("{backspace}")) {
			doType(KeyEvent.VK_BACK_SPACE);
		} else if (v.equals("{end}")) {
			doType(KeyEvent.VK_END);
		} else if (v.equals("{home}")) {
			doType(KeyEvent.VK_HOME);
		} else if (v.equals("{pageup}")) {
			doType(KeyEvent.VK_PAGE_UP);
		} else if (v.equals("{pagedown}")) {
			doType(KeyEvent.VK_PAGE_DOWN);
		} else if (v.equals("{capslock}")) {
			doType(KeyEvent.VK_CAPS_LOCK);
		} else if (v.equals("{f1}")) {
			doType(KeyEvent.VK_F1);
		} else if (v.equals("{f2}")) {
			doType(KeyEvent.VK_F2);
		} else if (v.equals("{f3}")) {
			doType(KeyEvent.VK_F3);
		} else if (v.equals("{f4}")) {
			doType(KeyEvent.VK_F4);
		} else if (v.equals("{f5}")) {
			doType(KeyEvent.VK_F5);
		} else if (v.equals("{f6}")) {
			doType(KeyEvent.VK_F6);
		} else if (v.equals("{f7}")) {
			doType(KeyEvent.VK_F7);
		} else if (v.equals("{f8}")) {
			doType(KeyEvent.VK_F8);
		} else if (v.equals("{f9}")) {
			doType(KeyEvent.VK_F9);
		} else if (v.equals("{f10}")) {
			doType(KeyEvent.VK_F10);
		} else if (v.equals("{f11}")) {
			doType(KeyEvent.VK_F11);
		} else if (v.equals("{f12}")) {
			doType(KeyEvent.VK_F12);
		} else {
			result = false;
			System.out.println("typeSpecial " + value + " not found");
		}
		return result;
	}

	private void type(char character) {
		switch (character) {
		case 'a':
			doType(KeyEvent.VK_A);
			break;
		case 'b':
			doType(KeyEvent.VK_B);
			break;
		case 'c':
			doType(KeyEvent.VK_C);
			break;
		case 'd':
			doType(KeyEvent.VK_D);
			break;
		case 'e':
			doType(KeyEvent.VK_E);
			break;
		case 'f':
			doType(KeyEvent.VK_F);
			break;
		case 'g':
			doType(KeyEvent.VK_G);
			break;
		case 'h':
			doType(KeyEvent.VK_H);
			break;
		case 'i':
			doType(KeyEvent.VK_I);
			break;
		case 'j':
			doType(KeyEvent.VK_J);
			break;
		case 'k':
			doType(KeyEvent.VK_K);
			break;
		case 'l':
			doType(KeyEvent.VK_L);
			break;
		case 'm':
			doType(KeyEvent.VK_M);
			break;
		case 'n':
			doType(KeyEvent.VK_N);
			break;
		case 'o':
			doType(KeyEvent.VK_O);
			break;
		case 'p':
			doType(KeyEvent.VK_P);
			break;
		case 'q':
			doType(KeyEvent.VK_Q);
			break;
		case 'r':
			doType(KeyEvent.VK_R);
			break;
		case 's':
			doType(KeyEvent.VK_S);
			break;
		case 't':
			doType(KeyEvent.VK_T);
			break;
		case 'u':
			doType(KeyEvent.VK_U);
			break;
		case 'v':
			doType(KeyEvent.VK_V);
			break;
		case 'w':
			doType(KeyEvent.VK_W);
			break;
		case 'x':
			doType(KeyEvent.VK_X);
			break;
		case 'y':
			doType(KeyEvent.VK_Y);
			break;
		case 'z':
			doType(KeyEvent.VK_Z);
			break;
		case 'é':
			doType(KeyEvent.VK_2);
			break;
		case 'è':
			doType(KeyEvent.VK_7);
			break;
		case 'à':
			doType(KeyEvent.VK_0);
			break;
		case 'A':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_A);
			break;
		case 'B':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_B);
			break;
		case 'C':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_C);
			break;
		case 'D':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_D);
			break;
		case 'E':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_E);
			break;
		case 'F':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_F);
			break;
		case 'G':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_G);
			break;
		case 'H':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_H);
			break;
		case 'I':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_I);
			break;
		case 'J':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_J);
			break;
		case 'K':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_K);
			break;
		case 'L':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_L);
			break;
		case 'M':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_M);
			break;
		case 'N':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_N);
			break;
		case 'O':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_O);
			break;
		case 'P':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_P);
			break;
		case 'Q':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_Q);
			break;
		case 'R':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_R);
			break;
		case 'S':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_S);
			break;
		case 'T':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_T);
			break;
		case 'U':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_U);
			break;
		case 'V':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_V);
			break;
		case 'W':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_W);
			break;
		case 'X':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_X);
			break;
		case 'Y':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_Y);
			break;
		case 'Z':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_Z);
			break;
		case '`':
			doType(KeyEvent.VK_ALT, KeyEvent.VK_CONTROL, KeyEvent.VK_7);
			break;
		case '0':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_0);
			break;
		case '1':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_1);
			break;
		case '2':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_2);
			break;
		case '3':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_3);
			break;
		case '4':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_4);
			break;
		case '5':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_5);
			break;
		case '6':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_6);
			break;
		case '7':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_7);
			break;
		case '8':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_8);
			break;
		case '9':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_9);
			break;
		case '-':
			doType(KeyEvent.VK_6);
			break;
		case '=':
			doType(KeyEvent.VK_EQUALS);
			break;
		case '~':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_BACK_QUOTE);
			break;
		case '!':
			doType(KeyEvent.VK_EXCLAMATION_MARK);
			break;
		case '@':
			doType(KeyEvent.VK_ALT, KeyEvent.VK_CONTROL, KeyEvent.VK_0);
			break;
		case '#':
			doType(KeyEvent.VK_ALT, KeyEvent.VK_CONTROL, KeyEvent.VK_3);
			break;
		case '$':
			doType(KeyEvent.VK_DOLLAR);
			break;
		case '%':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_5);
			break;
		case '^':
			doType(KeyEvent.VK_CIRCUMFLEX);
			break;
		case '&':
			doType(KeyEvent.VK_1);
			break;
		case '*':
			doType(KeyEvent.VK_ASTERISK);
			break;
		case '(':
			doType(KeyEvent.VK_5);
			break;
		case ')':
			doType(KeyEvent.VK_RIGHT_PARENTHESIS);
			break;
		case '_':
			doType(KeyEvent.VK_8);
			break;
		case '+':
			doType(KeyEvent.VK_PLUS);
			break;
		case '\t':
			doType(KeyEvent.VK_TAB);
			break;
		case '\n':
			doType(KeyEvent.VK_ENTER);
			break;
		case '[':
			doType(KeyEvent.VK_ALT, KeyEvent.VK_CONTROL, KeyEvent.VK_5);
			break;
		case ']':
			doType(KeyEvent.VK_CLOSE_BRACKET);
			break;
		case '\\':
			doType(KeyEvent.VK_ALT, KeyEvent.VK_CONTROL, KeyEvent.VK_8);
			break;
		case '{':
			doType(KeyEvent.VK_ALT, KeyEvent.VK_CONTROL, KeyEvent.VK_4);
			break;
		case '}':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_CLOSE_BRACKET);
			break;
		case '|':
			doType(KeyEvent.VK_ALT, KeyEvent.VK_CONTROL, KeyEvent.VK_6);
			break;
		case ';':
			doType(KeyEvent.VK_SEMICOLON);
			break;
		case ':':
			doType(KeyEvent.VK_COLON);
			break;
		case '\'':
			doType(KeyEvent.VK_4);
			break;
		case '"':
			doType(KeyEvent.VK_3);
			break;
		case ',':
			doType(KeyEvent.VK_COMMA);
			break;
		case '<':
			doType(KeyEvent.VK_LESS);
			break;
		case '.':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_SEMICOLON);
			break;
		case '>':
			doType(KeyEvent.VK_GREATER);
			break;
		case '/':
			doType(KeyEvent.VK_SLASH);
			break;
		case '?':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_SLASH);
			break;
		case ' ':
			doType(KeyEvent.VK_SPACE);
			break;
		}
	}

	private void doType(int... keyCodes) {
		doType(keyCodes, 0, keyCodes.length);
	}

	private void doType(int[] keyCodes, int offset, int length) {
		if (length > 0) {
			try {
				TrayPassObject.getRobot().keyPress(keyCodes[offset]);
				doType(keyCodes, offset + 1, length - 1);
				TrayPassObject.getRobot().keyRelease(keyCodes[offset]);
			} catch (Exception e) {
			}
		}
	}

}