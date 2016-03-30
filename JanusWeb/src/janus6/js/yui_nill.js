
var setterHash = {};
var nodeHash = {};

try {

	var mylogger = new Y.Console().render();
	Y.log("Startee das laden", '#info');

	addToSetterHash = function(divid, setter) {
		setterHash[divid] = setter;
		nodeHash[divid]	= Y.one(divid);
	}
	
	addToSetterHash2 = function(divid, setter, node) {
		setterHash[divid] = setter;
		nodeHash[divid] = node;
	}
	
	infomsg1 = function(text) {
		Y.log(text, '#info');
	};
	
	
	infomsg = function(text) {
		Y.log(text, '#info');
	};


	callSetterFunction = function(divid, node, prop, value) {
		infomsg("setze " + prop + " von " + divid + " auf " + value);
		var pos = 1; //divid.substr(4);
		var f = setterHash[divid];
		if (f) {
			infomsg("ok! finde den Setter f�r  " + divid);
			f(node, prop, value,pos);
		} else {
			infomsg("oje!! finde den Setter f�r  " + divid + " nicht");
			setterSTANDARD(node, prop, value,pos);
		}
	}
	
	setterBUTTON = function(node, prop, value, pos) {
			window.location.href = "http://localhost:8082/test";
	}

	setterRADIO = function(node, prop, value, pos) {
		if (prop == "value" || prop == "currentrow") {
			var rnode = node.one('input[checked="checked"]');
			infomsg("finde 1 " + rnode);
			if (rnode) {
				rnode.removeAttribute("checked");
			}
			rnode = node.one('input[value="' + value + '"]');
			infomsg("finde 2 " + rnode);
			infomsg("setze RADIO Wert auf " + value);
			if (rnode) {
				rnode.set("checked", "checked");
			}
		} else {
			setterSTANDARD(node, prop, value);
		}
	}

	setterCHECK = function(node, prop, value, pos) {
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
	}

	setterLIST = function(node, prop, value, pos) {
		if (prop == "value" || prop == "currentrow") {
			infomsg("setze Wert auf " + value);
			node.set("value", value);
		} else {
			setterSTANDARD(node, prop, value);
		}
	}
	
	setterTABS = function(node, prop, value, pos) {
		infomsg("setter TABS");
		if (prop == "value" || prop == "currentrow") {
			infomsg("setze TAB Wert auf " + value);
			infomsg("TAB Wert auf " + node);
			try {
				node.selectChild(value);
			//	tabview${pos}.selectChild(value);
				
			//	var index= node.Id$.indexOf();
			//	node.set('activeDescendant',sel);
				
			} catch(err) {
				infomsg(err.message);
			}
		} else {
			setterSTANDARD(node, prop, value);
		}
	}

	setterSTANDARD = function(node, prop, value, pos) {
		infomsg("setze Wert " + prop + " auf " + value);
		if (prop == "focus") {
			node.focus();
		}
		if (prop == "value") {
			infomsg("setze Wert auf " + value);
			node.set("value", value);
		}
		if (prop == "enabled") {
			infomsg("setze Wert auf " + value);
			if (value == "true") {
				node.set("disabled", "");
			} else {
				node.set("disabled", "disabled");
			}
		}
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
		if (prop == "style") {
			infomsg1("setze Style Wert auf " + value);
			try {
				node.setStyles( value);
			} catch (ex) {
				infomsg1("Exf " + ex);
			}
		}
	}

	setterLABEL = function(node, prop, value, pos) {
		if (prop == "label") {
			infomsg("setze LABEL auf " + value);
			node.setHTML(value);
		} else {
			setterSTANDARD(node, prop, value);
		}
	}

	setterGUI = function(node, prop, value, pos) {
		if (prop == "label") {
			infomsg("setze GUI auf " + value);
			document.title = value;
		} else {
			setterSTANDARD(node, prop, value);
		}
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
			if (prop == "currentrow") {
				table.set('selectedRow', value);
			} else {
				setterSTANDARD(node, prop, value);
			};
		}
	}

	einPropChangeEventAbarbeiten = function(data) {
		var node = nodeHash[data.div];
		if (node) {
			callSetterFunction(data.div, node, data.prop, data.newvalue);
		} else {
			infomsg("finde " + data.div + " nicht! ");
		}
	};

	allePropChangeEventsAbarbeiten = function(data) {
		for ( var k = 0; k < data.length; k++) {
			einPropChangeEventAbarbeiten(data[k]);
		}
	};

	var myDataSource = new Y.DataSource.IO( {
		source : "http://localhost:8082/test?ajax=true"
	});

	var myCallback = {
		success : function(e) {
			allePropChangeEventsAbarbeiten(e.response.results);
		},
		failure : function(e) {
			infomsg("Could not retrieve data: " + e.data.responseText + " " + e.error.message);
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
		Y.log("daten senden",'#info');
		var r = "&" + was + "=" + wert;
		myDataSource.sendRequest( {
			request : r,
			callback : myCallback
		});
	};
	
	Y.log("Stoppe das laden", '#info');

} catch (ex) {
	alert(ex);
};
// alert("ende laden");
