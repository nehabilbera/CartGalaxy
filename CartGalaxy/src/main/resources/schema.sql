--users
 CREATE TABLE IF NOT EXISTS users (   
    user_id INT PRIMARY KEY,
    username VARCHAR(100)
 ) ;


--product
 CREATE TABLE IF NOT EXISTS products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    selling_price FLOAT,
    discounted_price FLOAT,
    product_name VARCHAR(255),
    brand VARCHAR(255),
    category VARCHAR(255),
    image VARCHAR(255),
    availability BOOLEAN
 ) ;


--stocks
 CREATE TABLE IF NOT EXISTS stocks (   
    stock_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    available_quantity INT,
    total_quantity INT,
    FOREIGN KEY (product_id) REFERENCES products(product_id)
 ) ;


--cart
 CREATE TABLE IF NOT EXISTS cart (   
    user_id INT PRIMARY KEY,
    created_at DATETIME,
    status VARCHAR(20),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
 ) ;


--cartitems
 CREATE TABLE IF NOT EXISTS cartItems (   
    user_id INT,
    product_id INT,
    quantity INT,
    PRIMARY KEY (user_id, product_id),
    FOREIGN KEY (user_id) REFERENCES cart(user_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
) ;


--orders
 CREATE TABLE IF NOT EXISTS orders (   
    order_id VARCHAR(100) PRIMARY KEY,
    user_id INT,
    ordered_date DATE,
    status VARCHAR(255),
    transaction_amount FLOAT
) ;


--orderitems
 CREATE TABLE IF NOT EXISTS orderItems (   
    order_id VARCHAR(100),
    product_id INT,
    quantity INT,
    price_at_purchase FLOAT,
    PRIMARY KEY (order_id, product_id)
) ;