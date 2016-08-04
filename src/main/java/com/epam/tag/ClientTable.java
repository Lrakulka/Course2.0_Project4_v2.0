/**
 * 
 */
package com.epam.tag;

import java.io.IOException;
import java.util.List;
import javax.servlet.jsp.tagext.TagSupport;

import com.epam.model.Bill;
import com.epam.model.Card;
import lombok.Setter;

/**
 * @author asd
 * Handling tag cardsTable
 */
@Setter
public class ClientTable extends TagSupport {
    private static final long serialVersionUID = -3905900903200282870L;

    private List<Bill> bills;
    private String parameterName;
    private String token;
    private String buttonFillInfo;
    private String buttonMakePaymentInfo;
    private String buttonBlockInfo;
    private String textBlocked;
    private String textUnBlocked;
    private String textDeleted;
    private String blockButtonInfo;
    private String unBlockButtonInfo;
    private String deleteButtonInfo;
    private String unDeleteButtonInfo;
    private String cardName;
    private String passName;
    private String moneyName;

    @Override
    public int doStartTag() {
        StringBuilder tableBuilder = new StringBuilder(
                "<table border=\"2\" cellpadding=\"8\">");
        int i;
        for (Bill bill : bills) {
            i = 0;
            if (bill.getCards().size() > 1) {
                tableBuilder.append("<tr><td rowspan=" +
                        bill.getCards().size() + ">" +
                        bill.getName() + "</td><td rowspan=" +
                        bill.getCards().size() + ">" + bill.getScore() + "</td>");
            } else {
                tableBuilder.append("<tr><td>" + bill.getName() +
                        "</td><td rowspan=" +
                        bill.getCards().size() + ">" + bill.getScore() + "</td>");
            }
            for (Card card : bill.getCards()) {
                if (i > 0) {
                    tableBuilder.append("<tr>");
                }
                tableBuilder.append("<td>" + card.getName());
                tableBuilder.append(
                        "<form name=\"ClientTable\" action=\"/actionWithClientCard\" " +
                                "method=\"post\">" + "<input type=\"hidden\" name=\"" +
                                parameterName + "\" value=\"" + token + "\" />");
                if (card.getDeleted()) {
                    tableBuilder.append("<td>" + textDeleted + "</td>");
                } else {
                    tableBuilder.append("<td>" + (card.getActive() ? textUnBlocked : textBlocked) + "</td>");
                }
                if (card.getActive()) {
                    tableBuilder.append("<td><button name=\"actionAndCardId\"" +
                            " value=\"" + card.getId() + "+block\">" + blockButtonInfo + "</button></td>");
                } else {
                    tableBuilder.append("<td><button name=\"actionAndCardId\"" +
                            " value=\"" + card.getId() + "+unblock\">" + unBlockButtonInfo + "</button></td>");
                }
                if (card.getDeleted()) {
                    tableBuilder.append("<td><button name=\"actionAndCardId\"" +
                            " value=\"" + card.getId() + "+undelete\">" + unDeleteButtonInfo + "</button></td>");
                } else {
                    tableBuilder.append("<td><button name=\"actionAndCardId\"" +
                            " value=\"" + card.getId() + "+delete\">" + deleteButtonInfo + "</button></td>");
                }
                tableBuilder.append("</form>");
                tableBuilder.append("<td><form name=\"fillBill\" action=\"/fillClientBill\" " +
                        "method=\"post\">" +
                        "<input type=\"hidden\" name=\"" + parameterName +"\"" +
                        "	value=\"" + token + "\" /><p>" +
                        moneyName + ": <input size=\"10\" type=\"text\" name=\"moneyCount\" /></p>" +
                        "<button name=\"billId\" value=\"" +
                        bill.getId() + "\">" + buttonFillInfo + "</button></form></td>" +
                        // next form
                        "<td><form name=\"sentMoney\" action=\"/sentMoney\" " +
                        "method=\"post\">" +
                        "<input type=\"hidden\" name=\"" + parameterName +"\"" +
                        "	value=\"" + token + "\" />" +
                        "<input type=\"hidden\" name=\"nativeCardId\"" +
                        "	value=\"" + card.getId() + "\" /><p>" +
                        cardName + ": <input size=\"6\" type=\"text\" name=\"cardName\" /></p><p>" +
                        passName + ": <input size=\"6\" type=\"text\" name=\"passWord\" /></p><p>" +
                        moneyName + ": <input size=\"10\" type=\"text\" name=\"moneyCount\" /></p>" +
                        "<p><button name=\"billId\" value=\"" + bill.getId() + "\">" +
                        buttonMakePaymentInfo + "</button></p></form></td>");
                i++;
                if ((i > 0) && (i != bill.getCards().size())) {
                    tableBuilder.append("</tr>");
                }
            }
            tableBuilder.append("</tr>");
        }
        tableBuilder.append("</table>");
        try {
            pageContext.getOut().write(tableBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
}
