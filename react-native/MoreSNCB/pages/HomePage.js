import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View, Image, Dimensions, TextInput } from 'react-native';
import React from 'react';

const screenWidth = Dimensions.get('window').width;

export default function HomePage({ navigation }) {
    return (
        <View style={styles.container}>
      <View style={styles.imageContainer}>
        <Image source={require('../assets/home_background_dark.png')} style={styles.background} />
        <Image source={require('../assets/home_foreground_dark.png')} style={styles.foreground} />
      </View>

      <View style={styles.textContainer}>
        <View style={styles.marginContainer}>
          <Text style={styles.textHeader}>Where would you like to go?</Text>

          <View style={styles.inputRow}>
            <View style={styles.inputColumnOne}>
              <Text style={styles.inputLabel}>From:</Text>
              <View style={styles.line} />
              <Text style={styles.inputLabel}>To:</Text>
            </View>

            <View style={styles.inputColumnTwo}>
              <View style={styles.imageWrapper}>
                <Image source={require('../assets/shuffle-solid.png')} style={styles.shuffleIcon} />
              </View>
            </View>
          </View>
        </View>

      </View>


      <View style={styles.toolsContainer}>
          <View style={styles.marginContainer}>
          <View style={styles.inputRow}>
              <View style={styles.inputColumnOne}>
                <Text style={styles.textDefault}>Departure: today</Text>
              </View>

              <View style={styles.inputColumnTwo}>
                <View style={styles.imageWrapper}>
                  <Image source={require('../assets/sliders-solid.png')} style={styles.settingsIcon} />
                </View>
              </View>
            </View>
          </View>
       </View>
      


      <StatusBar style="auto" />
    </View>
    );
};

const styles = StyleSheet.create({
    container: {
      flex: 1,
      backgroundColor: '#000',
    },
    imageContainer: {
      backgroundColor: '#0065ae', // Color lightblue
      marginTop: 40,
      height: 200,
      width: screenWidth,
      position: 'relative',
    },
    background: {
      position: 'absolute',
      top: 25,
      left: -10,
      width: screenWidth,
      height: screenWidth / 4.5,
    },
    foreground: {
      position: 'absolute',
      top: 0,
      left: 0,
      width: screenWidth,
      height: screenWidth / 2.5,
      resizeMode: 'cover',
    },
    textContainer: {
      position: 'absolute',
      top: screenWidth / 2.5 + 40,
      left: 0,
      backgroundColor: '#202122', // Color darkgrey
      width: '100%',
    },
    toolsContainer: {
      position: 'absolute',
      top: screenWidth / 2.5 + 170,
      left: 0,
      backgroundColor: '#000', // Color darkgrey
      width: '100%',
      },
    marginContainer: {
      marginHorizontal: 20,
      marginTop: 10,
      marginBottom: 20,
    },
    textDefault: {
      color: '#fff',
    },
    textHeader: {
      color: '#fff',
      fontSize: 18,
    },
    inputRow: {
      flexDirection: 'row',
      justifyContent: 'space-between',
      marginTop: 15,
      width: '100%',
    },
    inputColumnOne: {
      alignItems: 'flex-start',
      marginEnd: 10,
      flex: 1
    },
    inputColumnTwo: {
      alignItems: 'flex-start',
    },
    inputLabel: {
      color: '#808080',
    },
    line: {
      borderBottomColor: '#808080',
      borderBottomWidth: 1,
      marginVertical: 15,
      width: '100%',
    },
    imageWrapper: {
      alignItems: 'center',
      marginTop: 'auto',
      marginTop: 15,
    },
    shuffleIcon: {
      width: 24,
      height: 24,
      resizeMode: 'contain',
      tintColor: '#808080'
    },
    settingsIcon: {
      width: 24,
      height: 24,
      resizeMode: 'contain',
      tintColor: '#0065ae'
    },
  });