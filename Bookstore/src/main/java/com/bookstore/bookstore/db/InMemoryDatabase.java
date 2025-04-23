/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.db;

import com.bookstore.bookstore.model.Author;
import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.Cart;
import com.bookstore.bookstore.model.CartItem;
import com.bookstore.bookstore.model.Customer;
import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.model.OrderItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

/**
 *
 * @author Hp
 */
public class InMemoryDatabase {
    // singleton instance
    private static InMemoryDatabase instance;
    
    private static final Logger LOGGER = Logger.getLogger(InMemoryDatabase.class.getName());
    
    // storage collections
    // book storage
    private Map<Long, Book> books = new HashMap<>();
    // Author storage
    private Map<Long, Author> authors = new HashMap<>();
    // customer storage
    private Map<Long, Customer> customers = new HashMap<>();
    // cart storage
    private Map<Long, Cart> carts = new HashMap<>();
    // order storage
    private Map<Long, Order> Orders = new HashMap<>();
    private Map<Long, List<Order>> customerOrders = new HashMap<>();
    
    
    // ID generators
    // book Id generator
    private AtomicLong bookIdGenerator = new AtomicLong(1);
    // author Id generator
    private AtomicLong authorIdGenerator = new AtomicLong(1);
    // customer Id generator
    private AtomicLong customerIdGenerator = new AtomicLong(1);
    // order Id generator
    private AtomicLong orderIdGenerator = new AtomicLong(1);
    
    // private constructor for singleton
    private InMemoryDatabase(){
      
    }
    
    // get singleton instance of the inmemory database
    public static synchronized InMemoryDatabase getInstance(){
        if(instance == null){
            instance = new InMemoryDatabase();
        }
        return instance;
    }
    
    // ------------------book operations------------------------
    /**
     * @return  all books
     */
    public List<Book> getAllBooks(){
        return new ArrayList<>(books.values());
    }
    
    /**
     * return book by its Id
     * @param id book id
     * @return book 
     */
    public Book getBookId(Long id){
        return books.get(id);
    }
    
    /**
     * add a new book
     * @param book instance
     * @return book
     */
    public Book addBook(Book book){
        Long id = bookIdGenerator.getAndIncrement();
        book.setId(id);
        books.put(id,book);
        return book;
    }
    
    /**
     * update existing book
     */
    public Book updateBook(Book book){
        if(books.containsKey(book.getId())){
            books.put(book.getId(),book);
            return book;
        }
        return null;
    }
    
    /**
     *delete a book by id
     */
    public boolean deleteBook(Long id){
        if(books.containsKey(id)){
            books.remove(id);
            return true;
        }
        return false;
    }
    
    // -----------------------Author operations---------------------
    public List<Author>getAllAuthors(){
        return new ArrayList<>(authors.values());
    }
    
    public Author getAuthorById(Long id){
        return authors.get(id);
    }
    
    public Author addAuthor (Author author){
        Long id = authorIdGenerator.getAndIncrement();
        author.setId(id);
        authors.put(id, author);
        return author;
    }
    
    public Author updateAuthor(Author author){
        if(authors.containsKey(author.getId())){
            authors.put(author.getId(),author);
            return author;
        }
        return null;
    }
    
    public boolean deleteAuthor(Long id){
        if(authors.containsKey(id)){
            authors.remove(id);
            return true;
        }
        return false;
    }
    
    // get books by author ID
    public List<Book>getBooksByAuthorId(Long authorId){
        List<Book> authorBooks = new ArrayList<>();
        for(Book book : books.values()){
            if(book.getAuthorId() != null && book.getAuthorId().equals(authorId)){
                authorBooks.add(book);
            }
        }
        return authorBooks;
    }
    
    // check is author exists
    public boolean authorExists(Long id){
        return authors.containsKey(id);
    }
    
    // ------------------------Customer operations----------------------------
    public List<Customer> getAllCustomers(){
        return new ArrayList<>(customers.values());
    }
    
    public Customer getCustomerById(Long id){
        return customers.get(id);
    }
    
    public Customer addCustomer(Customer customer){
        // check if email already exists
        for(Customer existingCustomer : customers.values()){
            if(existingCustomer.getEmail().equals(customer.getEmail())){
                return null;  // email already exists
            }
        }
        
        Long id = customerIdGenerator.getAndIncrement();
        customer.setId(id);
        customers.put(id,customer);
        return customer;
    }
    
    public Customer updateCustomer(Customer customer){
        // check if email is already used by another customer
        for(Customer existingCustomer : customers.values()){
            if(existingCustomer.getEmail().equals(customer.getEmail()) && 
                    !existingCustomer.getId().equals(customer.getId())){
                return null; // email already used by another customer
            }
        }
        
        if(customers.containsKey(customer.getId())){
            customers.put(customer.getId(), customer);
            return customer;
        }
        return null;
    }
    
