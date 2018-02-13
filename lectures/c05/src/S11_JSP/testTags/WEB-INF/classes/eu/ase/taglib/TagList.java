package eu.ase.taglib;

import java.io.*;
import java.util.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class TagList extends BodyTagSupport {

private List<String> mItems;

public void setItems(List<String> aItems) {
  mItems = aItems;
}

public int doAfterBody() throws JspException {
  BodyContent body = getBodyContent();
  String text = body.getString().trim();
  List<String> list = text.length() >0 ? text2List(text) : mItems;
  if (list == null) return SKIP_BODY;

  try {
    JspWriter out = body.getEnclosingWriter();
    out.println("<select>");
    String item;
    for (int i=0; i<list.size(); i++) {
      //item = (String)list.get(i);
      item = list.get(i);
      if (item.length()==0) continue;
      out.println(" <option>" + item + "</option>");
    }
    out.println("</select>");
  } catch (IOException ex) {
    throw new JspTagException(ex.getMessage());
  } // try

  return SKIP_BODY;
} // doAfterBody()

/** Break aText into a list of lines */
private List<String> text2List(String aText) {
  List<String> list = new ArrayList<String>();
  StringTokenizer tz = new StringTokenizer(aText, "\n\r");
  String item;
  while (tz.hasMoreTokens()) {
    item = tz.nextToken().trim();
    if (item.length() > 0) list.add(item);
  } // while
  return list;
} // text2List()

} // TagList