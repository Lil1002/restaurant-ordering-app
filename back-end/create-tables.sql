-- ============================================================================
-- DAAM (Dinner and a Movie) Database Schema
-- Database: daamdb
-- Description: Creates all necessary tables for the food delivery and movie 
--              theater management system
-- ============================================================================

USE daamdb;

-- ============================================================================
-- User Table
-- Stores all users of the system with their profile and payment information
-- ============================================================================
CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first VARCHAR(100),
    last VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(255),
    imageUrl VARCHAR(500),
    pan VARCHAR(19),
    expiryMonth INT,
    expiryYear INT,
    roles VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER',
    CONSTRAINT chk_roles CHECK (roles IN ('ROLE_ADMIN', 'ROLE_USER', 'ROLE_SERVER', 'ADMIN', 'USER', 'SERVER')),
    INDEX idx_username (username),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- MenuItem Table
-- Stores food items and their details that appear on the menu
-- ============================================================================
CREATE TABLE IF NOT EXISTS menuitems (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(100),
    price DECIMAL(10, 2) NOT NULL,
    imageUrl VARCHAR(500),
    available BOOLEAN NOT NULL DEFAULT TRUE,
    INDEX idx_category (category),
    INDEX idx_available (available)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- Order Table
-- Stores high-level order details
-- ============================================================================
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userid BIGINT NOT NULL,
    ordertime DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    pickuptime DATETIME,
    area VARCHAR(100),
    location VARCHAR(255),
    tax DECIMAL(10, 2) DEFAULT 0.00,
    tip DECIMAL(10, 2) DEFAULT 0.00,
    pan VARCHAR(19),
    expiryMonth INT,
    expiryYear INT,
    status VARCHAR(50) NOT NULL DEFAULT 'placed',
    CONSTRAINT fk_orders_userid FOREIGN KEY (userid) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT chk_status CHECK (status IN ('placed', 'paid', 'in-progress', 'delivered', 'cancelled')),
    INDEX idx_userid (userid),
    INDEX idx_ordertime (ordertime),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- OrderItems Table (Item)
-- Association table between Orders and MenuItems
-- Provides detailed information for each item in an order
-- ============================================================================
CREATE TABLE IF NOT EXISTS orderitems (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    orderid BIGINT NOT NULL,
    itemid BIGINT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    notes VARCHAR(500),
    firstname VARCHAR(100),
    CONSTRAINT fk_orderitems_orderid FOREIGN KEY (orderid) REFERENCES orders(id) ON DELETE CASCADE,
    CONSTRAINT fk_orderitems_itemid FOREIGN KEY (itemid) REFERENCES menuitems(id) ON DELETE RESTRICT,
    INDEX idx_orderid (orderid),
    INDEX idx_itemid (itemid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- Film Table
-- Stores information about films for the Dinner & a Movie experience
-- ============================================================================
CREATE TABLE IF NOT EXISTS films (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    homepage VARCHAR(500),
    releasedate DATE,
    overview TEXT,
    posterpath VARCHAR(500),
    runtime INT,
    tagline VARCHAR(255),
    popularity DECIMAL(5, 2),
    imdbid VARCHAR(20),
    voteaverage DECIMAL(3, 1),
    votecount INT,
    INDEX idx_title (title),
    INDEX idx_releasedate (releasedate)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- End of Schema Creation
-- ============================================================================
