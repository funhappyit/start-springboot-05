package org.zerock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.WebBoard;
import org.zerock.persistence.CustomCrudRepository;
import org.zerock.persistence.WebBoardRepository;
import org.zerock.vo.PageMaker;
import org.zerock.vo.PageVO;

import lombok.extern.java.Log;



@Controller
@RequestMapping("/boards/")
@Log
public class WebBoardController {
	
	@Autowired
	//private WebBoardRepository repo;
	private CustomCrudRepository repo;
	@GetMapping("/list")
	public void list(@ModelAttribute("pageVO") PageVO vo,Model model) {
		Pageable page = vo.makePageable(0, "bno");
		
		Page<Object[]> result = repo.getCustomPage(vo.getType(), vo.getKeyword(), page);
		log.info(""+page);
		log.info(""+result);
		
		log.info("TOTAL PAGE NUMBER"+result.getTotalPages());
		
		model.addAttribute("result", new PageMaker(result));
		
	}
	
	
	@GetMapping("/register")
	public void registerGET(@ModelAttribute("vo")WebBoard vo) {
		log.info("register get");
	}
	
	@PostMapping("/register")
	public String registerPOST(@ModelAttribute("vo")WebBoard vo,
								RedirectAttributes rttr) {
		log.info("register post");
		log.info(""+vo);
		
		repo.save(vo);
		rttr.addFlashAttribute("msg", "success");
		
		return "redirect:/boards/list";
	}
	@GetMapping("/view")
	public void view(Long bno, @ModelAttribute PageVO vo,Model model) {
		log.info("BNO: "+bno);
		
		repo.findById(bno).ifPresent(board->model.addAttribute("vo", board));
	}
	
	@GetMapping("/modify")
	public void modify(Long bno, @ModelAttribute PageVO vo,Model model) {
		log.info("modify bno: "+bno);
		
		repo.findById(bno).ifPresent(board->model.addAttribute("vo", board));
	}
	
	
	@PostMapping("/delete")
	public String delete(Long bno, PageVO vo, RedirectAttributes rttr) {
		log.info("DLETE BNO: "+bno);
		repo.deleteById(bno);
		rttr.addFlashAttribute("msg", "success");
		
		rttr.addAttribute("page", vo.getPage());
		rttr.addAttribute("size", vo.getSize());
		rttr.addAttribute("type", vo.getType());
		rttr.addAttribute("keyword", vo.getKeyword());
		
		return "redirect:/boards/list";
	}
	
	@PostMapping("/modify")
	public String modifyPost(WebBoard board,PageVO vo,RedirectAttributes rttr) {
	
		repo.findById(board.getBno()).ifPresent(origin->{
			origin.setTitle(board.getTitle());
			origin.setContent(board.getContent());
			
			repo.save(origin);
			rttr.addFlashAttribute("msg", "success");
			rttr.addAttribute("bno", origin.getBno());
		});
		
		
		rttr.addAttribute("page", vo.getPage());
		rttr.addAttribute("size", vo.getSize());
		rttr.addAttribute("type", vo.getType());
		rttr.addAttribute("keyword", vo.getKeyword());
		
		return "redirect:/boards/view";
	}
	
	
	

}
