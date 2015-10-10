package worldline.ssm.rd.ux.wltwitter.pojo;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.gson.annotations.SerializedName;

import dalvik.system.PathClassLoader;

public class Tweet implements Parcelable{

	@SerializedName("created_at")
	public String dateCreated;

	@SerializedName("id")
	public String id;

	@SerializedName("text")
	public String text;

	@SerializedName("in_reply_to_status_id")
	public String inReplyToStatusId;

	@SerializedName("in_reply_to_user_id")
	public String inReplyToUserId;

	@SerializedName("in_reply_to_screen_name")
	public String inReplyToScreenName;

	@SerializedName("user")
	public TwitterUser user;

	public static final Creator<Tweet> CREATOR = new Creator<Tweet>() {
		@Override
		public Tweet createFromParcel(Parcel source) {
			return new Tweet(source);
		}

		@Override
		public Tweet[] newArray(int size) {
			return new Tweet[size];
		}
	};

	@Override
	public String toString() {
		return text;
	}
	
	public long getDateCreatedTimestamp(){
		final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH);
		dateFormat.setLenient(false);
		try {
			final Date created = dateFormat.parse(dateCreated);
			return created.getTime();
		} catch (Exception e) {
			return 0;
		}
	}

	public Tweet(){

	}

	public Tweet(Parcel input){
		this.text = input.readString();
		this.dateCreated = input.readString();
		this.id = input.readString();
		this.inReplyToStatusId = input.readString();
		this.inReplyToUserId = input.readString();
		this.inReplyToScreenName = input.readString();
		Bundle user = input.readBundle();
		this.user = new TwitterUser();
		this.user.name = user.getString("name");
		this.user.screenName = user.getString("screenName");
		this.user.profileImageUrl = user.getString("pathToImage");
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.text);
		dest.writeString(this.dateCreated);
		dest.writeString(this.id);
		dest.writeString(this.inReplyToStatusId);
		dest.writeString(this.inReplyToUserId);
		dest.writeString(this.inReplyToScreenName);
		Bundle user = new Bundle();
		user.putString("name",this.user.name);
		user.putString("screenName",this.user.screenName);
		user.putString("pathToImage",this.user.profileImageUrl);
		dest.writeBundle(user);
	}
}