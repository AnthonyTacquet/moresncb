import 'package:flutter/material.dart';
import 'package:moresncb_flutter/values/colors.dart';
import 'bottom_navigator.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {

    return MaterialApp(
      title: 'MoreSNCB',
      theme: ThemeData(

        colorScheme: ColorScheme.fromSeed(seedColor: lightblue),
        useMaterial3: true,
      ),
      //home: const MyHomePage(title: 'Home Page'),
      home: BottomNavigationBarExample(),
    );
  }
}
