import 'dart:convert';

import 'package:moresncb_flutter/global/station.dart';
import 'package:http/http.dart' as http;

class MainData {


  static Future<List<Station>> getStations() async {
    List<Station> station = List.empty(growable: true);
    final response = await http.get(Uri.parse('https://api.irail.be/stations/?format=json&lang=en'));

    if (response.statusCode == 200) {
      final data = json.decode(response.body);

      List<dynamic> list = data["station"];
      
      for (int i = 0; i < list.length; i++){
        station.add(Station.fromJson(list[i]));
      }
    

    } else {
      throw Exception('Failed to load stations');
    }

    return station;
  }
}