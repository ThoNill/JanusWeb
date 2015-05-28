group WebUI ;



GUI(this) ::= <<

<html>
<head>
<title>$this.label$</title>
<link rel="stylesheet" type="text/css" href="default.css" /> 
</head>
<body>
$this:children()$
</body>
</html>

>>

children() ::= <<
$it.Children:{ t | $t.Html$ }$
>>

classStyle() ::= <<
class="$it.Style$" 
>>

HBOX(this) ::= <<
<div  class="nombp chbox"  style="width:$this.WidthEm$em; height:$this.HeightEm$em; background:yellow ">
<div class="b1title" style="background:blue" >text</div>
$this:children()$
</DIV>
>>

VBOX(this) ::= <<
<div class="nombp" style="width:$this.WidthEm$em; height:$this.HeightEm$em; background:green; " >
$this:children()$
</DIV>
>>

TEXTFIELD(this) ::= <<
<INPUT $this:id()$ name="$this.IdRef$" value="$this.GuiValue$"  style="width:$this.WidthEm$em; height:$this.HeightEm$em;" />
>>


id(this) ::= << id="DIV$this.Id$" >>


