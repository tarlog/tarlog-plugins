package com.tarlog.plugins.maven;

import java.util.Properties;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public enum PropertiesConentProvider implements IStructuredContentProvider {
	instance;

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

	@Override
	public Object[] getElements(Object inputElement) {
		Properties properties =  (Properties) inputElement;
		return properties.entrySet().toArray();
	}

}
