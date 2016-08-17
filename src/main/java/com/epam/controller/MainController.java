package com.epam.controller;

import com.epam.model.Bill;
import com.epam.model.Card;
import com.epam.model.User;
import com.epam.service.BillService;
import com.epam.service.CardService;
import com.epam.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.security.Principal;

/**
 * Created by fg on 7/27/2016.
 * Controller of model and view
 */

@Controller
public class MainController {
    private static final Logger LOGGER = Logger.getLogger(MainController.class);

    private UserService userService;
    private CardService cardService;
    private BillService billService;

    @Autowired
    public MainController(UserService userService, CardService cardService,
                          BillService billService) {
        this.userService = userService;
        this.cardService = cardService;
        this.billService = billService;
    }

    /**
     * Default page.
     * @return model of welcome page
     */
    @RequestMapping(value = "/**")
    public String welcome() {
        return "welcome";
    }

    /**
     * Handling logging in system.
     * @param error - error massage when logging is not successful
     * @param logout - logout massage when user log out from system
     * @return - return model of page which must see user
     */
    @RequestMapping(value = "/login**", method = RequestMethod.GET)
    public ModelAndView login(Principal principal,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {
        ModelAndView model = new ModelAndView();
        if (error != null) {
            LOGGER.warn(principal + " Invalid username or password!");
            model.addObject("error", "Invalid username or password!");
        }
        if (logout != null) {
            LOGGER.warn(principal + " successful logout");
            model.addObject("msg", "You've been logged out successfully.");
        }
        if (principal == null) {
            model.setViewName("login");
            LOGGER.warn("Not login");
        } else {
            LOGGER.warn(principal + " successful login");
            model.setViewName("welcome");
        }
        return model;
    }

    /**
     * Handling user access to not allowed page.
     * @param principal - contains information about authorized user
     * @return model of error page or model of welcome page if user not login
     */
    @RequestMapping(value = "/403**", method = RequestMethod.GET)
    public ModelAndView accessDenied(Principal principal) {
        LOGGER.debug(principal + " page 403");
        ModelAndView model = new ModelAndView();
        if (principal != null) {
            model.addObject("username", principal.getName());
            model.setViewName("403");
        } else {
            model.setViewName("welcome");
        }
        return model;
    }

    /**
     * Prepare admin room page.
     * @return model of admin room
     */
    @RequestMapping(value = "/admin**", method = RequestMethod.GET)
    public ModelAndView adminHomePage(ModelAndView modelAndView) {
        LOGGER.debug("page admin");
        modelAndView.setViewName("adminHomePage");
        modelAndView.addObject("clients", userService.getAllClientsWithBills());
        return modelAndView;
    }

    /**
     * Handling operations with client bill
     * @param actionAndBillId - contains bill id and action which must be done
     * @return redirect to admin room with updated data
     */
    @RequestMapping(value = "/actionWithClientBill", method = RequestMethod.POST)
    public ModelAndView clientBill(@RequestParam("actionAndBillId") String actionAndBillId,
                                   Principal principal) {
        LOGGER.debug(principal + " action with client bill");
        User user = userService.findByEmail(principal.getName());
        if (user.getActive()) {
            billService.doAction(actionAndBillId);
        }
        return new ModelAndView("redirect:/admin");
    }

    /**
     * Block client bill
     * @param billId if of blocking bill
     * @param principal - user data
     * @return client page
     */
    @RequestMapping(value = "/blockBill", method = RequestMethod.POST)
    public ModelAndView blockBill(@RequestParam("billId") Integer billId,
                                  Principal principal) {
        LOGGER.debug(principal + " block client bill");
        ModelAndView modelAndView = new ModelAndView("redirect:/client");
        Bill bill = billService.checkOwnerBill(principal.getName(), billId);
        if (bill != null) {
            billService.blockBill(bill.getId());
        } else {
            modelAndView.addObject("errMsg", "Impossible operation");
            LOGGER.warn(principal + " bill blocking operation fails");
        }
        return modelAndView;
    }

    /**
     * Prepare client room page.
     * @param principal - data about authorized user
     * @return model of client room
     */
    @RequestMapping(value = "/client**", method = RequestMethod.GET)
    public ModelAndView clientHomePage(ModelAndView modelAndView,
                                       Principal principal) throws Exception {
        LOGGER.debug(principal + " client page");
        User user = userService.findByEmail(principal.getName());
        modelAndView.setViewName("clientHomePage");
        modelAndView.addObject("bills", billService.getAllClientBills(user));
        return modelAndView;
    }

    /**
     * Handling operations with client cards
     * @param actionAndCardId - contains card id and action which must be done
     * @return redirect to client room with updated data
     */
    @RequestMapping(value = "/actionWithClientCard", method = RequestMethod.POST)
    public ModelAndView clientCard(@RequestParam("actionAndCardId") String actionAndCardId,
                                   Principal principal) {
        LOGGER.debug(principal + " action with client card");
        User user = userService.findByEmail(principal.getName());
        if (user.getActive()) {
            cardService.doAction(actionAndCardId, user);
        }
        return new ModelAndView("redirect:/client");
    }

    /**
     * Handling request of client's bill filling with money
     * @param billId - id of card which connected with bill
     * @param money - money count to fill bill
     * @param principal - data about user which want to fill his bill
     * @return model of client room with updated data
     */
    @RequestMapping(value = "/fillClientBill", method = RequestMethod.POST)
    public ModelAndView fillClientBill(@RequestParam("billId") Integer billId,
                                       @RequestParam("moneyCount") Double money,
                                       Principal principal) {
        LOGGER.debug(principal + " fill client bill");
        ModelAndView modelAndView = new ModelAndView("redirect:/client");
        Bill bill = billService.checkOwnerBill(principal.getName(), billId);
        if (!bill.getActive()) {
            LOGGER.warn(principal + " Your bill is blocked");
            modelAndView.addObject("msgBillBlocked", "Your bill is blocked");
            return modelAndView;
        }
        if (bill == null || !billService.fillBill(bill.getId(), money)) {
            modelAndView.addObject("errMsg", "Impossible operation");
        }
        return modelAndView;
    }

    /**
     * Handling request of client's bill payment
     * @param billId - id of card which connected with bill
     * @param payment - money count to make payment
     * @param cardName - name of card which will except money
     * @param password - password of one of the bills card
     * @param principal - data about user which want to make payment
     * @return model of client room with updated data
     */
    @RequestMapping(value = "/sentMoney", method = RequestMethod.POST)
    public ModelAndView makeClientPayment(@RequestParam("billId") Integer billId,
                                          @RequestParam("moneyCount") Double payment,
                                          @RequestParam("nativeCardId") Integer nativeCardId,
                                          @RequestParam("cardName") String cardName,
                                          @RequestParam("passWord") String password,
                                          Principal principal) {
        LOGGER.debug(principal + " sent money");
        ModelAndView modelAndView = new ModelAndView("redirect:/client");
        User user = userService.findByEmail(principal.getName());
        Card exceptCard = cardService.findByName(cardName);
        if (exceptCard == null || exceptCard.getDeleted() || !exceptCard.getActive()) {
            LOGGER.warn(principal + " Card with such name doesn't exist");
            modelAndView.addObject("msgCard", "The card with such name doesn't exist");
            return modelAndView;
        }
        Bill clientBill = billService.checkOwnerBill(user.getEmail(), billId);
        if (clientBill == null) {
            LOGGER.warn(principal + " You have no such bill");
            modelAndView.addObject("msgBill", "You have no such bill");
            return modelAndView;
        }
        if (!clientBill.getActive()) {
            LOGGER.warn(principal + " Your bill is blocked");
            modelAndView.addObject("msgBillBlocked", "Your bill is blocked");
            return modelAndView;
        }
        if (!billService.checkCardPass(clientBill, nativeCardId, password)) {
            LOGGER.warn(principal + " Password not correct");
            modelAndView.addObject("msgPass", "Password error");
            return modelAndView;
        }
        if (!billService.makePayment(billId, exceptCard.getBill().getId(), payment)) {
            LOGGER.warn(principal + " Operation error");
            modelAndView.addObject("msgMon", "Operation error");
        }
        return modelAndView;
    }

    /**
     * Catch all exception in controller
     * @param exception - thrown exception
     * @return View of error page
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleIOException(Exception exception) {
        LOGGER.error(exception);
        return new ModelAndView("brokenPage");
    }
}
