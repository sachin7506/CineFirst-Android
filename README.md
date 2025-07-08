# CineFirst - Platform Commons Android Assignment 🎬

CineFirst is a modern Android application built using **Kotlin** and **MVVM architecture**, developed as part of the Platform Commons assignment. The app demonstrates robust product thinking, clean code, offline-first design, and a smooth user experience.

---

## 📱 Features

### ✅ Section 1: User List & Navigation
- Displays a paginated list of users using [ReqRes API](https://reqres.in/api/users).
- Infinite scrolling with **Paging 3**.
- On user click, navigates to the Movie List screen.
- **Checks internet before fetching** – shows in-screen error message if offline.
- Built with **MVVM + Kotlin Flow** for reactive UI.

### ✅ Section 2: Add User with Offline Sync
- Form to add users (Name & Job).
- On submission:
  - ✅ If online: Submits to `POST https://reqres.in/api/users`.
  - 🚫 If offline: Stores locally in Room DB and shows "Connect to the internet".
- **Auto-sync** with server when online using `WorkManager`.
- After successful sync or add:
  - Automatically navigates back to the **User List screen** with success message/snackbar/dialog.

### ✅ Section 3: Movie List
- Lists trending movies from [TMDB API](https://developers.themoviedb.org/3/getting-started/introduction).
- Displays movie poster, title, and release date.
- Infinite scroll with Paging 3.
- Click to view detailed screen.

### ✅ Section 4: Movie Detail
- Displays:
  - Movie Title
  - Overview
  - Release Date
  - Poster from: `https://image.tmdb.org/t/p/w185/{poster_path}`

---

## 🔌 Offline Handling + Navigation

| Feature                      | Behavior                                                                 |
|-----------------------------|--------------------------------------------------------------------------|
| **Internet Check**          | Before every API call (user list, add, movies), internet is checked.     |
| **Error UI**                | If offline, shows red error text (`Connect to the internet`) in-screen.  |
| **Offline Add (Room)**      | Adds user locally when offline.                                          |
| **Auto Sync on Reconnect**  | When network is restored, `WorkManager` uploads pending users.           |
| **Post-Success Navigation** | After add/update completes, auto-navigates to User List Fragment.        |

---

## 🔑 Key Components

| Component            | Description                                                                   |
|----------------------|-------------------------------------------------------------------------------|
| **Retrofit**         | For API requests to ReqRes and TMDB.                                          |
| **Room**             | Stores offline user entries locally.                                          |
| **WorkManager**      | Periodically syncs pending data when internet is restored.                    |
| **BroadcastReceiver**| Detects internet connectivity changes.                                        |
| **ViewModel**        | Holds UI state and data, lifecycle-aware.                                     |
| **Paging 3**         | Efficient loading of paginated data (Users & Movies).                         |
| **Kotlin Flow**      | Observes data reactively in ViewModel → Fragment.                             |
| **Navigation Component** | Ensures safe and argument-based navigation between fragments.          |
| **Glide**            | Loads images efficiently for avatars and posters.                             |

---

## 🛠️ Tech Stack

- **Language**: Kotlin  
- **Architecture**: MVVM  
- **Networking**: Retrofit  
- **Local DB**: Room  
- **Pagination**: Paging 3  
- **Image Loading**: Glide  
- **Offline Sync**: WorkManager  
- **Reactive UI**: Kotlin Flow  
- **Dependency Injection**: None used (Manual ViewModelFactory)  

---

## 🧪 Assumptions

- No authentication or user session required.
- Offline entries sync immediately once internet is restored.
- Basic form validation for name and job.
- All navigation flows use SafeArgs for type safety.
- "Back to main screen" is always triggered on success using `findNavController().navigateUp()` or action.

---


