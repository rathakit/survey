package com.nimbl3.survey.utilities;

public class AppSettings {

	public static final ServerApi TARGET = ServerApi.PRODUCTION_SERVER_1_0;
	public static final String BASE_URL = (TARGET == ServerApi.PRODUCTION_SERVER_1_0) ?
			Constant.PRODUCTION_URL : Constant.DEVELOPMENT_URL;
}
