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

tableColumn(it) ::= << { key: "$it.Name$", label: "$it.Header$" } >>

SHOWTABLE(it) ::= <<
<!--  Table  -->
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

>>               

menuitem(it) ::= <<
<li class="yui3-menuitem">
	<a class="yui3-menu-label" href="http://localhost:8082/janus6/janus?$it.IdRef$">$it.Label$</a>
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
		 	fullpath : 'http://localhost:8082/janus6/js/yui_nill.js',
		}
	}
});

Y.use('node', 'event', 'button','dom','node-menunav','tabview',"console", "datasource","datasource-io", 
"datasource-jsonschema","console-filters", 'datatable','dd-plugin',  function (Y) {
Y.use("myjs", function (Y) {

try {

Y.log("start der Seitenverbindung", '#info');

// setterHash = {};
addToSetterHash("#DIV$it.Id$",setterGUI);   

// TODO Nur wenn ein Menue da ist
// var menu = Y.one("#hauptmenu");
// menu.plug(Y.Plugin.NodeMenuNav);
   
   Y.log("vor dem Verbindern der Kinder", '#info');   
   
   $jsChildren()$
   
   Y.log("Ende der Seitenverbindung", '#info');
} catch(err) {
    Y.log(err.message, '#info');
}

}); 
});


Y.log("Ende der Sandbox", '#info');
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
    <link rel="stylesheet" href="http://localhost:8082/janus6/js/tabelle.css" type="text/css">
    
 
    
 <script type="text/javascript" src="http://yui.yahooapis.com/3.13.0/build/yui/yui-debug.js"></script>


<title>$it.label$</title>

<!--link rel="stylesheet" type="text/css" href="http://localhost:8086/janus6/js/default.css" /--> 

</head>
<body $it:id()$ class="yui3-skin-sam"  style="display:table" >

$it:menubar()$



$it:htmlChildren()$
</body>
$it:skriptGUI()$
</html>

>>

JsTAB(it) ::= << 

$jsChildren()$ 

>>

JsTABS(it) ::= <<


tabview$it.Id$ = new Y.TabView({
        srcNode: "#DIV$it.Id$"
    });
tabview$it.Id$.render();

addToSetterHash2("#DIV$it.Id$",setterTABS,tabview$it.Id$);

// Step 2. Subscribe to its click event with a callback function
var send$it.Id$ = function (e) {
                    e.preventDefault(); 
                    
                    var sel = tabview$it.Id$.get('activeDescendant');
   					var index= tabview$it.Id$.indexOf(sel);
                    datenSenden("$it.IdRef$",index);
                    Y.log("value " +index, '#info');   
                    };

tabview$it.Id$.after('selectionChange',send$it.Id$);

Y.log("Ende Verbinde TABS ", '#info');   


$jsChildren()$ 


 >>

JsHBOX(it) ::= << $jsChildren()$ >>

JsVBOX(it) ::= <<$jsChildren()$>>

JsRADIO(it) ::= << 

Y.log("Verbinde RADIO", '#info');   

var g$it.Id$ = Y.one("#DIV$it.Id$");
var f$it.Id$ = Y.one("#FORM$it.Id$");


// Step 2. Subscribe to its click event with a callback function
var send$it.Id$ = function (e) {
Y.log("RADIO", '#info');
                    e.preventDefault(); 
                    var value = e.target.get("value");
                    datenSenden("$it.IdRef$",value);
                    };

f$it.Id$.on("submit", send$it.Id$);   
g$it.Id$.delegate('click',send$it.Id$,'input');

addToSetterHash("#DIV$it.Id$", setterRADIO);
>>

JsCHECK(it) ::= << 

Y.log("Verbinde CHECK", '#info');   

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

addToSetterHash("#DIV$it.Id$",setterCHECK);
>>

JsCOMBO(it) ::= << 

Y.log("Verbinde COMBO", '#info');   

var g$it.Id$ = Y.one("#DIV$it.Id$");
var f$it.Id$ = Y.one("#FORM$it.Id$");

// Step 2. Subscribe to its click event with a callback function
var send$it.Id$ = function (e) {e.preventDefault();  datenSenden("$it.IdRef$",g$it.Id$.get("value"))};
g$it.Id$.on("click", send$it.Id$);
f$it.Id$.on("submit", send$it.Id$);   

addToSetterHash("#DIV$it.Id$",setterLIST);
>>

JsLIST(it) ::= << 

Y.log("Verbinde LIST", '#info');   

var g$it.Id$ = Y.one("#DIV$it.Id$");
var f$it.Id$ = Y.one("#FORM$it.Id$");

// Step 2. Subscribe to its click event with a callback function
var send$it.Id$ = function (e) {e.preventDefault();  datenSenden("$it.IdRef$",g$it.Id$.get("value"))};
g$it.Id$.on("click", send$it.Id$);
f$it.Id$.on("submit", send$it.Id$);   

addToSetterHash("#DIV$it.Id$",setterLIST);
>>

JsBUTTON(it) ::= <<

Y.log("Verbinde BUTTON", '#info');   

var g$it.Id$ = Y.one("#DIV$it.Id$");
var f$it.Id$ = Y.one("#FORM$it.Id$");

// Step 2. Subscribe to its click event with a callback function
var send$it.Id$ = function (e) {e.preventDefault(); window.location.href = "http://localhost:8082/test?$it.IdRef$=1";}; // datenSenden("$it.IdRef$","1");};
g$it.Id$.on("click", send$it.Id$);
f$it.Id$.on("submit", send$it.Id$);     
addToSetterHash("#DIV$it.Id$",setterBUTTON);
>>

JsTEXTFIELD(it) ::= <<

Y.log("Verbinde TEXTFIELD", '#info');   

var g$it.Id$ = Y.one("#DIV$it.Id$");
var f$it.Id$ = Y.one("#FORM$it.Id$");

// Step 2. Subscribe to its click event with a callback function
var send$it.Id$ = function (e) {e.preventDefault();  datenSenden("$it.IdRef$",g$it.Id$.get("value"))};
g$it.Id$.on("click", send$it.Id$);
f$it.Id$.on("submit", send$it.Id$);     

>>

JsLABEL(it) ::= << 

Y.log("Verbinde LABEL", '#info');   

addToSetterHash("#DIV$it.Id$", setterLABEL);
>>

JsSHOWTABLE(it) ::= <<

Y.log("Verbinde SHOWTABLE", '#info');   

var data$it.Id$ = $it.TableData$;


var table$it.Id$ = new Y.DataTable({
    columns: [ { key: "zeilennr", label: "Zeile" },
    $it.TableColumnDescriptions:tableColumn(); separator=", "$
    ],
    data: data$it.Id$,

    // Optionally configure your table with a caption
    caption: "My first DataTable!",
    // and/or a summary (table attribute)
    summary: "Example DataTable showing basic instantiation configuration",
    highlightRows: true 
});


addToSetterHash("#DIV$it.Id$", function (node, prop, value) {
                                setterSHOWTABLE(node, prop, value,table$it.Id$);
                             });
                      



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