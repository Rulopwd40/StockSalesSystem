# StockSalesSystem

**StockSalesSystem** is a standalone Java application, designed especifically for inventory management and sales for a local Stationery. The system allows products control, sales tracking, prices update and generation of stats graphics and reports, giving a complete solution for small and medium businesses.

## Characteristics

- **Products Management**: 
  - Create new products with attributes like name, barcode, purchase cost and sale price.
  - Individual, Category and General product update.
  - Low-Stock alert.
  - Each product has its own prices and cost history.

- **Sales**:
  - Product Addition with Barcode Scanning and also an option to add a product out of the DB.
  - Sale Ticket generation with print option.
  - Automatic product inventory update.
  - Discounts Applied to Specific or All Products.

- **Reports(WIP)**:
  - Sales, Earnings and Products stats generation.
  - Refund generation.

## Technologies:

- **Backend**: Java SpringBoot to allow a Web version.
- **Frontend**: Java Swing for GUI.
- **ORM**: Hibernate.
- **DataBase**: SQLite.
