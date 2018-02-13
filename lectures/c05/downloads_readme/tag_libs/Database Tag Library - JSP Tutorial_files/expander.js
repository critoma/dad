var as = 1;
var fontt;
var expo;

function addChilds(das,exps)
{
	var nam =das;
	expo = exps;
      
      if(das == "operators")
	{

	var tdr = document.getElementById("operators");
	removeChilds(fontt);
	fontt = tdr.getElementsByTagName('font').item(0);
	removeChilds(fontt);
      addChil(fontt,"operators/arithmeticopr.php","Arithmetic");
	addChil(fontt,"operators/logicalopr.php","Logical");
	addChil(fontt,"operators/relationalopr.php","Relational");
      addChil(fontt,"operators/emptyopr.php","Empty");
      addChil(fontt,"operators/conditionalopr.php","Conditional");
      }
	else if(das == "jspelements")
	{
	var tdr = document.getElementById("jspelements");
	removeChilds(fontt);
	fontt = tdr.getElementsByTagName('font').item(0);
	removeChilds(fontt);
	addChil(fontt,"jspelements/directive-element.php","Directives");
      addChil(fontt,"jspelements/page-directive.php","Page Directive");
      addChil(fontt,"jspelements/include-directive.php","Include Directive");
      addChil(fontt,"jspelements/taglib-directive.php","Taglib Directive");
      addChil(fontt,"jspelements/standard-actions.php","Standard JSP Tags");
      addChil(fontt,"jspelements/jsp-scriptlets.php","JSP Scriptlets");
      addChil(fontt,"jspelements/jsp-expression.php","JSP Expression");
      addChil(fontt,"jspelements/jsp-declaration.php","JSP Declaration");
      	}
      else if(das == "jstltags")
	{
	var tdr = document.getElementById("jstltags");
	removeChilds(fontt);
	fontt = tdr.getElementsByTagName('font').item(0);
	removeChilds(fontt);
      addChil(fontt,"jstltags/jstl.php","JSTL");
	addChil(fontt,"jstltags/core-tag.php","CORE Tag");
      addChil(fontt,"jstltags/function-tag.php","Function Tag");
      addChil(fontt,"jstltags/database-tag.php","Database Tag");
      addChil(fontt,"jstltags/internationalization-tag.php","118N Tag");
      addChil(fontt,"jstltags/xml-tag.php","XML Tag");

	}
      else if(das == "webxml")
	{
	var tdr = document.getElementById("webxml");
	removeChilds(fontt);
	fontt = tdr.getElementsByTagName('font').item(0);
	removeChilds(fontt);
      addChil(fontt,"webxml/web-xml-syntax.php","Syntax of web.xml");
      addChil(fontt,"webxml/welcome-file-list-tag.php","Welcome file configuration");
      addChil(fontt,"webxml/session-config-tag.php","Session configuration");
      addChil(fontt,"webxml/error-page-tag.php","Error page configurtion");
      addChil(fontt,"webxml/servlet-mapping-tag.php","URL Mapping");
     }
      else if(das == "examples")
	{
	var tdr = document.getElementById("examples");
	removeChilds(fontt);
	fontt = tdr.getElementsByTagName('font').item(0);
	removeChilds(fontt);
      addChil(fontt,"examples/example1.php","Example1");
      addChil(fontt,"examples/example2.php","Example2");

      }
      else if(das == "customtag")
	{
	var tdr = document.getElementById("customtag");
	removeChilds(fontt);
	fontt = tdr.getElementsByTagName('font').item(0);
	removeChilds(fontt);
      addChil(fontt,"customtag/custom-tags.php","Custom Tags");
      addChil(fontt,"customtag/java-file.php","Creating Java File");
      addChil(fontt,"customtag/tld-file.php","Creating TLD");
      addChil(fontt,"customtag/using-tld.php","Using TLD");
      }


      /*
	else if(das == "others")
	{
	var tdr = document.getElementById("others");
	removeChilds(fontt);
	fontt = tdr.getElementsByTagName('font').item(0);
	removeChilds(fontt);

	addChil(fontt,"specialCharacter.php","Special Characters");
	addChil(fontt,"iframe.php","IFrame");
	addChil(fontt,"css.php","Simple CSS");
	}*/

}

function removeChilds(fontt)
{
	if(fontt != null)
	{
		while(fontt.hasChildNodes() && fontt.childNodes.length> 1)
		{	
		if(as > 1)
		{
		fontt.removeChild(fontt.lastChild);
		}
		as = as+1;
		}
	}
}

var love = "yes";
function addChil(parent,linkname,textname)
{

var breaka = document.createElement("br");
var divs= document.createElement("div");
var link = document.createElement("a");
link.setAttribute("href",ajss+expo+linkname);
link.style.color="#aa300a";
divs.style.marginLeft="8px";
divs.style.marginRight="4px";
divs.style.paddingLeft="3px";
divs.style.fontSize="11px";
divs.style.lineHeight="16px";
divs.style.border="0px green solid";

if(love == "yes")
{
divs.style.backgroundColor="#beefec";
love = "no";
}
else
{
divs.style.backgroundColor="#dfffdf";
love = "yes";
}
link.style.textDecoration="none";
//link.setAttribute("style","background-color: white;");

var name = document.createTextNode(textname);
//parent.appendChild(breaka);
parent.appendChild(divs);
divs.appendChild(link);
link.appendChild(name);
}

