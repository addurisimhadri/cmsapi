package com.sim.wicmsapi.utility;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class MenuGenerator {
	
	public static String getMenus(Connection con, String roleType) {				
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = null;
		Stack<Element> parentStack = new Stack<>();
		String res = "";
		Map<String, String> attrMap = new HashMap<String, String>();
		String menuLinkPath = "#";
		int limit = 0;
		//String logoutLink = "";
		
		try {			
			query = "select distinct cm.menu_id,cm.menu_name,cm.action_hyperlink from cms_menus cm left join role_permissions rp  on cm.menu_id = rp.menu_id left join user_master um on um.role_type = rp.role_id where um.role_type ='"+roleType+"' and menu_status='Y' order by cm.menu_order";
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();			
			
			int curMenu = 0, prevMenu = 0;
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			//Element ele = null;
			Element parentEle = doc.createElement("div");
			parentEle.setAttribute("class", "collapse navbar-collapse");
			parentEle.setAttribute("id","coll");
			doc.appendChild(parentEle);
			parentStack.push(parentEle);			
			//Element tempEle = null, tempEle1 = null, tempEle2 = null;			
						
			while(rs.next()) {	
				
				
				if( !rs.getString("action_hyperlink").isEmpty() )
					menuLinkPath = "return displayPage('/pcw"+rs.getString("action_hyperlink")+"?menuid="+rs.getString("menu_id")+"&menuname="+rs.getString("menu_name")+"');";
				curMenu = rs.getString(1).length(); 				

				if( curMenu > prevMenu ) {
					if( prevMenu == 2 ) {   	
						if( parentStack.peek().getFirstChild() != null && parentStack.peek().getFirstChild().getNodeName().equals("a") ) {
							attrMap.put("class", "caret");
							addElement(doc, "span", null, attrMap, parentStack.peek().getFirstChild(), parentStack, false);
							attrMap.clear();							
						}
					}else if( parentStack.peek().getNodeName().equals("li") ) {
						parentStack.peek().setAttribute("class", "dropdown-submenu");
					}			
					if( curMenu == 2 ) attrMap.put("class", "nav navbar-nav");
					else attrMap.put("class", "dropdown-menu");
					addElement(doc, "ul", null, attrMap, parentStack.peek(), parentStack, true);
					attrMap.clear();
					
					//li adding
					if( curMenu == 2 ) attrMap.put("class", "dropdown");
					else attrMap.put("class", "dropdown-submenu");
					addElement(doc, "li", null, attrMap, parentStack.peek(), parentStack, true);
					attrMap.clear();
					
					//a adding
					if( curMenu == 2 &&  menuLinkPath.equals("#") ) {
						attrMap.put("aria-expanded", "false");
						attrMap.put("tabindex", "0");
						attrMap.put("data-toggle", "dropdown");
						attrMap.put("data-submenu", "");
					}else {
						attrMap.put("onclick", menuLinkPath);
					}
					addElement(doc, "a", rs.getString(2), attrMap, parentStack.peek(), parentStack, false);
					attrMap.clear();
					
				} else if( curMenu < prevMenu ) {//descending
					
					if( parentStack.peek().getNodeName().equals("li")) {
						parentStack.peek().removeAttribute("class");						
					}
					
					limit = prevMenu-curMenu;
					if( curMenu == 2 ) limit = parentStack.size() - 1;
					//log.info("Pop limit:"+limit);

					for( int i=0; i< limit; i++ ) {
						//log.info("Pop of Stack Element:"+parentStack.peek());
						parentStack.pop();
					}					
					
					if( curMenu == 2 ) {
						//ul adding
						attrMap.put("class", "nav navbar-nav");
						addElement(doc, "ul", null, attrMap, parentStack.peek(), parentStack, true);
						attrMap.clear();					
					}
					
					//li adding
					attrMap.put("class", "dropdown");
					addElement(doc, "li", null, attrMap, parentStack.peek(), parentStack, true);
					attrMap.clear();
					
					//a adding
					if( curMenu == 2 &&  menuLinkPath.equals("#") ) {
						attrMap.put("aria-expanded", "false");
						attrMap.put("tabindex", "0");
						attrMap.put("data-toggle", "dropdown");
						attrMap.put("data-submenu", "");
					}else {
						attrMap.put("onclick", menuLinkPath);
					}
					addElement(doc, "a", rs.getString(2), attrMap, parentStack.peek(), parentStack, false);
					attrMap.clear();
					
				} else if( curMenu == 2) {//equal/same node					
					
					limit = parentStack.size() - 1;
					//log.info("Pop limit:"+limit);
					for( int i=0; i< limit; i++ ) {
						//log.info("Pop of Stack Element:"+parentStack.peek());
						parentStack.pop();
					}
					
					//ul adding					
					attrMap.put("class", "nav navbar-nav");				
					addElement(doc, "ul", null, attrMap, parentStack.peek(), parentStack, true);
					attrMap.clear();
					
					//li adding
					if( curMenu == 2 ) attrMap.put("class", "dropdown");
					else attrMap.put("class", "dropdown-submenu");
					addElement(doc, "li", null, attrMap, parentStack.peek(), parentStack, true);
					attrMap.clear();
					
					//a adding	
					attrMap.put("onclick", menuLinkPath);					
					addElement(doc, "a", rs.getString(2), attrMap, parentStack.peek(), parentStack, false);
					attrMap.clear();
					
				}else{
					
					if( parentStack.peek().getNodeName().equals("li")) {
						parentStack.peek().removeAttribute("class");
						parentStack.pop();
					}
						
					//li adding
					Element ele = addElement(doc, "li", null, attrMap, parentStack.peek(), parentStack, true);
					attrMap.clear();
					
					//a adding
					attrMap.put("onclick", menuLinkPath);
					addElement(doc, "a", rs.getString(2), attrMap, ele, parentStack, false);
					attrMap.clear();					
					
				}//else
				
				//log.info("parent#2:"+parentStack.peek());	
				prevMenu = curMenu;		
				menuLinkPath = "#";
				//log.info("-----------------");
			}//while
			
			limit = parentStack.size() - 1;
			//log.info("Pop limit:"+limit);
			for( int i=0; i< limit; i++ ) {
				//log.info("Pop of Stack Element:"+parentStack.peek());
				parentStack.pop();
			}
			
			/*********************** logout ************************/
			
			//ul adding					
			attrMap.put("class", "nav navbar-nav navbar-right");				
			addElement(doc, "ul", null, attrMap, parentStack.peek(), parentStack, true);
			attrMap.clear();
			
			//li adding	
			addElement(doc, "li", null, attrMap, parentStack.peek(), parentStack, true);
			attrMap.clear();
			
			//li adding	
			/*attrMap.put("data-toggle", "modal");
			attrMap.put("data-target", ".bs-example-modal-sm");
			attrMap.put("class", "glyphicon glyphicon-off");
			attrMap.put("style", "color:red;font-size:22px;cursor:pointer;");
			addElement(doc, "a", " ", attrMap, parentStack.peek(), parentStack, false);
			attrMap.clear();*/
			

			//div adding	
			attrMap.put("class", "modal bs-example-modal-sm");
			attrMap.put("tabindex", "-1");
			attrMap.put("role", "dialog");
			attrMap.put("aria-hidden", "true");
			addElement(doc, "div", null, attrMap, parentStack.peek(), parentStack, true);
			attrMap.clear();
			
			//div adding	
			attrMap.put("class", "modal-dialog modal-sm");			
			addElement(doc, "div", null, attrMap, parentStack.peek(), parentStack, true);
			attrMap.clear();
			
			//div adding	
			attrMap.put("class", "modal-content");			
			addElement(doc, "div", null, attrMap, parentStack.peek(), parentStack, true);
			attrMap.clear();
			
			Element ele = null;
			//div adding	
			attrMap.put("class", "modal-header");			
			ele = addElement(doc, "div", "Logout ", attrMap, parentStack.peek(), parentStack, false);
			attrMap.clear();
			
			//span adding	
			attrMap.put("class", "glyphicon glyphicon-lock");			
			ele = addElement(doc, "span", null, attrMap, ele, parentStack, false);
			attrMap.clear();
			
			//div adding	
			attrMap.put("class", "modal-body");			
			ele = addElement(doc, "div", "Are you sure you want to log-off ", attrMap, parentStack.peek(), parentStack, false);
			attrMap.clear();
			
			//span adding	
			attrMap.put("class", "glyphicon glyphicon-question-sign");			
			ele = addElement(doc, "span", null, attrMap, ele, parentStack, false);
			attrMap.clear();
			
			//div adding	
			attrMap.put("class", "modal-footer");			
			ele = addElement(doc, "div", null, attrMap, parentStack.peek(), parentStack, false);
			attrMap.clear();
			
			//a adding	
			attrMap.put("href", "#");
			attrMap.put("data-dismiss", "modal");
			attrMap.put("class", "btn btn-primary btn-block");
			addElement(doc, "a", "Cancel", attrMap, ele, parentStack, false);
			attrMap.clear();
			
			/*//a adding	
			attrMap.put("href", logoutLink);			
			attrMap.put("class", "btn btn-primary btn-block");
			ele = addElement(doc, "a", "Logout", attrMap, ele, parentStack, false);
			attrMap.clear();	*/
			
			/*********************** logout ************************/
		
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			//transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			//StreamResult result = new StreamResult(new File("C:\\file.xml"));			
			// Output to console for testing
			StringWriter sw = new StringWriter();		
			StreamResult result = new StreamResult(sw);
			transformer.transform(source, result);
			res = sw.toString();
			//log.info("===>"+res);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if( rs != null) rs.close(); rs = null;
				if( ps != null) ps.close(); ps = null;
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}//finally
		
		return res;
	}
	
	public static Element addElement(Document doc,String element, String val, Map<String, String> attr, Object parent, Stack<Element> parentStack,boolean push) throws Exception {
		
		Element parentEle = null;
		Node parentNode = null;
		
		//log.info("Adding an <"+element+"> element");
		Element ele = doc.createElement(element);
		if( val != null ) ele.appendChild( doc.createTextNode(val) );
		for( String key : attr.keySet() ) 
			ele.setAttribute(key, attr.get(key));
		
		if( parent instanceof Element ) {
			parentEle = (Element) parent;			
			parentEle.appendChild(ele);
			
		}else if( parent instanceof Node ) {
			parentNode = (Node) parent;			
			parentNode.appendChild(ele);
			
		}//else 
		
		if( push ) {
			//log.info("Pushing an Element:"+element);	
			parentStack.push(ele);
		}
		
		return ele;
	}//createElement
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection  con = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://192.168.1.60:3306/wrapper?characterEncoding=utf8", "cms", "w1c0r3");
			String res = getMenus(con, "1");
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}//finally
		
	}

}//class

