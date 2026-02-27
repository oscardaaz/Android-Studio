import 'package:audioplayers/audioplayers.dart';
import 'package:flutter/material.dart';
import 'song.dart';

/// Clase PlaylistProvider
///
/// Esta clase actúa como gestor de la lista de reproducción y extiende de
/// `ChangeNotifier`, lo que permite notificar cambios a los widgets que dependen de ella.
///
/// Funciones principales:
/// - Mantiene una lista de canciones.
/// - Gestiona el índice de la canción actual.
/// - Controla la reproducción de audio (reproducir, pausar, reanudar, detener, avanzar y
///   retroceder).
/// - Escucha los cambios en la duración y el estado del reproductor de audio.
/// - Notifica a los widgets cuando hay cambios en el estado de la reproducción.
class PlaylistProvider extends ChangeNotifier {
  // Lista de canciones en la playlist.
  final List<Song> _playlist = [
    // Canción 1
    Song(
      songName: "Dear Rosemary",
      artistName: "Foo Fighters",
      albumArtImagePath: "assets/images/dear_rosemary.jpg",
      // Las imágenes, que utilizan Image.asset(), requieren la ruta completa desde assets/.
      audioPath: "audio/Dear_Rosemary.mp3",
      // En el caso de audio, AudioPlayer.play(AssetSource()), la ruta es relativa
      // a la carpeta assets/, por lo que no se necesita el prefijo "assets/".
    ),
    // Canción 2
    Song(
      songName: "Ramble On",
      artistName: "Led Zeppelin",
      albumArtImagePath: "assets/images/ramble_on.jpg",
      audioPath: "audio/Ramble_On.mp3",
    ),
    // Canción 3
    Song(
      songName: "Stairway to Heaven",
      artistName: "Led Zeppelin",
      albumArtImagePath: "assets/images/stairway_to_heaven.jpg",
      audioPath: "audio/Stairway_to_Heaven.mp3",
    ),
  ];

  // Índice de la canción que se está reproduciendo actualmente.
  int? _currentSongIndex;

  // Getters para acceder a la lista de reproducción.
  List<Song> get playlist => _playlist;

  // En Java sería algo así:
  /*
 public List<Song> getPlaylist() {
 return _playlist;
 }
 => Se le llama arrow function o función flecha.
 */
  // Getter para obtener el índice de la canción actual.
  int? get currentSongIndex => _currentSongIndex;

  // Getters para obtener el estado de la reproducción.
  bool get isPlaying => _isPlaying;

  Duration get currentDuration => _currentDuration;

  Duration get totalDuration => _totalDuration;

  // Setter para actualizar el índice de la canción actual.
  set currentSongIndex(int? newIndex) {
    _currentSongIndex = newIndex;
    if (newIndex != null) {
      // Si hay una nueva canción seleccionada, se reproduce automáticamente.
      play();
    }
    // Notifica a los listeners (widgets que dependen de esta clase) que el estado ha cambiado.
    notifyListeners();
  }

  // ---------- C O N T R O L D E A U D I O -------------
  /// Reproductor de audio de la app
  ///
  /// - Requiere la dependencia: `flutter pub add audioplayers`.
  /// - Permite reproducir, pausar, reanudar, avanzar, retroceder y detener canciones.
  final AudioPlayer _audioPlayer = AudioPlayer();

  // Variables privadas para la duración del audio.
  Duration _currentDuration =
      Duration.zero; // Duration.zero = Duración inicial 0
  Duration _totalDuration = Duration.zero;

  // Indica si actualmente se está reproduciendo una canción.
  bool _isPlaying = false;

  // Constructor de la clase
  // Configura los listeners para escuchar cambios en la duración y el estado del
  // reproductor.
  PlaylistProvider() {
    listenToDuration();
  }

  /// Reproducir una canción
  /// - Si hay una canción en reproducción, la detiene antes de iniciar la nueva.
  /// - Se usa `await` para evitar que el hilo principal se bloquee.
  void play() async {
    final String path = _playlist[_currentSongIndex!].audioPath;
    await _audioPlayer.stop(); // Detener la canción actual
    await _audioPlayer.play(AssetSource(path)); // Reproducir la nueva canción
    _isPlaying = true;
    notifyListeners(); // Notificar cambios a la UI
  }

  /// Pausar la reproducción
  void pause() async {
    await _audioPlayer.pause();
    _isPlaying = false;
    notifyListeners();
  }

  /// Reanudar la canción pausada
  void resume() async {
    await _audioPlayer.resume();
    _isPlaying = true;
    notifyListeners();
  }

  /// Parar la cancion al dar atras.
  void stop() async {
    await _audioPlayer.stop();
    _isPlaying = false;
    _currentDuration = Duration.zero;
    _totalDuration = Duration.zero;
    notifyListeners();
  }

  /// Alternar entre pausar y reanudar
  /// - Si la canción está reproduciéndose, la pausa.
  /// - Si la canción está pausada, la reanuda.
  void pauseOrResume() async {
    if (_isPlaying) {
      pause();
    } else {
      resume();
    }
    notifyListeners();
  }

  /// Avanzar o retroceder en la canción
  void seek(Duration position) async {
    await _audioPlayer.seek(position);
  }

  /// Reproducir la siguiente canción
  /// - Si la canción actual es la última, vuelve a la primera.
  void playNextSong() {
    if (_currentSongIndex == _playlist.length - 1) {
      currentSongIndex = 0; // Volver a la primera canción
    } else {
      currentSongIndex = _currentSongIndex! + 1;
    }
    play();
  }

  /// Reproducir la canción anterior
  /// - Si la canción lleva menos de 3 segundos, se reinicia.
  /// - Si no, se retrocede a la canción anterior.
  void playPreviousSong() async {
    if (_currentDuration.inSeconds < 3) {
      // Reiniciar la canción actual si han pasado menos de 3 segundos
      seek(Duration.zero);
    } else {
      if (_currentSongIndex! > 0) {
        currentSongIndex = _currentSongIndex! - 1;
      } else {
        // Si es la primera canción, volver a la última
        currentSongIndex = _playlist.length - 1;
      }
    }
    play();
  }

  /// Escuchar eventos del reproductor de audio
  /// - Registra listeners para detectar cambios en:
  /// Duración total de la canción
  /// Posición actual
  /// Fin de la canción (para reproducir la siguiente automáticamente)
  /// Este método se ejecuta una sola vez en el constructor
  /// (PlaylistProvider()), por lo que los "listeners" se quedan funcionando
  /// todo el tiempo.
  void listenToDuration() {
    // Escucha cambios en la duración total de la canción.
    // (cuando se carga una nueva canción que tendrá otra duración).
    _audioPlayer.onDurationChanged.listen((newDuration) {
      _totalDuration = newDuration;
      notifyListeners();
    });
    // Escucha cambios en la posición de la canción (avance o retroceso).
    _audioPlayer.onPositionChanged.listen((newPosition) {
      _currentDuration = newPosition;
      notifyListeners();
    });
    // Detecta cuando una canción termina y reproduce automáticamente la siguiente.
    _audioPlayer.onPlayerComplete.listen((event) {
      playNextSong();
    });
  }

  // ---------- F I N C O N T R O L D E A U D I O -------------
} // Fin de la clase PlaylistProvider
