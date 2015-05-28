
mylogger = new Y.Console( {
	style : 'block', // keeps the Console in the page flow as a block element
	visible : true
}).render("#logger");

einPropChangeEventAbarbeiten = function(data) {
	var node = Y.one(data.div);
	if (node) {
		if (data.prop == "value") {
			infomsg("setze " + data.div + " auf " + data.newvalue);
			node.set("value", data.newvalue);
		}
		;
		if (data.prop == "label") {
			infomsg("setze " + data.div + " auf " + data.newvalue);
			node.set("title", data.newvalue);
		}
		;
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

infomsg = function(text) {
	Y.log(text, '#info');
};

infomsg("logger ok");

myDataSource = new Y.DataSource.IO( {
	source : "http://localhost:8080/janus6/janus?ajax=true"
}), myCallback = {
	success : function(e) {
		allePropChangeEventsAbarbeiten(e.response.results);
	},
	failure : function(e) {
		infomsg("Could not retrieve data: " + e.error.message);
	}
};

infomsg("datasource ok");

myDataSource.plug(Y.Plugin.DataSourceJSONSchema, {
	schema : {
		resultListLocator : "events",
		resultFields : [ "div", "prop", "oldvalue", "newvalue" ]
	}
});

infomsg("dataschema ok");

// This request string will get appended to the URI
datenSenden = function(was, wert) {
	var r = "&" + was + "=" + wert;
	myDataSource.sendRequest( {
		request : r,
		callback : myCallback
	});
};

infomsg("datensenden ok");
