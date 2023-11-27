
# Space Launches 

"Space Launches" is a sample Android app built using Jetpack Compose that allows users to fetch information about upcoming space launches.and set 
a reminder for particular space launch or mission.





## Setup

Install the latest version of Android Studio and Clone this repository by following link  

```bash
 https://github.com/vyom198/SpaceLaunches.git
```



## 🛠 Built With
|  Architecture   |  Clean MVVM Architecture |
|----------------	|------------------------------	|
| <img height="20" src="https://3.bp.blogspot.com/-VVp3WvJvl84/X0Vu6EjYqDI/AAAAAAAAPjU/ZOMKiUlgfg8ok8DY8Hc-ocOvGdB0z86AgCLcBGAsYHQ/s1600/jetpack%2Bcompose%2Bicon_RGB.png">    UI Framework  | [Jetpack Compose](https://www.jetbrains.com/lp/compose-multiplatform/)         |                        |
| 💉 DI                | [Dagger-Hilt](https://developer.android.com/training/dependency-injection/hilt-android)                        |             |
| 🌐 Networking        | [Retrofit](https://github.com/square/retrofit) + [Gson](https://github.com/google/gson)                   |
| :floppy_disk: Local Database      | [Room Database](https://developer.android.com/topic/libraries/architecture/room)                   |
| :compass: Navigation       |  [Compose Destinations Navigation](https://developer.android.com/jetpack/compose/navigation) |
| :building_construction: Persistent Background Work  | [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) + [AlarmManager](https://developer.android.com/reference/android/app/AlarmManager) |
| :thread: Asynchronous Work     |  [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)|
| 🖼️ Image Loading     |  [Coil](https://coil-kt.github.io/coil/)|
| 🖼️ for Pagination And caching data     |  [Paging 3](https://github.com/topics/android-paging3)|
<br>





##  Screenshots (Dark and Light Theme)
### Light Theme

<img src="https://github.com/vyom198/SpaceLaunches/assets/112750331/f576a1dc-baac-4df8-825e-925bc3a8c865"  width="200">
<img src="https://github.com/vyom198/SpaceLaunches/assets/112750331/9fae47c2-d945-444c-b5a9-80aed936a6bc"  width="200">
<img src="https://github.com/vyom198/SpaceLaunches/assets/112750331/f4ace75c-0ee7-4d1d-84bc-b6f9bdbb9940"  width="200">
<img src="https://github.com/vyom198/SpaceLaunches/assets/112750331/14153aa9-7ea5-4745-b728-59709af04134"  width="200">


### Dark Theme

<img src="https://github.com/vyom198/SpaceLaunches/assets/112750331/a7a1b8a1-b01d-43c7-8833-272a6dd1b4e5"  width="200">
<img src="https://github.com/vyom198/SpaceLaunches/assets/112750331/d795bfdc-d380-43d8-942d-fce635cf26c8"  width="200">
<img src="https://github.com/vyom198/SpaceLaunches/assets/112750331/8d2e8fcf-a72d-4e15-956b-0f2772907b0a"  width="200">
<img src="https://github.com/vyom198/SpaceLaunches/assets/112750331/88602b77-39b4-4f52-9f4e-e4a0432f7bcb"  width="200">


## Features

* Fetching data of upcoming rocket Launches
* showing status of upcoming Launches , and space 
  Agency , launch pad , mission name, location    and launch date
*  setting reminder for particular rocket launch
* cancelling the reminder
* getting paginated data and caching in local database for offline experience
* showing notification before launching of rocket

## Api Used
### 🚀  The app uses [Launch Library 2](https://thespacedevs.com/llapi) API for getting upcoming Rocket Launches.














