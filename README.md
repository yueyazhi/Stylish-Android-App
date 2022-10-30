# Stylish-Android-App
A E-commerce APP to practice developing Android App with Kotlin   

## Install
**1.  Download the source code**
```
$ git clone https://github.com/yueyazhi/Stylish-Android-App.git   
```   
**2. Import into Visual Studio Code**   

## Description    
This App was established a `BottomNavigation` with **home page**, **catalog page**, **cart page** and **profile page**, also includes other pages such as
**detail page**, **add to cart page**, and **payment page** to implement most function that A E-commerce APP should provide.    
    
  ![bottom navigation](https://user-images.githubusercontent.com/107618403/198902534-2fcac5bf-3ab5-4dc0-8114-1d61cdc7e93c.gif)

 
### Details of pages
#### Home Page 
* Use a `RecyclerView` with three different `ViewHolders` (header and two different cells) in layout

  [header](https://user-images.githubusercontent.com/107618403/198892451-74a7ed9a-5ec0-4ba4-a58c-6577f667ccbc.jpg)    
  ![title](https://user-images.githubusercontent.com/107618403/198892451-74a7ed9a-5ec0-4ba4-a58c-6577f667ccbc.jpg)

  [two different cells](https://user-images.githubusercontent.com/107618403/198892183-ab7fc0f3-bacc-4e83-b2a5-9d9ab76fef51.jpg)   
  ![two cells](https://user-images.githubusercontent.com/107618403/198892183-ab7fc0f3-bacc-4e83-b2a5-9d9ab76fef51.jpg)    
    
* Fetch data from the server and display different data cells in different ways decided by its position in data structure     
  (odd cells display only one image, and even cells display four images)    
      
  ![display in different methods](https://user-images.githubusercontent.com/107618403/198897753-c1efcf50-ecdd-402d-8751-ffbb455a1015.gif)

* Implement pull-down-refresh functional   
    
  ![layout refresh](https://user-images.githubusercontent.com/107618403/198896930-fac8f28d-08ac-4630-a05d-7241ffaefa2d.gif)



#### Catalog Page    
* Implement `ViewPager` with `TabLayout` to switch each child fragment (**women**, **man** and **accessories**)   
    
    ![catalog page](https://user-images.githubusercontent.com/107618403/198894274-51e3952f-e314-43b8-8ade-5c7bebc658df.gif)

* Fetch data from the server, and implement `addOnScrollListener` to request next group of products when user scrolls to the end of the `RecyclerView`      
    
    ![addOnScrollListener](https://user-images.githubusercontent.com/107618403/198895087-05b78f7a-c7d9-4977-b10f-2f5e1a90d75b.gif)
    
* Implement pull-down-refresh functional  

#### Detail Page    
* Navigate to detail page when user selects product in  **home page** or **catalog page**   

  ![navigate to detail](https://user-images.githubusercontent.com/107618403/198899105-1fab0f03-7283-4b11-b133-169c542c1a5b.gif)

* Implement image gallery with horizontal scroll    
    
  ![gallery](https://user-images.githubusercontent.com/107618403/198898363-de9aeb1e-ce78-4154-b209-0752c1d69ed0.gif)



#### Add to Cart Page     
* Establish with `BottomSheetDialogFragment`        
* Synchronize the size selections with color selections   
* Display stocks when size is chosen by user    
* Check if the purchase is out of stock or not, disable the relative button   
* Show **"successfully added product into the cart"** dialog if the product is added into the cart at first time and not out of stock, then save the order in `Local SQLite Database`;
otherwise, show **"this product is already exists in the cart"**    
    
  ![add to cart fragment ](https://user-images.githubusercontent.com/107618403/198900055-dcb02f31-2106-4d40-be0a-bfc82784361b.gif)


#### Cart Page     
* Implement remove function when user taps on the remove button in the cell    
* Load data from `Local SQLite Database`    
    
  ![cart page](https://user-images.githubusercontent.com/107618403/198901243-1b89ae16-28cd-47d2-829d-d1d00f682582.gif)


#### Payment Page    
* Establish credit cart layout by [TapPay SDK - Android](https://docs.tappaysdk.com/tutorial/zh/android/front.html) 
* Check enter information when user clicks **go to chckout button**, if any information user enters is invalid, 
wrong message will show on screen and fail to checkout    
    
  ![payment page](https://user-images.githubusercontent.com/107618403/198902470-a653054f-c912-4bc3-b072-5747788ce315.gif)

#### Profile Page    
    
   ![profile page](https://user-images.githubusercontent.com/107618403/198900562-6b46b764-713d-4414-8faa-dee2a6016b6f.jpg)

## Future work    
* Implement login flow for **profile page** and get user data through API
* Implemnt **checkout page**
