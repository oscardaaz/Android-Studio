import 'package:flutter/material.dart';
// import 'package:flutter_audio_player_24_25/components/neu_box.dart';
// import 'package:flutter_audio_player_24_25/models/playlist_provider.dart';
import 'package:provider/provider.dart';

import '../components/neu_box.dart';
import '../models/playlist_provider.dart';

/// Clase SongPage
///
/// `SongPage` es una pantalla que muestra la canción en reproducción con controles de reproducción.

/// - Muestra la imagen del álbum y el título de la canción.
/// - Muestra el progreso de la canción con una barra deslizante.
/// - Contiene botones para pausar/reproducir, avanzar y retroceder.
/// - Se integra con `PlaylistProvider` para obtener la canción actual y controlar la reproducción.

class SongPage extends StatelessWidget {
  /// Constructor de SongPage
  /// - No necesita parámetros porque obtiene la información del `PlaylistProvider`.
  const SongPage({super.key});

  /// Método formatTime
  /// Convierte un objeto `Duration` en un String en formato "mm:ss".
  /// - Por ejemplo, `Duration(minutes: 3, seconds: 5)` se convierte en `"3:05"`.
  String formatTime(Duration duration) {
    // Obtiene los segundos con dos dígitos
    String twoDigitSeconds = duration.inSeconds
        .remainder(60)
        .toString()
        .padLeft(2, "0");
    // Formatea la salida como "minutos:segundos"
    String formattedTime = "${duration.inMinutes}:$twoDigitSeconds";
    return formattedTime;
  }

  /// Método build
  /// Construye la interfaz gráfica de la pantalla de reproducción.
  @override
  Widget build(BuildContext context) {
    return Consumer<PlaylistProvider>( // Consumer es un widget que escucha
      // los cambios en PlaylistProvider
      builder: (context, value, child) {
        // - Obtiene la lista de canciones desde `PlaylistProvider`
        final playlist = value.playlist;
        // - Obtiene la canción actual (si no hay índice seleccionado, usa la primera)
        final currentSong = playlist[value.currentSongIndex ?? 0];
        // - Devuelve la estructura de la pantalla con `Scaffold`
        return Scaffold(
          backgroundColor: Theme
              .of(context)
              .colorScheme
              .surface, // Color de fondo
          body: SafeArea( // Asegura que el contenido no se superponga con la barra de estado
              child: Padding(
          padding: const EdgeInsets.only(left: 25, right: 25, bottom: 25),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              // - App bar personalizada
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  // Botón "Atrás" para regresar a la pantalla ant
                  IconButton(
                    onPressed: () => Navigator.pop(context),
                    icon: const Icon(Icons.arrow_back),
                  ),
// Título en la AppBar
                  const Text("P L A Y L I S T"),
                  // Botón de menú (actualmente no tiene funcionalidad)
                  IconButton(
                    onPressed: () {},
                    icon: const Icon(Icons.menu),
                  ),
                ],
              ),
              const SizedBox(height: 25),
              // - Imagen del Álbum con sombra, bordes redondeados y efecto 3D
              // Lo hemos creado nosotros en el archivo `neu_box.dart`
              NeuBox(
                child: Column(children: [
                  // `ClipRRect` redondea los bordes de la imagen
                  ClipRRect(
                    borderRadius: BorderRadius.circular(8),
                    child: Image.asset(currentSong.albumArtImagePath),
                  ),
                  // - Nombre de la Canción, Artista y Botón de Favoritos
                  Padding(
                    padding: const EdgeInsets.all(15.0),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        // Nombre de la Canción y Artista
                        Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text(
                              currentSong.songName,
                              style: const TextStyle(
                                fontWeight: FontWeight.bold,
                                fontSize: 20,
                              ),
                            ),
                            Text(currentSong.artistName)
                          ],
                        ),
                        // Icono de Favoritos (corazón rojo)
                        const Icon(
                          Icons.favorite,
                          color: Colors.red,
                        )
                      ],
                    ),
                  )
                ]),
              ),
              const SizedBox(height: 25),
              // - Progreso de la Canción
              Column(
                children: [
                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 25.0),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        // Tiempo actual de la canción
                        Text(formatTime(value.currentDuration)),
                        // Icono de "Shuffle" (mezclar canciones)
                        const Icon(Icons.shuffle),
                        // Icono de "Repetir canción"
                        const Icon(Icons.repeat),
                        // Duración total de la canción
                        Text(formatTime(value.totalDuration)),
                      ],
                    ),
                  ),
                  // - Barra de progreso de la canción
                  SliderTheme(
                    // Configuración para quitar el "punto gordo" del slider
                    data: SliderTheme.of(context).copyWith(
                      thumbShape: const RoundSliderThumbShape(
                          enabledThumbRadius: 0),
                    ),
                    child: Slider(
                      min: 0,
                      max: value.totalDuration.inSeconds.toDouble(),
                      value: value.currentDuration.inSeconds.toDouble(),
                      activeColor: Colors.green,
                      // Color de la barra de progreso
                      onChanged: (valor) {
                        // Acciones mientras el usuario mueve el slider
                      },
                      onChangeEnd: (valor) {
                        // Cuando el usuario suelta el slider, se actualiza la posición
                        value.seek(Duration(seconds: valor.toInt()));
                      },
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 10),
              // - Controles de Reproducción (Anterior - Play/Pausa - Siguiente)
              Row(
                children: [
                  // - Botón "Anterior"
                  Expanded(
                    child: GestureDetector(
                      onTap: value.playPreviousSong,
                      child: const NeuBox(
                        child: Icon(Icons.skip_previous),
                      ),
                    ),
                  ),
                  const SizedBox(width: 20),
                  // - Botón "Play/Pausa"
                  Expanded(
                    flex: 2, // Hace que el botón play sea más grande
                    child: GestureDetector(
                      onTap: value.pauseOrResume,
                      // Alterna entre pausa y reproducción
                      child: NeuBox(
                        child: Icon(
                          value.isPlaying ? Icons.pause : Icons.play_arrow,
                        ),
                      ),
                    ),
                  ),
                  const SizedBox(width: 20),
                  // - Botón "Siguiente"
                  Expanded(
                    child: GestureDetector(
                      onTap: value.playNextSong,
                      child: const NeuBox(
                        child: Icon(Icons.skip_next),
                      ),
                    ),
                  ),
                ],
              ),
            ],
          ),
        ),)
        ,
        );
      },
    ); // Fin de Consumer
  } // Fin del método build
} // Fin de la clase SongPage