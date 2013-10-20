package org.buaa.career.data.db;

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

public class DBTask {
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
		String sql = "select * from " + NotificationTable.TABLE_NAME
				+ " limit 0, 21";
		Cursor c = DataBaseHelper.getInstance(context).getReadableDatabase()
				.rawQuery(sql, null);
		int titleColumnIndex = c.getColumnIndex(NotificationTable.TITLE);
		int timeColumnIndex = c.getColumnIndex(NotificationTable.TIME);
		int urlColumnIndex = c.getColumnIndex(NotificationTable.URL);
		while (c.moveToNext()) {
			News news = new News();
			news.setTitle(c.getString(titleColumnIndex))
					.setTime(c.getString(timeColumnIndex))
					.setUrl(c.getString(urlColumnIndex));
			rt.add(news);
		}
		return rt;
	}

	public static ArrayList<News> getRecentRecentRecruitment(Context context) {
		ArrayList<News> rt = new ArrayList<News>();
		String sql = "select * from " + RecentRecruitmentTable.TABLE_NAME
				+ " limit 0, 21";
		Cursor c = DataBaseHelper.getInstance(context).getReadableDatabase()
				.rawQuery(sql, null);
		int titleColumnIndex = c.getColumnIndex(RecentRecruitmentTable.TITLE);
		int timeColumnIndex = c.getColumnIndex(RecentRecruitmentTable.TIME);
		int urlColumnIndex = c.getColumnIndex(RecentRecruitmentTable.URL);
		while (c.moveToNext()) {
			News news = new News();
			news.setTitle(c.getString(titleColumnIndex))
					.setTime(c.getString(timeColumnIndex))
					.setUrl(c.getString(urlColumnIndex));
			rt.add(news);
		}
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
					.insertWithOnConflict(NotificationTable.TABLE_NAME, null,
							values, SQLiteDatabase.CONFLICT_IGNORE);
			break;
		case News.RECENT_RECRUITMENT:
			values.put(RecentRecruitmentTable.TITLE, news.getTitle());
			values.put(RecentRecruitmentTable.TIME, news.getTime());
			values.put(RecentRecruitmentTable.URL, news.getUrl());
			DataBaseHelper
					.getInstance(context)
					.getWritableDatabase()
					.insertWithOnConflict(RecentRecruitmentTable.TABLE_NAME,
							null, values, SQLiteDatabase.CONFLICT_IGNORE);
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

	public static void removeStarredNews(String url, int channel,
			Context context) {
		DataBaseHelper.getInstance(context).getWritableDatabase()
				.delete(StarredTable.TABLE_NAME, "url=?", new String[] { url });

	}

	public static boolean isStarred(String url, Context context) {
		String sql = "select * from " + StarredTable.TABLE_NAME + " where "
				+ StarredTable.URL + "=\"" + url + "\"";
		Cursor c = DataBaseHelper.getInstance(context).getReadableDatabase()
				.rawQuery(sql, null);
		return c.moveToNext();
	}
}
