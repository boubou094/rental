package com.sii.rental.ui;

import org.eclipse.jface.viewers.IColorProvider;

public class Palette {
	private String id;
	private String name;
	private IColorProvider colorProvider;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public IColorProvider getColorProvider() {
		return colorProvider;
	}
	public void setColorProvider(IColorProvider colorProvider) {
		this.colorProvider = colorProvider;
	}
}
