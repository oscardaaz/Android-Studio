import 'package:flutter/material.dart';
import 'package:flutter_audio_player/themes/theme_provider.dart';
import 'models/playlist_provider.dart';
import 'package:provider/provider.dart';
import 'pages/home_page.dart';
// Este método es el punto de entrada de la aplicación.
// Se llama a la función runApp(), que recibe como parámetro
// un widget de tipo MyApp.
void main() {
  runApp(
    MultiProvider(
      providers: [
        // (1) Añadimos el proveedor del tema, que nos permite cambiar entre
        // el tema claro y el oscuro.
        ChangeNotifierProvider(create: (context) => ThemeProvider()),
        ChangeNotifierProvider(create: (context) => PlaylistProvider()),
      ],
      child: const MyApp(),
    ), // Fin MultiProvider
  );
}
/// Clase MyApp que extiende de StatelessWidget.
/// Es el widget raíz de la aplicación y define la estructura general.
class MyApp extends StatelessWidget {
  const MyApp({super.key});
// método build que construye la interfaz de la aplicación.
  // Se ejecuta cada vez que es necesario reconstruir la interfaz.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false, // Oculta la etiqueta de "debug"
      home: const HomePage(), // Página de inicio de la aplicación
      //theme: , // Aquí se podría definir un tema personalizado
    );
  } // Fin metodo build
} // Fin clase MyApp