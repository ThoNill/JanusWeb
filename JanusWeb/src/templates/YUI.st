group WebUI ;


float() ::= << $if(it.InHbox)$float:left$endif$ >> 


htmlChildren() ::= <<
$it.Children:{ t | $t:(t.HtmlTemplate)()$ }$
>>

jsChildren() ::= <<
$it.Children:{ t |$t:(t.JsTemplate)()$ }$
>>

position() ::= <<width:$it.WidthEm$em; height:$it.HeightEm$em>>

classStyle() ::= << style="display:table-cell;$it:position()$" >>

class() ::= <<class="$it.Style$ >>

id(it) ::= << id="DIV$it.Id$" >>
formid(it) ::= << id="FORM$it.Id$" name="FORM$it.Id$" >>

HBOX(it) ::= <<
<!-- HBOX-->
<div  $it:id()$ class="nombp chbox"  style="display:table"  >
<div style="display:table-row" >
<!--<div class="b1title" >text</div>-->
$it.Children:{ t |  <div style="display:table-cell" > $t:(t.HtmlTemplate)()$ </div> }$
</div>
</DIV>
>>

VBOX(it) ::= <<
<!-- VBOX-->
<div $it:id()$ class="nombp" style="display:table">
$it.Children:{ t |  <div style="display:table-row" > $t:(t.HtmlTemplate)()$ </div> }$
</DIV>
>>

LABEL(it) ::= <<
<!-- LABEL -->
<div $it:id()$ $it:classStyle()$ >$it.Label$</DIV>
>>

optionen(it) ::= <<
$it.ItemIterator:{ item | <option value="$item.Index$" $if(item.Selected)$ selected="selected" $endif$ >$item.Text$</option> 
} $
>>

LIST(it) ::= <<

<form  $it:formid()$ action=" " >
<select  $it:id()$ name="$it.IdRef$" size="$it.RowCount$" $it:classStyle()$ >
$it:optionen()$
</select>
</form>
>>

COMBO(it) ::= <<

<form  $it:formid()$ action="">
<select  $it:id()$ name="$it.IdRef$" size="1" $it:classStyle()$ >
$it:optionen()$
</select>
</form>
>>

RADIO(it) ::= <<

<form  $it:formid()$ action="">
<table $it:id()$ border="" border-width="0" $it:classStyle()$ >
$it.ItemIterator:{ item | <tr><td><input type="radio" name="$it.IdRef$" value="$item.Index$" $if(item.Selected)$ checked="checked" $endif$ />$item.Text$</td></tr> 
} $
</table>
</form>
>>

CHECK(it) ::= <<

<form  $it:formid()$ action="">
<input $it:id()$ $it:classStyle()$ type="checkbox" name="$it.IdRef$" $if (it.Selected)$  value="true" checked="checked" $else$  value="false" $endif$ >$it.Label$<br>
</form>

>>

BUTTON(it) ::= <<
<form  $it:formid()$ action="">
<button $it:id()$ $it:classStyle()$ class='yui3-button success b2body'  name="$it.IdRef$" value="1" >$it.Label$</button>
</form>
>>

TEXTFIELD(it) ::= <<
<form  $it:formid()$ action="">
<INPUT $it:id()$ name="$it.IdRef$" value="$it.GuiValue$"  $it:classStyle()$ />
</form>
>>

tableColumn(it) ::= << { key: "column$i0$", label: "$it.Header$" } >>

SHOWTABLE(it) ::= <<
<!--  $it.ActionDescription$ -->
<div $it:id()$ class="yui3-skin-sam" $it:classStyle()$ >$it.Label$</DIV>
>>

tabheader(it) ::= <<
   <li><a href="#DIV$it.Id$">$it.Label$</a></li>
>>

tabitem(it) ::= <<
     <div id="DIV$it.Id$">$it:htmlChildren()$</div>
>>


TABS(it) ::= << 
<div $it:id()$  $it:classStyle()$>
    <ul>
       $it.Children:tabheader()$
     </ul>
    <div>
      $it.Children:tabitem()$
    </div>
</div>
<SCRIPT>
YUI().use('node','node-core', 'event','event-mouseenter', 'button','node-menunav','tabview',"console", "datasource","datasource-io", 
"datasource-jsonschema","console-filters", 'datatable',"dd-plugin",  function (Y) {



 var tabview$it.Id$ = new Y.TabView({
        srcNode: "#DIV$it.Id$"
    });

    tabview$it.Id$.render();

$jsChildren()$ 

});
</SCRIPT>
>>               

