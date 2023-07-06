import * as React from 'react';
import { StyleSheet, Text, View, Image, Dimensions, TextInput, TouchableOpacity } from 'react-native';
import Ionicons from 'react-native-vector-icons/Ionicons';
import {NavigationContainer} from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';

// Screens
import HomePage from './HomePage';
import RoutePage from './RoutePage';
import SearchPage from './SearchPage';

const Stack = createStackNavigator();

export default function MainContainer() {
  
    return (
      <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen
          name="Home"
          component={HomePage}
          options={{title: 'Welcome'}}
        />
        <Stack.Screen
          name="Route"
          component={RoutePage}
          options={{title: 'Welcome'}}
        />
      </Stack.Navigator>
    </NavigationContainer>
    );
  }
  
  const styles = StyleSheet.create({
    container: {
      flex: 1,
      backgroundColor: '#202122',
    },
    tabContainer: {
      flexDirection: 'row',
      justifyContent: 'space-around',
      alignItems: 'center',
      height: 60,
      backgroundColor: '#202122',
      borderTopWidth: 1,
      borderTopColor: '#202122',
    },
    tabButton: {
      flex: 1,
      justifyContent: 'center',
      alignItems: 'center',
      height: '100%',
    },
    activeTabButton: {
      borderBottomWidth: 2,
      borderBottomColor: '#0065ae',
    },
    tabText: {
      fontSize: 12,
      marginTop: 2,
      color: '#808080',
    },
    contentContainer: {
      flex: 1,
    },
  });