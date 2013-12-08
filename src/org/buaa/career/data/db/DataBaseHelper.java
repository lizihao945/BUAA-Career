package org.buaa.career.data.db;

import org.buaa.career.data.db.table.ArticleTable;
import org.buaa.career.data.db.table.CenterRecruitmentTable;
import org.buaa.career.data.db.table.NotificationTable;
import org.buaa.career.data.db.table.RecentRecruitmentTable;
import org.buaa.career.data.db.table.StarredTable;
import org.buaa.career.data.db.table.WorkingRecruitmentTable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
	private static DataBaseHelper INSTANCE = null;
	public static final String DB_NAME = "buaa_career.db";
	public static final int DB_VERSION = 4;
	private static final String CREATE_NOTIFICATION_TABLE_SQL = "create table if not exists "
			+ NotificationTable.TABLE_NAME
			+ "("
			+ NotificationTable.ID
			+ " integer primary key autoincrement,"
			+ NotificationTable.TITLE
			+ " text,"
			+ NotificationTable.URL
			+ " text,"
			+ NotificationTable.TIME
			+ " text,"
			+ NotificationTable.DESC
			+ " text);";

	private static final String CREATE_URL_INDEX_ON_NOTIFICATION_TABLE = "create unique index url_idx_noti on "
			+ NotificationTable.TABLE_NAME + "(" + NotificationTable.URL + ")";

	private static final String CREATE_RECENT_RECRUITMENT_TABLE_SQL = "create table if not exists "
			+ RecentRecruitmentTable.TABLE_NAME
			+ "("
			+ RecentRecruitmentTable.ID
			+ " integer primary key autoincrement,"
			+ RecentRecruitmentTable.TITLE
			+ " text,"
			+ RecentRecruitmentTable.URL
			+ " text,"
			+ RecentRecruitmentTable.TIME
			+ " text,"
			+ RecentRecruitmentTable.END_TIME
			+ "text,"
			+ RecentRecruitmentTable.PLACE
			+ " text,"
			+ RecentRecruitmentTable.REQUIREMENT
			+ " text,"
			+ RecentRecruitmentTable.NUM_OF_PEOPLE
			+ " integer,"
			+ RecentRecruitmentTable.DESC + "text);";
	
	private static final String CREATE_URL_INDEX_ON_RECENT_RECRUITMENT_TABLE = "create unique index url_idx_recent on "
			+ RecentRecruitmentTable.TABLE_NAME + "(" + RecentRecruitmentTable.URL + ")";

	private static final String CREATE_CENTER_RECRUITMENT_TABLE = "create table if not exists "
			+ CenterRecruitmentTable.TABLE_NAME
			+ "("
			+ CenterRecruitmentTable.ID
			+ " integer primary key autoincrement,"
			+ CenterRecruitmentTable.TITLE
			+ " text,"
			+ CenterRecruitmentTable.URL
			+ " text);";

	private static final String CREATE_URL_INDEX_ON_CENTER_RECRUITMENT_TABLE = "create unique index url_idx_center on "
			+ CenterRecruitmentTable.TABLE_NAME + "(" + CenterRecruitmentTable.URL + ")";

	private static final String CREATE_WORKING_RECRUITMENT_TABLE = "create table if not exists "
			+ WorkingRecruitmentTable.TABLE_NAME
			+ "("
			+ WorkingRecruitmentTable.ID
			+ " integer primary key autoincrement,"
			+ WorkingRecruitmentTable.TITLE
			+ " text,"
			+ WorkingRecruitmentTable.URL
			+ " text);";

	private static final String CREATE_URL_INDEX_ON_WORKING_RECRUITMENT_TABLE = "create unique index url_idx_working on "
			+ WorkingRecruitmentTable.TABLE_NAME + "(" + WorkingRecruitmentTable.URL + ")";

	private static final String CREATE_ARTICLE_TABLE_SQL = "create table if not exists "
			+ ArticleTable.TABLE_NAME
			+ "("
			+ ArticleTable.ID
			+ " integer primary key autoincrement,"
			+ ArticleTable.NEWS_CHANNEL
			+ " text,"
			+ ArticleTable.URL
			+ " text,"
			+ ArticleTable.HTML
			+ "text);";

	private static final String CREATE_STARRED_TABLE_SQL = "create table if not exists "
			+ StarredTable.TABLE_NAME
			+ "("
			+ StarredTable.ID
			+ " integer primary key autoincrement,"
			+ StarredTable.TITLE
			+ " text,"
			+ StarredTable.URL
			+ " text,"
			+ StarredTable.TIME
			+ " text,"
			+ StarredTable.END_TIME
			+ "text,"
			+ StarredTable.PLACE
			+ " text,"
			+ StarredTable.REQUIREMENT
			+ " text,"
			+ StarredTable.NUM_OF_PEOPLE
			+ " integer,"
			+ StarredTable.DESC + "text);";

	private static final String CREATE_URL_INDEX_ON_STARRED_TABLE = "create unique index url_idx_star on "
			+ StarredTable.TABLE_NAME + "(" + StarredTable.URL + ")";

	private DataBaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_NOTIFICATION_TABLE_SQL);
		db.execSQL(CREATE_URL_INDEX_ON_NOTIFICATION_TABLE);

		db.execSQL(CREATE_RECENT_RECRUITMENT_TABLE_SQL);
		db.execSQL(CREATE_URL_INDEX_ON_RECENT_RECRUITMENT_TABLE);
		
		db.execSQL(CREATE_CENTER_RECRUITMENT_TABLE);
		db.execSQL(CREATE_URL_INDEX_ON_CENTER_RECRUITMENT_TABLE);
		
		db.execSQL(CREATE_WORKING_RECRUITMENT_TABLE);
		db.execSQL(CREATE_URL_INDEX_ON_WORKING_RECRUITMENT_TABLE);
		
		db.execSQL(CREATE_ARTICLE_TABLE_SQL);

		db.execSQL(CREATE_STARRED_TABLE_SQL);
		db.execSQL(CREATE_URL_INDEX_ON_STARRED_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists " + NotificationTable.TABLE_NAME);
		db.execSQL("drop table if exists " + RecentRecruitmentTable.TABLE_NAME);
		db.execSQL("drop table if exists " + CenterRecruitmentTable.TABLE_NAME);
		db.execSQL("drop table if exists " + WorkingRecruitmentTable.TABLE_NAME);
		db.execSQL("drop table if exists " + ArticleTable.TABLE_NAME);
		db.execSQL("drop table if exists " + StarredTable.TABLE_NAME);
		onCreate(db);
	}

	public static DataBaseHelper getInstance(Context context) {
		if (INSTANCE == null)
			INSTANCE = new DataBaseHelper(context.getApplicationContext());
		return INSTANCE;
	}
}
