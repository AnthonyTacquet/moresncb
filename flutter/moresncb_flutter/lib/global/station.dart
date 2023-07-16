class Station {
  final String name;

  const Station({
    required this.name,
  });

  factory Station.fromJson(dynamic json){
    return Station(
      name: json['name'],
    );
  }


}