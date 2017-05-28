package tp.paw.khet.webapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tp.paw.khet.model.Category;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.webapp.exception.UnauthorizedException;
import tp.paw.khet.webapp.form.FormProduct;
import tp.paw.khet.webapp.form.wrapper.MultipartFileImageWrapper;
import tp.paw.khet.webapp.form.wrapper.VideoStringWrapper;
import tp.paw.khet.webapp.validators.ImageOrVideoValidator;

@Controller
public class UploadController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ImageOrVideoValidator imageOrVideoValidator;

	@ModelAttribute("uploadForm")
	public FormProduct uploadForm() {
		return new FormProduct();
	}
	
	@RequestMapping("/upload")
	public ModelAndView formCompletion(@ModelAttribute("loggedUser") final User loggedUser) {
		LOGGER.debug("User with id {} accessed upload", loggedUser.getUserId());
		
		final ModelAndView mav = new ModelAndView("upload");
		mav.addObject("categories", Category.values());
		return mav;
	}
	
	@RequestMapping(value= "/upload", method = {RequestMethod.POST})
	public ModelAndView upload(@Valid @ModelAttribute("uploadForm") final FormProduct formProduct, final BindingResult errors,
							   @ModelAttribute("loggedUser") final User loggedUser,
							   final RedirectAttributes attr) throws IOException, UnauthorizedException {
		
		LOGGER.debug("User with id {} accessed upload POST", loggedUser.getUserId());
		
		imageOrVideoValidator.validate(formProduct, errors);
		
		if (errors.hasErrors()) {
			LOGGER.warn("User with id {} failed to post product: form has errors: {}", loggedUser.getUserId(), errors.getAllErrors());
			return errorState(formProduct, errors, attr);
		}
		
		final Product product =  productService.createProduct(formProduct.getName(), 
												formProduct.getDescription(), formProduct.getShortDescription(),
												formProduct.getWebsite(),
												formProduct.getCategory(),
												formProduct.getLogo().getBytes(), 
												loggedUser.getUserId(),
												imageByteList(formProduct.getImages()),
												videoIdList(formProduct.getVideos()));
		
		LOGGER.info("User with id {} posted product with id {}", loggedUser.getUserId(), product.getId());
		
		return new ModelAndView("redirect:/product/" + product.getId());
	}
		
	private List<String> videoIdList(final VideoStringWrapper[] videos) {
		List<String> videoIdList = new ArrayList<>();
		for (VideoStringWrapper video : videos)
			if (video.hasUrl())
				videoIdList.add(video.getVideoId());
		return videoIdList;
	}

	private List<byte[]> imageByteList(final MultipartFileImageWrapper[] images) {
		List<byte[]> byteList = new ArrayList<>();
		
		for (MultipartFileImageWrapper image : images) {
			if (image.hasFile()) {
				try {
					byteList.add(image.getFile().getBytes());
				} catch (IOException e) {
					LOGGER.error("Failed to get image's bytes");
					e.printStackTrace();
				}
			}
		}
		
		return byteList;
	}
	
	private ModelAndView errorState(FormProduct form, final BindingResult errors, RedirectAttributes attr) {
		attr.addFlashAttribute("org.springframework.validation.BindingResult.uploadForm", errors);
		attr.addFlashAttribute("uploadForm", form);
		return new ModelAndView("redirect:/upload");		
	}
	
}
