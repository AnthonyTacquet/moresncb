import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View, Button, Image, Dimensions, TextInput, FlatList } from 'react-native';
import React, { useEffect, useState } from 'react';
import NMBSData from '../classes/data/NMBSData';

export default function SearchPage({ navigation }) {

  const [text, setText] = useState('');
  const [data, setData] = useState([]);
  const [array, setArray] = useState([]);
  const nmbsData = new NMBSData(); // Create an instance of NMBSData class
  useEffect(() => {
    // Perform any initialization here
    getStations();
  }, []);
  
  useEffect(() => {
    if (array.length > 0) {
      const filteredData = array.filter((item) =>
        item.name.toLowerCase().includes(text.toLowerCase())
      );

      if (filteredData.length > 2)
        setData(filteredData);
    } else {
        console.log(array[0]);
        getStations();
    }
  }, [array, text]);
  
  async function getStations() {
    try {
      const response = await nmbsData.getStations(); // wait for the request to finish
      setArray(response);
    } catch (error) {
      console.log(error.toString());
      // setResult(error.toString())
    }
  }

  const handleTextChange = (inputText) => {
    setText(inputText);
  };

  return (
    <View>
      <TextInput
        placeholder="Search"
        onChangeText={handleTextChange}
        />

        <FlatList
            data={data}
            renderItem={({ item }) => <Text>{item.name}</Text>}
            keyExtractor={(item) => item.name}
        />
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