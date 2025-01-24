package com.techacsent.route_recon.model;

public class LandmarkCreateResponseV2 {
	private Success success;

	public void setSuccess(Success success){
		this.success = success;
	}

	public Success getSuccess(){
		return success;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"success = '" + success + '\'' + 
			"}";
		}
}
