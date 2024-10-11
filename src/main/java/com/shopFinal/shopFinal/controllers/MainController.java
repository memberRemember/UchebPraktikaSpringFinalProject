package com.shopFinal.shopFinal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {
    @GetMapping("/")
    public String home(Model model){
        return "homePage";
    }

    @PostMapping("/calculate")
    public String calculate(@RequestParam("operand1") double operand1,
                            @RequestParam("operand2") double operand2,
                            @RequestParam("operator") String operator,
                            Model model){
        double result = switch (operator){
            case "+" -> operand1+operand2;
            case "-" -> operand1-operand2;
            case "*" -> operand1*operand2;
            case "/" -> operand1/operand2;
            default -> 0.0;
        };

        model.addAttribute("result", result);
        return "result";
    }

    @GetMapping("/calculator")
    public String calculator(Model model){
        return "calculator";
    }


    @GetMapping("/convert")
    public String convert(@RequestParam("value1") double value1,
                          @RequestParam("currency1") String currency1,
                          @RequestParam("currency2") String currency2,
                          Model model){
        double result = switch (currency1){
            case "rubles" -> switch (currency2){
                case "rubles" -> value1;
                case "dollar" -> value1/93.10;
                case "euro" -> value1/103.47;
                default -> 0.0;
            };
            case "dollar" -> switch (currency2){
                case "rubles" -> value1*93.10;
                case "dollar" -> value1;
                case "euro" -> value1*0.9;
                default -> 0.0;
            };
            case "euro" -> switch (currency2){
                case "rubles" -> value1*103.47;
                case "dollar" -> value1*1.11;
                case "euro" -> value1;
                default -> 0.0;
            };
            default -> 0.0;
        };
        model.addAttribute("result", result);
        return "result_convertPage";
    }

    @GetMapping("/convertor")
    public String convertor(Model model){
        return "currency_converterPage";
    }

}
