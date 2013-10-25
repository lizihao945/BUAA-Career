package org.buaa.career.data.db;

import org.buaa.career.data.db.table.ArticleTable;
import org.buaa.career.data.db.table.NotificationTable;
import org.buaa.career.data.db.table.RecentRecruitmentTable;
import org.buaa.career.data.db.table.StarredTable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
	private static DataBaseHelper INSTANCE = null;
	public static final String DB_NAME = "buaa_career.db";
	public static final int DB_VERSION = 1;
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
			+ StarredTable.URL
			+ " text," + StarredTable.CATEGORY + " integer);";

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
		
		db.execSQL(CREATE_ARTICLE_TABLE_SQL);

		db.execSQL(CREATE_STARRED_TABLE_SQL);
		db.execSQL(CREATE_URL_INDEX_ON_STARRED_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists " + NotificationTable.TABLE_NAME);
		db.execSQL("drop table if exists " + RecentRecruitmentTable.TABLE_NAME);
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
