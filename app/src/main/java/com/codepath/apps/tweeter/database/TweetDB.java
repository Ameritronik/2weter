package com.codepath.apps.tweeter.database;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/*
 * This is a temporary, sample model that demonstrates the basic structure
 * of a SQLite persisted Model object. Check out the DBFlow wiki for more details:
 * https://github.com/codepath/android_guides/wiki/DBFlow-Guide
 *
 * Note: All models **must extend from** `BaseModel` as shown below.
 * 
 */
@Table(database = MyDatabase.class)
public class TweetDB extends BaseModel {

	@PrimaryKey
	@Column
	Long id;

	// Define table fields
	@Column (name = "name")
	private String name;

	public TweetDB() {
		super();
	}

	// Parse model from JSON
	public TweetDB(JSONObject object){
		super();

		try {
			this.name = object.getString("title");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// Getters
	public String getName() {
		return name;
	}

	// Setters
	public void setName(String name) {
		this.name = name;
	}

	/* The where class in this code below will be marked red until you first compile the project, since the code 
	 * for the SampleModel_Table class is generated at compile-time.
	 */
	
	// Record Finders
	public static TweetDB byId(long id) {
		return new Select().from(TweetDB.class).where(TweetDB_Table.id.eq(id)).querySingle();
	}

	public static List<TweetDB> recentItems() {
		return new Select().from(TweetDB.class).orderBy(TweetDB_Table.id, false).limit(300).queryList();
	}
}
