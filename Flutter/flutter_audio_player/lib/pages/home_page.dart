import 'package:flutter_audio_player/components/my_drawer.dart';
import 'package:flutter_audio_player/models/playlist_provider.dart';
import 'package:flutter_audio_player/models/song.dart';
import 'package:flutter_audio_player/pages/song_page.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

/// Clase HomePage
///
/// `HomePage` es la pantalla principal de la aplicación.
/// - Muestra una lista de canciones (Playlist).
/// - Permite seleccionar una canción y navegar a `SongPage`.
/// - Usa `PlaylistProvider` para obtener la lista de reproducción.
class HomePage extends StatefulWidget {
  /// Constructor de HomePage
  const HomePage({super.key});

  /// Crea el estado asociado a HomePage
  @override
  State<HomePage> createState() => _HomePageState();
}

/// Clase `_HomePageState`
/// - Gestiona el estado de `HomePage`.
/// - Obtiene la playlist desde `PlaylistProvider`.
/// - Controla la navegación a la página de la canción seleccionada.
class _HomePageState extends State<HomePage> {
  /// Proveedor de la lista de reproducción
  ///
  /// - `playlistProvider` permite acceder a la playlist y gestionar la reproducción.
  /// - Se usa `late final` porque se inicializa en `initState()`.
  late final dynamic playlistProvider;

  /// Inicialización del estado
  /// - Se ejecuta cuando se crea el widget por primera vez.
  @override
  void initState() {
    super.initState();
    // Obtenemos `PlaylistProvider` sin escuchar cambios (listen: false)
    playlistProvider = Provider.of<PlaylistProvider>(context, listen: false);
  }

  /// método para navegar a `SongPage` cuando se selecciona una canción
  /// - Recibe `songIndex`, el índice de la canción seleccionada en la lista.
  void goToSong(int songIndex) {
    // - Actualiza la canción actual en `PlaylistProvider`
    playlistProvider.currentSongIndex = songIndex;
    // - Navega a la página de la canción seleccionada (`SongPage`)
    Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => const SongPage()),
    );
  }

  /// método build
  /// - Construye la interfaz gráfica de la pantalla principal.

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Theme.of(context).colorScheme.surface,

      /// AppBar (barra superior) con el título "PLAYLIST"
      appBar: AppBar(title: const Text("P L A Y L I S T")),

      /// Consumer de `PlaylistProvider`
      /// - Escucha los cambios en la playlist y reconstruye la lista cuando cambian los datos.
      body: Consumer<PlaylistProvider>(
        builder: (context, value, child) {
          /// - Obtenemos la lista de canciones
          final List<Song> playlist = value.playlist.cast<Song>();

          /// - Mostramos la lista de canciones en un `ListView`
          return ListView.builder(
            itemCount: playlist.length, // Número de canciones en la lista
            itemBuilder: (context, index) {
              /// - Obtenemos la canción individual en la posición `index`
              final Song song = playlist[index];

              /// - Mostramos cada canción como un `ListTile` (fila de lista)
              return ListTile(
                title: Text(song.songName),
                // Nombre de la canción
                subtitle: Text(song.artistName),
                // Nombre del artista
                leading: Image.asset(song.albumArtImagePath),
                // Imagen del álbum
                onTap: () =>
                    goToSong(index), // Navegar a `SongPage` al tocar la canción
              );
            },
          );
        },
      ),
      drawer: const MyDrawer(),
    );
  }
}
