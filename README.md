# StockSalesSystem

**StockSalesSystem** is a standalone Java application, designed especifically for inventory management and sales for a local Stationery. The system allows products control, sales tracking, prices update and generation of stats graphics and reports, giving a complete solution for small and medium businesses.

## Characteristics

- **Products Management**: 
  - Create new products with attributes like name, barcode, purchase cost and sale price.
  - Individual, Category and General product update.
  - Low Stock alert.
  - Each product has its own prices and cost history.

- **Sales**:
  - Product Addition with Barcode Scanning and also an option to add a product out of the DB.
  - Sale Ticket generation with print option.
  - Automatic product inventory update.
  - Discounts Applied to Specific or All Products.

- **Reports**:
  - Sales, Earnings and Products stats generation.
  - Refund generation.

- **Configuration**:
   - Configure application settings, such as:
    - Custom paths
    - Minimum stock alert thresholds
  - Personalize ticket layout and content.

- **Backup**:
  - Manage application backups:
    - Create new backups
    - Delete existing backups

## Technologies

- **Backend**: Java SpringBoot to allow a future Web version, Python with matplotlib for graphic generation and pandas for csv reading.
- **Frontend**: Java Swing for GUI.
- **ORM**: Hibernate.
- **DataBase**: SQLite.
