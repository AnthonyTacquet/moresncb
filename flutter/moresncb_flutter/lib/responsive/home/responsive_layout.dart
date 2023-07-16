import 'package:flutter/material.dart';
import 'package:moresncb_flutter/global/station.dart';
import 'package:moresncb_flutter/responsive/home/desktop_body.dart';
import 'package:moresncb_flutter/responsive/home/mobile_body.dart';
import 'package:moresncb_flutter/values/dimensions.dart';

class ResponsiveLayout extends StatelessWidget {
  final Future<List<Station>> stations;

  ResponsiveLayout(this.stations);

  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(
      builder: (context, constraints) {
        if (constraints.maxWidth < mobileWidth) {
          return const MobileBody(

          );
        } else {
          return DesktopBody(
            stations: stations,
          );
        }
      },
    );
  }
}