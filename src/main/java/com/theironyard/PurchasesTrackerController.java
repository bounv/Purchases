package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import static org.aspectj.util.LangUtil.split;

/**
 * Created by boun on 12/22/16.
 */
@Controller
public class PurchasesTrackerController {

    @Autowired
    CustomerRepository customers;

    @Autowired
    PurchaseRepository purchases;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, String category) {
        List<Purchase> purchaseLog;

        if(category != null) {
            purchaseLog = purchases.findByCategory(category);
        } else {
            purchaseLog = (List<Purchase>) purchases.findAll();
        }

        model.addAttribute("purchases", purchaseLog);

        return "home";
    }

    @PostConstruct
    public void init() throws FileNotFoundException {
        if(customers.count() == 0) {
            File c = new File("customers.csv");
            Scanner scanner = new Scanner(c);
            scanner.nextLine();
            while(scanner.hasNext()) {

                String[] info = split(",");
                Customer n = new Customer(info[0], info[1]);
                customers.save(n);
            }
        }

        if(purchases.count() == 0) {
            File p = new File("purchases.csv");
            Scanner scanner = new Scanner(p);
            scanner.nextLine();
            while(scanner.hasNext()) {

                String[] trans = split(",");
                Purchase item = new Purchase(trans[1], trans[2], Integer.parseInt(trans[3]), trans[4], customers.findOne(Integer.parseInt(trans[0])));
                purchases.save(item);
            }
        }

    }

}
