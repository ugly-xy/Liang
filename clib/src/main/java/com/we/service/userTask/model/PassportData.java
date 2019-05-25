package com.we.service.userTask.model;

import java.util.List;

public class PassportData {
	private List<EncryptedPassportElement> data;
	private EncryptedCredentials credentials;
	public List<EncryptedPassportElement> getData() {
		return data;
	}
	public void setData(List<EncryptedPassportElement> data) {
		this.data = data;
	}
	public EncryptedCredentials getCredentials() {
		return credentials;
	}
	public void setCredentials(EncryptedCredentials credentials) {
		this.credentials = credentials;
	}
	
	
}
