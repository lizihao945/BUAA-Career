package org.buaa.career.data.db;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;

import org.buaa.career.data.db.table.ArticleTable;
import org.buaa.career.data.db.table.NotificationTable;
import org.buaa.career.data.db.table.RecentRecruitmentTable;
import org.buaa.career.data.db.table.StarredTable;
import org.buaa.career.data.model.News;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBTask {
	public static String TAG = "DBTask";

	public static LinkedList<News> getAllNews() {

		return null;
	}

	public static boolean checkHistory(Context context) {
		return true;
	}

	/**
	 * When there is no items in the database, a new List with 0 size should be
	 * returned.
	 * 
	 * @param context
	 * @return
	 */
	public static ArrayList<News> getRecentNotifications(Context context) {
		ArrayList<News> rt = new ArrayList<News>();
		String sql = "select * from " + NotificationTable.TABLE_NAME + " limit 0, 21";
		Cursor c = DataBaseHelper.getInstance(context).getReadableDatabase().rawQuery(sql, null);
		int titleColumnIndex = c.getColumnIndex(NotificationTable.TITLE);
		int timeColumnIndex = c.getColumnIndex(NotificationTable.TIME);
		int urlColumnIndex = c.getColumnIndex(NotificationTable.URL);
		while (c.moveToNext()) {
			News news = new News();
			news.setTitle(c.getString(titleColumnIndex)).setTime(c.getString(timeColumnIndex))
					.setUrl(c.getString(urlColumnIndex)).setChannel(News.NOTIFICATION);
			rt.add(news);
		}
		return rt;
	}

	public static ArrayList<News> getRecentRecentRecruitment(Context context) {
		ArrayList<News> rt = new ArrayList<News>();
		String sql = "select * from " + RecentRecruitmentTable.TABLE_NAME + " limit 0, 21";
		Cursor c = DataBaseHelper.getInstance(context).getReadableDatabase().rawQuery(sql, null);
		int titleColumnIndex = c.getColumnIndex(RecentRecruitmentTable.TITLE);
		int timeColumnIndex = c.getColumnIndex(RecentRecruitmentTable.TIME);
		int urlColumnIndex = c.getColumnIndex(RecentRecruitmentTable.URL);
		while (c.moveToNext()) {
			News news = new News();
			news.setTitle(c.getString(titleColumnIndex)).setTime(c.getString(timeColumnIndex))
					.setUrl(c.getString(urlColumnIndex)).setChannel(News.RECENT_RECRUITMENT);
			rt.add(news);
		}
		return rt;
	}

	public static ArrayList<News> getNewsByDate(Date date, Context context) {
		ArrayList<News> rt = new ArrayList<News>();
		String oriTime = date.toString();
		String tabOneTime = oriTime.replace("-0", "-");
		String tabOneSql = "select * from " + NotificationTable.TABLE_NAME + " where "
				+ NotificationTable.TIME + "=\"" + tabOneTime + "\"";
		String tabTwoSql = "select * from " + RecentRecruitmentTable.TABLE_NAME + " where "
				+ RecentRecruitmentTable.TIME + " like \"" + oriTime + "%\"";
		// String tabThreeSql = "select * from " + NotificationTable.TABLE_NAME
		// + " where "
		// + NotificationTable.TIME + "=\"" + time + "\"";
		// String tabFourSql = "select * from " + NotificationTable.TABLE_NAME +
		// " where "
		// + NotificationTable.TIME + "=\"" + time + "\"";
		Cursor c = DataBaseHelper.getInstance(context).getReadableDatabase()
				.rawQuery(tabOneSql, null);
		int titleColumnIndex = c.getColumnIndex(NotificationTable.TITLE);
		int timeColumnIndex = c.getColumnIndex(NotificationTable.TIME);
		int urlColumnIndex = c.getColumnIndex(NotificationTable.URL);
		while (c.moveToNext()) {
			News news = new News();
			news.setTitle(c.getString(titleColumnIndex)).setTime(c.getString(timeColumnIndex))
					.setUrl(c.getString(urlColumnIndex));
			rt.add(news);
		}

		c = DataBaseHelper.getInstance(context).getReadableDatabase().rawQuery(tabTwoSql, null);
		titleColumnIndex = c.getColumnIndex(RecentRecruitmentTable.TITLE);
		timeColumnIndex = c.getColumnIndex(RecentRecruitmentTable.TIME);
		urlColumnIndex = c.getColumnIndex(RecentRecruitmentTable.URL);
		while (c.moveToNext()) {
			System.out.println("found!");
			News news = new News();
			news.setTitle(c.getString(titleColumnIndex)).setTime(c.getString(timeColumnIndex))
					.setUrl(c.getString(urlColumnIndex));
			rt.add(news);
		}

		return rt;
	}

	public static ArrayList<News> getFavouriteNews(Context context) {
		ArrayList<News> rt = new ArrayList<News>();
		String sql = "select * from " + StarredTable.TABLE_NAME;

		Cursor c = DataBaseHelper.getInstance(context).getReadableDatabase().rawQuery(sql, null);
		int categoryColumnIndex = c.getColumnIndex(StarredTable.CATEGORY);
		int urlColumnIndex = c.getColumnIndex(StarredTable.URL);
		while (c.moveToNext()) {
			String url = c.getString(urlColumnIndex);
			switch (c.getInt(categoryColumnIndex)) {
			case News.NOTIFICATION:
				String subSql = "select * from " + NotificationTable.TABLE_NAME + " where "
						+ NotificationTable.URL + "=\"" + url + "\"";
				Cursor c1 = DataBaseHelper.getInstance(context).getReadableDatabase()
						.rawQuery(subSql, null);

				int titleColumnIndex = c1.getColumnIndex(NotificationTable.TITLE);
				int timeColumnIndex = c1.getColumnIndex(NotificationTable.TIME);

				if (c1.moveToNext()) {
					News news = new News();
					news.setTitle(c1.getString(titleColumnIndex))
							.setTime(c1.getString(timeColumnIndex)).setUrl(url);
					rt.add(news);
				}
				break;
			case News.RECENT_RECRUITMENT:
				break;
			case News.CENTER_RECRUITMENT:
				break;
			case News.WORKING_RECRUITMENT:
				break;
			}

		}
		Log.v(TAG, rt.size() + " favourite items");
		return rt;
	}

	public static void addNews(News news, Context context) {
		ContentValues values = new ContentValues();
		switch (news.getChannel()) {
		case News.NOTIFICATION:
			values.put(NotificationTable.TITLE, news.getTitle());
			values.put(NotificationTable.TIME, news.getTime());
			values.put(NotificationTable.URL, news.getUrl());
			DataBaseHelper
					.getInstance(context)
					.getWritableDatabase()
					.insertWithOnConflict(NotificationTable.TABLE_NAME, null, values,
							SQLiteDatabase.CONFLICT_IGNORE);
			break;
		case News.RECENT_RECRUITMENT:
			values.put(RecentRecruitmentTable.TITLE, news.getTitle());
			values.put(RecentRecruitmentTable.TIME, news.getTime());
			values.put(RecentRecruitmentTable.URL, news.getUrl());
			DataBaseHelper
					.getInstance(context)
					.getWritableDatabase()
					.insertWithOnConflict(RecentRecruitmentTable.TABLE_NAME, null, values,
							SQLiteDatabase.CONFLICT_IGNORE);
			break;
		}
	}

	public static void addArticle(News news, Context context) {
		ContentValues values = new ContentValues();
		values.put(ArticleTable.NEWS_CHANNEL, news.getChannel());
		values.put(ArticleTable.URL, news.getUrl());
		DataBaseHelper
				.getInstance(context)
				.getWritableDatabase()
				.insertWithOnConflict(ArticleTable.TABLE_NAME, null, values,
						SQLiteDatabase.CONFLICT_IGNORE);
	}

	public static void addStarrdNews(String url, int channel, Context context) {
		ContentValues values = new ContentValues();
		values.put(StarredTable.URL, url);
		values.put(StarredTable.CATEGORY, channel);
		DataBaseHelper
				.getInstance(context)
				.getWritableDatabase()
				.insertWithOnConflict(StarredTable.TABLE_NAME, null, values,
						SQLiteDatabase.CONFLICT_IGNORE);
	}

	public static void removeStarredNews(String url, int channel, Context context) {
		DataBaseHelper.getInstance(context).getWritableDatabase()
				.delete(StarredTable.TABLE_NAME, "url=?", new String[] { url });

	}

	public static boolean isStarred(String url, Context context) {
		String sql = "select * from " + StarredTable.TABLE_NAME + " where " + StarredTable.URL
				+ "=\"" + url + "\"";
		Cursor c = DataBaseHelper.getInstance(context).getReadableDatabase().rawQuery(sql, null);
		return c.moveToNext();
	}
}
