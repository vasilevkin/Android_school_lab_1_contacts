# Android_school_lab_1_contacts

![Great Contacts demo GIF](https://github.com/vasilevkin/Android_school_lab_1_contacts/blob/master/app_demo.gif)

Loading a huge number of contacts from file is implemented in the repository with these *use cases*:
1. Main Thread (blocking)
2. Kotlin Thread
3. Handler
4. AsyncTask
5. RxJava
6. ThreadPoolExecutor
7. Java executor
8. Loader
9. Coroutines

Switching use cases is possible from the *Options menu* on the contacts list, changes in code not required.

Add a new contact, edit contact, save all contacts to json file implementation is suboptimal and includes only basic error handling.
The app doesn't have any optimisation for tablets.
The project doesn't include any tests.

Technologies used in the project is for educational purposes only and the technology mix is a way overkill for such small project.

##Technologies list:
* MVP
* MVVM
* Repository pattern with 2 repos used simultaneously
* Use Cases
* OSS Licenses plugin
* Splash screen
* CompositeDelegateAdapter
* DiffUtilCompositeAdapter
* DiffUtilCallback
* SharedViewModel
* Dagger 2
* LiveData
* RxJava
* Gson
* SharedPreferences
* ConstraintLayout
* Stetho
