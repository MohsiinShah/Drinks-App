# Weather-App
This project demonstrates the implementation of https://www.thecocktaildb.com/api. MVVM architecture is followed, strong decoupling is performed whereever needed and possible. Retrofit is used to consume the Apis, code is kept clean and scalable for furture implementation of new features. 

Network bound resoruce is used to fetch and save data into ROOM database and ROOM persistant database behaves as the single source of truth (Data). UI gets its data from ROOM database in all cases. 
App fully supports offline-storage, all api calls are cached into ROOM persistant database and in case of no internet connectivity records are fetched from database. Images are cached using glide and not stored into the Database or external/internal storage for normal api calls. User can add/remove favorite drinks from the home page. Any drinks that has been added to favorite will be synced on the home page as well and the fav icon will change accordingly until removed from the favorites fragment or home fragment. 

Favorite drinks are stored into a separate entity, and its images are stored into the app's internal storage</b>, as it is recommended not to store the images into the database itself, I'm only saving the internal file path into ROOM database for the favorite drinks. User can remove any favorite drink from the favorites fragment.


