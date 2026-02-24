import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../themes/theme_provider.dart';
/// Esta clase se encarga de crear un contenedor con sombras
class NeuBox extends StatelessWidget {
  final Widget? child;
  // Constructor
  const NeuBox({
    super.key,
    required this.child,
  });
  @override
  Widget build(BuildContext context) {
    // Modo oscuro
    bool isDarkMode = Provider.of<ThemeProvider>(context).isDarkMode;
    return Container(
      decoration: BoxDecoration(
          color: Theme.of(context).colorScheme.background,
          borderRadius: BorderRadius.circular(12),
          boxShadow: [
            // Darker shadow on bottom right
            BoxShadow(
              color: isDarkMode ? Colors.black : Colors.grey.shade500,
              blurRadius: 15,
              offset: const Offset(4, 4),
            ),
            // Lighter shadow on top left
            BoxShadow(
              color: isDarkMode ? Colors.grey.shade800 : Colors.white,
              blurRadius: 15,
              offset: const Offset(-4, -4),
            ),
          ]),
      padding: const EdgeInsets.all(12),
      child: child, // El child será el icono, que es obligatorio (required)
    );
  }
}