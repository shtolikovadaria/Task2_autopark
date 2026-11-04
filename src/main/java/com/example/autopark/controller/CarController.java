package com.example.autopark.controller;

import com.example.autopark.entity.Car;
import com.example.autopark.repository.CarRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

@Controller
public class CarController {

    private final CarRepository carRepository;

    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/cars";
    }

    @GetMapping("/cars")
    public String list(@RequestParam(defaultValue = "") String q,
                       @RequestParam(defaultValue = "") String sort,
                       Model model) {
        fillList(model, q.trim(), sort);
        model.addAttribute("car", new Car());
        return "index";
    }

    @PostMapping("/cars/add")
    public String add(@Valid @ModelAttribute("car") Car car,
                      BindingResult result, Model model) {
        if (result.hasErrors()) {
            fillList(model, "", "");
            return "index";
        }
        car.setId(null);
        carRepository.save(car);
        return "redirect:/cars";
    }

    @GetMapping("/cars/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Car car = carRepository.findById(id).orElse(null);
        if (car == null) {
            return "redirect:/cars";
        }
        model.addAttribute("car", car);
        return "edit";
    }

    @PostMapping("/cars/edit/{id}")
    public String edit(@PathVariable Long id,
                       @Valid @ModelAttribute("car") Car car,
                       BindingResult result) {
        car.setId(id);
        if (result.hasErrors()) {
            return "edit";
        }
        carRepository.save(car);
        return "redirect:/cars";
    }

    @PostMapping("/cars/delete/{id}")
    public String delete(@PathVariable Long id) {
        carRepository.deleteById(id);
        return "redirect:/cars";
    }

    private void fillList(Model model, String q, String sort) {
        List<Car> cars = "year".equals(sort)
                ? carRepository.searchSortedByYear(q)
                : carRepository.search(q);
        long count = cars.stream()
                .filter(c -> c.getMarkaAvto() != null && !c.getMarkaAvto().isBlank())
                .count();
        model.addAttribute("cars", cars);
        model.addAttribute("count", count);
        model.addAttribute("q", q);
        model.addAttribute("sort", sort);

        // Гистограмма: последние 60 дней, включая сегодня
        LocalDate today = LocalDate.now();
        LocalDate from = today.minusDays(59);
        Map<LocalDate, Long> perDay = new HashMap<>();
        for (Object[] row : carRepository.countByDay(from, today)) {
            perDay.put((LocalDate) row[0], (Long) row[1]);
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM");
        List<String> chartLabels = new ArrayList<>();
        List<Long> chartValues = new ArrayList<>();
        for (LocalDate d = from; !d.isAfter(today); d = d.plusDays(1)) {
            chartLabels.add(d.format(fmt));
            chartValues.add(perDay.getOrDefault(d, 0L));
        }
        model.addAttribute("chartLabels", chartLabels);
        model.addAttribute("chartValues", chartValues);
    }
}