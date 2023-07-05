import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View, Button, Image, Dimensions, TextInput } from 'react-native';
import React, { useState } from 'react';
import NMBSData from '../classes/data/NMBSData';

export default function RoutePage({ navigation }) {

  const [result, setResult] = useState('');
  const data = new NMBSData(); // Create an instance of NMBSData class

  async function getStations(){
    try{
      const array = await data.getStations(); // wait for the request to finish
      let string = "";

      for (let i = 0; i < array.length; i++){
        string += array[i].name + "\n";
      }

      setResult(string);
    } catch (error) {
      setResult(error.toString())
    }

  }

  return (
    <View>
      <Text>This is the new page</Text>
      <Button title="Run Function" onPress={getStations} />

      <Text style={styles.resultText}>{result}</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  resultText: {
    marginTop: 20,
    fontSize: 18,
  },
});



