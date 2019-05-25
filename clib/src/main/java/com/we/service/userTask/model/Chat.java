package com.we.service.userTask.model;

public class Chat {
	private Long id;
	private String type;
	private String title;
	private String username;
	private String first_name;
	private String last_name;
	private String description;
	private String invite_link;
	private String sticker_set_name;
	private Boolean all_members_are_administrators;
	private ChatPhoto photo;
	private Message pinned_message;
	private Boolean can_set_sticker_set;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getInvite_link() {
		return invite_link;
	}
	public void setInvite_link(String invite_link) {
		this.invite_link = invite_link;
	}
	public String getSticker_set_name() {
		return sticker_set_name;
	}
	public void setSticker_set_name(String sticker_set_name) {
		this.sticker_set_name = sticker_set_name;
	}
	public Boolean getAll_members_are_administrators() {
		return all_members_are_administrators;
	}
	public void setAll_members_are_administrators(Boolean all_members_are_administrators) {
		this.all_members_are_administrators = all_members_are_administrators;
	}
	public ChatPhoto getPhoto() {
		return photo;
	}
	public void setPhoto(ChatPhoto photo) {
		this.photo = photo;
	}
	public Message getPinned_message() {
		return pinned_message;
	}
	public void setPinned_message(Message pinned_message) {
		this.pinned_message = pinned_message;
	}
	public Boolean getCan_set_sticker_set() {
		return can_set_sticker_set;
	}
	public void setCan_set_sticker_set(Boolean can_set_sticker_set) {
		this.can_set_sticker_set = can_set_sticker_set;
	}
	
}
