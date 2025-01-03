![image](https://github.com/user-attachments/assets/7095fcfd-9ca3-447e-ad6c-dd35481d86a4)# Android-Chatting-Application

This repository contains the code for an Android chatting application. The application enables real-time text communication between users. Below is an overview of the application’s structure and key functionalities.

---

## Features

- **User Authentication**: Login and signup functionality to manage user sessions.
- **Real-Time Messaging**: Send and receive messages instantly.
- **User Profiles**: Display and manage user details and profile pictures.
- **Message Timestamping**: Human-readable timestamps for messages.
- **Settings**: Options for notification preferences, themes, and profile management.

---

## Project Structure

### 1. **Model Package**
- **User Model**:
  - Represents each user in the application.
  - Fields include `userId`, `username`, `profileImageUrl`, etc.

### 2. **Adapter Package**
- **UsersAdapter**:
  - Handles the display of users in a list.
  - Binds user data to UI components efficiently using RecyclerView.
- **ChatAdapter**:
  - Manages chat messages for display in the chat interface.
  - Differentiates between sent and received messages.

### 3. **Util Package**
- **DateUtils**:
  - Formats timestamps for chat messages.
- **ImageUtils**:
  - Loads and caches images for profile pictures.
- **NetworkUtils**:
  - Manages HTTP requests and WebSocket connections for messaging.

### 4. **Activities**
- **LoginActivity**:
  - Handles user authentication.
  - Verifies credentials and starts user sessions.
- **ChatActivity**:
  - Manages the real-time chat interface.
  - Displays messages and handles message sending.
- **SettingsActivity**:
  - Allows customization of profile and app settings.

### 5. **Backend Interaction**
- The application interacts with a backend server for:
  - User authentication.
  - Real-time messaging using WebSocket or Firebase.
  - User data and profile management.

---

## Requirements

- **Android Studio**: Latest version recommended.
- **Min SDK**: 24
- **Target SDK**: 33

---

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/chatting-app.git
   ```

2. Open the project in Android Studio.

3. Sync Gradle dependencies.

4. Add your Firebase project configuration or backend server URL.

5. Build and run the application on an emulator or physical device.

---

## Key Dependencies

- **Firebase** (Optional): For real-time messaging.
- **AndroidX Libraries**: Core components for app development.
- **Material Design**: For modern UI components.

---

## Screenshots
1. **Splash Screen**

   ![image](https://github.com/user-attachments/assets/5990feb9-0a97-4e3e-9114-f14d606d21b7)

3. **Signup/Sign In Screen**
  
   ![image](https://github.com/user-attachments/assets/156f180a-8580-47d9-b7d8-dbeca56a9bb1)


4. **Recent Chart Screen**

   ![image](https://github.com/user-attachments/assets/5f5e298d-cc4d-440a-adaa-57f651c66e11)

5. **Chart Screen**

   ![image](https://github.com/user-attachments/assets/19b2daa7-d762-4e22-add4-fe48a9e2604f)

6. **Profile Screen**

   ![image](https://github.com/user-attachments/assets/17383450-599b-4579-a459-6b0a21f83ac8)

---

## Contributing

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Submit a pull request with detailed changes.

---

## License

This project is licensed under the [MIT License](LICENSE).

---

## Contact

For any inquiries or support, please reach out to [debasishpradhan3214@gmail.com ].
