package edu.bridgerdavidson.products.controllers;

import edu.bridgerdavidson.products.data.OrdersDataService;
import edu.bridgerdavidson.products.models.OrderModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    // add a data member for the repository using di
    @Autowired
    private OrdersDataService ordersDataService;

    // use constructor injection to inject the repo
    public OrdersController(OrdersDataService ordersDataService) {
        this.ordersDataService = ordersDataService;
    }

    @GetMapping("")
    public String showAllOrders(Model model) {
        model.addAttribute("orders", ordersDataService.getAll());
        model.addAttribute("title", "All Orders");
        return "allOrders";
    }

    @GetMapping("showOrder/{id}")
    public String showOneOrder(@PathVariable int id, Model model) {
        model.addAttribute("order", ordersDataService.getById(id));
        model.addAttribute("title", "One Order");
        return "oneOrder";
    }

    @GetMapping("/editOrder/{id}")
    public String editOrder(@PathVariable int id, Model model) {
        model.addAttribute("order", ordersDataService.getById(id));
        model.addAttribute("title", "Edit Order");
        return "editOrder";
    }

    @PostMapping("/processEditOrder")
    public String doUpdate(OrderModel orderModel, BindingResult br, Model model)
    {
        ordersDataService.update(orderModel);
        return "redirect:/orders";
    }

    @GetMapping("/newOrder")
    public String newOrder(Model model) {
        model.addAttribute("order", new OrderModel());
        model.addAttribute("title", "New Order");
        return "newOrder";
    }

    @PostMapping("/processNewOrder")
    public String processNewOrder(@Valid OrderModel orderModel, BindingResult br, Model model)
    {
        if(br.hasErrors())
        {
            model.addAttribute("title", "New Order");
            return "newOrder";
        }
        ordersDataService.create(orderModel);
        return "redirect:/orders";
    }
    @GetMapping("/deleteOrder/{id}")
    public String deleteOrder(@PathVariable int id, Model model) {
        ordersDataService.deleteById(id);
        return "redirect:/orders";
    }
}
