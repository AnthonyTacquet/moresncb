import 'package:flutter/material.dart';
import 'package:moresncb_flutter/data/main_data.dart';
import 'package:moresncb_flutter/global/station.dart';
import 'package:moresncb_flutter/responsive/home/responsive_layout.dart';
import 'package:moresncb_flutter/values/colors.dart';

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});
  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  Future<List<Station>> stations = MainData.getStations();

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      backgroundColor: black,

      body: ResponsiveLayout(stations),
    );
  }
}
