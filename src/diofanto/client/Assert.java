package diofanto.client;

public class Assert {

	public static void isTrue(boolean cond) {
		if (!cond) {
			throw new RuntimeException("Assert failed");
		}
	}

	public static void notNull(Object obj) {
		if (obj == null) {
			throw new RuntimeException("Assert failed");
		}
	}

}
