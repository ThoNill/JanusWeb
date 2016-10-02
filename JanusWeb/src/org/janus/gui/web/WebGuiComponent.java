package org.janus.gui.web;

import java.io.Serializable;
import java.util.List;

import org.janus.gui.basis.GuiComponent;

public interface WebGuiComponent extends GuiComponent, Serializable{

    List<? extends WebGuiComponent> getChildComponents();
}
