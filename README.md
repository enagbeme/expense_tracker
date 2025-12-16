# ExTracker - Android Expense Tracker

A comprehensive Android application for tracking and managing personal expenses with Firebase integration, built using Java and Android Studio.

## 📱 Features

### 🔐 Authentication
- **User Registration & Login** - Secure authentication system
- **Password Reset** - Email-based password recovery functionality
- **Firebase Integration** - Backed by Firebase Authentication

### 💰 Expense Management
- **Multi-Category Tracking** - Organize expenses by categories:
  - 🍕 Food & Dining
  - 🚗 Transportation
  - 🏠 Utilities
  - 🛒 Others (Custom expenses)
- **Real-time Updates** - Instant synchronization with Firebase Firestore
- **Expense History** - View all past transactions with detailed information

### 📊 Budgeting & Reporting
- **Budget Creation** - Set monthly budgets for different categories
- **Budget Tracking** - Monitor spending against budget limits
- **Visual Reports** - Graphical representation of expenses
- **Expense Analytics** - Insights into spending patterns

### 🎯 Additional Features
- **QR Code Scanner** - Quick expense entry via QR codes
- **Dashboard Overview** - At-a-glance financial summary
- **Transaction History** - Complete record of all financial activities
- **Responsive UI** - Material Design compliant interface
- **Dark/Light Theme** - Support for both light and dark modes

## 🛠️ Technical Stack

- **Language**: Java
- **Framework**: Android SDK
- **Database**: Firebase Firestore (NoSQL)
- **Authentication**: Firebase Auth
- **Build Tool**: Gradle
- **Minimum SDK**: Android 5.0 (API 21)
- **Target SDK**: Android 12 (API 31)

## 📦 Project Structure

```
app/
├── src/main/java/com/example/extracker/
│   ├── Model/              # Data models
│   │   ├── ExpenseModel.java
│   │   ├── BudgetModel.java
│   │   ├── FoodModel.java
│   │   ├── TransportModel.java
│   │   ├── UtilitiesModel.java
│   │   ├── OthersModel.java
│   │   └── Users.java
│   ├── Activities/         # UI components
│   │   ├── LoginActivity.java
│   │   ├── DashboardActivity.java
│   │   ├── ExpensesActivity.java
│   │   ├── BudgetActivity.java
│   │   ├── ReportActivity.java
│   │   └── QrScannerActivity.java
│   └── Adapters/          # RecyclerView adapters
│       ├── ExpenseAdapter.java
│       ├── BudgetAdapter.java
│       └── Category adapters
└── src/main/res/
    ├── layout/            # XML layouts
    ├── drawable/          # Images and vectors
    └── values/            # Strings, colors, themes
```

## 🚀 Getting Started

### Prerequisites
- Android Studio (Latest version)
- Java JDK 8 or higher
- Firebase project setup
- Physical Android device or emulator

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/enagbeme/expense_tracker.git
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the cloned directory

3. **Configure Firebase**
   - Create a new Firebase project at [Firebase Console](https://console.firebase.google.com/)
   - Add Android app to your Firebase project
   - Download `google-services.json` and place it in `app/` directory
   - Enable Firestore and Authentication in Firebase console

4. **Build and Run**
   - Connect Android device or start emulator
   - Click "Run" button in Android Studio
   - Select target device
   - Wait for build and installation

## 🔧 Configuration

### Firebase Setup
1. Enable Email/Password authentication in Firebase Console
2. Create Firestore database with following collections:
   - `users` - User profiles and preferences
   - `expenses` - Expense records
   - `budgets` - Budget information
   - `categories` - Expense categories

### Environment Variables
Update `google-services.json` with your Firebase project configuration.

## 📊 Database Schema

### Users Collection
```json
{
  "userId": "string",
  "email": "string",
  "name": "string",
  "createdAt": "timestamp",
  "totalExpenses": "number"
}
```

### Expenses Collection
```json
{
  "expenseId": "string",
  "userId": "string",
  "amount": "number",
  "category": "string",
  "description": "string",
  "date": "timestamp",
  "location": "string",
  "paymentMethod": "string"
}
```

### Budgets Collection
```json
{
  "budgetId": "string",
  "userId": "string",
  "category": "string",
  "amount": "number",
  "month": "string",
  "year": "number",
  "spent": "number"
}
```

## 🎨 UI/UX Features

- **Material Design Components** - Cards, buttons, and navigation drawer
- **Color-coded Categories** - Visual distinction between expense types
- **Interactive Charts** - Pie charts and bar graphs for data visualization
- **Smooth Animations** - Enhanced user experience with transitions
- **Responsive Layout** - Adapts to different screen sizes

## 📈 Performance Optimizations

- **Firestore Pagination** - Efficient data loading for large datasets
- **Image Compression** - Optimized drawable resources
- **Memory Management** - Proper lifecycle handling in activities
- **Network Optimization** - Efficient Firestore queries and caching

## 🔒 Security Features

- **Firebase Authentication** - Secure user management
- **Data Validation** - Input sanitization and validation
- **Secure Database Rules** - Firestore security rules implementation
- **Encrypted Storage** - Secure local data storage

## 🤝 Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

If you have any questions or need help with setup:
- Create an [Issue](https://github.com/enagbeme/expense_tracker/issues)
- Check Firebase documentation for setup help
- Review Android Studio documentation for build issues

**Built with ❤️ using Android Studio and Firebase**
