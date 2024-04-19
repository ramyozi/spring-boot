package fr.diginamic.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.diginamic.dto.GenreDTO;
import fr.diginamic.dto.ShowDTO;
import fr.diginamic.services.GenreService;
import fr.diginamic.services.ShowService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ShowViewController {
	@Autowired
	private ShowService showService;
	@Autowired
	private GenreService genreService;

	@Autowired
	private MessageSource messageSource;

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

	@PostMapping("/show/delete/{id}")
	public String deleteCity(@PathVariable int id) {
		showService.deleteShow(id);
		return "redirect:/show/list";
	}

	@PutMapping("/show/update/{id}")
	public String updateShow(@ModelAttribute ShowDTO show) {
		try {
			showService.updateShow(show);
			return "redirect:/show/details/" + show.getId();
		} catch (Exception e) {
			return "redirect:/show/details/" + show.getId() + "?error";
		}
	}

	@GetMapping("/")
	public ModelAndView index(HttpServletRequest request) {
		Locale locale = LocaleContextHolder.getLocale();

		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		Map<String, Object> model = new HashMap<>();

		if (authentication != null && authentication.isAuthenticated()
				&& !"anonymousUser".equals(authentication.getName())) {
			String username = authentication.getName();
			String roles = authentication.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.joining(", "));
			model.put("username", username);
			model.put("roles", roles);
			model.put("welcomeMessage",
					messageSource.getMessage("welcome.title",
							new Object[] { username }, locale));
		} else {
			model.put("welcomeMessage", messageSource
					.getMessage("welcome.guest", null, locale));
		}

		return new ModelAndView("index", model);
	}

}