menuitem(it) ::= <<
<li class="yui3-menuitem">
	<a class="yui3-menu-label" href="http://localhost:8080/janus6/janus?$it.IdRef$">$it.Label$</a>
</li>
>>

menu(it) ::= <<

            <li>
                <a class="yui3-menu-label" href="#submenu$it.Id$"><em>$it.Label$</em></a>
                <div id="submenu$it.Id$" class="yui3-menu">
                 <ul>
                    <div class="yui3-menu-content">
                        $it.Children:menuitem()$
                    </div>
                 </ul>
                </div>
            </li>
            
>>

menubar(it) ::= <<
$if(it.WithMenueBar)$
<div id="hauptmenu" class="yui3-menu yui3-menu-horizontal yui3-menubuttonnav">
    <div class="yui3-menu-content">
        <ul>
      $it.MenuBar.Children:menu()$
       </ul>
    </div>
</div>
$endif$
>>


skriptGUI(it) ::= <<
<script type="text/javascript">
// Create a YUI sandbox on your page.

var Y = YUI( {
	modules : {
		myjs : {
		 	fullpath : 'http://localhost:8080/janus6/js/yui_nill.js',
		}
	}
});


Y.use('node', 'event', 'button','dom','node-menunav','tabview',"console", "datasource","datasource-io", 
"datasource-jsonschema","console-filters", 'datatable','dd-plugin',  function (Y) {
Y.use("myjs", function (Y) {

setterHash = {};
setterHash["#DIV$it.Id$"] = setterGUI;   

 var menu = Y.one("#hauptmenu");
 menu.plug(Y.Plugin.NodeMenuNav);
   
   $jsChildren()$
}); 
});
</script>
>>


GUI(it) ::= <<
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="http://yui.yahooapis.com/3.6.0/build/cssreset/reset-min.css" type="text/css">
    <link rel="stylesheet" href="http://yui.yahooapis.com/3.6.0/build/cssfonts/fonts-min.css" type="text/css">
    <link rel="stylesheet" href="http://yui.yahooapis.com/3.6.0/build/cssbase/base-min.css" type="text/css">
    <link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/3.6.0/build/cssgrids/grids-min.css">
    <link rel="stylesheet" href="http://localhost:8080/janus6/js/tabelle.css" type="text/css">
    
 
    
 <script type="text/javascript" src="http://yui.yahooapis.com/3.13.0/build/yui/yui-debug.js"></script>

$it:skriptGUI()$

<title>$it.label$</title>

<!--link rel="stylesheet" type="text/css" href="http://localhost:8080/janus6/js/default.css" /--> 

</head>
<body $it:id()$ class="yui3-skin-sam"  style="display:table" >

$it:menubar()$



$it:htmlChildren()$
</body>
</html>

>>

JsTAB(it) ::= << $jsChildren()$ >>

JsTABS(it) ::= << >>

JsHBOX(it) ::= << $jsChildren()$ >>

JsVBOX(it) ::= <<$jsChildren()$>>

JsRADIO(it) ::= << 


var g$it.Id$ = Y.one("#DIV$it.Id$");
var f$it.Id$ = Y.one("#FORM$it.Id$");

// Step 2. Subscribe to its click event with a callback function
var send$it.Id$ = function (e) {
                    e.preventDefault(); 
                    var value = e.target.get("value");
                    datenSenden("$it.IdRef$",value);
                    };

f$it.Id$.on("submit", send$it.Id$);   
g$it.Id$.delegate('click',send$it.Id$,'input');

setterHash["#DIV$it.Id$"] = setterRADIO;
>>

JsCHECK(it) ::= << 
var g$it.Id$ = Y.one("#DIV$it.Id$");
var f$it.Id$ = Y.one("#FORM$it.Id$");

var send$it.Id$ = function (e) {
              e.preventDefault();  
              var value = "true";
              if (g$it.Id$.get("checked") == "checked") {
              	value = "true";
              } else {
              value = "false";
                  g$it.Id$.set("checked", "");
              };
       //       datenSenden("$it.IdRef$",value);
              };
              
