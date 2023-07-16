import 'package:flutter/material.dart';
import 'package:moresncb_flutter/global/station.dart';
import 'package:moresncb_flutter/values/colors.dart';

class DesktopBody extends StatefulWidget {
  final Future<List<Station>> stations;

  DesktopBody({
    required this.stations
  });

  @override
  State<DesktopBody> createState() => _DesktopBody();
}

class _DesktopBody extends State<DesktopBody> {
  late Future<List<Station>> _stations;
  List<Station> displayList = List.empty();

  @override
  void initState() {
    super.initState();
    _stations = widget.stations;

    _stations.then((value) {
      setState(() {
          displayList = value;
      });
    });

  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      backgroundColor: black,
      body: Row(
          children: [
            // First column
            Container(
              color: darkGrey,
              padding: const EdgeInsets.only(bottom: 20),

                child: AspectRatio(
                  aspectRatio: 10/15,
                  child: Column(
                  children: [
                
                  Container(
                    color: lightblue,
                    child: const Image(
                        image: AssetImage('resources/images/home_dark.png'),
                      ),
                  ),

                  Container(
                    padding: const EdgeInsets.all(10),
                    alignment: Alignment.topLeft,
                    child: const Text(
                      "Where would you like to go?",
                      style: TextStyle(
                        color: white,
                        fontSize: 20,
                        fontFamily: 'RobotoMono',
                        fontWeight: FontWeight.w300,
                      ),
                    ),
                  ),

                  Container(
                    padding: const EdgeInsets.only(left: 10),
                    alignment: Alignment.topLeft,
                    child: const Text(
                      "From: ",
                      style: TextStyle(
                        color: white,
                        fontSize: 15,
                        fontFamily: 'RobotoMono',
                        fontWeight: FontWeight.w100,
                      ),
                    ),
                  ),

                  Container(
                    padding: const EdgeInsets.only(left: 10, right: 10),

                    child: const Divider(
                      color: white,
                    ),
                  ),

                  Container(
                    padding: const EdgeInsets.only(left: 10),
                    alignment: Alignment.topLeft,
                    child: const Text(
                      "To: ",
                      style: TextStyle(
                        color: white,
                        fontSize: 15,
                        fontFamily: 'RobotoMono',
                        fontWeight: FontWeight.w100,
                      ),
                    ),
                  )


                ],
                ),
                ),
  
                
              ),

            // second column
            Expanded(
              child: ListView.builder(
                itemCount: displayList.length,
                itemBuilder: (context, index) {
                  return Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Container(
                      color: darkGrey,
                      height: 120,
                      child: Center(
                        child: Text(
                          displayList[index].name,
                          style: const TextStyle(
                            color: Colors.white,
                            fontSize: 16,
                          ),
                        ),
                    ),
                    )
                  );
                },
              ),
            )
          ],
        ),
    );
  }
}