try {

	var mylogger = new Y.Console().render();

	infomsg1 = function(text) {
		Y.log(text, '#info');
	};
	
	
	infomsg = function(text) {
		Y.log(text, '#info');
	};

	var setterHash = {};

	callSetterFunction = function(divid, node, prop, value) {
		infomsg("setze " + prop + " von " + divid + " auf " + value);
		var f = setterHash[divid];
		if (f) {
			infomsg("ok! finde den Setter für  " + divid);
			f(node, prop, value);
		} else {
			infomsg("oje!! finde den Setter für  " + divid + " nicht");
			setterSTANDARD(node, prop, value);
		}
	}

	setterRADIO = function(node, prop, value) {
		if (prop == "value") {
		} else if (prop == "currentRow") {
			var rnode = node.one('input[checked="checked"]');
			// alert("finde 1 " + rnode);
			if (rnode) {
				// rnode.removeAttribute("checked");
			}
			;
			rnode = node.one('input[value="' + value + '"]');
			// alert("finde 2 " + rnode);
			infomsg("setze Wert auf " + value);
			if (rnode) {
				rnode.set("checked", "checked");
			}
			;
		} else {
			setterSTANDARD(node, prop, value);
		}
		;
	}

	setterCHECK = function(node, prop, value) {
		if (prop == "value") {
			if (value == "true") {
				node.set("checked", "checked");
			} else {
				node.set("checked", "");
				value = "false";
			}
			node.set("value", value);
		} else {
			setterSTANDARD(node, prop, value);
		}
		;
	}

	setterLIST = function(node, prop, value) {
		if (prop == "value") {
		} else if (prop == "currentRow") {
			infomsg("setze Wert auf " + value);
			node.set("value", value);
		} else {
			setterSTANDARD(node, prop, value);
		}
		;
	}

	setterSTANDARD = function(node, prop, value) {
		infomsg("setze Wert " + prop + " auf " + value);
		if (prop == "focus") {
			node.focus();
		}
		;
		if (prop == "value") {
			infomsg("setze Wert auf " + value);
			node.set("value", value);
		}
		;
		if (prop == "enabled") {
			infomsg("setze Wert auf " + value);
			if (value == "true") {
				node.set("disabled", "");
			} else {
				node.set("disabled", "disabled");
			}
		}
		;
		if (prop == "visible") {
			infomsg("setze Sichtbarkeit auf " + value);
	//		node.set("visible",value);
			if (value == "true") {
				node.setStyle("visibility", "visible");
			} else {
	//			node.set("display", "none");
				node.setStyle("visibility", "hidden");
			}

		}
		;
		if (prop == "style") {
			infomsg1("setze Style Wert auf " + value);
			try {
				node.setStyles( value);
			} catch (ex) {
				infomsg1("Exf " + ex);
			}
			}
		;
	}

	setterLABEL = function(node, prop, value) {
		if (prop == "label") {
			infomsg("setze LABEL auf " + value);
			node.setHTML(value);
		} else {
			setterSTANDARD(node, prop, value);
		}
		;
	}

	setterGUI = function(node, prop, value) {
		if (prop == "label") {
			infomsg("setze GUI auf " + value);
			document.title = value;
		} else {
			setterSTANDARD(node, prop, value);
		}
		;
	}

	setterSHOWTABLE = function(node, prop, value, table) {
		if (prop == "value") {
			try {
				table.set("data", value.data);
				table.set('selectedRow',0);
			} catch (e) {
				infomsg(e);
			};
		} else {	
			if (prop == "currentRow") {
				table.set('selectedRow', value);
			} else {
				setterSTANDARD(node, prop, value);
			};
		}
		;
	}

	einPropChangeEventAbarbeiten = function(data) {
		var node = Y.one(data.div);
		if (node) {
			callSetterFunction(data.div, node, data.prop, data.newvalue);
		} else {
			infomsg("finde " + data.div + " nicht! ");
		}
		;
	};

	allePropChangeEventsAbarbeiten = function(data) {
		for ( var k = 0; k < data.length; k++) {
			einPropChangeEventAbarbeiten(data[k]);
		}
	};

	var myDataSource = new Y.DataSource.IO( {
		source : "http://localhost:8080/janus6/janus?ajax=true"
	});

	var myCallback = {
		success : function(e) {
			allePropChangeEventsAbarbeiten(e.response.results);
		},
		failure : function(e) {
			infomsg("Could not retrieve data: " + e.error.message);
		}
	};

	myDataSource.plug(Y.Plugin.DataSourceJSONSchema, {
		schema : {
			resultListLocator : "events",
			resultFields : [ "div", "prop", "oldvalue", "newvalue" ]
		}
	});

	// This request string will get appended to the URI
	datenSenden = function(was, wert) {
		var r = "&" + was + "=" + wert;
		myDataSource.sendRequest( {
			request : r,
			callback : myCallback
		});
	};
	


} catch (ex) {
	alert(ex);
};
// alert("ende laden");
