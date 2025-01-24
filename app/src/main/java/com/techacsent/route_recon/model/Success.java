package com.techacsent.route_recon.model;

import java.util.List;

public class Success{

	private List<LandmarkImageItem> landmarkImage;
	private String title;
	private String message;

	public void setLandmarkImage(List<LandmarkImageItem> landmarkImage){
		this.landmarkImage = landmarkImage;
	}

	public List<LandmarkImageItem> getLandmarkImage(){
		return landmarkImage;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"Success{" + 
			"landmarkImage = '" + landmarkImage + '\'' + 
			",title = '" + title + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}