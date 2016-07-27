/**
 * 
 */
package com.epam.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.epam.model.User;
import com.epam.model.Card;

/**
 * @author asd
 * Handling tag clientTable
 */
public class ClientsTable extends TagSupport {
    private static final long serialVersionUID = 8721266929256929800L;
    private static final Logger logger = Logger.getLogger(ClientsTable.class);  
    private String buttonInfo;
    private List<Actor> clients;
    private String parameterName;
    private String token;
    private String textBlocked;
    private String textUnBlocked;
    
    public void setTextBlocked(String textBlocked) {
        this.textBlocked = textBlocked;
    }

    public void setTextUnBlocked(String textUnBlocked) {
        this.textUnBlocked = textUnBlocked;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setButtonInfo(String buttonInfo) {
	this.buttonInfo = buttonInfo;
    }
    
    public void setClients(List<Actor> actors) {
	this.clients = actors;
    }
    
    public int doStartTag() {
	logger.info("Entering");
	StringBuilder tableBuilder = new StringBuilder(
		"<form name=\"ClientTable\" action=\"/Project_4/releaseClientBill\" " +
			"method=\"post\">" + "<table border=\"2\" cellpadding=\"8\">" +
			"<input type=\"hidden\" name=\"" + parameterName +"\"" +
			"	value=\"" + token + "\" />");
	int i;
	for (Actor actor : clients) {
	    i = 0;
	    if (actor.getCards().size() > 1) {
		tableBuilder.append("<tr><td rowspan=" + 
			actor.getCards().size() + ">" + 
			actor.getName() + "</td>");
	    } else {
		tableBuilder.append("<tr><td>" + actor.getName() + 
			    "</td>");
	    }
	    for (Card card : actor.getCards()) {
		if (i > 0) {
		    tableBuilder.append("<tr>");
		}
		tableBuilder.append("<td>" + card.getName() +
			"</td><td>" + card.getBill().getScore() + 
			"</td><td>" + (card.getBill().getIsBlocked() ? textBlocked :
			    textUnBlocked) + "</td>");
		if (card.getBill().getIsBlocked()) {
			tableBuilder.append("<td><button name=\"billBlockId\" value=\"" +
				card.getBill().getId() +
				"\">" + buttonInfo + "</button></td>");
		}
		i++;
		if ((i > 0) && (i != actor.getCards().size())) {
			tableBuilder.append("</tr>");
		}
	    }
	tableBuilder.append("</tr>");
	}
	tableBuilder.append("</table></form>");
	try {
	    pageContext.getOut().write(tableBuilder.toString());
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	logger.info("Leaving actorName=");
	return SKIP_BODY;
    }
}
