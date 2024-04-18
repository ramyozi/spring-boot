package fr.diginamic.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.diginamic.dto.GenreDTO;
import fr.diginamic.dto.ShowDTO;
import fr.diginamic.services.GenreService;
import fr.diginamic.services.ShowService;

@Controller
public class ShowViewController {
	@Autowired
	private ShowService showService;
	@Autowired
	private GenreService genreService;

	@GetMapping("/show/details/{id}")
	public ModelAndView showDetails(@PathVariable("id") int id) {
		ModelAndView mav = new ModelAndView();
		try {
			ShowDTO showDTO = showService.getShowById(id);
			mav.setViewName("show/details");
			mav.addObject("show", showDTO);
		} catch (Exception e) {
			mav.setViewName("error");
			mav.addObject("message",
					"Error retrieving show details: " + e.getMessage());
		}
		return mav;
	}

	@ModelAttribute("genres")
	public List<GenreDTO> populateGenres() {
		return genreService.getAllGenres();
	}

	@GetMapping("/show/list")
	public ModelAndView getShows() {
		Map<String, Object> model = new HashMap<>();
		model.put("shows", showService.getAllShows());
		return new ModelAndView("show/list", model);
	}

	@GetMapping("/show/add")
	public ModelAndView addShowForm() {
		return new ModelAndView("show/add", "show", new ShowDTO());
	}

	@PostMapping("/show/add")
	public String addShow(@ModelAttribute ShowDTO showDto, Model model) {
		try {
			showService.createShow(showDto);
			return "redirect:/show/list";
		} catch (Exception e) {
			model.addAttribute("show", showDto);
			return "show/add";
		}
	}
}
