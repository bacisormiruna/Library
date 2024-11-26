package model;
public class Order {
    private final Long userId; // ID-ul utilizatorului care a făcut comanda
    private final Long bookId; // ID-ul cărții comandate
    private final String title;
    private final String author;
    private final double totalPrice;
    private final int numberOfExemplars;

    public Order(Long userId, Long bookId, String title, String author, double totalPrice, int numberOfExemplars) {
        this.userId = userId;
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.totalPrice = totalPrice;
        this.numberOfExemplars = numberOfExemplars;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getNumberOfExemplars() {
        return numberOfExemplars;
    }

    @Override
    public String toString() {
        return "Order{" + "userId=" + userId + ", bookId=" + bookId + ", title='" + title + '\'' + ", author='" + author + '\'' + ", totalPrice=" + totalPrice + ", numberOfExemplars=" + numberOfExemplars + '}';
    }
}