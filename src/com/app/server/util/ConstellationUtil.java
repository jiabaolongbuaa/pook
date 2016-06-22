package com.app.server.util;

import java.util.Date;
import java.util.Calendar;

public class ConstellationUtil {
	public enum Constellation {
		Capricorn(1, "摩羯座"), Aquarius(2, "水瓶座"), Pisces(3, "双鱼座"), Aries(4,
				"白羊座"), Taurus(5, "金牛座"), Gemini(6, "双子座"), Cancer(7, "巨蟹座"), Leo(
				8, "狮子座"), Virgo(9, "处女座"), Libra(10, "天秤座"), Scorpio(11, "天蝎座"), Sagittarius(
				12, "射手座");

		private Constellation(int code, String chineseName) {
			this.code = code;
			this.chineseName = chineseName;
		}

		private int code;
		private String chineseName;

		public int getCode() {
			return this.code;
		}

		public String getChineseName() {
			return this.chineseName;
		}
	}

	public static final Constellation[] constellationArr = {
			Constellation.Aquarius, Constellation.Pisces, Constellation.Aries,
			Constellation.Taurus, Constellation.Gemini, Constellation.Cancer,
			Constellation.Leo, Constellation.Virgo, Constellation.Libra,
			Constellation.Scorpio, Constellation.Sagittarius,
			Constellation.Capricorn };

	public static final int[] constellationEdgeDay = { 21, 20, 21, 21, 22, 22,
			23, 24, 24, 24, 23, 22 };

	public static String calculateConstellation(Date birthday) {
		if (birthday == null)
			return "未知";


		Calendar cal = Calendar.getInstance();  
		cal.setTime(birthday);
		int month =cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH)+1;

		month = day < constellationEdgeDay[month - 1] ? month - 1 : month;
		return month > 0 ? constellationArr[month - 1].getChineseName()
				: constellationArr[11].getChineseName();
	}
}
