package com.imooc.factory.il8n;

public class I18NFactory {
	//静态工厂
	public static I18N getI18NObject(String area) {
		if (area.equals("China")) {
			return new Chinese();
		} else if (area.equals("spain")) {
			return new Spainish();
		} else if (area.equals("italy")) {
			return new Italian();
		} else {
			return null;
		}
	}

}
