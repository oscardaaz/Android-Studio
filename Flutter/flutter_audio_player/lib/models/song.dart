class Song {
  // Atributos de la clase Song
  final String songName;
  final String artistName;
  final String albumArtImagePath;
  final String audioPath;

  // Constructor. La palabra clave required indica que ese atributo es obligatorio.
  Song({
    required this.songName,
    required this.artistName,
    required this.albumArtImagePath,
    required this.audioPath,
  });
}
