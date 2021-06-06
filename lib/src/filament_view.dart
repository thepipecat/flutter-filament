part of filament;

int _nextFilamentCreationId = 0;

class FilamentView extends StatefulWidget {
  const FilamentView({Key? key}) : super(key: key);

  @override
  _FilamentViewState createState() => _FilamentViewState();
}

class _FilamentViewState extends State<FilamentView> {
  final _viewId = _nextFilamentCreationId++;

  @override
  Widget build(BuildContext context) {
    return FilamentViewPlatform.instance.buildView(_viewId, (id) { });
  }
}
