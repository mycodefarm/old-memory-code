package com.jimo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jimo.model.Bjs;
import com.jimo.model.Book;
import com.jimo.model.BookType;
import com.jimo.model.Books;
import com.jimo.service.BookService;
import com.jimo.util.Result;

@RestController
@RequestMapping("/book")
public class BookController {
	@Autowired
	private BookService bs;

	/////////////////// manageBook////////////////////////2017-05-08
	@PostMapping("/addBook")
	public Result addBook(Book b) {
		return bs.addBook(b);
	}

	@PostMapping("/delBook")
	public Result delBook(Integer id) {
		return bs.delBook(id);
	}

	@PostMapping("/updateBook")
	public Result updateBook(Book b) {
		return bs.updateBook(b);
	}

	@PostMapping("/getOneBook")
	public Result getOneBook(Integer id) {
		return bs.getOneBook(id);
	}

	@PostMapping("/getBooks")
	public Result getBooks() {
		return bs.getBooks();
	}

	@PostMapping("/query")
	public Result getQueryBooks(Book b, String sort) {
		return bs.getQueryBooks(b, sort);
	}

	/////////////////// manageBook end////////////////////////
	/////////////////// bjs////////////////////////
	@PostMapping("/addBjs")
	public Result addBjs(Bjs b) {
		return bs.addBjs(b);
	}

	@PostMapping("/delBjs")
	public Result delBjs(Integer id) {
		return bs.delBjs(id);
	}

	@PostMapping("/getBjs")
	public Result getBjs() {
		return bs.getBjs();
	}

	@PostMapping("/enableBjs")
	public Result enableBjs(Integer use, Integer id) {
		return bs.enableBjs(use, id);
	}

	//////////////// end bjs////////////////////////////

	////////////////// book type///////////////////
	@PostMapping("/addBookType")
	public Result addBookType(BookType b) {
		return bs.addBookType(b);
	}

	@PostMapping("/delBookType")
	public Result delBookType(Integer id) {
		return bs.delBookType(id);
	}

	@PostMapping("/getBookType")
	public Result getBookType() {
		return bs.getBookTypes();
	}
	////////////////// end book type///////////////////

	// ==============book1==============//
	@PostMapping("/addBook1")
	public Result addBook1(Books b) {
		return bs.addBook(b, 1);
	}

	@PostMapping("/delBook1")
	public Result delBook1(Integer id) {
		return bs.delBook(id, 1);
	}

	@PostMapping("/updateBook1")
	public Result updateBook1(Books b) {
		return bs.updateBook(b, 1);
	}

	@PostMapping("/getOneBook1")
	public Result getOneBook1(Integer id) {
		return bs.getOneBook(id, 1);
	}

	@PostMapping("/getBooks1")
	public Result getBooks1() {
		return bs.getBooks(1);
	}

	@PostMapping("/query1")
	public Result getQueryBooks1(Books b) {
		return bs.getQueryBooks(b, 1);
	}
	// ==============end book1==============//

	// ==============book2==============//
	@PostMapping("/addBook2")
	public Result addBook2(Books b) {
		return bs.addBook(b, 2);
	}

	@PostMapping("/delBook2")
	public Result delBook2(Integer id) {
		return bs.delBook(id, 2);
	}

	@PostMapping("/updateBook2")
	public Result updateBook2(Books b) {
		return bs.updateBook(b, 2);
	}

	@PostMapping("/getOneBook2")
	public Result getOneBook2(Integer id) {
		return bs.getOneBook(id, 2);
	}

	@PostMapping("/getBooks2")
	public Result getBooks2() {
		return bs.getBooks(2);
	}

	@PostMapping("/query2")
	public Result getQueryBooks2(Books b) {
		return bs.getQueryBooks(b, 2);
	}
	// ==============end book2==============//

	// ==============book3==============//
	@PostMapping("/addBook3")
	public Result addBook3(Books b) {
		return bs.addBook(b, 3);
	}

	@PostMapping("/delBook3")
	public Result delBook3(Integer id) {
		return bs.delBook(id, 3);
	}

	@PostMapping("/updateBook3")
	public Result updateBook3(Books b) {
		return bs.updateBook(b, 3);
	}

	@PostMapping("/getOneBook3")
	public Result getOneBook3(Integer id) {
		return bs.getOneBook(id, 3);
	}

	@PostMapping("/getBooks3")
	public Result getBooks3() {
		return bs.getBooks(3);
	}

	@PostMapping("/query3")
	public Result getQueryBooks3(Books b) {
		return bs.getQueryBooks(b, 3);
	}
	// ==============end book3==============//

	// bookSearch
	@PostMapping("/goodQuery")
	public Result getQueryBooks4(String con, String val) {
		return bs.getQueryBook(con, val);
	}
}
