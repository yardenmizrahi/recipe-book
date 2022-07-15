package application;

import mvc.UIeventsListener;

public interface actionTemplate {
	public void initData();
	public void registerListener(UIeventsListener myListener, mainViewPageTemplate myMainPageViewTemplate);
}
