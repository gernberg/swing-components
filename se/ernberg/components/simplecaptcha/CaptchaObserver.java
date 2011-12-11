package se.ernberg.components.simplecaptcha;

public interface CaptchaObserver {
	public void regenerationComplete(long id);
}
