package org.janus.gui.web;

import java.io.Serializable;

import org.janus.gui.enums.GuiField;

public class ChangeKey implements Serializable{
	private int id;
	private GuiField field;
	
	public ChangeKey(int id, GuiField field) {
		super();
		this.id = id;
		this.field = field;
	}

	protected int getId() {
		return id;
	}

	protected GuiField getField() {
		return field;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChangeKey other = (ChangeKey) obj;
		if (field != other.field)
			return false;
		if (id != other.id)
			return false;
		return true;
	}


}
