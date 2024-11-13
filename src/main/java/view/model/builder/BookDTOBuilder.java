package view.model.builder;

import model.builder.BookBuilder;
import view.model.BookDTO;

public class BookDTOBuilder {
    private BookDTO bookDTO;
    public BookDTOBuilder(){
        bookDTO = new BookDTO();
    }

    public BookDTOBuilder setId(Long id){
        bookDTO.setId(id);
        return this;
    }
    public BookDTOBuilder setAuthor(String author){
        bookDTO.setAuthor(author);
        return this;
    }

    public BookDTOBuilder setTitle(String title){
        bookDTO.setTitle(title);
        return this;
    }

    public BookDTOBuilder setStock(Integer stock){//am adaugat si set pentru noua coloana de stoc
        bookDTO.setStock(stock);
        return this;
    }

    public BookDTO build(){
        return bookDTO;
    }


}
