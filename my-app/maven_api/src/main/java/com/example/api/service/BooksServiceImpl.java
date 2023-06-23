package com.example.api.service;

import com.example.api.model.Book;
import com.example.api.repository.BooksRepository;
//import com.example.api.service.BooksService;

import lombok.AllArgsConstructor;

//import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;

import java.util.List;
//import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BooksServiceImpl implements BooksService {
    private BooksRepository booksRepository;

    @Override
    public Book createBook(Book book) {
        return booksRepository.save(book);
    }

    @Override
    public Book getBookById(long id) {
        Optional<Book> optionalBook = booksRepository.findById(id);
        return optionalBook.get();
    }

    @Override
    public List<Book> GetAllBooks() {
        return booksRepository.findAll();
    }

    @Override
    public Book updateBook(Book book) {
        Book existingBook = booksRepository.findById(book.getId()).get();

        existingBook.setAuthor(null != book.getAuthor() ? book.getAuthor() : existingBook.getAuthor());
        existingBook.setDescription(null != book.getDescription() ? book.getDescription() : existingBook.getDescription());
        existingBook.setId(0 != book.getId() ? book.getId() : existingBook.getId());
        existingBook.setImage_url(null != book.getImage_url() ? book.getImage_url() : existingBook.getImage_url());
        existingBook.setPrice(0 != book.getPrice() ? book.getPrice() : existingBook.getPrice());
        existingBook.setTitle(null != book.getTitle() ? book.getTitle() : existingBook.getTitle());
        existingBook.setType(null != book.getType() ? book.getType() : existingBook.getType());

        Book updatedBook = booksRepository.save(existingBook);

        return updatedBook;
    }

    @Override
    public void deleteBook(long id) {
        booksRepository.deleteById(id);
    }
}
