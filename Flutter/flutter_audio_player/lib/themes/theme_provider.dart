import 'package:flutter/material.dart';
import 'dark_mode.dart';
import 'light_mode.dart';
class ThemeProvider extends ChangeNotifier {
// Inicialmente, el tema es claro
  ThemeData _themeData = lightMode;
// Método getter para obtener el tema actual
  ThemeData get themeData => _themeData;
// is dark mode
  bool get isDarkMode => _themeData == darkMode;
// set theme
  set setTheme(ThemeData themeData) {
    _themeData = themeData;
    // Update UI
    notifyListeners();
  }
// toggle theme
  void toggleTheme() {
    // Si el tema actual es claro, se cambia a oscuro
    if (_themeData == lightMode) {
      _themeData = darkMode;
    } else {
      // Si el tema actual es oscuro, se cambia a claro
      _themeData = lightMode;
    }
    // Update UI
    notifyListeners(); // Hace falta
  }
}