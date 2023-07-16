import 'package:flutter/material.dart';
import 'package:moresncb_flutter/values/colors.dart';

class MobileBody extends StatelessWidget {
  const MobileBody({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: black,
      body: Column(
          children: [
            // Search
            Container(
              color: darkGrey,
              padding: const EdgeInsets.only(bottom: 20),

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


                ],)
  
                
              ),
            

            
          ],
        ),
      );
    
  }
}