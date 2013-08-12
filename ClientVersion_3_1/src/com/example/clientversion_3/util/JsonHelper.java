package com.example.clientversion_3.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.clientversion_3.entity.Album;
import com.example.clientversion_3.entity.User;

public class JsonHelper {
	
public static JSONArray getAlbumArray(String json) {
		
		JSONObject obj;
		try {
			obj = new JSONObject(json);
			String album=obj.getString("album");
			JSONArray ja=new JSONArray(album);
			return  ja;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

public static List<Album> getAlbum(String json) {
	
	
	List<Album> all = new ArrayList<Album>();
	JSONArray array = JsonHelper.getAlbumArray(json);
	for(int i=0;i<array.length();i++) {
		JSONObject obj = null;
		try {
			obj = array.getJSONObject(i);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		Album photo = new Album();			
		try {
			photo.createtime = obj.getString("createtime");
			photo.picnum=obj.getString("picnum");
			photo.name=obj.getString("name");
			photo.desc=obj.getString("desc");				
			photo.coverurl=obj.getString("coverurl");
			photo.albumid=obj.getString("albumid");
			photo.classid=obj.getString("classid");
			photo.priv=obj.getString("priv");
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		all.add(photo);
	}
	return all;
}
	
	public static User getInfo(String json){
		User userinfo=new User();
		JSONObject obj;
		try {
			obj = new JSONObject(json);
			userinfo.nickname=obj.getString("nickname");
			userinfo.headGraphPath=obj.getString("figureurl_qq_2");
			
		} catch (JSONException e) {
			
			e.printStackTrace();
		}	
		return userinfo;
	}
}
