package org.zerock.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.WebBoard;
import org.zerock.domain.WebReply;
import org.zerock.persistence.WebReplyRepository;



import lombok.extern.java.Log;

@RestController
@RequestMapping("/replies/*")
@Log
public class WebReplyController {

	@Autowired //setter를 만들어서 처리하는 것이 정석이지만...
	private WebReplyRepository replyRepo;
	
	@Transactional
	@PostMapping("/{bno}")
	public ResponseEntity<List<WebReply>> addReply(
			@PathVariable("bno")Long bno, 
			@RequestBody WebReply reply){

		log.info("addReply..........................");
		log.info("BNO: " + bno);
		log.info("REPLY: " + reply);
		
		WebBoard board = new WebBoard();
		board.setBno(bno);
		
		reply.setBoard(board);
		
		replyRepo.save(reply);		
		
		return new ResponseEntity<>(getListByBoard(board), HttpStatus.CREATED);
		
	}
	
	@Transactional
	@DeleteMapping("/{bno}/{rno}")
	public ResponseEntity<List<WebReply>> remove(
			@PathVariable("bno")Long bno,
			@PathVariable("rno")Long rno){
		
		log.info("delete reply: "+ rno);
		replyRepo.findById(rno).ifPresent(item->replyRepo.delete(item));


		
		WebBoard board = new WebBoard();
		board.setBno(bno);
		
		return  new ResponseEntity<>(getListByBoard(board), HttpStatus.OK);
		
	}
	
	public ResponseEntity<List<WebReply>> modify(@PathVariable("bno")Long bno,
			@RequestBody WebReply reply){
		log.info("modify reply: "+reply);
		replyRepo.findById(reply.getRno()).ifPresent(origin->{
			origin.setReplyText(reply.getReplyText());
			
			replyRepo.save(origin);
		});
		WebBoard board = new WebBoard();
		board.setBno(bno);
		
		return new ResponseEntity<>(getListByBoard(board),HttpStatus.CREATED);		
	}
	
	
	
	
	private List<WebReply> getListByBoard(WebBoard board) throws RuntimeException{
		log.info("getListByBoard...."+board);
		
		return replyRepo.getRepliesOfBoard(board);
	}
	
}