    public boolean deleteCustomer(Long id){
        if(customers.containsKey(id)){
            customers.remove(id);
            return true;
        }
        return false;
    }
    
    // check if customer exists
    public boolean customerExists(Long id){
        return customers.containsKey(id);
    }
    
    // ---------------------------------cart operations------------------------
    public Cart getCartByCustomerId(Long customerId){
        return carts.get(customerId);
    }
    
    public Cart createCart(Long customerId){
        Cart cart = new Cart(customerId);
        carts.put(customerId, cart);
        return cart;
    }
    
    public Cart addToCart(Long customerId, CartItem item){
        // Ensure cart exists
        Cart cart = carts.get(customerId);
        if(cart == null){
            cart = createCart(customerId);
        }
        
        // Ensure book exists anhd has enoght stock
        Book book = books.get(item.getBookId());
        if(book == null){
            return null;  // Book not found
        }
        
        // Check if adding this quantity wouls exceed stock
        int currentQuantity = 0;
        if(cart.getItems().containsKey(item.getBookId())){
            currentQuantity = cart.getItems().get(item.getBookId()).getQuantity();
        }
        
        if(currentQuantity + item.getQuantity() > book.getStock()){
            return null;  // Not enough stock
        }
        
        // set the current price from the book
        item.setUnitPrice(book.getPrice());
        
        //Add to cart
        cart.addItem(item);
        return cart;
    }
    
    public Cart updateCartItem(Long customerId, Long bookId, int quantity){
        //Ensure cart exists
        Cart cart = carts.get(customerId);
        if(cart == null){
            return null;  // Cart not found
        }
        
        // Ensure book exists in cart
        if(!cart.getItems().containsKey(bookId)){
            return null;  // Book not in cart
        }
        
        // Ensure book exists and has enough stock
        Book book = books.get(bookId);
        if(book == null){
            return null;  // Book not found
        }
        
        if(quantity > book.getStock()){
            return null;  
        }
        
        // update quantity
        if(quantity <= 0){
            cart.removeItem(bookId);
        } else {
            cart.updateItemQuantity(bookId, quantity);
        }
        
        return cart;
    }
    
    public Cart removeCartItem(Long customerId, long bookId){
        // ensure cart exists
        Cart cart = carts.get(customerId);
        if(cart == null){
            return null;   // cart not found
        }
        
        // remove item
        cart.removeItem(bookId);
        return cart;
    }
    
    public boolean clearCart(Long customerId){
        Cart cart = carts.get(customerId);
        if(cart == null){
            return false;  // cart not found
        }
        
        cart.clear();
        return true;
    }
    
    //----------------------------------order operations-----------------------
    public List<Order> getOrderByCustomerId(Long customerId){
        return customerOrders.getOrDefault(customerId, new ArrayList<>());
    }
    
    public Order getOrderById(Long customerId, Long orderId){
        List<Order> orders = getOrderByCustomerId(customerId);
        for(Order order : orders){
            if(order.getId().equals(orderId)){
                return order;
            }
        }
        return null;
    }
    
    public Order createOrderFromCart(Long customerId){
        // check if customer exists
        if(!customerExists(customerId)){
            return null;
        }
        
        // check if cart exists
        Cart cart = getCartByCustomerId(customerId);
        if(cart == null || cart.getItems().isEmpty()){
            return null;
        }
        
        // check stock levels before creating order
        for(CartItem cartItem : cart.getItems().values()){
            Book book = getBookId(cartItem.getBookId());
            if(book == null || book.getStock() < cartItem.getQuantity()){
                return null;   // not enough stock
            }
        } 
        
        // create new order
        Long orderId = orderIdGenerator.getAndIncrement();
        Order order = new Order(orderId, customerId);
        
        // Convert cart items to order items and update stock
        for(CartItem cartItem : cart.getItems().values()){
            Book book = getBookId(cartItem.getBookId());
            OrderItem orderItem = new OrderItem(cartItem, book.getTitle());
            order.addItem(orderItem);
            
            // update book stock
            book.setStock(book.getStock() - cartItem.getQuantity());
            updateBook(book);
        }
        
        // save order
        Orders.put(orderId, order);
        
        //Add to customer orders
        List<Order> customerOrderList = customerOrders.getOrDefault(customerId, new ArrayList<>());
        customerOrderList.add(order);
        customerOrders.put(customerId, customerOrderList);
        
        // Clear the cart
        clearCart(customerId);
        
        return order;
    } 

    public Book getBookById(Long bookId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