var click$it.Id$ = function (e) {
              var value = g$it.Id$.get("value");
             
              if (value == "true") {
              	value = "false";
              } else {
                value = "true";
              };
               g$it.Id$.set("value",value);
              datenSenden("$it.IdRef$",value);
              };              
              
              
              
g$it.Id$.on("change", click$it.Id$);
f$it.Id$.on("submit", send$it.Id$);   

setterHash["#DIV$it.Id$"] = setterCHECK;
>>

JsCOMBO(it) ::= << 


var g$it.Id$ = Y.one("#DIV$it.Id$");
var f$it.Id$ = Y.one("#FORM$it.Id$");

// Step 2. Subscribe to its click event with a callback function
var send$it.Id$ = function (e) {e.preventDefault();  datenSenden("$it.IdRef$",g$it.Id$.get("value"))};
g$it.Id$.on("click", send$it.Id$);
f$it.Id$.on("submit", send$it.Id$);   

setterHash["#DIV$it.Id$"] = setterLIST;
>>

JsLIST(it) ::= << 


var g$it.Id$ = Y.one("#DIV$it.Id$");
var f$it.Id$ = Y.one("#FORM$it.Id$");

// Step 2. Subscribe to its click event with a callback function
var send$it.Id$ = function (e) {e.preventDefault();  datenSenden("$it.IdRef$",g$it.Id$.get("value"))};
g$it.Id$.on("click", send$it.Id$);
f$it.Id$.on("submit", send$it.Id$);   

setterHash["#DIV$it.Id$"] = setterLIST;
>>

JsBUTTON(it) ::= <<

var g$it.Id$ = Y.one("#DIV$it.Id$");
var f$it.Id$ = Y.one("#FORM$it.Id$");

// Step 2. Subscribe to its click event with a callback function
var send$it.Id$ = function (e) {e.preventDefault();  datenSenden("$it.IdRef$","")};
g$it.Id$.on("click", send$it.Id$);
f$it.Id$.on("submit", send$it.Id$);     
setterHash["#DIV$it.Id$"] = setterLABEL;
>>

JsTEXTFIELD(it) ::= <<

var g$it.Id$ = Y.one("#DIV$it.Id$");
var f$it.Id$ = Y.one("#FORM$it.Id$");

// Step 2. Subscribe to its click event with a callback function
var send$it.Id$ = function (e) {e.preventDefault();  datenSenden("$it.IdRef$",g$it.Id$.get("value"))};
g$it.Id$.on("click", send$it.Id$);
f$it.Id$.on("submit", send$it.Id$);     

>>

JsLABEL(it) ::= << 
setterHash["#DIV$it.Id$"] = setterLABEL;
>>

JsSHOWTABLE(it) ::= <<

var data$it.Id$ = [];


var table$it.Id$ = new Y.DataTable({
    columns: [ "zeilennr" ,
    $it.TableColumnDescriptions:tableColumn(); separator=", "$
    ],
    data: data$it.Id$,

    // Optionally configure your table with a caption
    caption: "My first DataTable!",
    // and/or a summary (table attribute)
    summary: "Example DataTable showing basic instantiation configuration",
    highlightRows: true 
});


setterHash["#DIV$it.Id$"] = function (node, prop, value) {
                                setterSHOWTABLE(node, prop, value,table$it.Id$);
                             };
                      



table$it.Id$.addAttr("selectedRow", { value: null });

table$it.Id$.delegate('click', function (e) {
    var tr = e.currentTarget;
 	var rec = this.getRecord(tr); 
    var zeile = rec.get('zeilennr');
    
    datenSenden("$it.IdRef$",zeile);    
    
 }, '.yui3-datatable-data tr', table$it.Id$);
                      
 
 table$it.Id$.after('selectedRowChange', function (e) {

    var tr = this.view.body.tbodyNode.get('children').item( e.newVal);      // the Node for the TR clicked ...
    var last_tr = this.view.body.tbodyNode.get('children').item(e.prevVal);   // the current Record for the clicked TR
        
    var mclass = "myh";    
    if ( last_tr ) {
         last_tr.removeClass(mclass);
      //  last_tr.all('td').setStyle('background',this.oldRowStyle);
    }

  if ( tr ) {
       tr.addClass(mclass);
   }
     
 
 });               
                      
                         

table$it.Id$.render("#DIV$it.Id$");
    

>>